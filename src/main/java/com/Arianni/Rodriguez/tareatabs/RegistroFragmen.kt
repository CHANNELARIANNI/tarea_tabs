package com.Arianni.rodriguez.tareatabs
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class RegistroFragmen : AppCompatActivity()  /*Fragment()*/{
    private lateinit var nombreEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var enviarButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_registro)

        nombreEditText = findViewById(R.id.nombreEditText)
        emailEditText = findViewById(R.id.emailEditText)
        enviarButton = findViewById(R.id.enviarButton)

        enviarButton.setOnClickListener {
            val nombre = nombreEditText.text.toString()
            val email = emailEditText.text.toString()

            if (nombre.isEmpty() || email.isEmpty()) {
                mostrarAlerta("Error", "Por favor, complete todos los campos.")
            } else if (usuarioYaRegistrado(nombre, email)) {
                mostrarAlerta("Error", "Ya has sido registrado previamente.")
            } else {
                // Realizar el registro del usuario
                // ...
                mostrarAlerta("Éxito", "Registro exitoso.")
            }
        }
    }

    private fun mostrarAlerta(titulo: String, mensaje: String) {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setTitle(titulo)
        dialogBuilder.setMessage(mensaje)
        dialogBuilder.setPositiveButton("OK", null)
        val dialog = dialogBuilder.create()
        dialog.show()
    }

    private fun usuarioYaRegistrado(nombre: String, email: String): Boolean {
        // Lógica para verificar si el usuario ya está registrado
        // Retorna true si ya está registrado, false en caso contrario
        return false
    }
}