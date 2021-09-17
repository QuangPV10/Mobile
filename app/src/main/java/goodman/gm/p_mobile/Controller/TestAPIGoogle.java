package goodman.gm.p_mobile.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import goodman.gm.p_mobile.R;

public class TestAPIGoogle extends AppCompatActivity {

    public static final int REQUEST_CODE = 1;
    TextView tv;
    Button bt;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_api_google);

        tv = findViewById(R.id.tv);
        bt = findViewById(R.id.bt);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(TestAPIGoogle.this);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checkPermission = ContextCompat.checkSelfPermission(TestAPIGoogle.this, Manifest.permission.ACCESS_FINE_LOCATION);
                if (checkPermission == PackageManager.PERMISSION_GRANTED) {
                    fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                Double lat = location.getLatitude();
                                Double longitude = location.getLongitude();

                                tv.setText(lat + " ," +  longitude);
                            }
                        }
                    });
                } else {
                    ActivityCompat.requestPermissions(TestAPIGoogle.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
                }
            }
        });
    }
}