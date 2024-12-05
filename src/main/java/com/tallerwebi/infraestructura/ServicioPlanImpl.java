package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Plan;
import com.tallerwebi.dominio.RepositorioPlan;
import com.tallerwebi.dominio.ServicioPlan;
import com.tallerwebi.dominio.excepcion.PlanNoExisteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("servicioPlan")
@Transactional
public class ServicioPlanImpl implements ServicioPlan {

    private RepositorioPlan repositorioPlan;

    @Autowired
    public ServicioPlanImpl(RepositorioPlan repositorioPlan) {
        this.repositorioPlan = repositorioPlan;
    }

    @Override
    public Plan obtenerPorId(Long id) throws PlanNoExisteException {
        Plan plan = repositorioPlan.get(id);

        if(plan == null) {
            throw new PlanNoExisteException();
        }

        return plan;
    }

    @Override
    public List<Plan> obtenerTodos() {
        return repositorioPlan.getAll();
    }
}
