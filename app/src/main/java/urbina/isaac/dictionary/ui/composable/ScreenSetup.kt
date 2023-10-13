package urbina.isaac.dictionary.ui.composable

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import urbina.isaac.dictionary.ui.theme.MainActivityViewModel

@Composable
fun ScreenSetup(viewModel: MainActivityViewModel = viewModel()) {
    MainScreen(viewModel)
}