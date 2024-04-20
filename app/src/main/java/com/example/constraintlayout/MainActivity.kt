package com.example.constraintlayout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity(){
    private lateinit var tts: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //pegar id
        val edtConta: EditText = findViewById(R.id.edtConta)
        val edtpeople: EditText = findViewById(R.id.edtpeople)
        val textTotal: TextView = findViewById(R.id.textTotal)
        val btFalar: FloatingActionButton = findViewById(R.id.btFalar)
        val floatingActionButton: FloatingActionButton = findViewById(R.id.floatingActionButton)

      //calc

        fun rachou(valor: Double?, pessoa: Int?){
            var result: Double? = null

            if (valor != null && pessoa != null && pessoa!=0){
                result = (valor/pessoa)
            } else {
                result = 0.0
            }

            val formatter: NumberFormat = DecimalFormat("0.##")
            textTotal.text = formatter.format(result)
        }

        //pra ficar calculando
        edtConta.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                var valor = edtConta.text.toString().toDoubleOrNull()
                var pessoa = edtpeople.text.toString().toDoubleOrNull()
            }
        })

        edtpeople.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int){}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int){}
            override fun afterTextChanged(p0: Editable?) {
                var valor  = edtConta.text.toString().toDoubleOrNull()
                var pessoa = edtpeople.text.toString().toIntOrNull()
                rachou(valor,pessoa)
            }
        })

        tts = TextToSpeech(applicationContext, TextToSpeech.OnInitListener { status ->
            if (status != TextToSpeech.ERROR){
                tts.setLanguage(Locale("pt","BR"))
            }
        })

        //falar
        btFalar.setOnClickListener{
            val falar = textTotal.text.toString()
            if(falar != null){
                tts.speak(falar, TextToSpeech.QUEUE_FLUSH, null)
            }
        }

        //compartilhar

        floatingActionButton.setOnClickListener {
            val comparti =
                "O valor a ser rachado é: R$ " + textTotal.text.toString() + ". Valor total: R$ " + edtConta.text.toString() + " para " + edtpeople.text.toString() + "pessoas."
            val intent = Intent()

            intent.action = Intent.ACTION_SEND
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, comparti)
            startActivity(Intent.createChooser(intent, "Compartilhar"))
        }
    }










}

