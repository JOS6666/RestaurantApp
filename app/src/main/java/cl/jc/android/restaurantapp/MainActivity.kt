package cl.jc.android.restaurantapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import cl.jc.android.restaurantapp.model.MenuItem
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var editCantidadPastel: EditText
    private lateinit var editCantidadCazuela: EditText
    private lateinit var switchPropina: Switch
    private lateinit var textSubtotal: TextView
    private lateinit var textPropina: TextView
    private lateinit var textTotal: TextView
    private lateinit var textSubtotalPastel: TextView
    private lateinit var textSubtotalCazuela: TextView

    private val pastelChoclo = MenuItem("Pastel de Choclo", 12000)
    private val cazuela = MenuItem("Cazuela", 10000)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Vincular vistas
        editCantidadPastel = findViewById(R.id.editCantidadPastel)
        editCantidadCazuela = findViewById(R.id.editCantidadCazuela)
        switchPropina = findViewById(R.id.switchPropina)
        textSubtotal = findViewById(R.id.textSubtotal)
        textPropina = findViewById(R.id.textPropina)
        textTotal = findViewById(R.id.textTotal)
        textSubtotalPastel = findViewById(R.id.textSubtotalPastel)
        textSubtotalCazuela = findViewById(R.id.textSubtotalCazuela)

        val watcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = calcularTotales()
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        editCantidadPastel.addTextChangedListener(watcher)
        editCantidadCazuela.addTextChangedListener(watcher)
        switchPropina.setOnCheckedChangeListener { _, _ -> calcularTotales() }
    }

    private fun calcularTotales() {
        val cantidadPastel = editCantidadPastel.text.toString().toIntOrNull() ?: 0
        val cantidadCazuela = editCantidadCazuela.text.toString().toIntOrNull() ?: 0

        val subtotalPastel = cantidadPastel * pastelChoclo.precio
        val subtotalCazuela = cantidadCazuela * cazuela.precio
        val subtotal = subtotalPastel + subtotalCazuela
        val propina = if (switchPropina.isChecked) (subtotal * 0.1).toInt() else 0
        val total = subtotal + propina

        val formato = NumberFormat.getCurrencyInstance(Locale("es", "CL"))

        // Actualizar subtotales
        textSubtotalPastel.text = "Subtotal Pastel: ${formato.format(subtotalPastel)}"
        textSubtotalCazuela.text = "Subtotal Cazuela: ${formato.format(subtotalCazuela)}"

        // Actualizar totales
        textSubtotal.text = "Subtotal: ${formato.format(subtotal)}"
        textPropina.text = "Propina: ${formato.format(propina)}"
        textTotal.text = "Total: ${formato.format(total)}"
    }
}
