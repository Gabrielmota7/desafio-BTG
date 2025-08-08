package com.desafio.btg.pedidos

import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync


@SpringBootApplication
@EnableAsync
@EnableRabbit
class PedidosApplication

fun main(args: Array<String>) {
	runApplication<PedidosApplication>(*args)
}
