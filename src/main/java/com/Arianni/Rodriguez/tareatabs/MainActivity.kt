
package com.Arianni.rodriguez.tareatabs
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.*
import com.dionicio.moreta.tareatabs.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(InicioFragment(), "Inicio")
        adapter.addFragment(RegistroFragment(), "Registro")
        adapter.addFragment(ContactoFragment(), "Contacto")
        adapter.addFragment(CancionesFragment(), "Canciones favoritas")

        binding.viewPager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

    private inner class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        private val fragmentList = ArrayList<Fragment>()
        private val fragmentTitleList = ArrayList<String>()

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getCount(): Int {
            return fragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            fragmentList.add(fragment)
            fragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return fragmentTitleList[position]
        }
    }
}


class InicioFragment :  Fragment() {
    private lateinit var videoView: VideoView
    private lateinit var textView: TextView
    private lateinit var mediaController: MediaController

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_inicio, container, false)
        textView = view.findViewById<TextView>(R.id.welcomeTextView)
        videoView = view.findViewById(R.id.videoView)
        mediaController = MediaController(requireContext())
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val videoPath = "android.resource://" + requireContext().packageName + "/" + R.raw.video
        val videoUri = Uri.parse(videoPath)

        videoView.setVideoURI(videoUri)
        videoView.setOnPreparedListener { mp ->
            val videoWidth = mp.videoWidth
            val videoHeight = mp.videoHeight
            videoView.setMediaController(mediaController)
            mediaController.setAnchorView(videoView)
            videoView.start()

            val parentWidth = videoView.parent as ViewGroup
            val parentHeight = parentWidth.height
            val scale = parentWidth.width.toFloat() / videoWidth
            val adjustedHeight = (videoHeight * scale).toInt()

            val layoutParams = videoView.layoutParams
            layoutParams.width = parentWidth.width
            layoutParams.height = adjustedHeight
            videoView.layoutParams = layoutParams
        }

        textView.text = "¡Bienvenido!";
    }
}

class RegistroFragment : Fragment() {
    private lateinit var nombreEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var contrasenaEditText: EditText
    private lateinit var edadEditText: EditText
    private lateinit var enviarButton: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_registro, container, false)

        nombreEditText = view.findViewById(R.id.nombreEditText)
        emailEditText = view.findViewById(R.id.emailEditText)
        contrasenaEditText = view.findViewById(R.id.contrasenaEditText)
        edadEditText = view.findViewById(R.id.fechaEditText)
        enviarButton = view.findViewById(R.id.enviarButton)

        enviarButton.setOnClickListener {
            val nombre = nombreEditText.text.toString()
            val email = emailEditText.text.toString()
            val contrasena = contrasenaEditText.text.toString()
            val edad = edadEditText.text.toString()

            if (nombre.isEmpty() || email.isEmpty() || contrasena.isEmpty() || edad.isEmpty()) {
                mostrarAlerta("Error", "Por favor, complete todos los campos.")
            } else if (usuarioYaRegistrado(nombre, email)) {
                mostrarAlerta("Error", "Ya has sido registrado previamente.")
            } else {
                mostrarAlerta("Éxito", "Registro exitoso.")
            }
        }

        return view
    }

    private fun mostrarAlerta(titulo: String, mensaje: String) {
        val dialogBuilder = AlertDialog.Builder(requireContext())
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


class ContactoFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_contacto, container, false)


    }
}


class CancionesFragment : Fragment() {
    private val songList = arrayOf(
        "Brazos de papa - Pastor Moises",
        "Te rescatare - Priscilla bueno",
        "Entrada triunfal- Cita con el padre",
        "Generacion - oasis",
        "Sucedera - Grupo grace"
    )

    private val songLinks = arrayOf(
        "https://www.youtube.com/watch?v=OMNlF9gZsvc",
        "https://www.youtube.com/watch?v=tipI6mWr6oE",
        "https://www.youtube.com/watch?v=dTg0W6g8dIw",
        "https://www.youtube.com/watch?v=-fz292HNU_w&list=RD-fz292HNU_w&start_radio=1",
        "https://www.youtube.com/watch?v=d9g7P0gDITE"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_canciones, container, false)

        val listView: ListView = view.findViewById(R.id.songList)

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, songList)
        listView.adapter = adapter

        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(songLinks[position]))
            startActivity(intent)
        }

        return view
    }

}


