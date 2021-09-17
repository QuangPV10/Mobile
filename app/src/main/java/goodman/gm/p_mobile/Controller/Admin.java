package goodman.gm.p_mobile.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.navigation.NavigationView;

import goodman.gm.p_mobile.Model.User;
import goodman.gm.p_mobile.R;

public class Admin extends AppCompatActivity {

    User user;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageButton imgMenu, imgUser, imgFood, imgNear, imgCom,imgBlog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        init();

        receiveData();
        controlButton();
        actionNavigation();


    }

    private void receiveData() {
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("TaiKhoan");
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else super.onBackPressed();
    }

    private void actionNavigation() {
        navigationView.bringToFront();
        navigationView.setCheckedItem(R.id.trangquanly);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.trangchu:
                        Intent intent = new Intent(Admin.this, TrangChu.class);
                        startActivity(intent);
                        break;
                    case R.id.trangdangnhap:
                        Intent intent1 = new Intent(Admin.this, DangNhap.class);
                        startActivity(intent1);
                        break;
                    case R.id.trangdangky:
                        Intent intent2 = new Intent(Admin.this, DangKy.class);
                        startActivity(intent2);
                        break;
                }
                return true;
            }
        });
    }

    private void controlButton() {

        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin.this, Admin_User.class);
                startActivity(intent);
            }
        });
        imgFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin.this, Admin_Food.class);
                startActivity(intent);
            }
        });
        imgNear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin.this, Admin_Near.class);
                startActivity(intent);
            }
        });
        imgCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin.this, Admin_Comment.class);
                startActivity(intent);
            }
        });
        imgBlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin.this, Admin_Blog.class);
                startActivity(intent);
            }
        });

    }

    private void init() {
        imgUser = findViewById(R.id.imgUser);
        imgFood = findViewById(R.id.imgFood);
        imgNear = findViewById(R.id.imgNear);
        imgCom = findViewById(R.id.imgComment);
        imgMenu = findViewById(R.id.imgMenu);
        imgBlog = findViewById(R.id.imgBLog);

        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigation_view);
        drawerLayout = findViewById(R.id.drawerLayout);

    }
}