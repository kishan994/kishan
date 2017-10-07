package trainedge.autoprofiler.activity;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import trainedge.autoprofiler.R;
import trainedge.autoprofiler.TrackerService;

import static trainedge.autoprofiler.FetchAddressIntentService.Constants.LATITUDE_DATA_EXTRA;
import static trainedge.autoprofiler.FetchAddressIntentService.Constants.LONGITUDE_DATA_EXTRA;


//import trainedge.autoprofiler.R;

public class HomeActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    public static final int REQUEST_LOCATION_ADDR = 389;
    int PLACE_PICKER_REQUEST = 1;
    private EditText EtSearch;
    private TextView tvLocation;
    private SeekBar sbAlarm;
    private SeekBar sbNotification;
    private SeekBar sbVolume;
    private Spinner sRingtone;
    ArrayAdapter<String> adapter;
    private Spinner sAlarm;
    private Spinner sNotification;
    private MediaPlayer mediaplayer;
    private Drawable thumbA;
    private Drawable thumbB;
    private Drawable thumbC;
    private Drawable thumbD;
    private Drawable thumbE;
    private Drawable thumbF;
    private Button btnSave;
    private String urirt;
    private String urial;
    private String urint;
    private String sal;
    private String snt;
    private String srt;
    private int ringvolume;
    private int notificationvolumne;
    private int alarmvolume;
    private double latitude;
    private double longitude;
    private EditText etProfile;
    private FusedLocationProviderClient mFusedLocationClient;
    private FloatingActionButton fab;
    private double currentLat;
    private double currentLng;
    private String profileName;
    private CheckBox cbVibrate;
    private boolean isVibrate = false;
    private boolean isSilent = false;
    private boolean isActive = false;
    private SeekBar sbRadius;
    private MultiAutoCompleteTextView mtFeedback;
    private String message;
    private String email;

    //private PlaceAutocompleteFragment place_autocomplete_fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent i = new Intent(this, TrackerService.class);
        startService(i);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        cbVibrate = (CheckBox) findViewById(R.id.cbVibrate);
        cbVibrate.setOnCheckedChangeListener(this);
        etProfile = (EditText) findViewById(R.id.etProfile);
        sbAlarm = (SeekBar) findViewById(R.id.sbAlarm);
        sbVolume = (SeekBar) findViewById(R.id.sbVolume);
        sbNotification = (SeekBar) findViewById(R.id.sbNotification);
        sRingtone = (Spinner) findViewById(R.id.sRingtone);
        sAlarm = (Spinner) findViewById(R.id.sAlarm);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
        thumbA = getResources().getDrawable(R.drawable.ic_alarm_off_black_24dp);
        thumbB = getResources().getDrawable(R.drawable.ic_access_alarm_black_24dp);
        thumbC = getResources().getDrawable(R.drawable.ic_notifications_black_24dp);
        thumbD = getResources().getDrawable(R.drawable.ic_notifications_off_black_24dp);
        thumbE = getResources().getDrawable(R.drawable.ic_music_note_black_24dp);
        thumbF = getResources().getDrawable(R.drawable.ic_vibration_black_24dp);


        sbAlarm.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                alarmvolume = i;
                if (i == 0) {
                    isSilent = true;

                    seekBar.setThumb(thumbA);


                } else {
                    seekBar.setThumb(thumbB);

                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sbNotification.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                notificationvolumne = i;
                if (i == 0) {
                    isSilent = true;
                    seekBar.setThumb(thumbD);


                } else {
                    seekBar.setThumb(thumbC);

                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sbVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                ringvolume = i;

                if (i == 0) {
                    isSilent = true;
                    seekBar.setThumb(thumbF);


                } else {
                    seekBar.setThumb(thumbE);

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sNotification = (Spinner) findViewById(R.id.sNotification);

        tvLocation = (TextView) findViewById(R.id.tvLocation);
        mediaplayer = new MediaPlayer();


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(HomeActivity.this, MapsActivity.class);
                intent.putExtra("trainedge.autoprofiler.lat", currentLat);
                intent.putExtra("trainedge.autoprofiler.lng", currentLng);
                startActivityForResult(intent, REQUEST_LOCATION_ADDR);
                //try {
                //    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                //    startActivityForResult(builder.build(HomeActivity.this), PLACE_PICKER_REQUEST);
                //} catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                //    e.printStackTrace();
                //}

            }
        });
        Map<String, String> notifications = getNotifications();
        ArrayList<String> notificationname = new ArrayList<>();
        for (String ringName : notifications.keySet()) {
            notificationname.add(ringName);
        }
        final ArrayList<String> name = new ArrayList<>();
        for (String uri : notifications.values()) {
            name.add(uri);
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, notificationname);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sNotification.setAdapter(adapter);
        sNotification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               //snt = sNotification.toString().trim();
                //urint = name.get(position);
                /*mediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                try {
                    mediaplayer.setDataSource(getApplicationContext(), Uri.parse(urint));
                    mediaplayer.prepare();
                    mediaplayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Map<String, String> alarms = getAlarm();
        ArrayList<String> alarmname = new ArrayList<>();
        for (String ringName : alarms.keySet()) {
            alarmname.add(ringName);
        }
        final ArrayList<String> aname = new ArrayList<>();
        for (String uri : alarms.values()) {
            aname.add(uri);
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, alarmname);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sAlarm.setAdapter(adapter);
        sAlarm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sal = sAlarm.toString().trim();
                urial = aname.get(position);
                Toast.makeText(HomeActivity.this, "item is selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Map<String, String> ringtones = getRingtone();
        ArrayList<String> ringtonename = new ArrayList<>();
        for (String ringName : ringtones.keySet()) {
            ringtonename.add(ringName);
        }
        final ArrayList<String> rname = new ArrayList<>();
        for (String uri : ringtones.values()) {
            rname.add(uri);
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ringtonename);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sRingtone.setAdapter(adapter);
        sRingtone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //
                urirt = rname.get(position);
                Toast.makeText(HomeActivity.this, "item is selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //int permission = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
            //if (permission != PackageManager.PERMISSION_GRANTED) {
            //   requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 212);
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    //checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.MODIFY_AUDIO_SETTINGS) != PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.ACCESS_NOTIFICATION_POLICY) != PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.WRITE_SETTINGS) != PackageManager.PERMISSION_GRANTED
                    ) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                //Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.MODIFY_AUDIO_SETTINGS,
                                Manifest.permission.ACCESS_NOTIFICATION_POLICY,
                                Manifest.permission.MODIFY_AUDIO_SETTINGS,
                                Manifest.permission.WRITE_SETTINGS}
                        , 212);
            }
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
                    && !notificationManager.isNotificationPolicyAccessGranted()) {
                Intent intent = new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                context.startActivity(intent);
            } else {
                getMyLocation();
            }

        } else {
            getMyLocation();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            case R.id.radius:
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                View view = LayoutInflater.from(HomeActivity.this).inflate(R.layout.radius, null);
                sbRadius = (SeekBar) view.findViewById(R.id.sbRadius);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context, "hey", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context, "hii", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setView(view);
                builder.show();
            case R.id.feedback:
                AlertDialog.Builder nbuilder = new AlertDialog.Builder(HomeActivity.this);
                View nview = LayoutInflater.from(HomeActivity.this).inflate(R.layout.feedback, null);
                mtFeedback = (MultiAutoCompleteTextView) nview.findViewById(R.id.mtFeedback);
                message = mtFeedback.getText().toString();

                nbuilder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context, "hey", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setData(Uri.parse("mailto:kmatanhelia@gmail.com"));
                        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
                        intent.putExtra(Intent.EXTRA_TEXT, message);
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivity(intent);
                            finish();

                        }


                    }
                });
                nbuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context, "hii", Toast.LENGTH_SHORT).show();

                    }
                });
                nbuilder.setView(nview);
                nbuilder.show();


        }

        return super.onOptionsItemSelected(item);
    }

    private void getMyLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                //&& ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.MODIFY_AUDIO_SETTINGS) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NOTIFICATION_POLICY) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_SETTINGS) != PackageManager.PERMISSION_GRANTED
                ) {

            return;
        }
        //step 3 write one line then ctrl alt m (PARAGRAPH)and name it getMyLocation
        mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                fab.setVisibility(View.VISIBLE);
                currentLat = location.getLatitude();
                currentLng = location.getLongitude();
            }
        });
    }

    //step 5 write onRequestPermission alt enter
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //step 6 write by self
        if (requestCode == 212) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getMyLocation();

            }
            //Toast.makeText(this, "permission denied212", Toast.LENGTH_SHORT).show();
        }
    }


    public Map<String, String> getNotifications() {
        RingtoneManager manager = new RingtoneManager(this);
        manager.setType(RingtoneManager.TYPE_NOTIFICATION);
        Cursor cursor = manager.getCursor();

        Map<String, String> list = new HashMap<>();
        while (cursor.moveToNext()) {
            String notificationTitle = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX);
            String notificationUri = cursor.getString(RingtoneManager.URI_COLUMN_INDEX);

            list.put(notificationTitle, notificationUri);
        }

        return list;
    }

    public Map<String, String> getAlarm() {
        RingtoneManager manager = new RingtoneManager(this);
        manager.setType(RingtoneManager.TYPE_ALARM);
        Cursor cursor = manager.getCursor();

        Map<String, String> list = new HashMap<>();
        while (cursor.moveToNext()) {
            String notificationTitle = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX);
            String notificationUri = cursor.getString(RingtoneManager.URI_COLUMN_INDEX);

            list.put(notificationTitle, notificationUri);
        }

        return list;
    }

    public Map<String, String> getRingtone() {
        RingtoneManager manager = new RingtoneManager(this);
        manager.setType(RingtoneManager.TYPE_RINGTONE);
        Cursor cursor = manager.getCursor();

        Map<String, String> list = new HashMap<>();
        while (cursor.moveToNext()) {
            String notificationTitle = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX);
            String notificationUri = cursor.getString(RingtoneManager.URI_COLUMN_INDEX);

            list.put(notificationTitle, notificationUri);
        }

        return list;
    }


    @Override
    public void onClick(View view) {
        String userLocation = tvLocation.getText().toString().trim();
        if (userLocation.isEmpty()) {
            Toast.makeText(context, "select a location on map", Toast.LENGTH_SHORT).show();
            return;
        }

        addToFirebase(userLocation);
    }

    private void addToFirebase(String userLocation) {
        tvLocation.setText("");
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference db = fd.getReference(uid).child("sound_profiles");
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        email = currentUser.getEmail();
        HashMap<String, Object> location = new HashMap<>();
        location.put("email", email);
        location.put("location", userLocation);
        location.put("ringtone", urirt);
        location.put("notification", urint);
        location.put("alarm", urial);
        location.put("ringvolumne", ringvolume);
        location.put("alarmvolumne", alarmvolume);
        location.put("notificationvolumne", notificationvolumne);
        location.put("lat", latitude);
        location.put("lng", longitude);
        location.put("isVibrate", isVibrate);
        location.put("isSilent", isSilent);
        location.put("isActive", isActive);
        //profile name should be dynamic, get it with editText
        profileName = etProfile.getText().toString();
        db.child(profileName).setValue(location);
        DatabaseReference geoRef = db.child("geofire");
        GeoFire geoFire = new GeoFire(geoRef);

        geoFire.setLocation(profileName, new GeoLocation(latitude, longitude), new GeoFire.CompletionListener() {
            @Override
            public void onComplete(String key, DatabaseError error) {
                if (error != null) {
                    System.err.println("There was an error saving the location to GeoFire: " + error);
                } else {
                    Toast.makeText(context, "GeoLocation Registered", Toast.LENGTH_SHORT).show();
                }
            }
        });
        etProfile.setText("");
        sbAlarm.setProgress(0);
        sbVolume.setProgress(0);
        sbNotification.setProgress(0);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_LOCATION_ADDR) {
                if (data != null && data.getExtras() != null) {
                    String location = data.getStringExtra("trainedge.autoprofiler.activity.extra_loc");
                    latitude = data.getDoubleExtra(LATITUDE_DATA_EXTRA, 0.0);
                    longitude = data.getDoubleExtra(LONGITUDE_DATA_EXTRA, 0.0);
                    tvLocation.setText(location);
                }
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        isVibrate = b;
    }
}

