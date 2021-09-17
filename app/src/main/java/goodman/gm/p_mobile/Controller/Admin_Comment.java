package goodman.gm.p_mobile.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import goodman.gm.p_mobile.Adapter.Admin_Comment_Adapter;
import goodman.gm.p_mobile.Model.BinhLuan;
import goodman.gm.p_mobile.Model.QuanAn;
import goodman.gm.p_mobile.R;

public class Admin_Comment extends AppCompatActivity {
    ProgressBar progressBarAdminCommnent;
    ListView listView;
    Admin_Comment_Adapter adapter;
    List<QuanAn> lstQuanAn;

    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_comment);
        init();
        loadData();
    }

    private void loadData() {

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lstQuanAn.clear();
                DataSnapshot snapshotQuanAn = snapshot.child("quanans");
                for (DataSnapshot value : snapshotQuanAn.getChildren()) {
                    QuanAn quanAn = new QuanAn();
                    quanAn.setmMaQuanAn(value.getKey());
                    DataSnapshot snapshotBinhLuan = snapshot.child("binhluans").child(quanAn.getmMaQuanAn());
                    List<BinhLuan> list_BinhLuan = new ArrayList<>();
                    for (DataSnapshot valueBinhLuan : snapshotBinhLuan.getChildren()) {
                        BinhLuan binhLuan = new BinhLuan();
                        binhLuan.setManbinhluan(valueBinhLuan.getKey());
                        binhLuan.setmNoiDung(valueBinhLuan.child("mNoiDung").getValue().toString());
                        binhLuan.setmTieuDe(valueBinhLuan.child("mTieuDe").getValue().toString());
                        binhLuan.setmLuotThich(valueBinhLuan.child("mLuotThich").getValue().toString());
                        binhLuan.setmChamDiem(valueBinhLuan.child("mChamDiem").getValue().toString());
                        binhLuan.setTenuser(valueBinhLuan.child("tenuser").getValue().toString());
                        list_BinhLuan.add(binhLuan);

                    }
                    quanAn.setList_BinhLuan(list_BinhLuan);
                    lstQuanAn.add(quanAn);
                }
                progressBarAdminCommnent.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void init() {
        progressBarAdminCommnent = findViewById(R.id.progressBarAdminComment);
        listView = findViewById(R.id.lstBinhLuanAdmin);
        lstQuanAn = new ArrayList<>();
        adapter = new Admin_Comment_Adapter(this, R.layout.custom_listbinhluan, lstQuanAn);
        listView.setAdapter(adapter);


    }

    public void DeleteCommnet(int position) {
        lstQuanAn.remove(position);
        adapter.notifyDataSetChanged();
    }
}