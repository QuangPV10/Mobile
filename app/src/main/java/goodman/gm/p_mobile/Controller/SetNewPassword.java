package goodman.gm.p_mobile.Controller;

import androidx.annotation.NonNull;
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

import goodman.gm.p_mobile.R;

public class SetNewPassword extends AppCompatActivity {

    private String newPass , confirmPass;
    private TextInputLayout newPassWord, confirmPassWord;
    private Button btnUpdate;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("thanhviens");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_new_password);

        init();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPass = newPassWord.getEditText().getText().toString().trim();
                confirmPass = confirmPassWord.getEditText().getText().toString().trim();

                if (newPass.isEmpty() || confirmPass.isEmpty()) {
                    Toast.makeText(SetNewPassword.this, "Không được để trống", Toast.LENGTH_SHORT).show();
                } else {
                    if (!validatePass()) {
                        Toast.makeText(SetNewPassword.this, "Vui lòng nhập đúng định dạng", Toast.LENGTH_SHORT).show();
                    } else {
                        if (!confirmPass.equals(newPass)) {
                            Toast.makeText(SetNewPassword.this, "Xác nhận mật khẩu sai", Toast.LENGTH_SHORT).show();
                        } else {
                            final String userName = getIntent().getStringExtra("userNamefromVertify");
                            reference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    reference.child(userName).child("mPassword").setValue(newPass);
                                    Intent intent = new Intent(SetNewPassword.this, DangNhap.class);
                                    startActivity(intent);
                                    Toast.makeText(SetNewPassword.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }
                }
            }
        });

    }

    private void init() {
        newPassWord = findViewById(R.id.NewPassword);
        confirmPassWord = findViewById(R.id.ConfirmPassword);
        btnUpdate = findViewById(R.id.btnUpdatePassWord);
    }

    private boolean validatePass() {
        String val = newPass;

        String passwordVal = "^" +
                "(?=.*[A-Za-z])" +                //  bất kì kí tự nào
                "(?=.*[!@#$%^&*=])" +             //  ít nhất 1 kí tự đặc biệt
                "(?=\\S+$)" +                     //  không có khoảng trắng
                ".{4,}" +                         //  ít nhất có 4 kí tự
                "$";

        if (val.isEmpty()) {
            newPassWord.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            newPassWord.setError("Please add special characters ");
            return false;
        } else {
            newPassWord.setError(null);
            return true;
        }
    }
}