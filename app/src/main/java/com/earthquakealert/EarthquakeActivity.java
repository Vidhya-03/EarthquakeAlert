package com.earthquakealert;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.earthquakealert.fragment.EarthquakeDetailsFragment;
import com.earthquakealert.fragment.EarthquakeTypeFragment;
import com.earthquakealert.fragment.SplashFragment;
import com.earthquakealert.helper.ServerRequestTask;
import com.earthquakealert.interfaces.ServerRequestListener;
import com.earthquakealert.util.ApplicationGlobalDataManager;

public class EarthquakeActivity extends AppCompatActivity implements ServerRequestListener {

    ServerRequestReceiver _receiver;
    private static ProgressDialog _progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showFragment(new SplashFragment(), SplashFragment.class.getName(), false);
        getSupportActionBar().hide();
        registerReceiver();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getSupportActionBar().show();
                showFragment(new EarthquakeTypeFragment(), EarthquakeTypeFragment.class.getName(), false);
            }
        }, 2000);

    }

    public void showFragment(Fragment fragment, String fragmentTag, boolean addToStack) {
        if (this.isFinishing()) {
            return;
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (addToStack) {
            ft.addToBackStack(fragmentTag);
        }
        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        ft.replace(R.id.frameLayout, fragment, fragmentTag);
        ft.commit();
    }

    private void registerReceiver() {
        IntentFilter filter = new IntentFilter(ServerRequestReceiver.PROCESS_RESPONSE);
        _receiver = new ServerRequestReceiver();
        registerReceiver(_receiver, filter);
    }

    private void showLoadingDialog() {
        if (_progressDialog != null && _progressDialog.isShowing()) {
            return;
        }
        _progressDialog  = ProgressDialog.show(this, null, "Loading...", false, false);
    }

    private void dismissLoadingDialog() {
        if (_progressDialog != null && _progressDialog.isShowing()) {
            _progressDialog.dismiss();
        }
    }

    @Override
    public void fetchInfo() {
        if(isOnline(this)) {
            String defaultUrlPrefix = "http://www.earthquake.usgs.gov/earthquakes/feed/v1.0/summary/";
            String urlParam = ApplicationGlobalDataManager.getInstace().getMagnitude()+"_"+"day";
            Intent intent = new Intent(EarthquakeActivity.this, ServerRequestTask.class);
            intent.putExtra("url", defaultUrlPrefix+urlParam+".geojson");
            showLoadingDialog();
            startService(intent);
        } else {
            showToast(getString(R.string.err_offline));
        }
    }

    private boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public class ServerRequestReceiver extends BroadcastReceiver {

        public static final String PROCESS_RESPONSE = "PROCESS_RESPONSE";

        @Override
        public void onReceive(Context context, Intent intent) {
            dismissLoadingDialog();
            String response = intent.getStringExtra(ServerRequestTask.RESPONSE_STRING);
            if(response.equals("datareceived")) {
                EarthquakeDetailsFragment earthquakeDetailsFragment = (EarthquakeDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.frameLayout);
                earthquakeDetailsFragment.refresh(ApplicationGlobalDataManager.getInstace().getEathquakeDetails());
            }
        }
    }

    @Override
    protected void onDestroy() {
        this.unregisterReceiver(_receiver);
        dismissLoadingDialog();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getFragmentManager();
        if(fragmentManager.getBackStackEntryCount() > 0)
        {
            fragmentManager.popBackStack();
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
