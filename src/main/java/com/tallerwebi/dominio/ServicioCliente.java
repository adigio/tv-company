package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.ClienteNoExisteException;
import com.tallerwebi.dominio.excepcion.PlanAsociadoException;

import java.util.List;

public interface ServicioCliente {
    void guardar(Cliente cliente) throws PlanAsociadoException;
    Cliente obtenerPorId(Long id) throws ClienteNoExisteException;
    Cliente obtenerPorDni(String dni);
    List<Cliente> obtenerTodos();
}
