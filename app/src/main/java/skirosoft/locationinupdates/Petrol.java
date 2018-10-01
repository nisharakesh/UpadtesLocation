package skirosoft.locationinupdates;

/**
 * Created by Nisha on 28-08-2018.
 */

public class Petrol {

    String time;
    String loctn;
    String longt;
    String lat;
    String petrol_liter_amt;
    String petrol_galon_amt;
    String diesel_liter_amt;
    String diesel_galon_amt;
    String date;

    public Petrol() {
    }

    public Petrol(String loctn, String longt, String lat, String petrol_liter_amt, String petrol_galon_amt,
                  String diesel_liter_amt, String diesel_galon_amt, String time, String date) {

        this.loctn = loctn;
        this.longt = longt;
        this.lat = lat;
        this.petrol_liter_amt = petrol_liter_amt;
        this.petrol_galon_amt = petrol_galon_amt;
        this.diesel_liter_amt = diesel_liter_amt;
        this.diesel_galon_amt = diesel_galon_amt;
        this.time = time;
        this.date = date;
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

    public String getpetrol_liter_amt() {
        return petrol_liter_amt;
    }

    public String getpetrol_galon_amt() {
        return petrol_galon_amt;
    }

    public String getdiesel_liter_amt() {
        return diesel_liter_amt;
    }

    public String getdiesel_galon_amt() {
        return diesel_galon_amt;
    }

    public String gettime() {
        return time;
    }

    public String getdate() {
        return date;
    }
}
