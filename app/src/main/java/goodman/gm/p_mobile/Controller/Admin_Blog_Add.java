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

import de.hdodenhof.circleimageview.CircleImageView;
import goodman.gm.p_mobile.Model.Blog;
import goodman.gm.p_mobile.R;

public class Admin_Blog_Add extends AppCompatActivity {
    TextView tvAdminMaQuanAn, tvAdminTenQA, tvAdminTieuDe, tvAdminNgay, tvAdminDiem, tvAdminNoiDung;
    CircleImageView circleImageView;
    String image;
    Uri uri;
    Button btnThem, btnBack;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("blogs");
    StorageReference storage = FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__blog__add);

        init();
        xuly();
    }

    private void xuly() {

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 16);
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (!snapshot.child(tvAdminMaQuanAn.getText().toString()).exists()) {
                            String maBlog = tvAdminMaQuanAn.getText().toString();
                            String tenQuan = tvAdminTenQA.getText().toString();
                            String tieude = tvAdminTieuDe.getText().toString();
                            String ngay = tvAdminNgay.getText().toString();
                            String noidung = tvAdminNoiDung.getText().toString();
                            String diem = tvAdminDiem.getText().toString();

                            Blog blog = new Blog(tieude, image, noidung, tenQuan, ngay, Double.parseDouble(diem));
                            reference.child(maBlog).setValue(blog);
                            Toast.makeText(Admin_Blog_Add.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Admin_Blog_Add.this, Admin_Blog.class);
                            startActivity(intent);
                        }

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
            circleImageView.setImageURI(uri);
            uploadImage();
        }
    }

    private void uploadImage() {
        ProgressDialog dialog = new ProgressDialog(Admin_Blog_Add.this);
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

    private void init() {
        tvAdminMaQuanAn = findViewById(R.id.tvBlogMaBlogAdminAdd);
        tvAdminTenQA = findViewById(R.id.tvBlogTenQuanAnAdminAdd);
        tvAdminTieuDe = findViewById(R.id.tvBlogTieuDeAdminAdd);
        tvAdminNgay = findViewById(R.id.tvNgayCapNhatAdminAdd);
        tvAdminDiem = findViewById(R.id.tvBlogDiemAdminAdd);
        tvAdminNoiDung = findViewById(R.id.tvBlogNoiDungAdminAdd);
        circleImageView = findViewById(R.id.imageBlog);
        btnThem = findViewById(R.id.btnBlogAddDone);
        btnBack = findViewById(R.id.btnBlogBackDoneAdd);
    }
}