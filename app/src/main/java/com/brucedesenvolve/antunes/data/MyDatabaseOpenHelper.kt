package com.brucedesenvolve.antunes.data

import android.content.Context
import com.brucedesenvolve.antunes.data.OsContract.OsTable
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

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

    override fun onCreate(db: SQLiteDatabase) {
        // Here you create tables
        db.createTable(OsTable.TABLE_NAME, true,
                OsTable._ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT + UNIQUE + NOT_NULL,
                OsTable.ENTRADA to TEXT,
                OsTable.ORCAMENTO_PRONTO to TEXT,
                OsTable.ORCAMENTO_APROVADO to TEXT,
                OsTable.SERVICO_PRONTO to TEXT,
                OsTable.NOME to TEXT,
                OsTable.ENDERECO to TEXT,
                OsTable.TELS to TEXT,
                OsTable.APARELHO to TEXT,
                OsTable.DEFEITO_RECLAMADO to TEXT,
                OsTable.DEFEITO_CONSTATADO to TEXT,
                OsTable.OBS to TEXT,
                OsTable.PRECO to TEXT,
                OsTable.SINAL to TEXT,
                OsTable.PAGO to INTEGER + DEFAULT("0"))
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        db.dropTable(OsTable.TABLE_NAME, true)
    }
}

// Access property for Context
val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)