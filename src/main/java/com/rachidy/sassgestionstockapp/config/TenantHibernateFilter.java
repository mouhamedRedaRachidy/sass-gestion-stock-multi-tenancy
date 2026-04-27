package com.rachidy.sassgestionstockapp.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TenantHibernateFilter {    

    @PersistenceContext
    private EntityManager entityManager;

    @Before("execution(* com.rachidy.sassgestionstockapp.repositories.*.*(..) )")
    public void activateTenantFilter(){
       final String tenantId=TenantContext.getCurrentTenant();

       if (tenantId != null && !tenantId.isBlank()){
           Session session=entityManager.unwrap(Session.class);
           session.enableFilter("tenantFilter")
                   .setParameter("tenantId",tenantId);
       }
    }

}
