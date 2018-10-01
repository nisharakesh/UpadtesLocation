package skirosoft.locationinupdates;

/**
 * Created by Nisha on 28-08-2018.
 */

public class Gold {




    String time;
    String loctn;
    String longt;
    String lat;
    String amt1;
    String amt2;
    String svrg;
    String gram;
    String date;



    public Gold() {

    }

    public Gold(String loctn, String longt,String lat,String amt1,String time,String amt2,  String svrg, String gram,String date){

        this.time=time;
        this.loctn=loctn;
        this.longt=longt;
        this.lat=lat;
        this.amt1=amt1;
        this.amt2=amt2;
        this.svrg=svrg;
        this.gram=gram;
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

    public String getamt1() {
        return amt1;
    }


    public String getamt2() {
        return amt2;
    }

    public String getsvrg() {
        return svrg;
    }

    public String getgram() {
        return gram;
    }

    public String gettime() {
        return time;
    }
    public String getDate() {
        return date;
    }
}
