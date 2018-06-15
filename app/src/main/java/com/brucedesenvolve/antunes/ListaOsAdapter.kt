package com.brucedesenvolve.antunes

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_lista_os.view.*
import org.jetbrains.anko.*

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
        holder.apaparOs.setOnClickListener {context.alert("Deseja apagar esta OS?" ,"Apagar") {
                positiveButton("Apagar") {context.toast("apagar: $produto")}
                negativeButton("Cancelar") {}
            }.show()
        }
        holder.itemView.setOnClickListener{context.startActivity<EditarOsActivity>()}
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

