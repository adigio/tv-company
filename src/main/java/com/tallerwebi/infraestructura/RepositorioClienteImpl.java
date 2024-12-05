package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Cliente;
import com.tallerwebi.dominio.Plan;
import com.tallerwebi.dominio.RepositorioCliente;
import com.tallerwebi.dominio.Usuario;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("repositorioCliente")
public class RepositorioClienteImpl implements RepositorioCliente {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioClienteImpl(SessionFactory sessionFactory) { this.sessionFactory = sessionFactory; }

    @Override
    public void save(Cliente cliente) {
        sessionFactory.getCurrentSession().save(cliente);
    }

    @Override
    public Cliente get(Long id) {
        return sessionFactory.getCurrentSession().get(Cliente.class, id);
    }

    @Override
    public Cliente getByDni(String dni) {
        return (Cliente) sessionFactory.getCurrentSession().createCriteria(Cliente.class)
                .add(Restrictions.eq("dni", dni))
                .uniqueResult();
    }

    @Override
    public List<Cliente> getAll() {
        return sessionFactory.getCurrentSession()
                .createCriteria(Cliente.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
    }
}
