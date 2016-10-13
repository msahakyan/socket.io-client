package com.android.msahakyan.ottonovaclient.fragment;

import android.support.v4.app.Fragment;

/**
 * @author msahakyan
 */

public interface NavigationManager {

    void showLoginFragment();

    void showChatFragment(String username);

    Fragment getCurrentFragment();

    void onBackPress();
}
