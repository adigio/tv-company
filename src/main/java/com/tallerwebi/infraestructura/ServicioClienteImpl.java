package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Cliente;
import com.tallerwebi.dominio.RepositorioCliente;
import com.tallerwebi.dominio.ServicioCliente;
import com.tallerwebi.dominio.excepcion.ClienteNoExisteException;
import com.tallerwebi.dominio.excepcion.PlanAsociadoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("servicioCliente")
@Transactional
public class ServicioClienteImpl implements ServicioCliente {

    private RepositorioCliente repositorioCliente;

    @Autowired
    public ServicioClienteImpl(RepositorioCliente repositorioCliente) {
        this.repositorioCliente = repositorioCliente;
    }

    @Override
    public void guardar(Cliente cliente) throws PlanAsociadoException {

        Cliente clienteExistente = repositorioCliente.getByDni(cliente.getDni());

        if (clienteExistente != null) {
            if(clienteExistente.getPlan().getId().equals(cliente.getPlan().getId())) {
                throw new PlanAsociadoException();
            }

            clienteExistente.setPlan(cliente.getPlan());

            repositorioCliente.save(clienteExistente);
        } else {
            repositorioCliente.save(cliente);
        }
    }

    @Override
    public Cliente obtenerPorId(Long id) throws ClienteNoExisteException {

        Cliente cliente = repositorioCliente.get(id);

        if(cliente == null) {
            throw new ClienteNoExisteException();
        }

        return cliente;
    }

    @Override
    public Cliente obtenerPorDni(String dni) {
        return repositorioCliente.getByDni(dni);
    }

    @Override
    public List<Cliente> obtenerTodos() {
        return repositorioCliente.getAll();
    }
}
