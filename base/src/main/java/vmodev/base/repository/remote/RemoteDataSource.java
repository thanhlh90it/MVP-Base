package vmodev.base.repository.remote;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import vmodev.base.repository.IRemoteDataSource;
import vmodev.base.repository.enity.Profile;


/**
 * Created by thanhle on 8/2/16.
 */
public class RemoteDataSource implements IRemoteDataSource {

    ApiService mService;

    public RemoteDataSource (ApiService apiService) {
        mService = apiService;
    }


    public Observable<Profile> login(String email, String password) {
        return mService.login("login",email, password)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
