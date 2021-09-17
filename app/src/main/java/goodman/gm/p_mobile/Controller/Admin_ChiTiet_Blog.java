package goodman.gm.p_mobile.Controller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


import java.util.HashMap;

import goodman.gm.p_mobile.Model.Blog;
import goodman.gm.p_mobile.R;

public class Admin_ChiTiet_Blog extends AppCompatActivity {
    TextView tvAdminTenQA, tvAdminTieuDe, tvAdminNgay, tvAdminDiem, tvAdminNoiDung;
    ImageView img;
    String maBlog, image;
    Uri uri;
    Button btnUpdate, btnBack;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("blogs");
    StorageReference storage = FirebaseStorage.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__chitiet__blog);

        init();
        loadData();
        xuly();
    }

    private void loadData() {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(maBlog).exists()) {
                    if (snapshot.child(maBlog).hasChild("mHinhAnh")) {
                        String image = snapshot.child(maBlog).child("mHinhAnh").getValue().toString();
                        Picasso.get().load(image).into(img);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void xuly() {

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 16);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String tenQuan = tvAdminTenQA.getText().toString();
                        String tieude = tvAdminTieuDe.getText().toString();
                        String ngay = tvAdminNgay.getText().toString();
                        String noidung = tvAdminNoiDung.getText().toString();
                        String diem = tvAdminDiem.getText().toString();

                        Blog blog = new Blog(tieude, image, noidung, tenQuan, ngay, Double.parseDouble(diem));
                        reference.child(maBlog).setValue(blog);
                        Toast.makeText(Admin_ChiTiet_Blog.this, "Update thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Admin_ChiTiet_Blog.this, Admin_Blog.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 16 && resultCode == RESULT_OK && data != null) {
            uri = data.getData();
            img.setImageURI(uri);
            uploadImage();
        }
    }

    private void uploadImage() {
        ProgressDialog dialog = new ProgressDialog(Admin_ChiTiet_Blog.this);
        dialog.setTitle("Đang xử lý");
        dialog.show();
        if (uri != null) {
            StorageReference file = storage.child(System.currentTimeMillis() + "." + getFileExtension(uri));
            file.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            file.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    image = uri.toString();
                                    dialog.dismiss();
                                }
                            });
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            float percent = (100 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                            dialog.setTitle("Đang tải " + (int) percent + "%");
                        }
                    });
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        Blog blog = (Blog) intent.getSerializableExtra("adminBlogs");
        maBlog = blog.getmMaBlog();
        tvAdminTenQA.setText(blog.getmTenQuan());
        tvAdminTieuDe.setText(blog.getmTieuDe());
        tvAdminNgay.setText(blog.getmNgayCapNhat());
        tvAdminDiem.setText(String.valueOf(blog.getmPoint()));
        tvAdminNoiDung.setText(blog.getmNoiDung());

    }

    private void init() {
        tvAdminTenQA = findViewById(R.id.tvBlogTenQuanAnAdmin);
        tvAdminTieuDe = findViewById(R.id.tvBlogTieuDeAdmin);
        tvAdminNgay = findViewById(R.id.tvNgayCapNhatAdmin);
        tvAdminDiem = findViewById(R.id.tvBlogDiemAdmin);
        tvAdminNoiDung = findViewById(R.id.tvBlogNoiDungAdmin);
        img = findViewById(R.id.imageBlogChiTiet);
        btnUpdate = findViewById(R.id.btnBlogUpdateDone);
        btnBack = findViewById(R.id.btnBlogBackDone);
    }
}