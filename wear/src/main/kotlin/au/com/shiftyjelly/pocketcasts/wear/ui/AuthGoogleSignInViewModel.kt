package au.com.shiftyjelly.pocketcasts.wear.ui

import au.com.shiftyjelly.pocketcasts.account.AccountAuth
import au.com.shiftyjelly.pocketcasts.account.SignInSource
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.horologist.auth.data.googlesignin.GoogleSignInEventListener
import com.google.android.horologist.auth.ui.googlesignin.signin.GoogleSignInViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthGoogleSignInViewModel @Inject constructor(
    googleSignInClient: GoogleSignInClient,
    googleSignInEventListener: GoogleSignInEventListener,
) : GoogleSignInViewModel(googleSignInClient, googleSignInEventListener) {

}

class GoogleSignInEventListenerImpl @Inject constructor(
    private val accountAuth: AccountAuth
) : GoogleSignInEventListener {
    override suspend fun onSignedIn(account: GoogleSignInAccount) {
        account.idToken?.let { idToken ->
            accountAuth.signInWithGoogle(idToken, SignInSource.WatchPhoneSync)
        }
    }
}
