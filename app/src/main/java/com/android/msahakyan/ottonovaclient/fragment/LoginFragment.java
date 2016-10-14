package com.android.msahakyan.ottonovaclient.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.msahakyan.ottonovaclient.R;
import com.android.msahakyan.ottonovaclient.util.AppUtils;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import butterknife.Bind;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * @author msahakyan
 */

public class LoginFragment extends BaseFragment implements GoogleApiClient.OnConnectionFailedListener {

    private static final int RC_SIGN_IN = 101;
    @Bind(R.id.insert_username)
    EditText usernameView;

    @Bind(R.id.action_login)
    Button buttonLogin;

    @Bind(R.id.sign_in_button)
    SignInButton signInButton;


    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions signInOptions;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        googleApiClient = new GoogleApiClient.Builder(mActivity)
            .enableAutoManage(mActivity, this)
            .addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions)
            .build();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity.setTitle(null);

        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setScopes(signInOptions.getScopeArray());
        setGooglePlusButtonText(signInButton, getString(R.string.sign_in_with_google));

        usernameView.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_GO) {
                onLoginButtonClick();
                return true;
            }
            return false;
        });
    }

    @OnClick(R.id.sign_in_button)
    public void onGoogleSignInButtonClick() {
        signInWithGoogle();
    }

    private void signInWithGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @OnClick(R.id.action_login)
    public void onLoginButtonClick() {
        // Clear errors.
        usernameView.setError(null);
        String username = usernameView.getText().toString().trim();

        // Check for a empty username.
        if (TextUtils.isEmpty(username)) {
            usernameView.setError(getString(R.string.error_empty_username));
            usernameView.requestFocus();
            return;
        }

        // Clear username view content
        usernameView.setText("");

        AppUtils.hideKeyboard(mActivity);
        navigationManager.showChatFragment(username);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Timber.d("handleSignInResult: %1$s", result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount account = result.getSignInAccount();
            if (account != null) {
                usernameView.setText("");
                AppUtils.hideKeyboard(mActivity);
                navigationManager.showChatFragment(account.getDisplayName());
            }
        } else {
            // Signed out, show unauthenticated UI.
//            updateUI(false);
        }
    }

    protected void setGooglePlusButtonText(SignInButton signInButton, String buttonText) {
        // Find the TextView that is inside of the SignInButton and set its text
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);
            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                tv.setText(buttonText);
                tv.setPadding(0, 0, 20, 0);
                return;
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Timber.e("Connection failed: %1$s", connectionResult.getErrorMessage());
        Toast.makeText(mActivity, connectionResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }
}

