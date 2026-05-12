package com.rachidy.sassgestionstockapp.security;

import com.rachidy.sassgestionstockapp.config.TenantContext;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthentificationFilter extends OncePerRequestFilter {
    private final JwtTokenService  jwtTokenService;

    @Override
    protected void doFilterInternal(
            @Nonnull
             HttpServletRequest request,
            @Nonnull
               HttpServletResponse response,
            @Nonnull
            FilterChain filterChain
    ) throws ServletException, IOException {

        if (request.getRequestURI().contains("/api/v1/login")){
            filterChain.doFilter(request,response);
            return;
        }

        try{
            final String jwt=getJwtTokenFromRequest(request);

            if (StringUtils.hasText(jwt) && jwtTokenService.validateToken(jwt) ){

                String userId=jwtTokenService.getUserFromToken(jwt);
                String tenantId=jwtTokenService.getTenantIdFromToken(jwt);
                String role=jwtTokenService.getRoleFromToken(jwt);

                if (tenantId != null){
                    TenantContext.setCurrentTenant(tenantId);
                    final String schemaName="not-yet-found";
                    TenantContext.setCurrentSchema(schemaName); // todo resolve schema name;
                }

                // Create authentification
                final SimpleGrantedAuthority authority=new SimpleGrantedAuthority(role);
                UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(
                        userId,
                        null,
                        Collections.singleton(authority)
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                filterChain.doFilter(request,response);
                TenantContext.clear();

                log.info("authenticated successfully");

            }
        }catch (Exception e){
            log.error("error authentification");
        }
    }

    private String getJwtTokenFromRequest(HttpServletRequest request){
        final String authorization=request.getHeader("Authorization");
        if(StringUtils.hasText(authorization) && authorization.startsWith("Bearer ")){
            return authorization.substring(7);
        }
        return null;
    }
}
