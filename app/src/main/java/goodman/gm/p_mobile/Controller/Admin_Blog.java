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

import goodman.gm.p_mobile.Adapter.Admin_Blog_Adapter;
import goodman.gm.p_mobile.Model.Blog;
import goodman.gm.p_mobile.R;

public class Admin_Blog extends AppCompatActivity {
    ProgressBar progressBarAdminBlog;
    ListView listView;
    Admin_Blog_Adapter adapter;
    List<Blog> lstBlog;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("blogs");
    Button btnThem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_blog);
        init();
        loadData();

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_Blog.this, Admin_Blog_Add.class);
                startActivity(intent);
            }
        });
    }

    private void loadData() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lstBlog.clear();
                for (DataSnapshot value : snapshot.getChildren()) {
                    Blog blog = new Blog();
                    blog.setmMaBlog(value.getKey());
//                    blog.setmHinhAnh(value.child("mHinhAnh").getValue().toString());
                    blog.setmNgayCapNhat(value.child("mNgayCapNhat").getValue().toString());
                    blog.setmNoiDung(value.child("mNoiDung").getValue().toString());
                    blog.setmPoint(Double.valueOf(value.child("mPoint").getValue().toString()));
                    blog.setmTieuDe(value.child("mTieuDe").getValue().toString());
                    blog.setmTenQuan(value.child("mTenQuan").getValue().toString());
                    lstBlog.add(blog);


                }
                progressBarAdminBlog.setVisibility(View.GONE);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void init() {
        progressBarAdminBlog = findViewById(R.id.progressBarAdminBlog);
        listView = findViewById(R.id.listAdminBlog);
        lstBlog = new ArrayList<>();
        adapter = new Admin_Blog_Adapter(Admin_Blog.this, R.layout.custom_listblog, lstBlog);
        btnThem = findViewById(R.id.btnThemBlogAdmin);

    }

    public void DeleteBlog(int position) {
        lstBlog.remove(position);
        adapter.notifyDataSetChanged();
    }
}