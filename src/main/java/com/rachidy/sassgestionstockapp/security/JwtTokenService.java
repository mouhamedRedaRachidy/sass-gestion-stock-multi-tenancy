package com.rachidy.sassgestionstockapp.security;

import com.rachidy.sassgestionstockapp.entities.User;
import com.rachidy.sassgestionstockapp.exceptions.UnauthorizedException;
import com.rachidy.sassgestionstockapp.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SecurityException;
import jakarta.annotation.Nonnull;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtTokenService {
    private final JwtProperties jwtProperties;
    private PrivateKey privateKey;
    private PublicKey publicKey;

    @PostConstruct
    public void init() throws Exception{
       try{
           this.privateKey=loadPrivateKey(jwtProperties.getPrivateKeyPath());
           this.publicKey=loadPublicKey(jwtProperties.getPublicKeyPath());
           log.info("Load private & public keys SuccessFully !");
       }catch (final Exception e){
           log.error("Error loading keys", e);
           throw new IllegalStateException("Failed to load JWT keys", e);
       }

    }

    public String generateAccessToken(
            @Nonnull
            final String userId,
            @Nonnull
            final String tenantId,
            final String role
    ){
        Date now=new Date();
        Date experationDate= new Date(now.getTime() + this.jwtProperties.getExpirationDate());

        return Jwts.builder()
                .subject(userId)
                .claim("tenant_id",tenantId)
                .claim("role",role)
                .issuedAt(now)
                .expiration(experationDate)
                .issuer("sass-gestion-stock")
                .signWith(this.privateKey)
                .compact();
    }

    public String getUserFromToken(final String token){
        Claims claims=getClaimsFromToken(token);
        return claims.getSubject();
    }

    public String getRoleFromToken(final String token){
        Claims claims=getClaimsFromToken(token);
        return claims.get("role",String.class);
    }

    public String getTenantIdFromToken(final String token){
        Claims claims=getClaimsFromToken(token);
        return claims.get("tenant_id",String.class);
    }

    public boolean validateToken(final String token){
        try{
            Jwts.parser()
                    .verifyWith(this.publicKey)
                    .build()
                    .parseSignedClaims(token);
            return true;
        }catch (ExpiredJwtException e){
            throw new UnauthorizedException("Token has expired");
        }catch (UnsupportedOperationException e){
            throw new UnauthorizedException("Token is not signed");
        }catch(MalformedJwtException e){
            throw new UnauthorizedException("Jwt mal formed");
        }catch (SecurityException e){
            throw new UnauthorizedException("Ivalid jwt signateur");
        }catch (IllegalArgumentException e){
            throw new UnauthorizedException("JWT claim is empty");
        }
        //Token reçu
        //   → Syntaxe OK ? (MalformedJwtException)
        //   → Non signé/Algo inconnu ? (UnsupportedJwtException)
        //   → Signature cryptographique valide ? (SecurityException)
        //   → Claims temporels valides ? (ExpiredJwtException)
        //   → Entrée non vide ? (IllegalArgumentException)
        //   → TOKEN AUTHENTIQUE & INTÈGRE

    }

    private Claims getClaimsFromToken(final String token) {
        return Jwts.parser()
                .verifyWith(this.publicKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private PrivateKey loadPrivateKey(final String privateKeyPath)throws Exception {
        try(final InputStream is=JwtTokenService.class.getClassLoader().getResourceAsStream(privateKeyPath)){

            if(is == null){
                log.error("private key path not found");
                throw new RuntimeException("private key not found");
            }

            final byte[] dataBytes=is.readAllBytes();
            final String data=new String(dataBytes, StandardCharsets.UTF_8);

            final String privateKeyPEM=data
                    .replace("-----BEGIN PRIVATE KEY-----","")
                    .replace("-----END PRIVATE KEY-----","")
                    .replaceAll("\\s","");

            final byte[] decodedKey= Base64.getDecoder().decode(privateKeyPEM);
            final PKCS8EncodedKeySpec keySpec=new PKCS8EncodedKeySpec(decodedKey);
            return KeyFactory.getInstance("RSA").generatePrivate(keySpec);

        }
    }

    private PublicKey loadPublicKey(final String publicKeyPath) throws Exception {
        try(final InputStream is=getClass().getClassLoader().getResourceAsStream(publicKeyPath)){

            if(is == null){
                throw new RuntimeException("Not found public key path");
            }

            final byte[] dataBytes=is.readAllBytes();
            final String data=new String(dataBytes);

            final String publicKeyPEM= data
                    .replace("-----BEGIN PUBLIC KEY-----","")
                    .replace("-----END PUBLIC KEY-----","")
                    .replaceAll("\\s","");

            byte[] decodeKey=Base64.getDecoder().decode(publicKeyPEM);
            X509EncodedKeySpec keySpec=new X509EncodedKeySpec(decodeKey);
            return KeyFactory.getInstance("RSA").generatePublic(keySpec);
        }
    }


}
