package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Cliente;
import com.tallerwebi.dominio.Plan;
import com.tallerwebi.dominio.ServicioCliente;
import com.tallerwebi.dominio.ServicioPlan;
import com.tallerwebi.dominio.excepcion.PlanAsociadoException;
import com.tallerwebi.dominio.excepcion.PlanNoExisteException;
import com.tallerwebi.presentacion.dto.ClienteDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.*;

public class ControladorSuscripcionTest {

    private ControladorSuscripcion controladorSuscripcion;

    private ServicioCliente servicioCliente;
    private ServicioPlan servicioPlan;

    @BeforeEach
    public void init(){

        servicioCliente = mock(ServicioCliente.class);
        servicioPlan = mock(ServicioPlan.class);

        controladorSuscripcion = new ControladorSuscripcion(servicioPlan, servicioCliente);
    }

    @Test
    public void suscripcionTeLlevaALaVistaParaCrearUnaNuevaSuscripcion(){
        List<Plan> planes = List.of(new Plan());

        when(servicioPlan.obtenerTodos()).thenReturn(planes);

        ModelAndView mav = controladorSuscripcion.suscripcion();

        assertThat(mav.getViewName(), equalToIgnoringCase("suscribir"));
        assertThat(mav.getModel().get("planes"), equalTo(planes));
    }

    @Test
    public void alGuardarUnaSuscripcionYNoTieneElPlanAsociadoGuardeCorrectamente() throws PlanNoExisteException, PlanAsociadoException {
        ClienteDTO clienteDTO = new ClienteDTO("1234", 1L);
        Plan plan = new Plan(1L, "basico", 123);
        Cliente cliente = new Cliente(null, clienteDTO.getDni(), plan);

        when(servicioPlan.obtenerPorId(clienteDTO.getPlanId())).thenReturn(plan);
        doNothing().when(servicioCliente).guardar(cliente);


        ModelAndView mav = controladorSuscripcion.guardar(clienteDTO);

        assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/historial"));
    }

    @Test
    public void alGuardarUnaSuscripcionYElPlanNoExisteTiraExcepcion() throws PlanNoExisteException {
        ClienteDTO clienteDTO = new ClienteDTO("1234", 1L);
        List<Plan> planes = List.of(new Plan());

        when(servicioPlan.obtenerPorId(1L)).thenThrow(new PlanNoExisteException());
        when(servicioPlan.obtenerTodos()).thenReturn(planes);

        ModelAndView mav = controladorSuscripcion.guardar(clienteDTO);

        assertThat(mav.getViewName(), equalToIgnoringCase("suscribir"));
        assertThat(mav.getModel().get("error").toString(), equalToIgnoringCase("El plan ingresado no existe"));
        assertThat(mav.getModel().get("planes"), equalTo(planes));
    }

    @Test
    public void alGuardarUnaSuscripcionYTieneElPlanAsociadoTiraExcepcion() throws PlanNoExisteException, PlanAsociadoException {
        ClienteDTO clienteDTO = new ClienteDTO("1234", 1L);
        Plan plan = new Plan(1L, "basico", 123);
        List<Plan> planes = List.of(new Plan());
        Cliente cliente = new Cliente(null, clienteDTO.getDni(), plan);

        when(servicioPlan.obtenerPorId(clienteDTO.getPlanId())).thenReturn(plan);
        doThrow(new PlanAsociadoException()).when(servicioCliente).guardar(cliente);
        when(servicioPlan.obtenerTodos()).thenReturn(planes);

        ModelAndView mav = controladorSuscripcion.guardar(clienteDTO);

        assertThat(mav.getViewName(), equalToIgnoringCase("suscribir"));
        assertThat(mav.getModel().get("error").toString(), equalToIgnoringCase("El cliente ya tiene el plan asociado"));
        assertThat(mav.getModel().get("planes"), equalTo(planes));
    }

}
