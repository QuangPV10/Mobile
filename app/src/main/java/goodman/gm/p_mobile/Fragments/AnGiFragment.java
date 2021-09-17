package goodman.gm.p_mobile.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import goodman.gm.p_mobile.Adapter.AnGi_Adapter;
import goodman.gm.p_mobile.Model.BinhLuan;
import goodman.gm.p_mobile.Model.QuanAn;
import goodman.gm.p_mobile.R;

public class AnGiFragment extends Fragment {
    RecyclerView recyclerView;
    AnGi_Adapter adapter;
    List<QuanAn> list_QuanAn;
    View view;
    //    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("quanans");
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_angi, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        init();
        loadData();

    }

    private void init() {
        recyclerView = view.findViewById(R.id.recyclerViewAnGi);
        list_QuanAn = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AnGi_Adapter(getContext(), R.layout.custom_layout_gridview_angi, list_QuanAn);
        recyclerView.setAdapter(adapter);


    }

    private void loadData() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list_QuanAn.clear();
                DataSnapshot snapshotQuanAn = snapshot.child("quanans");
                for (DataSnapshot value : snapshotQuanAn.getChildren()) {
                    QuanAn quanAn = new QuanAn();
                    quanAn.setmMaQuanAn(value.getKey());
                    quanAn.setmDiaChiQuan(value.child("mDiaChiQuan").getValue().toString());
                    quanAn.setmTenQuanAn(value.child("mTenQuanAn").getValue().toString());
                    quanAn.setmGioMoCua(value.child("mGioMoCua").getValue().toString());
                    quanAn.setmGioDongCua(value.child("mGioDongCua").getValue().toString());
                    quanAn.setmHinhAnh(value.child("mHinhAnh").getValue().toString());
                    quanAn.setmGiaoHang((Boolean) value.child("mGiaoHang").getValue());
                    quanAn.setmHinhAnhQuanAn(value.child("mHinhAnhQuanAn").getValue().toString());
                    quanAn.setmGiaTien(value.child("mGiaTien").getValue().toString());
                    quanAn.setmMoTaQuanAn(value.child("mMoTaQuanAn").getValue().toString());

                    DataSnapshot snapshotBinhLuan = snapshot.child("binhluans").child(quanAn.getmMaQuanAn());
                    List<BinhLuan> list_BinhLuan = new ArrayList<>();
                    for (DataSnapshot valueBinhLuan : snapshotBinhLuan.getChildren()) {
                        BinhLuan binhLuan = new BinhLuan();
                        binhLuan.setManbinhluan(valueBinhLuan.getKey());
                        binhLuan.setmNoiDung(valueBinhLuan.child("mNoiDung").getValue().toString());
                        binhLuan.setmTieuDe(valueBinhLuan.child("mTieuDe").getValue().toString());
                        binhLuan.setmLuotThich(valueBinhLuan.child("mLuotThich").getValue().toString());
                        binhLuan.setmChamDiem(valueBinhLuan.child("mChamDiem").getValue().toString());

                        DataSnapshot snapshotHinhAnhBL = snapshot.child("hinhanhbinhluans").child(binhLuan.getManbinhluan());
                        List<String> list_HinhAnhBinhLuan = new ArrayList<>();
                        for (DataSnapshot valueHinhBinhLuan : snapshotHinhAnhBL.getChildren()) {
                            list_HinhAnhBinhLuan.add(valueHinhBinhLuan.getValue(String.class));
                        }
                        binhLuan.setHinhanhBinhLuanList(list_HinhAnhBinhLuan);
                        list_BinhLuan.add(binhLuan);
                    }
                    quanAn.setList_BinhLuan(list_BinhLuan);
                    list_QuanAn.add(quanAn);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
