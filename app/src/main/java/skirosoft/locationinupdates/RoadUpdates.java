package skirosoft.locationinupdates;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RoadUpdates extends AppCompatActivity {

    TextView textView2,textView3,textView1,txtCurrentTime,txtdate;
    EditText ed;
    Button button1;
    RadioButton rad1, rad2;
    String selected;
    Spinner dropdown ;
    ListView listview;
    ArrayList<String> list=new ArrayList<>();
    String[] SPINNER_DATA = {"Road Block","Traffic on stand still","Pothholes","Accident happened","Event along the road","Political meeting on the road"};
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mRootReference1 = firebaseDatabase.getReference();
    private  DatabaseReference mHeadingRefence=mRootReference1.child("Road Updates");
    DatabaseReference databaseRoad = FirebaseDatabase.getInstance().getReference("Road Updates");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_road_updates);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("Road", MODE_PRIVATE);
        final SharedPreferences.Editor editor=pref.edit();
        Thread myThread = null;
        Runnable myRunnableThread = new CountDownRunner();
        myThread= new Thread(myRunnableThread);
        myThread.start();
        textView2=(TextView)findViewById(R.id.textView2);// text to show latitude
        textView3=(TextView)findViewById(R.id.textView3);// text to show longitude
        textView1=(TextView)findViewById(R.id.textView1);// text to show addresses
       // ed=(EditText) findViewById(R.id.ed_descp);
        txtCurrentTime=(TextView)findViewById(R.id.textView5);
        txtdate=(TextView)findViewById(R.id.txtdate);
        rad1 = (RadioButton) findViewById(R.id.radioY);
        rad2 = (RadioButton) findViewById(R.id.radioN);
        button1=(Button)findViewById(R.id.button1);
        listview=(ListView)findViewById(R.id.listview);
        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,list);
        listview.setAdapter(adapter);
         list.clear();
        // click the below to get the current location and address.
        button1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (rad1.isChecked()) {
                    selected = rad1.getText().toString();
                } else if (rad2.isChecked()) {

                    selected = rad2.getText().toString();
                }

               /* listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position,
                                            long id) {
                        String item = ((TextView)view).getText().toString();Toast.makeText(getBaseContext(), item, Toast.LENGTH_LONG).show();
                    }
                });*/
                turnGPSOn(); // method to turn on the GPS if its in off state.
                String flightlev = textView1.getText().toString().trim();
                String flightlgo = textView2.getText().toString().trim();
                String flightdept = textView3.getText().toString().trim();
                //String flightretn = ed.getText().toString().trim();
                String flightpasn = txtCurrentTime.getText().toString().trim();
                String flightpasncls = dropdown.getSelectedItem().toString().trim();
                String flightpasndate = txtdate.getText().toString().trim();
               // list.add(flightpasncls+ '@' + flightpasn);
                String iti=flightpasndate+'\n'+flightpasn;
                String id = databaseRoad.push().getKey();
                Flights flights = new Flights(flightlev, flightlgo, flightdept,flightpasn,flightpasncls,selected,flightpasndate);
                databaseRoad.child(id).setValue(flights);
                String r="    "+"ROAD UPDATES"+'\n'+dropdown.getSelectedItem().toString();
                // String and=android.getSelectedItem().toString();
                flightpasn= txtCurrentTime.getText().toString().trim();
                flightpasndate= txtdate.getText().toString().trim();
                editor.putString("timer1",iti);
                editor.putString("date1",flightpasndate);
                editor.putString("road",r);
                //editor.putString("android", and);
                editor.commit();
                if(dropdown.getSelectedItem().toString().equals("")){
                    Toast.makeText(getBaseContext(),"field is empty",Toast.LENGTH_LONG).show();
                }
                else{
                    Intent in=new Intent(RoadUpdates.this,Navigatee.class);
                    startActivity(in);
                }
            }
        });
        getMyCurrentLocation();
        dropdown = (Spinner)findViewById(R.id.material_spinner2);
        ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(RoadUpdates.this, android.R.layout.simple_dropdown_item_1line, SPINNER_DATA);
        dropdown.setAdapter(adapter5);
        rad1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    rad2.setChecked(false);
                }
            }
        });
        rad2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    rad1.setChecked(false);
                }
            }
        });
        databaseRoad.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String flightlev = textView1.getText().toString().trim();
                String flightlgo = textView2.getText().toString().trim();
                String flightdept = textView3.getText().toString().trim();
               // String flightretn = ed.getText().toString().trim();
                String flightpasn = txtCurrentTime.getText().toString().trim();
                String flightpasncls = dropdown.getSelectedItem().toString().trim();
                String flightpasndate = txtdate.getText().toString().trim();
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void doWork() {
        runOnUiThread(new Runnable() {
            public void run() {
                try{
                    TextView txtCurrentTime= (TextView)findViewById(R.id.textView5);
                    Calendar c = Calendar.getInstance();
                    int seconds = c.get(Calendar.SECOND);
                    int minutes = c.get(Calendar.MINUTE);
                    int hour = c.get(Calendar.HOUR);
                    String time = hour + ":" + minutes + ":" + seconds;
                    int day = c.get(Calendar.DAY_OF_MONTH);
                    int month = c.get(Calendar.MONTH)+1;
                    int year = c.get(Calendar.YEAR);
                    String date = day + "/" + month + "/" + year;
                    txtCurrentTime.setText(time);
                    txtdate.setText(date);
                }catch (Exception e) {}
            }
        });
    }



    private  class CountDownRunner implements Runnable {
        // @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    doWork();
                    Thread.sleep(3000); // Pause of 1 Second
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (Exception e) {
                }
            }
        }
    }
    /** Method to turn on GPS **/

    public void turnGPSOn(){
        try
        {

            String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

            if(!provider.contains("gps")){ //if gps is disabled
                final Intent poke = new Intent();
                poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
                poke.addCategory(Intent.CATEGORY_ALTERNATIVE);            poke.setData(Uri.parse("3"));
                sendBroadcast(poke);
            }
        }
        catch (Exception e) {

        }
    }
    // Method to turn off the GPS
    public void turnGPSOff(){
        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if(provider.contains("gps")){ //if gps is enabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            sendBroadcast(poke);
        }
    }


    // turning off the GPS if its in on state. to avoid the battery drain.

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        turnGPSOff();
    }

    /** Check the type of GPS Provider available at that instance and  collect the location informations
     @Output Latitude and Longitude
      * */
    void getMyCurrentLocation() {

        LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener locListener = new MyLocationListener();
        try{gps_enabled=locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);}catch(Exception ex){}           try{network_enabled=locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);}catch(Exception ex){}
        //don't start listeners if no provider is enabled

        //if(!gps_enabled && !network_enabled)

        //return false;

        if(gps_enabled){
            locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locListener);

        }


        if(gps_enabled){
            location=locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        }

        if(network_enabled && location==null){

            locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locListener);

        }
        if(network_enabled && location==null)    {
            location=locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        }

        if (location != null) {

            MyLat = location.getLatitude();
            MyLong = location.getLongitude();
        } else {
            Location loc= getLastKnownLocation(this);
            if (loc != null) {
                MyLat = loc.getLatitude();
                MyLong = loc.getLongitude();
            }
        }

        locManager.removeUpdates(locListener); // removes the periodic updates from location listener to avoid battery drainage. If you want to get location at the periodic intervals call this method using pending intent.

        try
        {
// Getting address from found locations.
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(this, Locale.getDefault());
            addresses = geocoder.getFromLocation(MyLat, MyLong, 1);
            StateName= addresses.get(0).getAdminArea();
            CityName = addresses.get(0).getLocality();
            CountryName = addresses.get(0).getCountryName();
            Locality = addresses.get(0).getAddressLine(0);

            // you can get more details other than this . like country code, state code, etc.
            System.out.println(" StateName " + StateName);
            System.out.println(" CityName " + CityName);
            System.out.println(" CountryName " + CountryName);
            System.out.println(" CountryName " + Locality);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        textView2.setText(""+MyLat);
        textView3.setText(""+MyLong);
        //textView1.setText(" StateName " + StateName +" CityName " + CityName +
        // " CountryName " + CountryName  +" Locality " + Locality);
        textView1.setText(Locality);
    }

    // Location listener class. to get location.
    public class MyLocationListener implements LocationListener {
        public void onLocationChanged(Location location) {
            if (location != null) {
            }
        }        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub
        }

        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            // TODO Auto-generated method stub
        }
    }
    private boolean gps_enabled=false;
    private boolean network_enabled=false;
    Location location;
    Double MyLat, MyLong;
    String CityName="";
    String StateName="";
    String CountryName="";
    String Locality="";


// below method to get the last remembered location. because we don't get locations all the times .At some instances we are unable to get the location from GPS. so at that moment it will show us the last stored location.

    public static Location getLastKnownLocation(Context context)

    {
        Location location = null;
        LocationManager locationmanager = (LocationManager)context.getSystemService("location");
        List list = locationmanager.getAllProviders();
        boolean i = false;
        Iterator iterator = list.iterator();
        do
        {
            //System.out.println("---------------------------------------------------------------------");
            if(!iterator.hasNext())
                break;
            String s = (String)iterator.next();
            //if(i != 0 && !locationmanager.isProviderEnabled(s))
            if(i != false && !locationmanager.isProviderEnabled(s))
                continue;
            // System.out.println("provider ===> "+s);
            Location location1 = locationmanager.getLastKnownLocation(s);
            if(location1 == null)
                continue;
            if(location != null)
            {
                //System.out.println("location ===> "+location);
                //System.out.println("location1 ===> "+location);
                float f = location.getAccuracy();
                float f1 = location1.getAccuracy();
                if(f >= f1)
                {
                    long l = location1.getTime();
                    long l1 = location.getTime();
                    if(l - l1 <= 600000L)
                        continue;
                }
            }
            location = location1;
            // System.out.println("location  out ===> "+location);
            //System.out.println("location1 out===> "+location);
            i = locationmanager.isProviderEnabled(s);
            // System.out.println("---------------------------------------------------------------------");
        } while(true);
        return location;
    }
}
