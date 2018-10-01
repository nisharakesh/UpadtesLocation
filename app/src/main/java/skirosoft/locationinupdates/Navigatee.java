package skirosoft.locationinupdates;

import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Navigatee extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,android.location.LocationListener {


    SharedPreferences pref,mypref,mygold,mypetrolrate,myPrefs,share;
    SharedPreferences sp;
    ListView listview;
    private TextView latitudePosition;
    private TextView longitudePosition;
    private TextView currentCity;
    private LocationManager locationManager;
    private Location location;
    private final int REQUEST_LOCATION = 200;
    Button btn,btn1;
    public static final String TAG = "FirstPage";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigatee);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




        listview= (ListView) findViewById(R.id.list);
        btn=(Button)findViewById(R.id.btn);
       // btn1=(Button)findViewById(R.id.updte);
        latitudePosition = (TextView) findViewById(R.id.latitude);
        longitudePosition = (TextView) findViewById(R.id.longitude);
        currentCity = (TextView) findViewById(R.id.city);
        locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Navigatee.this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }
        else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,100,2,this);
            if (locationManager != null) {
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
        }
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (location != null) {
                latitudePosition.setText(String.valueOf(location.getLatitude()));
                longitudePosition.setText(String.valueOf(location.getLongitude()));
                getAddressFromLocation(location, getApplicationContext(), new GeoCoderHandler());
            }
        } else {
            showGPSDisabledAlertToUser();
        }
        share=getApplication().getSharedPreferences("Location", MODE_PRIVATE);
        String location=share.getString("loc","");
        currentCity.setText(location);
        pref=getApplication().getSharedPreferences("Options", MODE_PRIVATE);
        String gen=pref.getString("rain", "");
        String iti=pref.getString("timer", "");
        mypref=getApplication().getSharedPreferences("Road", MODE_PRIVATE);
        String r=mypref.getString("road", "");
        String p=mypref.getString("timer1", "");
        mygold=getApplication().getSharedPreferences("Goldrate", MODE_PRIVATE);
        String q=mygold.getString("timer2", "");
        String g=mygold.getString("gold", "");
        mypetrolrate=getApplication().getSharedPreferences("Petrolrate", MODE_PRIVATE);
        String s=mypetrolrate.getString("timer3", "");
        String l=mypetrolrate.getString("date3", "");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Navigatee.this,MapsActivity.class);
                startActivity(intent);
            }
        });


      /*  btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Navigatee.this,MainActivity.class);
                startActivity(i);
            }
        });*/

        HashMap<String,String> name= new HashMap<>();
        name.put(pref.getString("rain",""),pref.getString("timer",""));
        name.put(mypref.getString("road",""),mypref.getString("timer1",""));
        name.put(mygold.getString("timer2",""),mygold.getString("gold",""));
        name.put(mypetrolrate.getString("timer3",""),mypetrolrate.getString("date3",""));
        List<HashMap<String,String>> listitems = new ArrayList<>();
        SimpleAdapter adapter = new SimpleAdapter(this,listitems,R.layout.list_item,new String[]{"first","second"},new int[]{R.id.textView1,R.id.textView2});
        Iterator it=name.entrySet().iterator();
        while ( it.hasNext())


        {
            HashMap<String,String> result=new HashMap<>();
            Map.Entry pair =(Map.Entry)it.next();
            result.put("first", pair.getKey().toString());
            result.put("second", pair.getValue().toString());
            listitems.add(result);


        }
        listview.setAdapter(adapter);


    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigatee, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Intent log = new Intent(Navigatee.this,MapsActivity.class);
            startActivity(log);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            Intent log = new Intent(Navigatee.this,RainUpdates.class);
            startActivity(log);
        } else if (id == R.id.nav_gallery) {
            Intent log = new Intent(Navigatee.this,RoadUpdates.class);
            startActivity(log);

        } else if (id == R.id.nav_slideshow) {
            Intent log = new Intent(Navigatee.this,GoldUpdates.class);
            startActivity(log);

        } else if (id == R.id.nav_manage) {
            Intent log = new Intent(Navigatee.this,PetrolUpdates.class);
            startActivity(log);

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    @Override
    public void onLocationChanged(Location location) {
        latitudePosition.setText(String.valueOf(location.getLatitude()));
        longitudePosition.setText(String.valueOf(location.getLongitude()));
        getAddressFromLocation(location, getApplicationContext(), new Navigatee. GeoCoderHandler());
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
        if (provider.equals(LocationManager.GPS_PROVIDER)) {
            showGPSDisabledAlertToUser();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            }
        }
    }

    private void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Goto Settings Page To Enable GPS", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(callGPSSettingIntent);
                    }
                });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    public static void getAddressFromLocation(final Location location, final Context context, final Handler handler) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                String result = null;
                try {
                    List<Address> list = geocoder.getFromLocation(
                            location.getLatitude(), location.getLongitude(), 1);
                    if (list != null && list.size() > 0) {
                        Address address = list.get(0);
                        // sending back first address line and locality
                        result = address.getAddressLine(0) + ", " + address.getLocality() + ", " + address.getCountryName();
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Impossible to connect to Geocoder", e);
                } finally {
                    Message msg = Message.obtain();
                    msg.setTarget(handler);
                    if (result != null) {
                        msg.what = 1;
                        Bundle bundle = new Bundle();
                        bundle.putString("address", result);
                        msg.setData(bundle);
                    } else
                        msg.what = 0;
                    msg.sendToTarget();
                }
            }
        };
        thread.start();
    }

    private class GeoCoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String result;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    result = bundle.getString("address");
                    break;
                default:
                    result = null;
            }
            //  currentCity.setText(result);
        }
    }

}
