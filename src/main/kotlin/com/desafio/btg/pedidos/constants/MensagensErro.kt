package com.desafio.btg.pedidos.constants

object MensagensErro {

    const val CLIENT_ID_OBRIGATORIO = "clienteId é obrigatório"
    const val ITENS_NAO_PODE_SER_NULO = "itens não pode ser nulo"
    const val ITENS_VAZIO = "itens não pode ser vazio"
    const val ITEM_VAZIO = "item não pode ser vazio"

    const val ERRO_VALIDACAO_CAMPOS = "Erro de validação nos campos"
    const val CAMPO_OBRIGATORIO_AUSENTE = "campo obrigatório ausente"
    const val BODY_INVALIDO = "corpo da requisição inválido"
    const val ID_PEDIDO_INVALIDO = "O parâmetro 'id' deve ser um UUID válido"
    const val RECURSO_NAO_ENCONTRADO = "Recurso não encontrado"
}