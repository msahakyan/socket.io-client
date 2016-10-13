package com.android.msahakyan.ottonovaclient.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import com.android.msahakyan.ottonovaclient.R;
import com.android.msahakyan.ottonovaclient.util.AppUtils;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author msahakyan
 */

public class LoginFragment extends BaseFragment {

    @Bind(R.id.insert_username)
    EditText usernameView;

    @Bind(R.id.action_login)
    Button buttonLogin;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
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

        usernameView.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_GO) {
                onLoginButtonClick();
                return true;
            }
            return false;
        });
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
}

