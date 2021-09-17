package goodman.gm.p_mobile.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import goodman.gm.p_mobile.Model.User;
import goodman.gm.p_mobile.R;

public class Admin_ChiTiet_User extends AppCompatActivity {
    TextView tvAdminFullName, tvAdminEmail, tvAdminSdt, tvAdminUserName, tvAdminPass;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_chi_tiet_user);
        init();

    }


    private void init() {
        tvAdminFullName = findViewById(R.id.tvAdminHoTen);
        tvAdminEmail = findViewById(R.id.tvAdminEmail);
        tvAdminPass = findViewById(R.id.tvAdminMK);
        tvAdminSdt = findViewById(R.id.tvAdminSdt);
        tvAdminUserName = findViewById(R.id.tvAdminTDN);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("adminUsers");
        tvAdminFullName.setText(user.getmFullName());
        tvAdminEmail.setText(user.getmEmail());
        tvAdminPass.setText(user.getmPassword());
        tvAdminSdt.setText(user.getmPhoneNumber());
        tvAdminUserName.setText(user.getmUserName());


    }
}