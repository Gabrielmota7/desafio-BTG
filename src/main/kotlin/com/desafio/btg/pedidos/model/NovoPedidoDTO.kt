package com.desafio.btg.pedidos.model

import com.desafio.btg.pedidos.constants.MensagensErro
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull


data class NovoPedidoDTO(
        @field:NotBlank(message = MensagensErro.CLIENTE_ID_OBRIGATORIO)
        val clienteId: String,

        @field:NotNull(message = MensagensErro.ITENS_NAO_PODE_SER_NULO)
        @field:NotEmpty(message = MensagensErro.ITENS_VAZIO)
        val itens: List< @NotBlank(message = MensagensErro.ITEM_VAZIO) String>
)
