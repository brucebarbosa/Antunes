package com.brucedesenvolve.antunes

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_nova_os.*
import org.jetbrains.anko.toast

class NovaOsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nova_os)

        bt_salvar_os.setOnClickListener {
            val nome = et_nome.text.toString()
            val endereco = et_endereco.text.toString()
            val tels = et_tels.text.toString()
            val aparelho = et_aparelho.text.toString()
            val defeitoReclamado = et_defeito_reclamado.text.toString()

            toast("$nome\n$endereco\n$tels\n$aparelho\n$defeitoReclamado")
        }
    }
}
