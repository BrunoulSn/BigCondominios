package com.api.BlogAppApi.DTOs;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PagamentoDTO(
        Long id,

        @NotNull(message = "ID do morador é obrigatório")
        Long moradorId,

        @NotNull(message = "Valor do pagamento é obrigatório")
        @DecimalMin(value = "0.01", message = "Valor deve ser maior que zero")
        BigDecimal valor,

        @PastOrPresent(message = "Data de pagamento não pode ser futura")
        LocalDateTime dataPagamento,

        @NotBlank(message = "Tipo de pagamento é obrigatório")
        @Pattern(regexp = "^(multa|mensalidade|outro)$", message = "Tipo deve ser 'multa', 'mensalidade' ou 'outro'")
        String tipo,

        @NotBlank(message = "Status é obrigatório")
        @Pattern(regexp = "^(pago|pendente|cancelado)$", message = "Status deve ser 'pago', 'pendente' ou 'cancelado'")
        String status,

        @Pattern(regexp = "^(boleto|pix|cartao|dinheiro)?$", message = "Forma de pagamento deve ser 'boleto', 'pix', 'cartao' ou 'dinheiro'")
        String formaPagamento
) {}
