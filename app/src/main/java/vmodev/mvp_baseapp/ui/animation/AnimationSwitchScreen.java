package vmodev.mvp_baseapp.ui.animation;
import vmodev.mvp_baseapp.R;

/**
 * Created by thanhle on 01/11/2016.
 */
public enum AnimationSwitchScreen {
    FULL_OPEN(R.anim.enter_from_right, R.anim.exit_from_right, R.anim.enter_from_left, R.anim.exit_from_left),
    FULL_BACK(R.anim.enter_from_left, R.anim.exit_from_left, R.anim.enter_from_right, R.anim.exit_from_right),
    ONLY_OPEN(R.anim.enter_from_right, R.anim.exit_from_right, 0, 0),
    ONLY_EXIT(0, 0, R.anim.enter_from_left, R.anim.exit_from_left),
    EMPTY(0, 0, 0, 0);

    private int openEnter;
    private int openExit;
    private int closeEnter;
    private int closeExit;

    private AnimationSwitchScreen(int openEnter, int openExit, int closeEnter, int closeExit) {
        this.openEnter = openEnter;
        this.openExit = openExit;
        this.closeEnter = closeEnter;
        this.closeExit = closeExit;
    }

    public int getOpenEnter() {
        return openEnter;
    }

    public int getOpenExit() {
        return openExit;
    }

    public int getCloseEnter() {
        return closeEnter;
    }

    public int getCloseExit() {
        return closeExit;
    }
}
