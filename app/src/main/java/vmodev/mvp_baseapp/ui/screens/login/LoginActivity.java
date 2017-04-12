package vmodev.mvp_baseapp.ui.screens.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import rx.functions.Action1;
import vmodev.base.mvpbase.MvpPresenter;
import vmodev.base.repository.local.SharePreferenceUtils;
import vmodev.base.utils.StringUtil;
import vmodev.mvp_baseapp.R;
import vmodev.mvp_baseapp.base.BaseMvpActivity;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseMvpActivity implements Login.View{

    private EditText mPasswordView;
    private EditText mUsernameView;


    private LoginPresenter presenter = new LoginPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void openRegister() {
        Intent intent = new Intent();
        intent.setClass(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            hideKeyBoard();
            SharePreferenceUtils.saveEmailPassword(this, username, password);
            showProgress();
            presenter.login(username, password, new Action1<Profile.ProfileBean>() {
                @Override
                public void call(Profile.ProfileBean profileBean) {
                    loginSuccess(profileBean);
                }
            }, new Action1<String>() {
                @Override
                public void call(String s) {
                    loadError(s);
                }
            });
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    @Override
    public MvpPresenter getPresenter() {
        return presenter;
    }

    @Override
    public int getLayoutMain() {
        return R.layout.activity_login;
    }

    @Override
    public void findViewByIds() {
        mUsernameView = (EditText) findViewById(R.id.edUsername);
        mPasswordView = (EditText) findViewById(R.id.edPassword);
    }

    @Override
    public void setEvents() {
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        ((Button) findViewById(R.id.btnLogin)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        findViewById(R.id.btnRegister).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegister();
            }
        });
    }

    @Override
    public void initComponents() {
        if(!StringUtil.isEmptyString(SharePreferenceUtils.getEmail(this)))
            mUsernameView.setText(SharePreferenceUtils.getEmail(this));
        if(!StringUtil.isEmptyString(SharePreferenceUtils.getPassword(this)))
            mPasswordView.setText(SharePreferenceUtils.getPassword(this));
    }

    @Override
    public void gotoHome() {
        Intent intent = new Intent();
//        intent.setClass(this, MainActivity2.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void loginSuccess(Profile.ProfileBean profileBean) {
        hideProgress();
        gotoHome();
    }

    @Override
    public void loadError(String message) {
        hideProgress();
        Snackbar.make(findViewById(R.id.rootView), message, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}

