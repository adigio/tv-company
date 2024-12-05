package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.ClienteNoExisteException;
import com.tallerwebi.dominio.excepcion.PlanAsociadoException;
import com.tallerwebi.infraestructura.ServicioClienteImpl;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ServicioClienteTest {

    private ServicioCliente servicioCliente;

    private RepositorioCliente repositorioCliente;
    private RepositorioPlan repositorioPlan;

    @BeforeEach
    public void init() {
        repositorioCliente = mock(RepositorioCliente.class);

        servicioCliente = new ServicioClienteImpl(repositorioCliente);
    }

    @Test
    public void alGuardarUnClienteYExisteYElPlanEsDistintoEntoncesGuarda() throws PlanAsociadoException {
        String dni = "1234";
        Plan planA = new Plan(1L, "Premium", 321);
        Plan planB = new Plan(2L, "basico", 123);

        Cliente clienteNuevo = new Cliente(null, dni, planA);
        Cliente clienteExistente = new Cliente(1L, dni, planB);
        Cliente clienteActualizado = new Cliente(1L, dni, planA);

        when(repositorioCliente.getByDni(dni)).thenReturn(clienteExistente);

        servicioCliente.guardar(clienteNuevo);

        verify(repositorioCliente, times(1)).save(clienteActualizado);
    }

    @Test
    public void alGuardarUnClienteYNoExisteEntoncesGuarda() throws PlanAsociadoException {
        String dni = "1234";
        Plan planA = new Plan(1L, "Premium", 321);

        Cliente clienteNuevo = new Cliente(null, dni, planA);

        when(repositorioCliente.getByDni(dni)).thenReturn(null);

        servicioCliente.guardar(clienteNuevo);

        verify(repositorioCliente, times(1)).save(clienteNuevo);
    }

    @Test
    public void alGuardarUnClienteYExistePeroElPlanYaEstaAsociadoEntoncesTiraExcepcion() {
        String dni = "1234";
        Plan planA = new Plan(1L, "Premium", 321);

        Cliente clienteNuevo = new Cliente(null, dni, planA);
        Cliente clienteExistente = new Cliente(1L, dni, planA);

        when(repositorioCliente.getByDni(dni)).thenReturn(clienteExistente);

        assertThrows(PlanAsociadoException.class, () -> {
            servicioCliente.guardar(clienteNuevo);
        });
    }

    @Test
    public void obtieneUnClientePorIdYExisteEntoncesLoDevuelve() throws ClienteNoExisteException {
        Long id = 1L;
        Plan planA = new Plan(id, "Premium", 321);
        Cliente cliente = new Cliente(id, "1234", planA);

        when(repositorioCliente.get(id)).thenReturn(cliente);

        Cliente response = servicioCliente.obtenerPorId(id);

        assertThat(response, equalTo(cliente));
    }

    @Test
    public void obtieneUnClientePorIdYNoExisteEntoncesTiraExcepcion() {
        Long id = 1L;

        when(repositorioCliente.get(id)).thenReturn(null);

        assertThrows(ClienteNoExisteException.class, () -> {
            servicioCliente.obtenerPorId(id);
        });
    }

    @Test
    public void obtieneUnClientePorDni() {
        String dni = "1234";
        Plan planA = new Plan(1L, "Premium", 321);
        Cliente cliente = new Cliente(1L, dni, planA);

        when(repositorioCliente.getByDni(dni)).thenReturn(cliente);

        Cliente response = servicioCliente.obtenerPorDni(dni);

        assertThat(response, equalTo(cliente));
    }

    @Test
    public void obtieneTodosLosClientesExistentes() {
        Plan planA = new Plan(1L, "Premium", 321);
        Cliente cliente1 = new Cliente(1L, "1234", planA);
        Cliente cliente2 = new Cliente(2L, "5678", planA);

        List<Cliente> clientes = Arrays.asList(cliente1, cliente2);

        when(repositorioCliente.getAll()).thenReturn(clientes);

        List<Cliente> response = servicioCliente.obtenerTodos();

        assertThat(response, equalTo(clientes));
    }
}
