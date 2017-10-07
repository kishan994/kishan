package trainedge.autoprofiler.activity;

/**
 * Created by kishan on 05-10-2017.
 */

import android.content.Context;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.firebase.geofire.GeoLocation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import trainedge.autoprofiler.model.LocationModel;
import trainedge.autoprofiler.utils.ProfileNotification;

public class SoundProfileManager {

    private int count;
    Context context;

    private LocationModel soundProfile;

    public SoundProfileManager(Context context) {
        count = 0;
        this.context = context;

    }

    public void changeLocationModel(final Context context, final DatabaseReference profilesRef) {
        profilesRef.child("isActive").setValue(true, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError==null){
                    Log.d("haha","successfully changed");
                }
            }
        });
    }

    public void updateLocationModel(LocationModel soundProfile, String key) {
        boolean active = soundProfile.isActive;
        boolean isSilent = soundProfile.isSilent;
        boolean isVibrate = soundProfile.isVibrate;
        String name = key;
        final AudioManager profileMode = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        if (isSilent) {
            profileMode.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        } else if (isVibrate) {
            profileMode.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
        } else {
            int volume = soundProfile.ringvolumne;
            String msgtone = soundProfile.notification;
            String ringtone = soundProfile.ringtone;
            String alarmtone = soundProfile.alarm;
            profileMode.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            try {
                RingtoneManager.setActualDefaultRingtoneUri(context, RingtoneManager.TYPE_RINGTONE, Uri.parse(ringtone));
                RingtoneManager.setActualDefaultRingtoneUri(context, RingtoneManager.TYPE_NOTIFICATION, Uri.parse(msgtone));
                RingtoneManager.setActualDefaultRingtoneUri(context, RingtoneManager.TYPE_ALARM, Uri.parse(alarmtone));
            } catch (Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            profileMode.setStreamVolume(AudioManager.STREAM_RING, volume, 0);

        }
        ProfileNotification.notify(context, "Sound Profile loaded ->" + name, 0);


    }

    public void setToDefault(String key) {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final DatabaseReference profileRef = FirebaseDatabase.getInstance().getReference(uid).child("sound_profiles").child(key);
        profileRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    dataSnapshot.getRef().child("isActive").setValue(false);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(SoundProfileManager.this.context, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}
