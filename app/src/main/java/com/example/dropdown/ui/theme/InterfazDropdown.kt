import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.dropdown.R


@Composable
@Preview(showBackground = true)
fun Dropdowns() {

    var selectedColor by remember { mutableStateOf("Azul") }
    var selectedFont by remember { mutableStateOf("Roboto") }

    var appliedColor by remember { mutableStateOf("Azul") }
    var appliedFont by remember { mutableStateOf("Roboto") }

    val colorsMap = mapOf(
        "Rojo" to Color.Red,
        "Verde" to Color.Green,
        "Azul" to Color.Blue,
        "Amarillo" to Color.Yellow,
        "Morado" to Color.Magenta
    )

    val fontsMap = mapOf(
        "Roboto" to FontFamily.SansSerif,
        "Sans" to FontFamily.SansSerif,
        "Serif" to FontFamily.Serif,
        "Monospace" to FontFamily.Monospace,
        "Cursive" to FontFamily.Cursive
    )

    val context = LocalContext.current

     Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(id = R.drawable.logo_uniminuto),
            contentDescription = "Logo uniminuto",
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Dropdown para seleccionar color
        DropdownSelector("Seleccionar Color", colorsMap.keys.toList(), selectedColor) { selectedColor = it }
        Spacer(modifier = Modifier.height(8.dp))

        // Dropdown para seleccionar fuente
        DropdownSelector("Seleccionar Fuente", fontsMap.keys.toList(), selectedFont) { selectedFont = it }
        Spacer(modifier = Modifier.height(16.dp))

        // Botón para aplicar estilos
        ElevatedButton(
            onClick = {
                appliedColor = selectedColor
                appliedFont = selectedFont
                Toast.makeText(context, "Color: $appliedColor, Fuente: $appliedFont", Toast.LENGTH_SHORT).show()
            },
            colors = ButtonDefaults.elevatedButtonColors(containerColor = Color(0xFF002C57)),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "Aplicar Estilos", fontSize = 18.sp, color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar el mensaje con el estilo aplicado
        Text(
            text = "Mensaje con estilos aplicados",
            fontSize = 20.sp,
            color = colorsMap[appliedColor] ?: Color.Black, // Color dinámico
            fontFamily = fontsMap[appliedFont] ?: FontFamily.Default, // Fuente dinámica
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun DropdownSelector(label: String, options: List<String>, selectedOption: String, onOptionSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        Button(
            onClick = { expanded = true },
            colors = ButtonDefaults.buttonColors(Color(0xFF8F959E)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "$label: $selectedOption", color = Color.White)
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Dropdown Arrow", tint = Color.White)
            }
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}