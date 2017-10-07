package trainedge.autoprofiler.activity;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.os.ResultReceiver;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import trainedge.autoprofiler.FetchAddressIntentService;
import trainedge.autoprofiler.R;

public class MapsActivity extends BaseActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;
    protected Location mLastLocation;
    private AddressResultReceiver mResultReceiver;
    private EditText EtSearch;
    private ImageButton ibSetting;
    private double latitude;
    private double longitude;
    private double mylatitude;
    private double mylongitude;
    private PlaceAutocompleteFragment place_autocomplete_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        place_autocomplete_fragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        /*place_autocomplete_fragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                Toast.makeText(context, "Places"+place.getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Status status) {
                Toast.makeText(context, "An Error Occured", Toast.LENGTH_SHORT).show();

            }
        });*/
        EtSearch = (EditText) findViewById(R.id.EtSearch);
        EtSearch.setVisibility(View.GONE);
        ibSetting = (ImageButton) findViewById(R.id.ibSetting);
        ibSetting.setOnClickListener(this);
        if (getIntent() != null) {
            mylatitude = getIntent().getDoubleExtra("trainedge.autoprofiler.lat", 0);
            mylongitude = getIntent().getDoubleExtra("trainedge.autoprofiler.lng", 0);
        }

    }

    @Override
    public void onClick(View view) {
        String location = EtSearch.getText().toString();
        Intent nintent = new Intent(this, HomeActivity.class);
        nintent.putExtra("trainedge.autoprofiler.activity.extra_loc", location);
        nintent.putExtra(FetchAddressIntentService.Constants.LATITUDE_DATA_EXTRA, latitude);
        nintent.putExtra(FetchAddressIntentService.Constants.LONGITUDE_DATA_EXTRA, longitude);
        setResult(RESULT_OK, nintent);
        finish();
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();

    }

    class AddressResultReceiver extends ResultReceiver {
        private String mAddressOutput;
        private Context context;

        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            mAddressOutput = resultData.getString(FetchAddressIntentService.Constants.RESULT_DATA_KEY);

            if (resultCode == FetchAddressIntentService.Constants.SUCCESS_RESULT) {

                //message(mAddressOutput);
                displayAddressOutput();

            }

        }

        private void displayAddressOutput() {
            EtSearch.setText(mAddressOutput);

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng myLocation = new LatLng(mylatitude, mylongitude);
        mMap.addMarker(new MarkerOptions().position(myLocation).title("My Location"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 15f));

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                EtSearch.setVisibility(View.VISIBLE);
                latitude = latLng.latitude;
                longitude = latLng.longitude;

                startIntentService(latLng);
            }
        });

    }


    protected void startIntentService(LatLng latLng) {

        mResultReceiver = new AddressResultReceiver(new Handler());
        Intent intent = new Intent(this, FetchAddressIntentService.class);
        intent.putExtra(FetchAddressIntentService.Constants.RECEIVER, mResultReceiver);
        intent.putExtra(FetchAddressIntentService.Constants.LATITUDE_DATA_EXTRA, latitude);
        intent.putExtra(FetchAddressIntentService.Constants.LONGITUDE_DATA_EXTRA, longitude);
        startService(intent);
    }

}
