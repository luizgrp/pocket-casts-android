package au.com.shiftyjelly.pocketcasts.wear.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SendToMobile
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import au.com.shiftyjelly.pocketcasts.R
import com.google.android.horologist.auth.composables.chips.GuestModeChip
import com.google.android.horologist.auth.composables.chips.SignInChip
import com.google.android.horologist.auth.ui.common.screens.prompt.SignInPromptScreen
import com.google.android.horologist.auth.ui.common.screens.prompt.SignInPromptViewModel
import com.google.android.horologist.auth.ui.googlesignin.prompt.GoogleSignInPromptViewModelFactory
import com.google.android.horologist.base.ui.components.StandardChip
import com.google.android.horologist.base.ui.components.StandardChipType
import com.google.android.horologist.compose.layout.ScalingLazyColumnState

@Composable
fun AuthPromptScreen(
    navController: NavController,
    columnState: ScalingLazyColumnState,
    modifier: Modifier = Modifier,
    viewModel: SignInPromptViewModel = viewModel(factory = GoogleSignInPromptViewModelFactory)
) {
    SignInPromptScreen(
        message = stringResource(id = R.string.auth_prompt_screen_message),
        onAlreadySignedIn = {
            // WatchListScreen would only navigate to this screen if it's not signed in
            throw IllegalStateException("App is already signed-in, it should not navigate to this screen")
        },
        columnState = columnState,
        modifier = modifier,
        viewModel = viewModel
    ) {
        item {
            SignInChip(
                onClick = {
                    navController.navigate(AuthenticationRoutes.googleSignIn) {
                        popUpTo(WatchListScreen.route)
                    }
                },
                label = stringResource(id = R.string.auth_prompt_screen_google_sign_in_button),
                chipType = StandardChipType.Secondary
            )
        }

        item {
            StandardChip(
                label = stringResource(id = R.string.auth_prompt_screen_sign_in_phone_button),
                onClick = {
                    // TODO: use DataLayer helpers to launch phone app to user to sign-in
                    // https://google.github.io/horologist/datalayer-helpers-guide/
                },
                modifier = modifier,
                icon = Icons.Default.SendToMobile,
                chipType = StandardChipType.Secondary,
            )
        }

        item {
            GuestModeChip(
                onClick = navController::popBackStack,
                chipType = StandardChipType.Secondary
            )
        }
    }
}
