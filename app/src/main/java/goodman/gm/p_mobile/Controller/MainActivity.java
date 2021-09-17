package goodman.gm.p_mobile.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;


import goodman.gm.p_mobile.R;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE = 1;

    FusedLocationProviderClient fusedLocationProviderClient;
    SharedPreferences sharedPreferences;

    ImageView img;
    TextView txtHere, txtBack;
    Animation topAnim, bottomAnim;
    private static int SPLASH_SCREEN = 2000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Init();
        getLocation();
        getAnimation();

    }

    private void Init() {
        //Animation
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_anim);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        img = findViewById(R.id.logoImage);
        txtHere = findViewById(R.id.texthere);
        txtBack = findViewById(R.id.textback);

        img.setAnimation(topAnim);
        txtHere.setAnimation(bottomAnim);
        txtBack.setAnimation(bottomAnim);

        sharedPreferences = getSharedPreferences("ToaDo", MODE_PRIVATE);

        // tạo api yêu cầu truy cập service
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);
    }

    private void getLocation() {
        // kiểm tra coi xin quyền chưa
        int checkPermission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (checkPermission == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("Latitude", String.valueOf(location.getLatitude()));
                        editor.putString("Longitude", String.valueOf(location.getLongitude()));
                        editor.commit();
                        Log.d("kiemtraodau", location.getLatitude() + "");
                    }
                }
            });
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        }
    }

    private void getAnimation() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, DangNhap.class);

                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View, String>(img, "logo_image");
                pairs[1] = new Pair<View, String>(img, "logo_text");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);

                startActivity(intent, options.toBundle());
            }
        }, SPLASH_SCREEN);
    }


}