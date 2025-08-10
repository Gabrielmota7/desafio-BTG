package com.desafio.btg.pedidos.controller

import com.desafio.btg.pedidos.constants.MensagensErro
import com.desafio.btg.pedidos.exceptions.PedidoNaoEncontradoException
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import java.util.UUID

@ControllerAdvice
class RestExceptionHandler {

    data class ErrorResponse(
            val status: Int,
            val error: String,
            val message: String?,
            val details: Map<String, String?> = emptyMap()
    )

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidation(ex: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val fieldErrors = ex.bindingResult.fieldErrors
                .associate { it.field to (it.defaultMessage ?: "inválido") }

        val body = ErrorResponse(
                status = HttpStatus.BAD_REQUEST.value(),
                error = HttpStatus.BAD_REQUEST.reasonPhrase,
                message = MensagensErro.ERRO_VALIDACAO_CAMPOS,
                details = fieldErrors
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body)
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleTypeMismatch(ex: MethodArgumentTypeMismatchException): ResponseEntity<ErrorResponse> {
        val paramName = try { ex.name } catch (_: Exception) { ex.getName() }
        val required = try { ex.requiredType } catch (_: Exception) { ex.getRequiredType() }

        val isUuidId = (paramName == "id") && (required == UUID::class.java)
        val details = if (isUuidId)
            mapOf("id" to MensagensErro.ID_PEDIDO_INVALIDO)
        else
            mapOf(paramName to "tipo inválido")

        val body = ErrorResponse(
                status = HttpStatus.BAD_REQUEST.value(),
                error = HttpStatus.BAD_REQUEST.reasonPhrase,
                message = MensagensErro.ERRO_VALIDACAO_CAMPOS,
                details = details
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleNotReadable(ex: HttpMessageNotReadableException): ResponseEntity<ErrorResponse> {
        val cause = ex.cause
        val details = when (cause) {
            is MissingKotlinParameterException -> {
                val field = cause.path.lastOrNull()?.fieldName ?: "body"
                mapOf(field to MensagensErro.CAMPO_OBRIGATORIO_AUSENTE)
            }
            else -> mapOf("body" to MensagensErro.BODY_INVALIDO)
        }

        val body = ErrorResponse(
                status = HttpStatus.BAD_REQUEST.value(),
                error = HttpStatus.BAD_REQUEST.reasonPhrase,
                message = MensagensErro.ERRO_VALIDACAO_CAMPOS,
                details = details
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body)
    }

    @ExceptionHandler(PedidoNaoEncontradoException::class)
    fun handlePedidoNaoEncontrado(ex: PedidoNaoEncontradoException): ResponseEntity<ErrorResponse> {
        val body = ErrorResponse(
                status = HttpStatus.NOT_FOUND.value(),
                error = HttpStatus.NOT_FOUND.reasonPhrase,
                message = MensagensErro.RECURSO_NAO_ENCONTRADO
        )
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body)
    }
}
