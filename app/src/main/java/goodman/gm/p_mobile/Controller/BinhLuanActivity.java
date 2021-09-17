package goodman.gm.p_mobile.Controller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import goodman.gm.p_mobile.Adapter.AdapterHienThiHinhBinhLuan;
import goodman.gm.p_mobile.Model.BinhLuan;
import goodman.gm.p_mobile.R;

public class BinhLuanActivity extends AppCompatActivity {
    TextView txtTen, txtDiaChi, tvDang;
    String maquanan;
    EditText edtTieuDe, edtNoiDung, edtDiem;
    ImageButton btnChonHinh;
    RecyclerView recyclerViewChonHinhBinhLuan;
    AdapterHienThiHinhBinhLuan adapter;
    SharedPreferences sharedPreferences;
    List<String> listHinhDuocChon;
    final int REQUEST_CHONHINHBINHLUAN = 100;
    StorageReference storage = FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binh_luan);

        init();
        XuLy();


    }

    private void init() {
        txtTen = findViewById(R.id.txtTenQuanAn);
        txtDiaChi = findViewById(R.id.txtDiaChiQuanAn);
        btnChonHinh = findViewById(R.id.btnChonHinh);
        recyclerViewChonHinhBinhLuan = findViewById(R.id.recyclerChonHinhBinhLuan);
        tvDang = findViewById(R.id.txtDangBinhLuan);
        edtTieuDe = findViewById(R.id.edtTieuDeBinhLuan);
        edtNoiDung = findViewById(R.id.edtNoiDungBinhLuan);
        edtDiem = findViewById(R.id.edtDiemBinhLuan);
        listHinhDuocChon = new ArrayList<>();
        sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);

    }

    private void XuLy() {

        btnChonHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BinhLuanActivity.this, ChonHinhBinhLuan.class);
                startActivityForResult(intent, REQUEST_CHONHINHBINHLUAN);
            }
        });

        tvDang.setOnClickListener(v -> {
            BinhLuan binhLuan = new BinhLuan();
            String tieude = edtTieuDe.getText().toString();
            String noidung = edtNoiDung.getText().toString();
            String diem = edtDiem.getText().toString();
            String tenuser = sharedPreferences.getString("UserName", "");

            binhLuan.setmTieuDe(tieude);
            binhLuan.setmNoiDung(noidung);
            binhLuan.setmChamDiem(diem);
            binhLuan.setmLuotThich("0");
            binhLuan.setTenuser(tenuser);

            ThemBinhLuan(maquanan, binhLuan, listHinhDuocChon);
//            Intent intent = new Intent(BinhLuanActivity.this, ChiTietQuanAn.class);
//            startActivity(intent);

            finish();

        });
    }

    private void ThemBinhLuan(String maquanan, BinhLuan binhLuan, List<String> listHinhDuocChon) {
        DatabaseReference nodeBinhLuan = FirebaseDatabase.getInstance().getReference("binhluans");
        String mabinhluan = nodeBinhLuan.child(maquanan).push().getKey();

        nodeBinhLuan.child(maquanan).child(mabinhluan).setValue(binhLuan).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                    if (listHinhDuocChon.size() > 0) {
                        for (String valueHinh : listHinhDuocChon) {
                            Uri uri = Uri.fromFile(new File(valueHinh));
                            ProgressDialog dialog = new ProgressDialog(BinhLuanActivity.this);
                            dialog.setTitle("Đang đăng");
                            dialog.show();
//                            StorageReference storageReference = FirebaseStorage.getInstance().getReference("hinhanh/" + uri.getLastPathSegment());
//                            storageReference.putFile(uri)
                            StorageReference file = storage.child(System.currentTimeMillis() + "." + getFileExtension(uri));
                            file.putFile(uri)
                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            file.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    FirebaseDatabase.getInstance().getReference().child("hinhanhbinhluans").child(mabinhluan).push().setValue(uri.toString());
                                                    dialog.dismiss();
                                                    Toast.makeText(BinhLuanActivity.this, "Upload thành công", Toast.LENGTH_SHORT).show();

                                                }
                                            });

//                                            dialog.dismiss();
//                                            Toast.makeText(BinhLuanActivity.this, "Upload thành công", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                                            float percent = (100 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                                            dialog.setTitle("Đang tải " + (int) percent + "%");
                                        }
                                    });
                            Log.d("ccc", uri + "");
                        }
                    }
                    Toast.makeText(BinhLuanActivity.this, "Đã đăng", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(BinhLuanActivity.this, TrangChu.class);
                    startActivity(intent);
                }
            }
        });
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
        txtTen.setText(intent.getStringExtra("tqa"));
        txtDiaChi.setText(intent.getStringExtra("dc"));
        maquanan = intent.getStringExtra("mqa");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHONHINHBINHLUAN) {
            if (resultCode == RESULT_OK) {
                listHinhDuocChon = data.getStringArrayListExtra("listHinhDuocChon");
                adapter = new AdapterHienThiHinhBinhLuan(this, R.layout.custom_layout_hienthihinhbinhluan, listHinhDuocChon);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
                recyclerViewChonHinhBinhLuan.setLayoutManager(layoutManager);
                recyclerViewChonHinhBinhLuan.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }
    }
}