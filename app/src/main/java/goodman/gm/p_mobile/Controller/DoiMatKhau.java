package goodman.gm.p_mobile.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import goodman.gm.p_mobile.Model.User;
import goodman.gm.p_mobile.R;

public class DoiMatKhau extends AppCompatActivity {
    TextInputLayout oldPass, newPass, confirmPass;
    Button btnHuy, btnDongY;

    String passWord, fullName, email, phoneNumber, userName;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("thanhviens");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_mat_khau);

        init();
        loadData();
        controlButton();


    }

    private void controlButton() {
        btnDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (oldPass.getEditText().getText().toString().isEmpty() || newPass.getEditText().getText().toString().isEmpty()
                        || confirmPass.getEditText().toString().isEmpty()) {
                    Toast.makeText(DoiMatKhau.this, "Không được để trống", Toast.LENGTH_SHORT).show();
                } else {
                    if (!oldPass.getEditText().getText().toString().equals(passWord)) {
                        Toast.makeText(DoiMatKhau.this, "Mật khẩu cũ bị sai", Toast.LENGTH_SHORT).show();
                    } else {
                        if (!validatePass()) {
                            Toast.makeText(DoiMatKhau.this, "Vui lòng nhập đúng định dạng", Toast.LENGTH_SHORT).show();
                        } else {
                            if (!newPass.getEditText().getText().toString().equals(confirmPass.getEditText().getText().toString())) {
                                Toast.makeText(DoiMatKhau.this, "Xác nhận mật khẩu sai!!!", Toast.LENGTH_SHORT).show();
                            } else {
                                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        HashMap<String, Object> userMap = new HashMap<>();
                                        userMap.put("mFullName", fullName);
                                        userMap.put("mUserName", userName);
                                        userMap.put("mPassword", newPass.getEditText().getText().toString());
                                        userMap.put("mEmail", email);
                                        userMap.put("mPhoneNumber", phoneNumber);
                                        reference.child(userName).updateChildren(userMap);
                                        Toast.makeText(DoiMatKhau.this, "Đổi thành công!!! Vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(DoiMatKhau.this, DangNhap.class);
                                        startActivity(intent);
                                        finish();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }
                        }
                    }
                }


            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoiMatKhau.this, TrangCaNhan.class);
                startActivity(intent);
            }
        });

    }

    private void loadData() {
        Intent intent = getIntent();
        User user = (User) intent.getSerializableExtra("send");
        passWord = user.getmPassword();
        fullName = user.getmFullName();
        email = user.getmEmail();
        phoneNumber = user.getmPhoneNumber();
        userName = user.getmUserName();


    }

    private void init() {
        oldPass = findViewById(R.id.oldpassword);
        newPass = findViewById(R.id.newPassword);
        confirmPass = findViewById(R.id.confirmPassword);
        btnHuy = findViewById(R.id.btnHuy);
        btnDongY = findViewById(R.id.btnDongY);
    }

    private boolean validatePass() {
        String val = newPass.getEditText().getText().toString();

        String passwordVal = "^" +
                "(?=.*[A-Za-z])" +                //  bất kì kí tự nào
                "(?=.*[!@#$%^&*=])" +             //  ít nhất 1 kí tự đặc biệt
                "(?=\\S+$)" +                     //  không có khoảng trắng
                ".{4,}" +                         //  ít nhất có 4 kí tự
                "$";

        if (val.isEmpty()) {
            newPass.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            newPass.setError("Please add special characters ");
            return false;
        } else {
            newPass.setError(null);
            return true;
        }
    }
}