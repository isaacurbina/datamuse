package urbina.isaac.dictionary.ui.composable

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ResultWord(word: String) {
    Text(
        modifier = Modifier.padding(4.dp),
        text = word.uppercase(),
        style = TextStyle(
            fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black
        )
    )
}