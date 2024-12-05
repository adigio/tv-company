package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.PlanNoExisteException;

import java.util.List;

public interface ServicioPlan {
    Plan obtenerPorId(Long id) throws PlanNoExisteException;
    List<Plan> obtenerTodos();
}
