package com.easyapp.convert_rgb_hexa

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.easyapp.convert_rgb_hexa.Model.RespuestaConsumo
import com.easyapp.convert_rgb_hexa.databinding.ActivityMainBinding
import com.google.gson.Gson
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val TAG = "MainActivity"
    private var progress: ProgressBar? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.also {
            progress = findViewById<ProgressBar>(R.id.progress)
            val etValorR = findViewById<EditText>(R.id.etValorR)
            val etValorG = findViewById<EditText>(R.id.etValorG)
            val etValorB = findViewById<EditText>(R.id.etValorB)

            findViewById<Button>(R.id.btnConvert).setOnClickListener {
                val r = etValorR.text.toString().trim()
                val g = etValorG.text.toString().trim()
                val b = etValorB.text.toString().trim()

                val validacionR = validaciones(r, etValorR)
                val validacionG = validaciones(g, etValorG)
                val validacionB = validaciones(b, etValorB)

                val etValidacion = findViewById<EditText>(R.id.etValidacion)
                val cadena = etValidacion.text.toString().trim()

                if (!cadena.isEmpty()) {
                    Log.w(TAG, "onCreate: ${validar(cadena)}")
                }else{
                    mostrarError("Ingresar cadena", etValidacion)
                }

                if (validacionR && validacionG && validacionB) {
                    realizarConversion(r, g, b)
                    peticion("https://worldtimeapi.org/api/timezone/America/Mexico_City")
                }
            }

        }
    }

    fun validar(str: String): Boolean {
        if (str.isEmpty()) return true
        val stack: Stack<Char> = Stack<Char>()
        for (i in 0..str.length-1) {
            val current = str[i]
            if (current == '{' || current == '(' || current == '[') {
                stack.push(current)//push agrega
            }
            if (current == '}' || current == ')' || current == ']') {
                if (stack.isEmpty()) return false
                val last: Char = stack.peek()
                Log.d(TAG, "validar: $current/$last")
                if (current == '}' //&& last == '{'
                    || current == ')'// && last == '('
                    || current == ']'// && last == '['
                ) {
                    stack.pop()//pop elimina
                } else {
                    return false
                }
            }
        }
        return stack.isEmpty()
    }

    private fun peticion(path: String) {
        mostrarLoader(true)
        val pet = Volley.newRequestQueue(this)
        val solicitud = StringRequest(Request.Method.GET, path, { response ->
            try {
                val datos = Gson().fromJson(response, RespuestaConsumo::class.java)
                val tvDateTime = findViewById<TextView>(R.id.tvDateTime)
                tvDateTime.text = datos.datetime
            } catch (e: Exception) {
                Log.e(TAG, "peticion: ${e.localizedMessage}")
            }
            mostrarLoader(false)

        }, {
            Log.e(TAG, "peticion: ERROR: ${it.networkResponse}")
            mostrarLoader(false)
        })

        pet.add(solicitud)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun realizarConversion(r: String, g: String, b: String) {
        val color = Color.rgb(r.toInt(), g.toInt(), b.toInt())
        val buf: String = Integer.toHexString(color)
        val hex = "#" + buf.substring(buf.length - 6)

        val tvHexa = findViewById<TextView>(R.id.tvHexadecimal)
        tvHexa.text = hex

        val viewIzq = findViewById<View>(R.id.vContenedorIzq)
        val viewDer = findViewById<View>(R.id.vContenedorDer)

        viewIzq.setBackgroundColor(Color.parseColor(hex))
        viewDer.setBackgroundColor(Color.parseColor(hex))

        tvHexa.setTextColor(color)

    }

    private fun validaciones(campoTexto: String, editText: EditText): Boolean {
        if (campoTexto.isEmpty()) {
            mostrarError("Ingresar dato", editText)
            return false
        } else if (campoTexto.toInt() > 256) {
            mostrarError("Tamaño incorrecto", editText)
            return false
        } else {
            return true
        }
    }

    private fun mostrarError(error: String, editText: EditText) {
        editText.error = error
    }

    private fun mostrarLoader(mostrar: Boolean) {
        if (mostrar) {
            progress!!.visibility = View.VISIBLE
        } else {
            progress!!.visibility = View.INVISIBLE
        }
    }
}