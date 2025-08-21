package com.br.domain.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record SimulacaoRequestDTO(

        @NotNull(message = "Valor desejado é obrigatório")
        @DecimalMin(value = "200.00", message = "Valor mínimo é R$ 200,00")
        @Digits(integer = 10, fraction = 2, message = "Valor deve ter no máximo 10 dígitos inteiros e 2 decimais")
        BigDecimal valorDesejado,


        @NotNull(message = "Prazo é obrigatório")
        @Min(value = 1, message = "Prazo mínimo é 1 mês")
        @Max(value = 240, message = "Prazo máximo é 240 meses")
        Integer prazo) {
}
