package vmodev.base.repository;

import rx.Observable;
import vmodev.base.repository.enity.Profile;

/**
 * Created by thanhle on 8/2/16.
 */
public interface IRemoteDataSource {
    Observable<Profile> login(String email, String password);
}

