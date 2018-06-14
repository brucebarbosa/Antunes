package com.brucedesenvolve.antunes

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bt_criar_os.setOnClickListener {startActivity(Intent(this, NovaOsActivity::class.java))}

        val list = arrayOf("afinador", "baixo", "cavaco", "distorcão", "escalopado", "flauta", "guitarra", "holoforte", "iGuitar", "jojoba", "ll200", "mano a mano")

        rv_lista_os.layoutManager = LinearLayoutManager(this)
        rv_lista_os.adapter = ListaOsAdapter(list, this)
    }
}
