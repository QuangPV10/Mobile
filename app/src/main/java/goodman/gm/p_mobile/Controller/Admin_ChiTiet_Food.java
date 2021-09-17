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

import goodman.gm.p_mobile.Model.QuanAn;
import goodman.gm.p_mobile.R;

public class Admin_ChiTiet_Food extends AppCompatActivity {
    TextView tvAdminTenQA, tvAdminDC, tvAdminGMC, tvAdminGDC, tvAdminGT, tvAdminMT;
    ImageView imgAnhQuan, imgAnhMon;
    Uri uri;
    String maquanan, imageAnhQuan, imageAnhMon;
    Button btnUpdate, btnBack;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("quanans");
    StorageReference storage = FirebaseStorage.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_chi_tiet_food);

        init();
        loadData();
        xuly();
    }


    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        QuanAn quanAn = (QuanAn) intent.getSerializableExtra("adminFoods");
        maquanan = quanAn.getmMaQuanAn();
        tvAdminTenQA.setText(quanAn.getmTenQuanAn());
        tvAdminDC.setText(quanAn.getmDiaChiQuan());
        tvAdminGMC.setText(quanAn.getmGioMoCua());
        tvAdminGDC.setText(quanAn.getmGioDongCua());
        tvAdminGT.setText(quanAn.getmGiaTien());
        tvAdminMT.setText(quanAn.getmMoTaQuanAn());
    }


    private void xuly() {

        imgAnhQuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 2);
            }
        });

        imgAnhMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 3);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String tenQuan = tvAdminTenQA.getText().toString();
                        String diaChiQuan = tvAdminDC.getText().toString();
                        String GMC = tvAdminGMC.getText().toString();
                        String GDC = tvAdminGDC.getText().toString();
                        String moTa = tvAdminMT.getText().toString();
                        String giaTien = tvAdminGT.getText().toString();

                        QuanAn quanAn = new QuanAn(false, GDC, GMC, imageAnhMon, tenQuan, diaChiQuan, maquanan, imageAnhQuan, giaTien, moTa);
                        reference.child(maquanan).setValue(quanAn);
                        Toast.makeText(Admin_ChiTiet_Food.this, "Update thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Admin_ChiTiet_Food.this, Admin_Food.class);
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
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            uri = data.getData();
            imgAnhQuan.setImageURI(uri);
            uploadImageAnhQuan();
        }
        if (requestCode == 3 && resultCode == RESULT_OK && data != null) {
            uri = data.getData();
            imgAnhMon.setImageURI(uri);
            uploadImageAnhMon();

        }
    }

    private void uploadImageAnhQuan() {
        ProgressDialog dialog = new ProgressDialog(Admin_ChiTiet_Food.this);
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
                                    imageAnhQuan = uri.toString();
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

    private void uploadImageAnhMon() {
        ProgressDialog dialog = new ProgressDialog(Admin_ChiTiet_Food.this);
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
                                    imageAnhMon = uri.toString();
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

    private void loadData() {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(maquanan).exists()) {
                    if (snapshot.child(maquanan).hasChild("mHinhAnh")) {
                        String image = snapshot.child(maquanan).child("mHinhAnh").getValue().toString();
                        Picasso.get().load(image).into(imgAnhMon);
                    }
                    if (snapshot.child(maquanan).hasChild("mHinhAnhQuanAn")) {
                        String image = snapshot.child(maquanan).child("mHinhAnhQuanAn").getValue().toString();
                        Picasso.get().load(image).into(imgAnhQuan);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void init() {
        imgAnhQuan = findViewById(R.id.imageAnhQuanAn);
        tvAdminTenQA = findViewById(R.id.tvAdminTenQA);
        tvAdminDC = findViewById(R.id.tvAdminDC);
        tvAdminGMC = findViewById(R.id.tvAdminGMC);
        tvAdminGDC = findViewById(R.id.tvAdminGDC);
        tvAdminGT = findViewById(R.id.tvAdminGT);
        tvAdminMT = findViewById(R.id.tvAdminMT);
        btnUpdate = findViewById(R.id.btnFoodUpdateDone);
        btnBack = findViewById(R.id.btnFoodBackDone);
        imgAnhMon = findViewById(R.id.imageAnhMonAn);
    }
}