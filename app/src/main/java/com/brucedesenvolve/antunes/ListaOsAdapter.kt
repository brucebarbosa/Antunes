package com.brucedesenvolve.antunes

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.brucedesenvolve.antunes.data.OsContract.OsTable
import com.brucedesenvolve.antunes.data.database
import kotlinx.android.synthetic.main.item_lista_os.view.*
import org.jetbrains.anko.*
import org.jetbrains.anko.db.SqlOrderDirection
import org.jetbrains.anko.db.select

class ListaOsAdapter(private val context: Context) : RecyclerView.Adapter<ListaOsAdapter.ViewHolder>() {

    /*Com isso os dados do banco de dados são organizados em uma lista*/
    private var listOs: List<ItemOs> = ArrayList()
    private var isRefreshing = false

    /**
     * Aqui os dados do banco de dados são atribuídos a lista quando o app é iniciado.
     */
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        refresh()
    }

    /**
     * Aqui define o layout para os itens da lista.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_lista_os, parent, false)
        return ViewHolder(view)
    }

    /**
     * Aqui os dados da lista são atribuídos as referências do layout.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val os = listOs[position]

        holder.numeroOs.text = os.numeroOs.toString()
        holder.nome.text = os.nome
        holder.aparelho.text = os.aparelho
        /*Apaga a OS determinada e atualiza os itens da lista com uma função declarada em MainActivity*/
        holder.apaparOs.setOnClickListener {context.alert("Deseja apagar esta OS?" ,"Apagar") {
                positiveButton("Apagar") {
                    context.database.use {
                        delete(OsTable.TABLE_NAME, "${OsTable.ID} = ${os.numeroOs}", null)
                    }
                    (context as MainActivity).deleteLine(position)
                }
                negativeButton("Cancelar") {}
            }.show()
        }
        /*Abre a tela de edição para a OS determinada*/
        holder.itemView.setOnClickListener{context.startActivity<EditarOsActivity>("id" to os.numeroOs)}
    }

    override fun getItemCount(): Int {
        return listOs.size
    }

    /**
     * Aqui são criadas referências para cada view do layout para os itens da lista.
     */
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val numeroOs = itemView.tv_numero_os!!
        val nome = itemView.tv_nome!!
        val aparelho = itemView.tv_aparelho!!
        val apaparOs = itemView.bt_apagar_os!!
    }

    /**
     * Esta função atualiza a lista.
     */
    fun refresh() {
        if (isRefreshing) return
        isRefreshing = true
        doAsync {
            context.database.use {
                val itemsOs = mutableListOf<ItemOs>()
                select(OsTable.TABLE_NAME).orderBy(OsTable.ID, SqlOrderDirection.DESC).exec {
                    while (moveToNext()) {
                        val itemOs = ItemOs(getLong(getColumnIndex(OsTable.ID)),
                                getString(getColumnIndex(OsTable.NOME)),
                                getString(getColumnIndex(OsTable.DESCRICAO)))
                        itemsOs.add(itemOs)
                    }
                }
                listOs = itemsOs
                isRefreshing = false
            }
        }
    }
}

