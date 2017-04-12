package vmodev.base.mvpbase;

/**
 * Created by thanhle on 1/11/17.
 */

public interface MvpView {
    MvpPresenter getPresenter();

    int getLayoutMain();

    void showProgress();

    void hideProgress();

    void showMessage(String message);

    void showMessage(int messageId);

    void cancelProgress(boolean cancel);

    void setCancelableLoading(boolean cancelLoading);

}
