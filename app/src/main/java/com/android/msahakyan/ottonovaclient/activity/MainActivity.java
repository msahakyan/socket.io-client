package com.android.msahakyan.ottonovaclient.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.android.msahakyan.ottonovaclient.R;
import com.android.msahakyan.ottonovaclient.fragment.FragmentNavigationManager;
import com.android.msahakyan.ottonovaclient.fragment.NavigationManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * @author msahakyan
 */
public class MainActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private NavigationManager mFragmentNavManager;
    private MenuItem randomCommandItem;
    private MenuItem moreCommandsItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);

        mFragmentNavManager = FragmentNavigationManager.obtain(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        mFragmentNavManager.showLoginFragment();
    }

    public void pressBack() {
        super.onBackPressed();
    }

    @Override
    public void onBackPressed() {
        mFragmentNavManager.onBackPress();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        randomCommandItem = menu.findItem(R.id.item_generate);
        moreCommandsItem = menu.findItem(R.id.item_more);

        return true;
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                Timber.d("Home pressed");
                mFragmentNavManager.onBackPress();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void showMenuItems(boolean show) {
        if (randomCommandItem != null) {
            randomCommandItem.setVisible(show);
        }
//        if (moreCommandsItem != null) {
//            moreCommandsItem.setVisible(show);
//        }
    }

    public void setTitle(String text) {
        ActionBar toolbar = getSupportActionBar();
        if (toolbar != null) {
            toolbar.setTitle(text);
        }
    }
}
