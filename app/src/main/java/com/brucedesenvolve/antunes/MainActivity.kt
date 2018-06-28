package com.brucedesenvolve.antunes

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*Abre a tela para criar uma nova OS*/
        bt_criar_os.setOnClickListener {startActivity<NovaOsActivity>()}

        rv_lista_os.layoutManager = LinearLayoutManager(this)
        rv_lista_os.adapter = ListaOsAdapter(this)
    }

    /**
     * Sempre que voltar de uma outra atividade, a lista será atializada.
     */
    override fun onResume() {
        super.onResume()
        (rv_lista_os.adapter as ListaOsAdapter).refresh()
        rv_lista_os.adapter.notifyDataSetChanged()
    }

    public override fun onDestroy() {
        super.onDestroy()
        rv_lista_os.adapter = null
    }

    /**
     * Esta função possibilita a atualização visual quando um item da lista é removido.
     */
    fun deleteLine(position: Int) {
        rv_lista_os.adapter.notifyItemRemoved(position)
    }
}
