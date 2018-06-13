package com.brucedesenvolve.antunes

import android.app.AlertDialog
import android.content.Context
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
        holder.nomeCliente.text = "Diele Priscila Barbosa"
        holder.produto.text = produto
        holder.apaparOs.setOnClickListener {
            AlertDialog.Builder(context).setMessage("Deseja realmente apagar esta OS?")
                    .setPositiveButton("Apagar") {_, _ -> Toast.makeText(context, "Produto: $produto", Toast.LENGTH_SHORT).show() }
                    .setNegativeButton("Cancelar") {_, _ -> return@setNegativeButton }.create().show()
        }
        holder.itemView.setOnClickListener{Toast.makeText(context, "Esta é a linha: $position", Toast.LENGTH_SHORT).show()}
    }

    override fun getItemCount(): Int {
        return teste.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val numeroOs = itemView.numero_os
        val nomeCliente = itemView.nome_cliente
        val produto = itemView.produto
        val apaparOs = itemView.apagar_os
    }
}

