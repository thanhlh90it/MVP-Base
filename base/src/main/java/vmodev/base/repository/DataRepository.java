package vmodev.base.repository;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import vmodev.base.common.Constants;
import vmodev.base.repository.remote.ApiService;
import vmodev.base.repository.remote.RemoteDataSource;
import vmodev.base.repository.remote.ServiceFactory;

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


}
