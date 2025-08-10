package com.desafio.btg.pedidos.controller

import com.desafio.btg.pedidos.exceptions.PedidoNaoEncontradoException
import com.desafio.btg.pedidos.model.NovoPedidoDTO
import com.desafio.btg.pedidos.model.Pedido
import com.desafio.btg.pedidos.model.StatusPedido
import com.desafio.btg.pedidos.service.PedidoService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.util.*

class PedidoControllerTest {

    private val service = mock<PedidoService>()
    private val objectMapper = ObjectMapper()
    private val controller = PedidoController(service)
    private val mockMvc: MockMvc =
            MockMvcBuilders.standaloneSetup(controller)
                    .setControllerAdvice(RestExceptionHandler())
                    .build()

    @Test
    fun `POST deve validar itens vazio`() {
        val dto = mapOf("clienteId" to "João", "itens" to emptyList<String>())
        mockMvc.perform(
                post("/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
        )
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("$.message").value("Erro de validação nos campos"))
    }

    @Test
    fun `POST deve validar itens nulo`() {
        val dto = mapOf("clienteId" to "João", "itens" to null)
        mockMvc.perform(
                post("/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
        )
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("$.message").value("Erro de validação nos campos"))
    }

    @Test
    fun `GET deve retornar 404 quando nao encontrado`() {
        val id = UUID.randomUUID()
        whenever(service.buscarPedido(id)).thenThrow(PedidoNaoEncontradoException(id))

        mockMvc.perform(get("/pedidos/$id"))
                .andExpect(status().isNotFound)
                .andExpect(jsonPath("$.message").value("Recurso não encontrado"))
    }

    @Test
    fun `GET deve retornar 400 quando id nao UUID`() {
        mockMvc.perform(get("/pedidos/nao-e-uuid"))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("$.message").value("Erro de validação nos campos"))
    }

    @Test
    fun `POST deve criar pedido com sucesso`() {
        val id = UUID.randomUUID()
        val pedido = Pedido(id, "cli-1", listOf("a"), StatusPedido.PENDENTE)
        whenever(service.criarPedido(any())).doReturn(pedido)

        val dto = NovoPedidoDTO(clienteId = "cli-1", itens = listOf("a"))
        mockMvc.perform(
                post("/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
        )
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.status").value("PENDENTE"))
    }
}
