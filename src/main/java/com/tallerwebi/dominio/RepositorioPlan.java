package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioPlan {
    Plan get(Long id);
    List<Plan> getAll();
}
