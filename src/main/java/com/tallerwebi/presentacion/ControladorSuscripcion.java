package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Cliente;
import com.tallerwebi.dominio.Plan;
import com.tallerwebi.dominio.ServicioCliente;
import com.tallerwebi.dominio.ServicioPlan;
import com.tallerwebi.dominio.excepcion.PlanAsociadoException;
import com.tallerwebi.dominio.excepcion.PlanNoExisteException;
import com.tallerwebi.presentacion.dto.ClienteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller()
public class ControladorSuscripcion {

    private ServicioPlan servicioPlan;
    private ServicioCliente servicioCliente;

    @Autowired
    public ControladorSuscripcion(ServicioPlan servicioPlan, ServicioCliente servicioCliente) {
        this.servicioPlan = servicioPlan;
        this.servicioCliente = servicioCliente;
    }

    @RequestMapping(value = "/nueva", method = RequestMethod.GET)
    public ModelAndView suscripcion() {
        ModelMap model = new ModelMap();

        model.put("cliente", new ClienteDTO());
        model.put("planes", servicioPlan.obtenerTodos());

        return new ModelAndView("suscribir", model);
    }

    @RequestMapping(value = "/historial", method = RequestMethod.GET)
    public ModelAndView historial() {
        ModelMap model = new ModelMap();

        model.put("clientes", servicioCliente.obtenerTodos());

        return new ModelAndView("historial", model);
    }

    @RequestMapping(value = "/guardar", method = RequestMethod.POST)
    public ModelAndView guardar(@ModelAttribute("cliente") ClienteDTO clienteDTO) {
        ModelMap model = new ModelMap();

        try {
            Plan plan = servicioPlan.obtenerPorId(clienteDTO.getPlanId());

            Cliente nuevaSuscripcion = new Cliente(null, clienteDTO.getDni(), plan);

            servicioCliente.guardar(nuevaSuscripcion);

            return new ModelAndView("redirect:/historial", model);

        } catch (PlanNoExisteException e) {
            model.put("error", "El plan ingresado no existe");
        } catch (PlanAsociadoException e) {
            model.put("error", "El cliente ya tiene el plan asociado");
        }

        model.put("cliente", clienteDTO);
        model.put("planes", servicioPlan.obtenerTodos());

        return new ModelAndView("suscribir", model);
    }
}
