package skirosoft.locationinupdates;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.ListActivity;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;



public class RecyclerViewActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "RecyclerViewActivity";
    //SharedPreferences pref;
    String[] presidents;
    ListView listview;
    ArrayAdapter<String> adapter;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    //TextView txt1,txt2;
    ArrayList<String> list=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        listview= (ListView) findViewById(R.id.list);

        pref=getApplication().getSharedPreferences("Options", Context.MODE_PRIVATE);
        String  flightpasn=pref.getString("timer","");
        //String  password=Pref.getString("pwd", null);
       // String[] values = new String[] {flightpasn};
       // ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);



        //listview.setAdapter(adapter);
       // adapter.add("cdsd");
       // list.add("Rain Updates");
        //list.add(flightpasn);
        HashMap<String,String> name= new HashMap<>();
        name.put("rain",flightpasn);
        name.put("road",flightpasn);
        List<HashMap<String,String>> listitems = new ArrayList<>();
        SimpleAdapter adapter = new SimpleAdapter(this,listitems,R.layout.list_item,new String[]{"first","second"},new int[]{R.id.textView1,R.id.textView2});
        Iterator it=name.entrySet().iterator();
        while ( it.hasNext())


        {
            HashMap<String,String> result=new HashMap<>();
            Map.Entry pair =(Map.Entry)it.next();
            result.put("first", pair.getKey().toString());
           result.put("second", pref.getString("timer",""));
            listitems.add(result);

        }
      listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                String item = ((TextView)view).getText().toString();

                Toast.makeText(getBaseContext(), item, Toast.LENGTH_LONG).show();



                //myPrefs = getSharedPreferences("mypreference",
                //      Context.MODE_PRIVATE);




                //Integer mob = myPrefs.getInt("key_name",0);


                // Toast.makeText(getApplicationContext(),mob,Toast.LENGTH_LONG).show();


                      /*  Uri SMS_URI = Uri.parse("smsto:"+mob); //Replace the phone number
                        Intent sms = new Intent(Intent.ACTION_VIEW,SMS_URI);
                        sms.putExtra("sms_body",item); //Replace the message witha a vairable
                        startActivity(sms);

                               try{

                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(mob, null, item, null, null);
                            Toast.makeText(getApplicationContext(), "SMS Sent!",
                                    Toast.LENGTH_LONG).show();
                       } catch (Exception e) {
                            Toast.makeText(getApplicationContext(),
                                    "SMS faild, please try again later!",+-+
                            e.printStackTrace();
                        }*/


                       /* NotificationManager notif=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                        Notification notify=new Notification.Builder
                                (getApplicationContext()).setContentTitle("").setContentText(item).
                                setContentTitle("alert").setSmallIcon(R.drawable.n).build();

                        notify.flags |= Notification.FLAG_AUTO_CANCEL;
                        notif.notify(0, notify);*/



            }



        });

    }
}