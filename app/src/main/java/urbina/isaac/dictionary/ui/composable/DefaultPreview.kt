package urbina.isaac.dictionary.ui.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import urbina.isaac.dictionary.ui.theme.DictionaryTheme

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DictionaryTheme {
        ScreenSetup(viewModel())
    }
}