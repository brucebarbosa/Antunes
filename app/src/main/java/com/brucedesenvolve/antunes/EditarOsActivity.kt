package com.brucedesenvolve.antunes

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_editar_os.*
import org.jetbrains.anko.toast

class EditarOsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_os)

        bt_salvar.setOnClickListener { salvar() }

        bt_gerar_pdf.setOnClickListener {
            //salvar()
            toast("gerar pdf")}
    }

    fun salvar() {
        val dataOracamentoPronto = et_data_orcamento_pronto.text.toString()
        val dataOrcamentoAprovado = et_data_orcamento_aprovado.text.toString()
        val dataServicoPronto = et_data_servico_pronto.text.toString()
        val nome = et_nome.text.toString()
        val endereco = et_endereco.text.toString()
        val tels = et_tels.text.toString()
        val aparelho = et_aparelho.text.toString()
        val defeitoReclamado = et_defeito_reclamado.text.toString()
        val defeitoConstatado = et_defeito_constatado.text.toString()
        val obs = et_obs.text.toString()
        val preco = et_preco.text.toString()
        val sinal = et_sinal.text.toString()
        val aPagar = tv_a_pagar.text.toString()
        val pago = cb_pago.isChecked

        toast("$preco\n$sinal")
    }
}
