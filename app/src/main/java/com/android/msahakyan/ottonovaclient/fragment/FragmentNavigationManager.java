package com.android.msahakyan.ottonovaclient.fragment;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.android.msahakyan.ottonovaclient.BuildConfig;
import com.android.msahakyan.ottonovaclient.R;
import com.android.msahakyan.ottonovaclient.activity.MainActivity;

import timber.log.Timber;

/**
 * @author msahakyan
 */

public class FragmentNavigationManager implements NavigationManager {

    private static final int LEAVE_APP_INTERVAL = 2000;

    private static FragmentNavigationManager sInstance;

    private FragmentManager mFragmentManager;
    private MainActivity mActivity;
    private long mLastBackPressed;

    public static FragmentNavigationManager obtain(MainActivity activity) {
        if (sInstance == null) {
            sInstance = new FragmentNavigationManager();
        }
        sInstance.configure(activity);
        return sInstance;
    }

    private void configure(MainActivity activity) {
        mActivity = activity;
        mFragmentManager = mActivity.getSupportFragmentManager();
    }

    @Override
    public void showLoginFragment() {
        showFragment(LoginFragment.newInstance(), true, true);
    }

    @Override
    public void showChatFragment(String username) {
        showFragment(ChatFragment.newInstance(username), false, false);
    }

    private void showFragment(Fragment fragment, boolean allowStateLoss, boolean clearStack) {
        FragmentManager fm = mFragmentManager;

        if (clearStack) {
            clearBackStack(fm);
        }

        @SuppressLint("CommitTransaction")
        FragmentTransaction ft = fm.beginTransaction()
            .replace(R.id.container, fragment);

        ft.addToBackStack(null);

        if (allowStateLoss || !BuildConfig.DEBUG) {
            ft.commitAllowingStateLoss();
        } else {
            ft.commit();
        }

        fm.executePendingTransactions();
    }

    @Override
    public Fragment getCurrentFragment() {
        return mFragmentManager.findFragmentById(R.id.container);
    }

    @Override
    public void onBackPress() {
        int backStackSize = mFragmentManager.getBackStackEntryCount();

        // If there is only one fragment left, then show toast for exit
        if (backStackSize <= 1) {
            long currentTime = System.currentTimeMillis();
            if (mLastBackPressed + LEAVE_APP_INTERVAL > currentTime) {
                mActivity.finish();
            } else {
                Toast.makeText(mActivity, R.string.toast_back_to_exit, Toast.LENGTH_SHORT).show();
                mLastBackPressed = currentTime;
            }
            return;
        }

        mActivity.pressBack();
    }

    private void clearBackStack(FragmentManager fragmentManager) {
        try {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } catch (Exception e) {
            Timber.e(e.getMessage());
        }
    }
}
