package goodman.gm.p_mobile.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import goodman.gm.p_mobile.Adapter.HinhBinhLuan_Adapter;
import goodman.gm.p_mobile.Model.BinhLuan;
import goodman.gm.p_mobile.R;

public class HienThiChiTietBinhLuan extends AppCompatActivity {

    CircleImageView circleImageView;
    TextView txtTieuDeBinhLuan, txtNoiDungBinhLuan, txtSoDiem;
    RecyclerView recyclerViewBinhLuan;
    List<String> list_HinhAnh;
    BinhLuan binhLuan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_binhluan);

        init();
        loadData();

    }

    private void loadData() {
        binhLuan = (BinhLuan) getIntent().getSerializableExtra("binhluanmodel");
        txtTieuDeBinhLuan.setText(binhLuan.getmTieuDe());
        txtNoiDungBinhLuan.setText(binhLuan.getmNoiDung());
        txtSoDiem.setText(binhLuan.getmChamDiem());

        for (String value : binhLuan.getHinhanhBinhLuanList()) {
            list_HinhAnh.add(value);
            if (list_HinhAnh.size() == binhLuan.getHinhanhBinhLuanList().size()) {
                HinhBinhLuan_Adapter adapter = new HinhBinhLuan_Adapter(HienThiChiTietBinhLuan.this, R.layout.custom_hinh_binh_luan, list_HinhAnh, binhLuan, true);
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(HienThiChiTietBinhLuan.this, 2);
                recyclerViewBinhLuan.setLayoutManager(layoutManager);
                recyclerViewBinhLuan.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }
    }

    private void init() {
        recyclerViewBinhLuan = findViewById(R.id.lstHinhAnhBinhLuan);
        circleImageView = findViewById(R.id.cicleImageUser);
        txtTieuDeBinhLuan = findViewById(R.id.txtTieudebinhluan);
        txtNoiDungBinhLuan = findViewById(R.id.txtNoidungbinhluan);
        txtSoDiem = findViewById(R.id.txtChamDiemBinhLuan);

        list_HinhAnh = new ArrayList<>();
    }
}