package vmodev.mvp_baseapp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import java.util.List;

import vmodev.base.mvpbase.MvpViewFragment;
import vmodev.mvp_baseapp.commom.MyApplication;
import vmodev.mvp_baseapp.ui.animation.AnimationSwitchScreen;


/**
 * Created by thanhle on 11/1/16.
 */
public abstract class BaseMvpFragment extends Fragment implements MvpViewFragment, Animation.AnimationListener {
    private View mRootView;

    private static final String TAG = BaseMvpFragment.class.getSimpleName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ( getPresenter() != null ) {
            getPresenter().attachView(this);
        }

    }

    @Override
    public void onDestroy() {
        if ( getPresenter() != null ) {
            getPresenter().detachView();
        }

        super.onDestroy();
    }

    @Override
    public void onStop() {
        super.onStop();
        if ( getPresenter() != null ) {
            getPresenter().onStop();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        if ( getPresenter() != null ) {
            getPresenter().onPause();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if ( getPresenter() != null ) {
            getPresenter().onResume();
        }

    }

    public void attachRootView(View view) {
        mRootView = view;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutMain(), container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViewByIds(view);
        initComponents(view);
        setEvents(view);
    }

    public void showSimpleSnack(final String message) {
//        if (mRootView == null) return;
//        Snackbar snackbar = Snackbar.make(mRootView, message, Snackbar.LENGTH_LONG);
//        snackbar.show();
    }

    /**
     * Implement MvpView interface
     */
    @Override
    public void showProgress() {
        getMvpActivity().showProgress();
    }

    @Override
    public void hideProgress() {
        getMvpActivity().hideProgress();
    }

    @Override
    public void showMessage(String message) {
        getMvpActivity().showMessage(message);
    }

    @Override
    public void showMessage(int messageId) {
        getMvpActivity().showMessage(messageId);
    }

    @Override
    public void cancelProgress(boolean cancel) {
        getMvpActivity().cancelProgress(cancel);
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        if ( getView() == null ) {
            return;
        }
        if (getView().getAnimation() != null) {
            getView().getAnimation().setAnimationListener(this);
        } else {
            getMvpActivity().setIsStartAnimationFragment(false);
        }
    }


    public BaseMvpActivity getMvpActivity() {
        return (BaseMvpActivity) getActivity();

    }

    @Override
    public void reload(Bundle bundle) {

    }

    public boolean isGroupFragment() {
        return false;
    }

    @Override
    public void onAnimationStart(Animation animation) {
        getMvpActivity().setIsStartAnimationFragment(true);
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        getMvpActivity().setIsStartAnimationFragment(false);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }



    public static void openFragment(FragmentManager manager, Class<? extends BaseMvpFragment> clazz, Bundle bundle,
                                    boolean hasAddbackstack, boolean hasCommitTransaction, AnimationSwitchScreen animations,
                                    int fragmentContent) {
        String tag = clazz.getName();
        BaseMvpFragment fragment;
        FragmentTransaction transaction = manager.beginTransaction();
        try {
            //if added backstack
            if (hasAddbackstack) {
                boolean fragmentPopped = manager.popBackStackImmediate(tag , 0);
                if (!fragmentPopped && manager.findFragmentByTag(tag) == null) {
                    fragment = clazz.newInstance();
                    transaction.addToBackStack(tag);
                    fragment.setArguments(bundle);
                    if (animations != null) {
                        transaction.setCustomAnimations(animations.getOpenEnter(), animations.getOpenExit(), animations.getCloseEnter(), animations.getCloseExit());
                    }
                    transaction.replace(fragmentContent, fragment, tag);
                }
            } else {
                if (isFragmentAdded(manager, tag)) {
                    fragment = (BaseMvpFragment) manager.findFragmentByTag(tag);
                    if (animations != null) {
                        transaction.setCustomAnimations(animations.getOpenEnter(), animations.getOpenExit(), animations.getCloseEnter(), animations.getCloseExit());
                    }
                    if (fragment.getMvpActivity() != null) {
                        fragment.getMvpActivity().setIsStartAnimationFragment(true);
                    }
                    transaction.show(fragment);
                } else {
                    fragment = clazz.newInstance();
                    fragment.setArguments(bundle);
                    if (animations != null) {
                        transaction.setCustomAnimations(animations.getOpenEnter(), animations.getOpenExit(), animations.getCloseEnter(), animations.getCloseExit());
                    }
                    transaction.add(fragmentContent, fragment, tag);
                }
            }

            if (hasCommitTransaction) {
                transaction.commit();
            }


//            BaseMvpFragment currentFragment = getCurrenFragment(manager);
//            if (currentFragment != null) {
//                if (animations != null) {
//                    transaction.setCustomAnimations(animations.getOpenEnter(), animations.getOpenExit(),
//                            animations.getCloseEnter(), animations.getCloseExit()).hide(currentFragment);
//                } else {
//                    transaction.setCustomAnimations(AnimationSwitchScreen.FULL_OPEN.getOpenEnter(), AnimationSwitchScreen.FULL_OPEN.getOpenExit(),
//                            AnimationSwitchScreen.FULL_OPEN.getCloseEnter(), AnimationSwitchScreen.FULL_OPEN.getCloseExit()).hide(currentFragment);
//                }
//            }
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void hideFragment(FragmentManager manager,
                                    FragmentTransaction transaction, AnimationSwitchScreen animation,
                                    boolean hasAddBackstack, boolean hasCommit, String tag) {
        BaseMvpFragment fragment = (BaseMvpFragment) manager.findFragmentByTag(tag);
        if (fragment != null && !fragment.isVisible()) {
            transaction.setCustomAnimations(animation.getOpenEnter(), animation.getOpenExit(), animation.getCloseEnter(), animation.getCloseExit());
            transaction.hide(fragment);
            if (hasAddBackstack) {
                transaction.addToBackStack(tag);
            }
            if (hasCommit) {
                transaction.commit();
            }
        }
    }
//
//    public static void removeFragment(FragmentManager manager, AnimationSwitchScreen animation,
//                                      boolean hasAddBackStack, boolean hasCommit, String tag) {
//        BaseMvpFragment fragment = (BaseMvpFragment) manager.findFragmentByTag(tag);
//        FragmentTransaction transaction = manager.beginTransaction();
//        if (fragment != null) {
//            transaction.setCustomAnimations(animation.getOpenEnter(), animation.getOpenExit(), animation.getCloseEnter(), animation.getCloseExit());
//            transaction.remove(fragment);
//            if (hasAddBackStack) {
//                transaction.addToBackStack(tag);
//            }
//            if (hasCommit) {
//                transaction.commit();
//            }
//        }
//    }
//
    private static boolean isFragmentAdded(FragmentManager manager, String tag) {
        List<Fragment> fragmentList = manager.getFragments();
        if (fragmentList != null) {
            if (fragmentList.size() > 0) {
                for (Fragment fragment : fragmentList) {
                    if (fragment != null) {
                        if (fragment.getClass().getName().equals(tag))
                            return true;
                    }
                }
            }
        }
        return false;
    }
//
//    public static BaseMvpFragment getCurrenFragment(FragmentManager fragmentManager) {
//        List<Fragment> frags = fragmentManager.getFragments();
//        if (frags != null) {
//            for (Fragment fr : frags) {
//                if (fr != null && fr.isVisible() && fr.getTag() != null) {
//                    return (BaseMvpFragment) fr;
//                }
//            }
//        }
//
//        return null;
//    }
//
//    @Override
//    public void setCancelableLoading(boolean cancleLoading) {
//        getMvpActivity().setCancelableLoading(cancleLoading);
//    }
//
//    public static void removeAllFragment(FragmentTransaction transaction, FragmentManager manager, AnimationSwitchScreen animation,
//                                         String showFragmentName, boolean hasCommit) {
//
//        int count = manager.getBackStackEntryCount();
//        if (count > 0) {
//            for (int i = count - 1; i >= 0; i--) {
//                manager.popBackStack();
//            }
//        }
//
//        List<Fragment> fragments = manager.getFragments();
//        int countFragment = fragments.size();
//        for (int i = 0; i < countFragment; i++) {
//            Fragment fragment = fragments.get(i);
//            if (fragment == null) {
//                continue;
//            }
//            if (!fragment.getClass().getName().equals(showFragmentName)) {
//                transaction.setCustomAnimations(AnimationSwitchScreen.FULL_BACK.getOpenEnter(), AnimationSwitchScreen.FULL_BACK.getOpenExit())
//                        .remove(fragment);
//            } else {
//                transaction.setCustomAnimations(AnimationSwitchScreen.FULL_BACK.getOpenEnter(), AnimationSwitchScreen.FULL_BACK.getOpenExit())
//                        .show(fragment);
//            }
//
//        }
//
//        if (hasCommit) {
//            transaction.commit();
//        }
//
//    }

    public BaseMvpActivity getBaseActivity() {
        return (BaseMvpActivity)getActivity();
    }

    public MyApplication getApp() {
        return (MyApplication) getActivity().getApplication();
    }

    public boolean processBackPressed() {
        return false;
    }


}
