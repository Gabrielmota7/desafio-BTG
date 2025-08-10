package com.desafio.btg.pedidos.service

import com.desafio.btg.pedidos.messaging.PedidoProducer
import com.desafio.btg.pedidos.model.NovoPedidoDTO
import com.desafio.btg.pedidos.model.Pedido
import com.desafio.btg.pedidos.repository.PedidoRepository
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*

class PedidoServiceTest {

    private val repo = mock<PedidoRepository>()
    private val producer = mock<PedidoProducer>()
    private val service = PedidoService(repo, producer)

    @Test
    fun `criarPedido deve salvar e publicar id na fila`() {
        whenever(repo.salvar(any())).thenAnswer {}
        val dto = NovoPedidoDTO("cli", listOf("a"))

        val pedido: Pedido = service.criarPedido(dto)

        verify(repo, times(1)).salvar(any())
        verify(producer, times(1)).enviarPedidoParaFila(pedido.id)
    }

    @Test
    fun `marcarComoProcessado deve delegar ao repo`() {
        val id = java.util.UUID.randomUUID()
        service.marcarProcessado(id)
        verify(repo, times(1)).atualizarStatus(id)
    }
}
