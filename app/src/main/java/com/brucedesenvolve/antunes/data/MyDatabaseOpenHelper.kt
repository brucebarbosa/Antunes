package com.brucedesenvolve.antunes.data

import android.content.Context
import com.brucedesenvolve.antunes.data.OsContract.OsTable
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

/**
 * Este é o banco de dados do app. Está simplificado graças a biblioteca ANKO.
 */
class MyDatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "MyDatabase", null, 1) {
    companion object {
        private var instance: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseOpenHelper {
            if (instance == null) {
                instance = MyDatabaseOpenHelper(ctx.applicationContext)
            }
            return instance!!
        }
    }

    /**
     * Esta é a única função que é preciso implementar.
     * Aqui criamos a tabela que oraganiza o banco de dados em colunas.
     */
    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(OsTable.TABLE_NAME, true,
                OsTable.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT + UNIQUE + NOT_NULL,
                OsTable.ENTRADA to TEXT,
                OsTable.ORCAMENTO_PRONTO to TEXT,
                OsTable.ORCAMENTO_APROVADO to TEXT,
                OsTable.SERVICO_PRONTO to TEXT,
                OsTable.NOME to TEXT,
                OsTable.ENDERECO to TEXT,
                OsTable.TELS to TEXT,
                OsTable.APARELHO to TEXT,
                OsTable.DESCRICAO to TEXT,
                OsTable.DEFEITO_RECLAMADO to TEXT,
                OsTable.DEFEITO_CONSTATADO to TEXT,
                OsTable.OBS to TEXT,
                OsTable.PRECO to TEXT,
                OsTable.SINAL to TEXT,
                OsTable.PAGO to INTEGER + DEFAULT("0"))
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(OsTable.TABLE_NAME, true)
    }
}
/*Esta é a variável que é usada para acessar o banco de dados em qualquer atividade*/
val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)