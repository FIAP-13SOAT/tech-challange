package com.fiapchallenge.garage.domain.servicetype;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Schema(name = "ServiceTypeRequest", description = "Dados para criação de um tipo de Serviço")
public record ServiceTypeRequestDTO(
        @NotNull(message = "Necessário informar a descricao do Serviço") String description,
        @NotNull(message = "Necessário informar o valor do Serviço") BigDecimal value
) {
}
