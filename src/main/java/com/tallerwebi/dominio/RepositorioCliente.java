package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioCliente {
    void save(Cliente cliente);
    Cliente get(Long id);
    Cliente getByDni(String dni);
    List<Cliente> getAll();

}
