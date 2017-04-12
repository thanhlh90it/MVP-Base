package vmodev.base.mvpbase;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;
import vmodev.base.utils.Log;

/**
 * Created by thanhle on 11/1/16.
 */
public class MvpRxPresenter<V extends MvpView> extends MvpPresenter<V> {
    private final CompositeSubscription mSubscriptions = new CompositeSubscription();

    protected  <T> Subscription subscribe(final Observable<T> observable, final Subscriber<T> subscriber) {
        if (observable == null) return null;
        Subscription subscription = observable.subscribe(subscriber);
        mSubscriptions.add(subscription);
        return subscription;
    }

    protected <T> Subscription subscribe(final Observable<T> observable, final Action1<T> onNext, final Action1<Throwable> onError) {
        if (observable == null) return null;
        return subscribe(observable, new Subscriber<T>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (onError != null) {
                    e.printStackTrace();
                    onError.call(e);
                }
            }

            @Override
            public void onNext(T t) {
                if (onNext != null) {
                    onNext.call(t);
                }
            }
        });
    }

    public <T> Subscription subscribe(final Observable<T> observable, final Action1<T> onNext) {
        if (observable == null) return null;
        return subscribe(observable, onNext, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.RX_ANDROID.e(throwable.getMessage());
                if (getView() != null) getView().showMessage(throwable.getMessage());
            }
        });
    }

}
