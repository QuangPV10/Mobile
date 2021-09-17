package goodman.gm.p_mobile.Controller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

import goodman.gm.p_mobile.Model.User;
import goodman.gm.p_mobile.R;


public class DangKy extends AppCompatActivity {

    TextInputLayout edtFullName, edtUserName, edtEmail, edtPhone, edtPassWord;
    Button btnBack, btnDangKy;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("thanhviens");
    CountryCodePicker countryCodePicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

        // khởi tạo
        Init();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangKy.this, DangNhap.class);
                startActivity(intent);
            }
        });
        controlButton();


    }


    private void Init() {
        edtFullName = findViewById(R.id.edtFullName);
        edtUserName = findViewById(R.id.edtUserName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhone = findViewById(R.id.edtPhone);
        edtPassWord = findViewById(R.id.edtPassWord);
        btnBack = findViewById(R.id.btnBack);
        btnDangKy = findViewById(R.id.btnDangKy);
        countryCodePicker = findViewById(R.id.country_code);
    }

    private void controlButton() {
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateName() | !validateUser() | !validatePass() | !validateEmail() | !validatePhoneNumber()) {
                    return;
                }

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // kiem tra tai khoan co ton tai trong database
                        if (!dataSnapshot.child(edtUserName.getEditText().getText().toString()).exists()) {
                            String fullname = edtFullName.getEditText().getText().toString();
                            String username = edtUserName.getEditText().getText().toString();
                            String password = edtPassWord.getEditText().getText().toString();
                            String email = edtEmail.getEditText().getText().toString();
                            String phone = edtPhone.getEditText().getText().toString().substring(1);

                            String phoneNumber = "+" + countryCodePicker.getFullNumber() + phone;

                            User user = new User(fullname, username, password, email, phoneNumber);
                            reference.child(username).setValue(user);

                            Toast.makeText(DangKy.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(DangKy.this, DangNhap.class);
                            startActivity(intent);

                        } else {
//                            finish();
//                            Toast.makeText(DangKy.this, "Tên đăng nhập đã tồn tại!!! Vui lòng nhập lại", Toast.LENGTH_SHORT).show();
                        }

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });


    }

    private boolean validateName() {
        String val = edtFullName.getEditText().getText().toString();

        if (val.isEmpty()) {
            edtFullName.setError("Field cannot be empty");
            return false;
        } else {
            edtFullName.setError(null);
            return true;
        }
    }

    private boolean validateUser() {
        String val = edtUserName.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if (val.isEmpty()) {
            edtUserName.setError("Field cannot be empty");
            return false;
        } else if (val.length() >= 15) {
            edtUserName.setError("username is too long");
            return false;
        } else if (!val.matches(noWhiteSpace)) {
            edtUserName.setError("White Space are not allowed");
            return false;
        } else {
            edtUserName.setError(null);
            return true;
        }
    }

    private boolean validatePass() {
        String val = edtPassWord.getEditText().getText().toString();

        String passwordVal = "^" +
                "(?=.*[A-Za-z])" +                //  bất kì kí tự nào
                "(?=.*[!@#$%^&*=])" +             //  ít nhất 1 kí tự đặc biệt
                "(?=\\S+$)" +                     //  không có khoảng trắng
                ".{4,}" +                         //  ít nhất có 4 kí tự
                "$";

        if (val.isEmpty()) {
            edtPassWord.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            edtPassWord.setError("Please add special characters ");
            return false;
        } else {
            edtPassWord.setError(null);
            return true;
        }
    }

    private boolean validateEmail() {
        String val = edtEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            edtEmail.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            edtEmail.setError("Invalid email address");
            return false;
        } else {
            edtEmail.setError(null);
            return true;
        }
    }

    private boolean validatePhoneNumber() {
        String val = edtPhone.getEditText().getText().toString();
        String MobilePattern = "[0-9]{10}";

        if (val.length() < 10 || val.length() > 12 || !val.matches(MobilePattern)) {
            edtPhone.setError("Wrong phone number");
            return false;
        } else {
            edtPhone.setError(null);
            return true;
        }
    }


}