package com.medicard.ipc.queuecoorv16.drawer;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.medicard.ipc.queuecoorv16.utilities.Globals;
import com.medicard.ipc.queuecoorv16.Mem_Activity;
import com.medicard.ipc.queuecoorv16.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

/**
 * Created by IPC on 8/4/2017.
 */

public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;

    // needed for communication to bluetooth device / network
    static OutputStream mmOutputStream;
    InputStream mmInputStream;
    Thread workerThread;

    byte[] readBuffer;
    int readBufferPosition;
    volatile boolean stopWorker;

    private Toolbar mActionBarToolbar;
    private DrawerLayout mDrawerLayout;
    protected NavigationView mNavigationView;
    private ActionBarDrawerToggle mToggle;

    final Globals sharedData = Globals.getInstance();

    ViewSwitcher switcher;
    SwitchCompat nav_switch_txtreg,nav_switch_txtcoor,nav_switch_txtreminder,nav_switch_bluetooth;




    /**
     * Helper method that can be used by child classes to
     * specify that they don't want a {@link Toolbar}
     * @return true
     */
    protected boolean useToolbar() {
        return true;
    }


    /**
     * Helper method to allow child classes to opt-out of having the
     * hamburger menu.
     * @return
     */
    protected boolean useDrawerToggle() {
        return true;
    }




    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        getActionBarToolbar();

        setupNavDrawer();
//        switches ();
    }//end setContentView


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    protected Toolbar getActionBarToolbar() {
        if (mActionBarToolbar == null) {
            mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
            if (mActionBarToolbar != null) {
                // Depending on which version of Android you are on the Toolbar or the ActionBar may be
                // active so the a11y description is set here.
                mActionBarToolbar.setNavigationContentDescription(getResources()
                        .getString(R.string.navdrawer_description_a11y));
                //setSupportActionBar(mActionBarToolbar);

                if (useToolbar()) { setSupportActionBar(mActionBarToolbar);
                } else { mActionBarToolbar.setVisibility(View.GONE); }

            }
        }

        return mActionBarToolbar;
    }



    private void setupNavDrawer() {


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (mDrawerLayout == null) {
            return;
        }


        // use the hamburger menu
        if( useDrawerToggle()) {
            mToggle = new ActionBarDrawerToggle(
                    this, mDrawerLayout, mActionBarToolbar,
                    R.string.navigation_drawer_open,
                    R.string.navigation_drawer_close);
            mDrawerLayout.setDrawerListener(mToggle);
            mToggle.syncState();
        }
        else if(useToolbar() && getSupportActionBar() != null) {
            // Use home/back button instead
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(ContextCompat
                    .getDrawable(this, R.drawable.abc_ic_ab_back_material));
        }


        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        switches ();
        mNavigationView.setNavigationItemSelectedListener(this);

    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {


        switcher = (ViewSwitcher) findViewById(R.id.ViewSwitcher);
        Animation animIn = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in);
        Animation animOut = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);
        switcher.setInAnimation(animIn);
        switcher.setOutAnimation(animOut);

        int id = item.getItemId();
        switch (id) {
            case R.id.nav_queue_list:
                new AnimationUtils();
                if(sharedData.getVswitcher().toString().equals("0")){
                    switcher.showNext();
                    sharedData.setVswitcher("1");
                }else {

                }
                break;
            case R.id.nav_served_list:
                new AnimationUtils();
                if(sharedData.getVswitcher().toString().equals("1")){
                    switcher.showNext();
                    sharedData.setVswitcher("0");
                }else {

                }
                break;
            case R.id.nav_home:
                createBackStack(new Intent(this, Mem_Activity.class));
                break;

        }

        closeNavDrawer();
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_left);

        return true;
    }


    protected boolean isNavDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START);
    }

    protected void closeNavDrawer() {
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
    }



    /**
     * Enables back navigation for activities that are launched from the NavBar. See
     * {@code AndroidManifest.xml} to find out the parent activity names for each activity.
     * @param intent
     */
    private void createBackStack(Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            TaskStackBuilder builder = TaskStackBuilder.create(this);
            builder.addNextIntentWithParentStack(intent);
            builder.startActivities();
        } else {
            startActivity(intent);
            finish();
        }
    }

    public static boolean openApp(Context context, String packageName) {
        PackageManager manager = context.getPackageManager();
        Intent i = manager.getLaunchIntentForPackage(packageName);
        if (i == null) {
            return false;
            //throw new PackageManager.NameNotFoundException();
        }
        i.addCategory(Intent.CATEGORY_LAUNCHER);
        context.startActivity(i);
        return true;
    }


    public void switches (){
        //switch for bluetooth
        sharedData.setVswitcher("1");
        Menu menu = mNavigationView.getMenu();
        MenuItem menuItem = menu.findItem(R.id.nav_switch_bluetooth);
        nav_switch_bluetooth = (SwitchCompat)MenuItemCompat.getActionView(menuItem).findViewById(R.id.switch_bluetooth);
        nav_switch_bluetooth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                }
            }
        });

        final Globals sharedData = Globals.getInstance();
        //switch options for the 3 sms
        Menu menu1 = mNavigationView.getMenu();
        MenuItem menuItem1 = menu1.findItem(R.id.nav_switch_txtreg);
        nav_switch_txtreg = (SwitchCompat)MenuItemCompat.getActionView(menuItem1).findViewById(R.id.switch_register);
        nav_switch_txtreg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sharedData.setTxtReg("allow");
                    Toast.makeText(getApplicationContext(), "allow",
                            Toast.LENGTH_LONG).show();
                    nav_switch_txtreg.setChecked(true);
                } else {
                    Toast.makeText(getApplicationContext(), "disallow",
                            Toast.LENGTH_LONG).show();
                    sharedData.setTxtReg("disallow");
                }
            }
        });
        Menu menu2 = mNavigationView.getMenu();
        MenuItem menuItem2 = menu2.findItem(R.id.nav_switch_txtcoor);
        nav_switch_txtcoor = (SwitchCompat)MenuItemCompat.getActionView(menuItem2).findViewById(R.id.switch_coordinator);
        nav_switch_txtcoor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sharedData.setTxtCoor("allow");
                    Toast.makeText(getApplicationContext(), "allow",
                            Toast.LENGTH_LONG).show();
                    nav_switch_txtcoor.setChecked(true);
                } else {
                    Toast.makeText(getApplicationContext(), "disallow",
                            Toast.LENGTH_LONG).show();
                    sharedData.setTxtCoor("disallow");
                }
            }
        });
        Menu menu3 = mNavigationView.getMenu();
        MenuItem menuItem3 = menu3.findItem(R.id.nav_switch_txtreminder);
        nav_switch_txtreminder = (SwitchCompat)MenuItemCompat.getActionView(menuItem3).findViewById(R.id.switch_reminder);
        nav_switch_txtreminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sharedData.setTxtReminder("allow");
                    Toast.makeText(getApplicationContext(), "allow",
                            Toast.LENGTH_LONG).show();
                    nav_switch_txtreminder.setChecked(true);
                } else {
                    Toast.makeText(getApplicationContext(), "disallow",
                            Toast.LENGTH_LONG).show();
                    sharedData.setTxtReminder("disallow");
                }
            }
        });

    }

    //printing experimental
    // this will find a bluetooth printer device
    void findBT() {

        try {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            if(mBluetoothAdapter == null) {
                Toast.makeText(getApplicationContext(), "No bluetooth adapter available",
                        Toast.LENGTH_LONG).show();
            }

            if(!mBluetoothAdapter.isEnabled()) {
                Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBluetooth, 0);
            }

            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

            if(pairedDevices.size() > 0) {
                for (BluetoothDevice device : pairedDevices) {

                    // RPP300 is the name of the bluetooth printer device
                    // we got this name from the list of paired devices
                    if (device.getName().equals("HP-M200E_9044")) {
                        mmDevice = device;
                        break;
                    }
                }
            }
            Toast.makeText(getApplicationContext(), "Bluetooth device found.",
                    Toast.LENGTH_LONG).show();


        }catch(Exception e){
            e.printStackTrace();
        }
    }


    // close the connection to bluetooth printer.
    void closeBT() throws IOException {
        try {
            stopWorker = true;
            mmOutputStream.close();
            mmInputStream.close();
            mmSocket.close();
            Toast.makeText(getApplicationContext(), "Bluetooth Closed",
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}//end BaseActivity

