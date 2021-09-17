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
import android.widget.EditText;
import android.widget.ImageView;
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

import goodman.gm.p_mobile.Model.QuanAn;
import goodman.gm.p_mobile.R;

public class Admin_Food_Add extends AppCompatActivity {
    EditText edtMaQuan, edtTenQuan, edtDiaChi, edtGioMoCua, edtGioDongCua, edtGiaTien, edtMoTa;
    ImageView imgAnhQuanAn, imgAnhMonAn;
    Uri uri;
    String imageAnhQuan, imageAnhMon;
    Button btnThem, btnQuayLai;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("quanans");
    StorageReference storage = FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_food_add);

        init();
        controlButton();

    }

    private void controlButton() {

        imgAnhQuanAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 2);
            }
        });

        imgAnhMonAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 3);
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (!snapshot.child(edtMaQuan.getText().toString()).exists()) {
                            String maquanan = edtMaQuan.getText().toString();
                            String tenQuan = edtTenQuan.getText().toString();
                            String diaChiQuan = edtDiaChi.getText().toString();
                            String GMC = edtGioMoCua.getText().toString();
                            String GDC = edtGioMoCua.getText().toString();
                            String moTa = edtMoTa.getText().toString();
                            String giaTien = edtGiaTien.getText().toString();

                            QuanAn quanAn = new QuanAn(false, GDC, GMC, imageAnhMon, tenQuan, diaChiQuan, maquanan, imageAnhQuan, giaTien, moTa);
                            reference.child(maquanan).setValue(quanAn);
                            Toast.makeText(Admin_Food_Add.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Admin_Food_Add.this, Admin_Food.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(Admin_Food_Add.this, "Mã quán ăn tồn tại", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        btnQuayLai.setOnClickListener(new View.OnClickListener() {
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
            imgAnhQuanAn.setImageURI(uri);
            uploadImageAnhQuan();
        }
        if (requestCode == 3 && resultCode == RESULT_OK && data != null) {
            uri = data.getData();
            imgAnhMonAn.setImageURI(uri);
            uploadImageAnhMon();

        }
    }

    private void uploadImageAnhQuan() {
        ProgressDialog dialog = new ProgressDialog(Admin_Food_Add.this);
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
        ProgressDialog dialog = new ProgressDialog(Admin_Food_Add.this);
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

    private void init() {
        edtMaQuan = findViewById(R.id.edtMaQuanAnFood);
        edtTenQuan = findViewById(R.id.edtTenQuanAnFood);
        edtDiaChi = findViewById(R.id.edtDiaChiFood);
        edtGioDongCua = findViewById(R.id.edtGioDongCuaFood);
        edtGioMoCua = findViewById(R.id.edtGioMoCuaFood);
        edtGiaTien = findViewById(R.id.edtGiaTienFood);
        edtMoTa = findViewById(R.id.edtMoTaFood);
        imgAnhQuanAn = findViewById(R.id.imageQuanAnAdd);
        imgAnhMonAn = findViewById(R.id.imageMonAnAdd);
        btnThem = findViewById(R.id.btnThemFood);
        btnQuayLai = findViewById(R.id.btnBackFood);

    }


}