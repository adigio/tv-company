package com.tallerwebi.presentacion.dto;

public class ClienteDTO {

    private String dni;
    private Long planId;

    public ClienteDTO() {}

    public ClienteDTO(String dni, Long planId) {
        this.dni = dni;
        this.planId = planId;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }
}
