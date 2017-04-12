package vmodev.base.repository;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import vmodev.base.common.Constants;
import vmodev.base.repository.enity.Profile;
import vmodev.base.repository.remote.ApiService;
import vmodev.base.repository.remote.RemoteDataSource;
import vmodev.base.repository.remote.ServiceFactory;
import vmodev.base.utils.StringUtil;

/**
 * Created by thanhle on 8/2/16.
 */
public class DataRepository implements IDataSource {
    private static final String TAG = DataRepository.class.getSimpleName();
    private final IRemoteDataSource mRemoteDataSource;
    static public IDataSource instance;

    public DataRepository(@NonNull IRemoteDataSource remoteDataSource) {
        mRemoteDataSource = remoteDataSource;
    }

    static public IDataSource getInstance(){
        if(instance == null) {
            instance = new DataRepository(new RemoteDataSource(ServiceFactory.createServiceFrom(ApiService.class, Constants.REMOTE_SERVICE_URL)));
        }
        return instance;
    }


    public IRemoteDataSource getRemoteDataSource() {
        return mRemoteDataSource;
    }

    @Override
    public Observable<Profile> login(String email, String password) {
        Observable<Profile> remote = mRemoteDataSource.login(email, password)
                .doOnNext(new Action1<Profile>() {
                    @Override
                    public void call(Profile profile) {


                    }
                });
        return remote;
    }


}
