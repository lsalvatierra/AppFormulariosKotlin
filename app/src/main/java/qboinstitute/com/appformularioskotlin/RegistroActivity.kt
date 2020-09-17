package qboinstitute.com.appformularioskotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_registro.*

class RegistroActivity : AppCompatActivity() {

    private val listapreferencias = ArrayList<String>()
    private val listausuarios = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        val nomusuario = intent.getStringExtra("usuario")
        tvtituloregistro.text = "Bienvenido $nomusuario"
        chkdeporte.setOnClickListener {
            agregarListaDePreferenciasSeleccionadas(it)
        }
        chkdibujo.setOnClickListener {
            agregarListaDePreferenciasSeleccionadas(it)
        }
        chkotros.setOnClickListener {
            agregarListaDePreferenciasSeleccionadas(it)
        }
        btnregistrar.setOnClickListener{
            if(validarFormulario(it)){
                var infousuario = etnombre.text.toString() + " " +
                        etapellido.text.toString() + " " +
                        obtenerGeneroSeleccinado() + " " +
                        obtenerPreferenciasSeleccionadas()
                listausuarios.add(infousuario)
                setearControles()
            }
        }
        swemail.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                tipemail.visibility = View.VISIBLE
            }else{
                tipemail.visibility = View.GONE
            }
        }
        btnlistar.setOnClickListener{
            val intentlista = Intent(this,
            ListaActivity::class.java).apply {
                putExtra("listausuarios",
                    listausuarios)
            }
            startActivity(intentlista)
        }
    }

    fun setearControles(){
        listapreferencias.clear()
        etnombre.setText("")
        etapellido.setText("")
        etemail.setText("")
        swemail.isChecked = false
        chkdeporte.isChecked = false
        chkdibujo.isChecked = false
        chkotros.isChecked = false
        rggenero.clearCheck()
        etnombre.isFocusableInTouchMode = true
        etnombre.requestFocus()
    }

    fun obtenerGeneroSeleccinado():String{
        var genero = ""
        when(rggenero.checkedRadioButtonId){
            R.id.rbtnmasculino -> {
                genero = rbtnmasculino.text.toString()
            }
            R.id.rbtnfemenino -> {
                genero = rbtnfemenino.text.toString()
            }
        }
        return genero
    }

    fun obtenerPreferenciasSeleccionadas():String{
        var preferencias = ""
        for (pref in listapreferencias){
            preferencias += "$pref -"
        }
        return preferencias
    }

    fun agregarListaDePreferenciasSeleccionadas(view: View) {
        if (view is CheckBox) {
            val checked: Boolean = view.isChecked
            when (view.id) {
                R.id.chkdeporte -> {
                    if (checked) {
                        listapreferencias.add(view.text.toString())
                    } else {
                        listapreferencias.remove(view.text.toString())
                    }
                }
                R.id.chkdibujo -> {
                    if (checked) {
                        listapreferencias.add(view.text.toString())
                    } else {
                        listapreferencias.remove(view.text.toString())
                    }
                }
                R.id.chkotros -> {
                    if (checked) {
                        listapreferencias.add(view.text.toString())
                    } else {
                        listapreferencias.remove(view.text.toString())
                    }
                }

            }
        }
    }

    fun validarFormulario(vista: View): Boolean {
        var respuesta = false
        if(!validarNombreApellido()){
            enviarMensajeError(vista,
               getString(R.string.errorNombreApellido))
        }else if(!validarGenero()){
            enviarMensajeError(vista,
                "Seleccione un género")
        }else if(!validarPreferencias()){
            enviarMensajeError(vista,
                "Seleccione una preferencia")
        }else if(!validarEmail()){
            enviarMensajeError(vista,
                "Ingrese su email para notificar")
        } else{
            respuesta = true
        }
        return respuesta
    }

    fun enviarMensajeError(vista: View, mensajeError : String){
        Snackbar.make(vista, mensajeError, Snackbar.LENGTH_LONG).show()
    }

    fun validarEmail(): Boolean{
        var respuesta = true
        if(swemail.isChecked){
            if(etemail.text.toString().trim().isEmpty()){
                respuesta = false
                etemail.isFocusable = true
            }
        }
        return respuesta
    }

    fun validarPreferencias():Boolean{
        var respuesta = false
        if(chkdeporte.isChecked || chkdibujo.isChecked
            || chkotros.isChecked){
            respuesta = true
        }
        return respuesta
    }

    fun validarGenero():Boolean{
        var respuesta = true
        if(rggenero.checkedRadioButtonId == -1){
            respuesta = false
        }
        return respuesta
    }

    fun validarNombreApellido():Boolean{
        var respuesta = true
        if(etnombre.text.toString().trim().isEmpty()){
            etnombre.isFocusableInTouchMode = true
            etnombre.requestFocus()
            respuesta = false
        } else if(etapellido.text.toString().trim().isEmpty()){
            etapellido.isFocusable = true
            etapellido.isFocusableInTouchMode = true
            etapellido.requestFocus()
            respuesta = false
        }
        return respuesta
    }
}