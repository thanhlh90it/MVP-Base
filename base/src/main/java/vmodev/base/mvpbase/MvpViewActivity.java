package vmodev.base.mvpbase;

/**
 * Created by thanhle on 11/11/2016.
 */

public interface MvpViewActivity extends MvpView{
    void setViewRoot();

    void findViewByIds();

    void setEvents();

    void initComponents();
}
