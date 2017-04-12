package vmodev.mvp_baseapp.ui.screens.login;

import rx.functions.Action1;
import vmodev.base.mvpbase.MvpRxPresenter;
import vmodev.base.repository.DataRepository;
import vmodev.base.repository.enity.Profile;
import vmodev.base.utils.StringUtil;

/**
 * Created by thanhle on 3/21/17.
 */

public class LoginPresenter extends MvpRxPresenter<Login.View> implements Login.Presenter {
    @Override
    public void login(String email, String password, final Action1<Profile.ProfileBean> success, final Action1<String> error) {
        subscribe(DataRepository.getInstance().login(email, password), new Action1<Profile>() {
            @Override
            public void call(Profile profile) {
                if(profile.getError() == null || StringUtil.isEmptyString(profile.getError()))
                    success.call(profile.getTable().get(0));
                else
                    error.call(profile.getError());
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                error.call(throwable.getMessage());
            }
        });
    }
}
