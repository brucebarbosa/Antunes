package com.brucedesenvolve.antunes

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        criar_os.setOnClickListener {Toast.makeText(this, "Abre activity", Toast.LENGTH_SHORT).show()}

        val list = arrayOf("afinador", "baixo", "cavaco", "distorcão", "escalopado", "flauta", "guitarra", "holoforte", "iGuitar", "jojoba", "ll200", "mano a mano")

        lista_os.layoutManager = LinearLayoutManager(this)
        lista_os.adapter = ListaOsAdapter(list, this)
    }
}
