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

import de.hdodenhof.circleimageview.CircleImageView;
import goodman.gm.p_mobile.Model.User;
import goodman.gm.p_mobile.R;

public class EditProfile extends AppCompatActivity {

    CircleImageView circleImageView;
    Button btnSave, btnClose;
    Uri uri;
    User user;
    static final int REQUEST_EDIT_PROFILE = 1;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("thanhviens");
    StorageReference storage = FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        init();
        receiveData();
        xuly();
    }

    private void receiveData() {
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("sendEdit");
    }

    private void xuly() {
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_EDIT_PROFILE);
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfile.this, TrangCaNhan.class);
                startActivity(intent);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadProfileImage();
            }
        });

        getUserInfomation();
    }

    private void getUserInfomation() {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(user.getmUserName()).exists() && snapshot.child(user.getmUserName()).getChildrenCount() >0){
                    if(snapshot.child(user.getmUserName()).hasChild("mHinhAnh")){
                        String image = snapshot.child(user.getmUserName()).child("mHinhAnh").getValue().toString();
                        Picasso.get().load(image).into(circleImageView);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_EDIT_PROFILE && resultCode == RESULT_OK && data != null) {
            uri = data.getData();
            circleImageView.setImageURI(uri);
        }
    }

    private void uploadProfileImage() {
        ProgressDialog dialog = new ProgressDialog(EditProfile.this);
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
                                    HashMap<String, Object> userMap = new HashMap<>();
                                    userMap.put("mHinhAnh", uri.toString());
                                    reference.child(user.getmUserName()).updateChildren(userMap);
                                    dialog.dismiss();
                                    Toast.makeText(EditProfile.this, "Upload thành công", Toast.LENGTH_SHORT).show();
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
        circleImageView = findViewById(R.id.avatarEdit);
        btnClose = findViewById(R.id.btnClose);
        btnSave = findViewById(R.id.btnSave);
    }
}