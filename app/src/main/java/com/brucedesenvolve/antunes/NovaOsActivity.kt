package com.brucedesenvolve.antunes

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.brucedesenvolve.antunes.data.OsContract.OsTable
import com.brucedesenvolve.antunes.data.database
import kotlinx.android.synthetic.main.activity_nova_os.*
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.*

class NovaOsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nova_os)

        /*De forma simples, essa função pega os dados do formulario e cria uma nova entrada no banco de dados*/
        bt_salvar_os.setOnClickListener {
            /*Este código pega a data atual formatada*/
            val entrada = SimpleDateFormat("dd/MM/yy", Locale.getDefault()).format(Date())
            val nome = et_nome.text.toString()
            if (nome == "") {
                toast("Digite o nome do cliente")
                return@setOnClickListener
            }
            val endereco = et_endereco.text.toString()
            val tels = et_tels.text.toString()
            val aparelho = et_aparelho.text.toString()
            val defeitoReclamado = et_defeito_reclamado.text.toString()

            database.use {
                insert(OsTable.TABLE_NAME,
                        OsTable.ENTRADA to entrada,
                        OsTable.NOME to nome,
                        OsTable.ENDERECO to endereco,
                        OsTable.TELS to tels,
                        OsTable.APARELHO to aparelho,
                        OsTable.DEFEITO_RECLAMADO to defeitoReclamado,
                        OsTable.PRECO to "0",
                        OsTable.SINAL to "0")
            }
            finish()
        }
    }
}
