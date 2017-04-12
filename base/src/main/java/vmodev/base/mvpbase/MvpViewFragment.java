package vmodev.base.mvpbase;

import android.os.Bundle;
import android.view.View;

/**
 * Created by thanhle on 11/11/2016.
 */

public interface MvpViewFragment extends MvpView{
    void findViewByIds(View view);

    void setEvents(View view);

    void initComponents(View view);

//    BaseActivity getMvpActivity();

    void reload(Bundle bundle);
}
