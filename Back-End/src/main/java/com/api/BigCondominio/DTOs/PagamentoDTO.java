package com.api.BigCondominio.DTOs;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PagamentoDTO(
        Long id,

        @NotNull(message = "ID do morador é obrigatório")
        Long moradorId,

        @NotNull(message = "Valor do pagamento é obrigatório")
        @DecimalMin(value = "0.01", message = "Valor deve ser maior que zero")
        BigDecimal valor,

        @PastOrPresent(message = "Data de pagamento não pode ser futura")
        LocalDate dataPagamento,

        @NotBlank(message = "Tipo de pagamento é obrigatório")
        @Pattern(regexp = "^(multa|condominio|outros)$", message = "Tipo deve ser 'multa', 'condominio' ou 'outros'")
        String tipo,

        @NotBlank(message = "Status é obrigatório")
        @Pattern(regexp = "^(pago|pendente|cancelado|automatico)$", message = "Status deve ser 'pago', 'pendente', 'automatico' ou 'cancelado'")
        String status,

        @Pattern(regexp = "^(boleto|pix|cartao|dinheiro)?$", message = "Forma de pagamento deve ser 'boleto', 'pix', 'cartao', 'automatica' ou 'dinheiro'")
        String formaPagamento
) {}
