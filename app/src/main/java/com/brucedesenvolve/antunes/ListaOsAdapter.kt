package com.brucedesenvolve.antunes

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.item_lista_os.view.*

class ListaOsAdapter(private val teste: Array<String>, private val context: Context) : RecyclerView.Adapter<ListaOsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_lista_os, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val produto = teste[position]
        holder.numeroOs.text = "1000"
        holder.nome.text = "Diele Priscila Barbosa"
        holder.aparelho.text = produto
        holder.apaparOs.setOnClickListener {
            AlertDialog.Builder(context).setTitle("Deseja apagar esta OS?")
                    .setPositiveButton("Apagar") {_, _ -> Toast.makeText(context, "Produto: $produto", Toast.LENGTH_SHORT).show() }
                    .setNegativeButton("Cancelar") {_, _ -> return@setNegativeButton }.create().show()
        }
        holder.itemView.setOnClickListener{context.startActivity(Intent(context, EditarOsActivity::class.java))}
    }

    override fun getItemCount(): Int {
        return teste.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val numeroOs = itemView.tv_numero_os
        val nome = itemView.tv_nome
        val aparelho = itemView.tv_aparelho
        val apaparOs = itemView.bt_apagar_os
    }
}

