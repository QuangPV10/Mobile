package goodman.gm.p_mobile.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import goodman.gm.p_mobile.Model.Blog;
import goodman.gm.p_mobile.R;

public class ChiTietBlog extends AppCompatActivity {
    TextView tvTenQuan, tvTieuDe, tvNgayDang, tvNoiDung, tvDiem;
    ImageView img;
    Blog blog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_blog);
        init();
    }

    private void init() {
        tvTenQuan = findViewById(R.id.tvtenQuanBlog);
        tvTieuDe = findViewById(R.id.tvTieuDeBLog);
        tvNgayDang = findViewById(R.id.tvNgayDangBlog);
        tvNoiDung = findViewById(R.id.tvNoiDungBLog);
        tvDiem = findViewById(R.id.tvDiemBlog);
        img = findViewById(R.id.imgHinhBlog);


    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();
        blog = (Blog) intent.getSerializableExtra("chitietblog");
        Picasso.get().load(blog.getmHinhAnh()).into(img);
        tvTenQuan.setText(blog.getmTenQuan());
        tvTieuDe.setText(blog.getmTieuDe());
        tvNgayDang.setText(blog.getmNgayCapNhat());
        tvNoiDung.setText(blog.getmNoiDung());
        tvDiem.setText(blog.getmPoint().toString());

    }
}