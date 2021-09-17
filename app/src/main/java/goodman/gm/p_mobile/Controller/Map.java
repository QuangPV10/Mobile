package goodman.gm.p_mobile.Controller;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import goodman.gm.p_mobile.Model.DiaChi;
import goodman.gm.p_mobile.R;

public class Map extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    DiaChi diaChi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Intent intent = getIntent();
        diaChi = (DiaChi) intent.getSerializableExtra("maps");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker
        LatLng vn = new LatLng(diaChi.getmLatitue(), diaChi.getmLongitue());
        mMap.addMarker(new MarkerOptions()
                .position(vn)
                .title(diaChi.getmTenQuanAn()));
        // 1 world 5 countries 10 city 15 street 20 building
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(vn,15));


    }
}