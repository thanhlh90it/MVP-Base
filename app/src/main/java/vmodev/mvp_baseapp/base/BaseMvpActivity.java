package vmodev.mvp_baseapp.base;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import cn.pedant.SweetAlert.SweetAlertDialog;
import vmodev.base.mvpbase.MvpViewActivity;
import vmodev.mvp_baseapp.R;
import vmodev.mvp_baseapp.commom.MyApplication;


/**
 * Created by thanhle on 11/1/16.
 */
public abstract class BaseMvpActivity extends AppCompatActivity implements MvpViewActivity {
    private boolean mIsActivityAvailable = false;
    private View mRootView;
    protected boolean mIsStartAnimationFragment;
    protected boolean mIsClearMemoryActivity = false;
    protected SweetAlertDialog mProgress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (hasProgress()) {
//            mProgress = new SweetAlertDialog (this);
//            mProgress.setTitle(null);
//            mProgress.setMessage(getString(R.string.loading));
//        }
        beforeInit(savedInstanceState);
        if (!mIsClearMemoryActivity) {
            setViewRoot();
            findViewByIds();
            initComponents();
            setEvents();
//            if (hasProgress()) {
//                mProgress = new ProgressDialog(this);
//                mProgress.setTitle(null);
//                mProgress.setMessage(getString(R.string.loading));
//            }
            if (getPresenter() != null) {
                getPresenter().attachView(this);
            }
        }

    }

    protected void beforeInit(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mIsClearMemoryActivity = true;
        }
    }

    @Override
    protected void onDestroy() {
        if (getPresenter() != null) {
            getPresenter().detachView();
        }

        super.onDestroy();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (getPresenter() != null) {
            getPresenter().onStop();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        mIsActivityAvailable = false;
        if (getPresenter() != null) {
            getPresenter().onPause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mIsActivityAvailable = true;
        if (getPresenter() != null) {
            getPresenter().onResume();
        }
    }

    public void attachRootView(View view) {
        mRootView = view;
    }

    public boolean isActivityAvailable() {
        return mIsActivityAvailable;
    }

    /**
     * Implement MvpView interface
     */


    @Override
    public void setViewRoot() {
        setContentView(getLayoutMain());
    }

    @Override
    public void hideProgress() {
        if (mProgress != null) {
            mProgress.dismissWithAnimation();
        }
    }


    @Override
    public void showProgress() {
        mProgress = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        mProgress.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        mProgress.setTitleText(getString(R.string.loading));
        mProgress.setCancelable(false);
        mProgress.show();
    }

    @Override
    public void showMessage(String message) {
        showSimpleSnack(message);
    }

    @Override
    public void showMessage(int messageId) {
        showSimpleSnack(messageId);
    }

    @Override
    public void cancelProgress(boolean cancel) {
        if (mProgress != null) {
            mProgress.setCancelable(cancel);
            mProgress.setCanceledOnTouchOutside(cancel);
        }
    }

    @Override
    public void setCancelableLoading(boolean isCancelLoading) {
        if (mProgress != null) {
            mProgress.setCanceledOnTouchOutside(isCancelLoading);
        }
    }

    protected boolean hasProgress() {
        return true;
    }

    public boolean isIsStartAnimationFragment() {
        return mIsStartAnimationFragment;
    }

    public void setIsStartAnimationFragment(boolean isStartAnimationFragment) {
        mIsStartAnimationFragment = isStartAnimationFragment;
    }

    public MyApplication getApp() {
        return (MyApplication) getApplication();
    }

    public void showSimpleSnack(final String message) {
        if (mRootView == null) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            return;
        }
        Snackbar snackbar = Snackbar.make(mRootView, message, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    public void showSimpleSnack(final int messageId) {
        if (mRootView == null) {
            Toast.makeText(this, messageId, Toast.LENGTH_SHORT).show();
            return;
        }
        Snackbar snackbar = Snackbar.make(mRootView, messageId, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    public void hideKeyBoard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
