package com.brucedesenvolve.antunes

import android.Manifest
import android.app.Dialog
import android.content.ContentValues
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextWatcher
import com.brucedesenvolve.antunes.data.OsContract.OsTable
import com.brucedesenvolve.antunes.data.database
import kotlinx.android.synthetic.main.activity_editar_os.*
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast
import android.os.Environment.getExternalStorageDirectory
import com.itextpdf.text.pdf.PdfWriter
import java.io.File
import java.io.FileOutputStream
import android.content.Intent
import android.net.Uri
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import android.graphics.Bitmap.CompressFormat
import android.graphics.BitmapFactory
import android.os.Handler
import com.itextpdf.text.*
import org.jetbrains.anko.indeterminateProgressDialog
import java.io.ByteArrayOutputStream
import com.itextpdf.text.BaseColor
import android.view.WindowManager




class EditarOsActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_PERMISSION = 1
    }

    var id: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_os)

        /*Pega o número da OS que será editada e a atribui a uma variavel global*/
        id = intent.getLongExtra("id", 0)

        /*Esses dois métdodos verificam constantemente a mudança dos valores de preço e sinal para calcular o valor a pagar*/
        et_preco.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                var preco = et_preco.text.toString().replace("R$ ", "")
                var sinal = et_sinal.text.toString().replace("R$ ", "")
                if (preco == "") preco = "0"
                if (sinal == "") sinal = "0"

                val aPagar = preco.toInt() - sinal.toInt()
                val final = "R$ $aPagar"
                tv_a_pagar.text = final
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        et_sinal.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                var preco = et_preco.text.toString().replace("R$ ", "")
                var sinal = et_sinal.text.toString().replace("R$ ", "")
                if (preco == "") preco = "0"
                if (sinal == "") sinal = "0"

                val aPagar = preco.toInt() - sinal.toInt()
                val final = "R$ $aPagar"
                tv_a_pagar.text = final
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        })

        preencher()

        bt_salvar.setOnClickListener {
            val mProgressDialog = indeterminateProgressDialog( "Salvando...")
            mProgressDialog.show()
            manterDialog(mProgressDialog)
            Handler().postDelayed({
                salvar()
                finish()
                mProgressDialog.dismiss()
            }, 1000)
        }

        bt_gerar_pdf.setOnClickListener { gerarPdf() }
    }

    /**
     * Função responsável por preencher o formulário com os dados guardados no banco de dados.
     */
    private fun preencher() {
        database.use {
            select(OsTable.TABLE_NAME).whereArgs("${OsTable.ID} = $id").exec {
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
                    toast("Desculpe, ocorreu um erro")
                    finish()
                }
            }
        }
    }

    /**
     * Função responsável por pegar os dados do formulário e atualiza-los no banco de dados.
     */
    private fun salvar() {
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
            update(OsTable.TABLE_NAME, contentValues, "${OsTable.ID} = $id", null)
        }
    }

    /**
     * Esta função checa se existe permissão para usar o armazenamento externo.
     * Se houver, a função criarPdf() será executada, do contrario será executado a função que pede permissões ao usuário.
     */
    private fun gerarPdf() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_PERMISSION)
        } else {
            val mProgressDialog = indeterminateProgressDialog("Aguarde enquanto sua OS é criada", "Gerando PDF")
            mProgressDialog.show()
            manterDialog(mProgressDialog)
            Handler().postDelayed({
                criarPdf()
                mProgressDialog.dismiss()
            }, 2000)
        }
    }

    /**
     * Esta função pede permissões ao usuário. Neste app apenas a permissão para o uso do armazenamento externo é necessária.
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_PERMISSION -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val mProgressDialog = indeterminateProgressDialog("Aguarde enquanto sua OS é criada", "Gerando PDF")
                mProgressDialog.show()
                manterDialog(mProgressDialog)
                Handler().postDelayed({
                    criarPdf()
                    mProgressDialog.dismiss()
                }, 2000)
            }
        }
    }

    /**
     * Esta é a função que pega os dados do formulário e os organiza em um documento pdf.
     */
    private fun criarPdf() {

        salvar()

        val entrada = tv_data_entrada.text
        val orcamentoPronto = et_data_orcamento_pronto.text
        val orcamentoAprovado = et_data_orcamento_aprovado.text
        val servicoPronto = et_data_servico_pronto.text
        val nome = et_nome.text
        val endereco = et_endereco.text
        val tels = et_tels.text
        val aparelho = et_aparelho.text
        val defeitoReclamado = et_defeito_reclamado.text
        val defeitoConstatado = et_defeito_constatado.text
        val obs = et_obs.text
        val preco = et_preco.text
        val sinal = et_sinal.text
        val aPagar = tv_a_pagar.text

        /*Este código verifica se temos a pasta para salvar as OS. se não tivermos ela será criada*/
        val docsFolder = File(getExternalStorageDirectory(), "/Antunes OS")
        if (!docsFolder.exists()) docsFolder.mkdir()

        /*Aqui é criado o documento*/
        val pdfFile = File(docsFolder.absolutePath, "(OS $id) $nome.pdf")
        val output = FileOutputStream(pdfFile)
        val document = Document()
        PdfWriter.getInstance(document, output)
        document.open()
        document.setMargins(20F, 20F, 20F, 20F)

        /*Daqui em diante teremos todos os elementos em ordem que farão parte do documento*/
        val logo = BitmapFactory.decodeStream(assets.open("logo.jpg"))
        val streamLogo = ByteArrayOutputStream()
        logo.compress(CompressFormat.PNG, 100, streamLogo)
        val imgLogo = Image.getInstance(streamLogo.toByteArray())
        val logoTable = PdfPTable(1)
        logoTable.widthPercentage = 100F
        logoTable.addCell(PdfPCell(imgLogo, true))
        document.add(logoTable)

        val fonte = Font(Font.FontFamily.HELVETICA, 17F, Font.BOLD)

        val osTable = PdfPTable(1)
        osTable.widthPercentage = 100F
        val osCell = PdfPCell(Phrase("OS: $id", fonte))
        osCell.setPadding(5F)
        osCell.horizontalAlignment = Element.ALIGN_CENTER
        osTable.addCell(osCell)
        document.add(osTable)

        val datasTable1 = PdfPTable(2)
        datasTable1.widthPercentage = 100F
        val entradaCell = PdfPCell(Phrase("Entrada: $entrada", fonte))
        entradaCell.setPadding(5F)
        val orcamentoProntoCell = PdfPCell(Phrase("Orçamento Pronto: $orcamentoPronto", fonte))
        orcamentoProntoCell.setPadding(5F)
        datasTable1.addCell(entradaCell)
        datasTable1.addCell(orcamentoProntoCell)
        document.add(datasTable1)
        val datasTable2 = PdfPTable(2)
        datasTable2.widthPercentage = 100F
        val orcamentoAprovadoCell = PdfPCell(Phrase("Orçamento Aprovado: $orcamentoAprovado", fonte))
        orcamentoAprovadoCell.setPadding(5F)
        val servicoProntoCell = PdfPCell(Phrase("Seriço Pronto: $servicoPronto", fonte))
        servicoProntoCell.setPadding(5F)
        datasTable2.addCell(orcamentoAprovadoCell)
        datasTable2.addCell(servicoProntoCell)
        document.add(datasTable2)

        val nomeTable = PdfPTable(1)
        nomeTable.widthPercentage = 100F
        val nomeCell = PdfPCell(Phrase("Nome: $nome", fonte))
        nomeCell.setPadding(5F)
        nomeTable.addCell(nomeCell)
        document.add(nomeTable)

        val enderecoTable = PdfPTable(1)
        enderecoTable.widthPercentage = 100F
        val enderecoCell = PdfPCell(Phrase("Endereço: $endereco", fonte))
        enderecoCell.setPadding(5F)
        enderecoTable.addCell(enderecoCell)
        document.add(enderecoTable)

        val telsTable = PdfPTable(1)
        telsTable.widthPercentage = 100F
        val telsCell = PdfPCell(Phrase("Tels: $tels", fonte))
        telsCell.setPadding(5F)
        telsTable.addCell(telsCell)
        document.add(telsTable)

        val aparelhoTable = PdfPTable(1)
        aparelhoTable.widthPercentage = 100F
        val aparelhoCell = PdfPCell(Phrase("Aparelho: $aparelho", fonte))
        aparelhoCell.setPadding(5F)
        aparelhoCell.minimumHeight = 50F
        aparelhoTable.addCell(aparelhoCell)
        document.add(aparelhoTable)

        val defeitoReclamadoTable = PdfPTable(1)
        defeitoReclamadoTable.widthPercentage = 100F
        val defeitoReclamadoCell = PdfPCell(Phrase("Defeito Reclamado: $defeitoReclamado", fonte))
        defeitoReclamadoCell.setPadding(5F)
        defeitoReclamadoCell.minimumHeight = 50F
        defeitoReclamadoTable.addCell(defeitoReclamadoCell)
        document.add(defeitoReclamadoTable)

        val defeitoConstatadoTable = PdfPTable(1)
        defeitoConstatadoTable.widthPercentage = 100F
        val defeitoConstatadoCell = PdfPCell(Phrase("Defeito Constatado: $defeitoConstatado", fonte))
        defeitoConstatadoCell.setPadding(5F)
        defeitoConstatadoCell.minimumHeight = 50F
        defeitoConstatadoTable.addCell(defeitoConstatadoCell)
        document.add(defeitoConstatadoTable)

        val obsTable = PdfPTable(1)
        obsTable.widthPercentage = 100F
        val obsCell = PdfPCell(Phrase("Obs: $obs", fonte))
        obsCell.setPadding(5F)
        obsCell.minimumHeight = 50F
        obsTable.addCell(obsCell)
        document.add(obsTable)

        val valoresTable = PdfPTable(3)
        valoresTable.widthPercentage = 100F
        val precoCell = PdfPCell(Phrase("Preço: $preco,00", fonte))
        precoCell.setPadding(5F)
        val sinalCell = PdfPCell(Phrase("Sinal: $sinal,00", fonte))
        sinalCell.setPadding(5F)
        valoresTable.addCell(precoCell)
        valoresTable.addCell(sinalCell)
        if (cb_pago.isChecked) {
            val fontePago = Font(Font.FontFamily.HELVETICA, 19F, Font.BOLD, BaseColor.RED)
            val pagoCell = PdfPCell(Phrase("Pago $preco,00", fontePago))
            pagoCell.setPadding(5F)
            valoresTable.addCell(pagoCell)
        } else {
            val aPagarCell = PdfPCell(Phrase("A Pagar: $aPagar,00", fonte))
            aPagarCell.setPadding(5F)
            valoresTable.addCell(aPagarCell)
        }
        document.add(valoresTable)

        val rodape = BitmapFactory.decodeStream(assets.open("rodape.jpg"))
        val stream2 = ByteArrayOutputStream()
        rodape.compress(CompressFormat.PNG, 100, stream2)
        val imgRodape = Image.getInstance(stream2.toByteArray())
        val pdfRodape = PdfPTable(1)
        pdfRodape.widthPercentage = 100F
        pdfRodape.addCell(PdfPCell(imgRodape, true))
        document.add(pdfRodape)

        /*Então finalizamos a montagem do documento e abrimos ele para visualização*/
        document.close()
        abrirPdf(pdfFile)
    }

    /**
     * Esta função confere se o usuário tem um app que visualiza pdf.
     * Se ele tiver, o documento será aberto neste app.
     * Mas se ele não tiver, uma menssagem pedirá para ele baixar um app.
     */
    private fun abrirPdf(pdfFile: File) {
        val testIntent = Intent(Intent.ACTION_VIEW)
        testIntent.type = "application/pdf"
        if (packageManager.queryIntentActivities(testIntent, 0).size > 0) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(Uri.fromFile(pdfFile), "application/pdf")
            startActivity(intent)
            return
        }
        toast("Baixe um aplicativo para ler pdf")
    }

    /**
     * Função para quando girar a tela, o ProgressDialog seja mantido.
     */
    private fun manterDialog(dialog: Dialog) {
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window.attributes)
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog.window.attributes = lp
    }
}
