package com.desafio.btg.pedidos.controller

import com.desafio.btg.pedidos.model.NovoPedidoDTO
import com.desafio.btg.pedidos.model.Pedido
import com.desafio.btg.pedidos.service.PedidoService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/pedidos")
class PedidoController(
        private val pedidoService: PedidoService
) {

    @PostMapping
    fun criarPedido(@RequestBody dto: NovoPedidoDTO): ResponseEntity<Pedido> {
        val pedido = pedidoService.criarPedido(dto)
        return ResponseEntity.ok(pedido)
    }

    @GetMapping("/{id}")
    fun buscarPedido(@PathVariable id: UUID ): ResponseEntity<String> {
        val pedido = pedidoService.buscarPedido(id)
        return if (pedido != null) {
            ResponseEntity.ok(pedido.status.name.lowercase())
        } else {
            ResponseEntity.notFound().build()
        }

    }

}