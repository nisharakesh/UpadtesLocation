package skirosoft.locationinupdates;

/**
 * Created by Nisha on 28-08-2018.
 */

public class Flights {


    String time;
    String loctn;
    String longt;
    String lat;
    //String descptn;
    String updates;
    String toggle;
    String forpetrol;
    String date;




    public Flights() {

    }

    public Flights(String loctn, String longt,String lat,String time,String updates,  String toggle,String date){

        this.time=time;
        this.loctn=loctn;
        this.longt=longt;
        this.lat=lat;
       // this.descptn=descptn;
        this.updates=updates;
        this.toggle=toggle;
        this.date=date;

    }

    public String getloctn() {
        return loctn;
    }

    public String getlongt() {
        return longt;
    }

    public String getlat() {
        return lat;
    }
    public String getupdates() {
        return updates;
    }

    public String gettoggle() {
        return toggle;
    }

    public String gettime() {
        return time;
    }
    public String getDate() {return date;}


}
