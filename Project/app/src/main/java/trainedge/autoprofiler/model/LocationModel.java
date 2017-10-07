package trainedge.autoprofiler.model;

/**
 * Created by kishan on 17-09-2017.
 */

public class LocationModel {
    public String email;
    public double lng;
    public int ringvolumne;
    public int notificationvolumne;
    public int alarmvolumne;
    public String ringtone;
    public String notification;
    public String alarm;
    public String location;
    public double lat;
    public boolean isSilent;
    public boolean isActive;
    public boolean isVibrate;
    public int volume;

    public LocationModel() {
    }
    public LocationModel(String email, double lng, boolean isActive, boolean isSilent, boolean isVibrate, int ringvolumne, int notificationvolumne, int alarmvolumne, String ringtone, String notification, String alarm, String location, double lat) {

        this.isVibrate = isVibrate;
        this.isActive = isActive;
        this.isSilent = isSilent;
        this.email = email;
        this.lng = lng;
        this.ringvolumne = ringvolumne;
        this.notificationvolumne = notificationvolumne;
        this.alarmvolumne = alarmvolumne;
        this.ringtone = ringtone;
        this.notification = notification;
        this.alarm = alarm;
        this.location = location;
        this.lat = lat;
    }


}



