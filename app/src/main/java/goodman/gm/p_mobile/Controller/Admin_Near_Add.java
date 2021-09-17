package goodman.gm.p_mobile.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import goodman.gm.p_mobile.Model.DiaChi;
import goodman.gm.p_mobile.R;

public class Admin_Near_Add extends AppCompatActivity {
    EditText editTenQuan, edtDiaChi, edtLong, edtLati, edtMaQuan;
    Button btnThem, btnQuayLai;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("gantois");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_near_add);

        init();
        controlButton();

    }

    private void controlButton() {
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (!snapshot.child(edtMaQuan.getText().toString()).exists()) {
                            String maQuan = edtMaQuan.getText().toString();
                            String tenQuan = editTenQuan.getText().toString();
                            String diaChiQuan = edtDiaChi.getText().toString();
                            Double longtitude = Double.valueOf(edtLong.getText().toString());
                            Double latitude = Double.valueOf(edtLati.getText().toString());

                            DiaChi diaChi = new DiaChi(tenQuan, diaChiQuan, latitude, longtitude, maQuan);
                            reference.child(maQuan).setValue(diaChi);
                            Toast.makeText(Admin_Near_Add.this, "Thêm thành công", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(Admin_Near_Add.this, Admin_Near.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(Admin_Near_Add.this, "Mã quán ăn tồn tại", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });

        btnQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init() {
        editTenQuan = findViewById(R.id.edtTenQuanNear);
        edtDiaChi = findViewById(R.id.edtTenDiaChiNear);
        edtLong = findViewById(R.id.edtLongNear);
        edtLati = findViewById(R.id.edtLatitudeNear);
        btnThem = findViewById(R.id.btnNearThemAdd);
        btnQuayLai = findViewById(R.id.btnNearBackAdd);
        edtMaQuan = findViewById(R.id.edtMaQuanNear);
    }
}