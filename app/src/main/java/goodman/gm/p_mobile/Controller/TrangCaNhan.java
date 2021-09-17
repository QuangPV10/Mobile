package goodman.gm.p_mobile.Controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import goodman.gm.p_mobile.Model.User;
import goodman.gm.p_mobile.R;

public class TrangCaNhan extends AppCompatActivity {
    CircleImageView circleImageView;
    TextView tvFullName, tvUserName, tvPassword, tvEmail, tvPhone;
    Button btnChangePass, btnLogOut, btnUp;

    SharedPreferences sharedPreferences;
    String passWord, fullName, email, phoneNumber, userName;
    //    User user;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("thanhviens");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_ca_nhan);

        Init();
        loadData();
        sendData();
        controlButton();

    }

    private void Init() {
        tvFullName = findViewById(R.id.FullName);
        tvUserName = findViewById(R.id.userName);
        tvPassword = findViewById(R.id.password);
        tvEmail = findViewById(R.id.email);
        tvPhone = findViewById(R.id.phoneNumber);
        btnChangePass = findViewById(R.id.btnChangPass);
        btnLogOut = findViewById(R.id.btnLogOut);
        circleImageView = findViewById(R.id.avatar);
        btnUp = findViewById(R.id.btnUp);

        sharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE);
    }

    private void controlButton() {
        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrangCaNhan.this, CapNhatThongTin.class);
                startActivity(intent);
            }
        });

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrangCaNhan.this, EditProfile.class);
                User user = new User(fullName, userName, passWord, email, phoneNumber);
                intent.putExtra("sendEdit", user);
                startActivity(intent);
            }
        });

        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrangCaNhan.this, DoiMatKhau.class);
                User user = new User(fullName, userName, passWord, email, phoneNumber);
                intent.putExtra("send", user);
                startActivity(intent);
            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(TrangCaNhan.this);
                builder.setMessage("Bạn muốn đăng xuất ?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(TrangCaNhan.this, MainActivity.class);
                        startActivity(intent);
                    }
                });

                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.setCancelable(false);
                builder.show();

            }
        });

        getUserInfomation();
    }

    private void getUserInfomation() {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(userName).exists() && snapshot.child(userName).getChildrenCount() > 0) {
                    if (snapshot.child(userName).hasChild("mHinhAnh")) {
                        String image = snapshot.child(userName).child("mHinhAnh").getValue().toString();
                        Picasso.get().load(image).into(circleImageView);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void loadData() {
        tvFullName.setText(sharedPreferences.getString("FullName", "1"));
        tvUserName.setText(sharedPreferences.getString("UserName", "1"));
        tvPassword.setText(sharedPreferences.getString("PassWord", "1"));
        tvEmail.setText(sharedPreferences.getString("Email", "1"));
        tvPhone.setText(sharedPreferences.getString("Phone", "1"));
    }

    private void sendData() {
        fullName = tvFullName.getText().toString();
        email = tvEmail.getText().toString();
        phoneNumber = tvPhone.getText().toString();
        userName = tvUserName.getText().toString();
        passWord = tvPassword.getText().toString();


    }
}