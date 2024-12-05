package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Plan;
import com.tallerwebi.dominio.RepositorioPlan;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("repositorioPlan")
public class RepositorioPlanImpl implements RepositorioPlan {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioPlanImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Plan get(Long id) {
        return sessionFactory.getCurrentSession().get(Plan.class, id);
    }

    @Override
    public List<Plan> getAll() {
        return sessionFactory.getCurrentSession()
                .createCriteria(Plan.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
    }
}
