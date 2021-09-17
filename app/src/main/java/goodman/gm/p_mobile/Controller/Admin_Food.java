package goodman.gm.p_mobile.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import goodman.gm.p_mobile.Adapter.Admin_Food_Adapter;
import goodman.gm.p_mobile.Model.QuanAn;
import goodman.gm.p_mobile.R;

public class Admin_Food extends AppCompatActivity {
    ProgressBar progressBarAdminFood;
    ListView listView;
    Admin_Food_Adapter adapter;
    List<QuanAn> list_quanan;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("quanans");
    Button btnThem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_food);

        init();
        LoadData();

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_Food.this, Admin_Food_Add.class);
                startActivity(intent);
            }
        });

    }

    private void LoadData() {
        reference.orderByValue().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list_quanan.clear();

                for (DataSnapshot value : snapshot.getChildren()) {

                    QuanAn quanAn = new QuanAn();
                    quanAn.setmMaQuanAn(value.getKey());
                    quanAn.setmTenQuanAn(value.child("mTenQuanAn").getValue().toString());
                    quanAn.setmDiaChiQuan(value.child("mDiaChiQuan").getValue().toString());
                    quanAn.setmGioDongCua(value.child("mGioDongCua").getValue().toString());
                    quanAn.setmGioMoCua(value.child("mGioMoCua").getValue().toString());
                    quanAn.setmGiaTien(value.child("mGiaTien").getValue().toString());
                    quanAn.setmMoTaQuanAn(value.child("mMoTaQuanAn").getValue().toString());

                    list_quanan.add(quanAn);
//                    Log.e("abc", quanAn.getmMaQuanAn());

                }
                progressBarAdminFood.setVisibility(View.GONE);
                listView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void init() {
        progressBarAdminFood = findViewById(R.id.progressBarAdminFood);
        listView = findViewById(R.id.listAdminFood);
        list_quanan = new ArrayList<>();
        adapter = new Admin_Food_Adapter(this, R.layout.custom_listquanan, list_quanan);
        btnThem = findViewById(R.id.btnThemQuanAnAdmin);


    }


    public void DeleteFood(final int position) {
        list_quanan.remove(position);
        adapter.notifyDataSetChanged();
    }


}