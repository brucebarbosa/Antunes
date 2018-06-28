package com.brucedesenvolve.antunes.data

/**
 * Contrato com as constantes usadas no banco de dados.
 */
object OsContract {
    object OsTable{
        const val TABLE_NAME = "OS"
        const val ID = "_id"
        const val ENTRADA = "entrada"
        const val ORCAMENTO_PRONTO = "orcamento_pronto"
        const val ORCAMENTO_APROVADO = "orcamento_aprovado"
        const val SERVICO_PRONTO = "servico_pronto"
        const val NOME = "nome"
        const val ENDERECO = "endereco"
        const val TELS = "tels"
        const val APARELHO = "aparelho"
        const val DEFEITO_RECLAMADO = "defeito_reclamado"
        const val DEFEITO_CONSTATADO = "defeito_constatado"
        const val OBS = "obs"
        const val PRECO = "preco"
        const val SINAL = "sinal"
        const val PAGO = "pago"
    }
}