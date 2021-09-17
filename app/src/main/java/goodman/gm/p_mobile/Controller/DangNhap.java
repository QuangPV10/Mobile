package goodman.gm.p_mobile.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import goodman.gm.p_mobile.Model.User;
import goodman.gm.p_mobile.R;

public class DangNhap extends AppCompatActivity {

    Button btnDangKy, btnDangnhap, btnQuenMK;
    TextInputEditText edtUser, edtPass;
    SharedPreferences sharedPreferences;
    User user;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("thanhviens");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        // khởi tạo
        Init();


        btnDangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edtUser.getText().toString().isEmpty() || edtPass.getText().toString().isEmpty()) {
                    Toast.makeText(DangNhap.this, "Please Enter values", Toast.LENGTH_SHORT).show();
                } else {
                    final ProgressDialog dialog = new ProgressDialog(DangNhap.this);
                    dialog.setMessage("Please waiting");
                    dialog.show();
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            // kiem tra tai khoan co ton tai trong database
                            if (dataSnapshot.child(edtUser.getText().toString()).exists()) {
                                dialog.dismiss();
                                // lay du lieu
                                user = dataSnapshot.child(edtUser.getText().toString()).getValue(User.class);
                                if (user.getmUserName().equals("admin")) {
                                    if (user.getmPassword().equals(edtPass.getText().toString())) {
                                        saveData();
                                        Toast.makeText(DangNhap.this, "Sign in successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(DangNhap.this, Admin.class);
                                        intent.putExtra("TaiKhoan", user);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(DangNhap.this, "Wrong PassWord!!", Toast.LENGTH_SHORT).show();
                                    }
                                } else if (user.getmPassword().equals(edtPass.getText().toString())) {
                                    saveData();
                                    Toast.makeText(DangNhap.this, "Sign in successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(DangNhap.this, TrangChu.class);
                                    intent.putExtra("TaiKhoan", user);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(DangNhap.this, "Wrong PassWord!!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(DangNhap.this, "User not exist!!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }
        });


        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangNhap.this, DangKy.class);
                startActivity(intent);
                finish();
            }
        });


        btnQuenMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangNhap.this, QuenMatKhau.class);
                startActivity(intent);
                finish();

            }
        });
    }

    private void saveData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("FullName", user.getmFullName());
        editor.putString("UserName", user.getmUserName());
        editor.putString("PassWord", user.getmPassword());
        editor.putString("Email", user.getmEmail());
        editor.putString("Phone", user.getmPhoneNumber());
        editor.commit();
    }


    private void Init() {
        btnDangnhap = findViewById(R.id.btnLogin);
        btnDangKy = findViewById(R.id.btnRegister);
        btnQuenMK = findViewById(R.id.btnForgot);
        edtUser = findViewById(R.id.edtUser);
        edtPass = findViewById(R.id.edtPass);
        sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
        user = new User();


    }


}