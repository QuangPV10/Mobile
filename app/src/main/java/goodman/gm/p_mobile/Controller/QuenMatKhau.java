package goodman.gm.p_mobile.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
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

public class QuenMatKhau extends AppCompatActivity {

    private Button btnNext, btnBack;
    private TextInputEditText edtUserName;
    private final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("thanhviens");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quen_mat_khau);

        init();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callVertifyOTP();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuenMatKhau.this, DangNhap.class);
                startActivity(intent);
            }
        });
    }

    private void callVertifyOTP() {
        if (edtUserName.getText().toString().isEmpty()) {
            Toast.makeText(QuenMatKhau.this, "Vui lòng nhập dữ liệu", Toast.LENGTH_SHORT).show();
        } else {
            final ProgressDialog dialog = new ProgressDialog(QuenMatKhau.this);
            dialog.setMessage("Please waiting");
            dialog.show();
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.child(edtUserName.getText().toString()).exists()) {
                        dialog.dismiss();
                        // lay du lieu
                        User user = snapshot.child(edtUserName.getText().toString()).getValue(User.class);
                        Intent intent = new Intent(getApplicationContext(), Vertify_OTP.class);
                        intent.putExtra("QuenMKUser", user);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(QuenMatKhau.this, "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private void init() {
        btnNext = findViewById(R.id.btnNext);
        edtUserName = findViewById(R.id.edtUser_Name);
        btnBack = findViewById(R.id.btnBackFor);
    }

}