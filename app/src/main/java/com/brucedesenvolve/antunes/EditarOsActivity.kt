package com.brucedesenvolve.antunes

import android.content.ContentValues
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.brucedesenvolve.antunes.data.OsContract.OsTable
import com.brucedesenvolve.antunes.data.database
import kotlinx.android.synthetic.main.activity_editar_os.*
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast

class EditarOsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_os)

        val id = intent.getLongExtra("id", 0)

        et_preco.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                var preco = et_preco.text.toString().replace("R$ ", "")
                var sinal = et_sinal.text.toString().replace("R$ ", "")
                if (preco.equals("")) preco = "0"
                if (sinal.equals("")) sinal = "0"

                val aPagar = preco.toInt() - sinal.toInt()

                tv_a_pagar.text = "R$ $aPagar"
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        et_sinal.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                var preco = et_preco.text.toString().replace("R$ ", "")
                var sinal = et_sinal.text.toString().replace("R$ ", "")
                if (preco.equals("")) preco = "0"
                if (sinal.equals("")) sinal = "0"

                val aPagar = preco.toInt() - sinal.toInt()

                tv_a_pagar.text = "R$ $aPagar"
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        })

        preencher(id)

        bt_salvar.setOnClickListener { salvar(id) }

        bt_gerar_pdf.setOnClickListener {
            gerarPdf(id)
        }
    }

    fun preencher(id: Long) {
        database.use {
            select(OsTable.TABLE_NAME).whereArgs("${OsTable._ID} = $id").exec {
                if (moveToNext()) {
                    var col = 1
                    tv_data_entrada.text = getString(col++)
                    et_data_orcamento_pronto.setText(getString(col++))
                    et_data_orcamento_aprovado.setText(getString(col++))
                    et_data_servico_pronto.setText(getString(col++))
                    et_nome.setText(getString(col++))
                    et_endereco.setText(getString(col++))
                    et_tels.setText(getString(col++))
                    et_aparelho.setText(getString(col++))
                    et_defeito_reclamado.setText(getString(col++))
                    et_defeito_constatado.setText(getString(col++))
                    et_obs.setText(getString(col++))
                    et_preco.setText(getString(col++))
                    et_sinal.setText(getString(col++))
                    cb_pago.isChecked = getInt(col) != 0
                } else {
                    toast("Ocorreu um erro")
                    finish()
                }
            }
        }
    }

    fun salvar(id: Long) {
        val orcamentoPronto = et_data_orcamento_pronto.text.toString().replace("/", "")
        val orcamentoAprovado = et_data_orcamento_aprovado.text.toString().replace("/", "")
        val servicoPronto = et_data_servico_pronto.text.toString().replace("/", "")
        val nome = et_nome.text.toString()
        val endereco = et_endereco.text.toString()
        val tels = et_tels.text.toString()
        val aparelho = et_aparelho.text.toString()
        val defeitoReclamado = et_defeito_reclamado.text.toString()
        val defeitoConstatado = et_defeito_constatado.text.toString()
        val obs = et_obs.text.toString()
        val preco = et_preco.text.toString().replace("R$ ", "")
        val sinal = et_sinal.text.toString().replace("R$ ", "")
        val aPagar = tv_a_pagar.text.toString()
        val pago = if (cb_pago.isChecked) 1 else 0

        val contentValues = ContentValues().apply {
            put(OsTable.ORCAMENTO_PRONTO, orcamentoPronto)
            put(OsTable.ORCAMENTO_APROVADO, orcamentoAprovado)
            put(OsTable.SERVICO_PRONTO, servicoPronto)
            put(OsTable.NOME, nome)
            put(OsTable.ENDERECO, endereco)
            put(OsTable.TELS, tels)
            put(OsTable.APARELHO, aparelho)
            put(OsTable.DEFEITO_RECLAMADO, defeitoReclamado)
            put(OsTable.DEFEITO_CONSTATADO, defeitoConstatado)
            put(OsTable.OBS, obs)
            put(OsTable.PRECO, preco)
            put(OsTable.SINAL, sinal)
            put(OsTable.PAGO, pago)
        }

        database.use {
            update(OsTable.TABLE_NAME, contentValues, "${OsTable._ID} = $id", null)
        }
        finish()
    }

    fun gerarPdf(id: Long) {
        salvar(id)
        toast("Gera o PDF para a os $id")
    }
}
