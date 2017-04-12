package vmodev.mvp_baseapp.ui.screens.login;

import rx.functions.Action1;
import vmodev.base.mvpbase.MvpView;

/**
 * Created by thanhle on 3/21/17.
 */

public interface Login {
    interface View extends MvpView {
        void gotoHome();
        //void loginSuccess(Profile.ProfileBean profileBean);
        void loginSuccess();
        void loadError(String message);
    }
    interface Presenter {
        void login(String email, String password, Action1<Profile.ProfileBean> success, Action1<String> error);
    }
}
