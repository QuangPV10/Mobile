package goodman.gm.p_mobile.Adapter;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import goodman.gm.p_mobile.Controller.Admin_Comment_MaBinhLuan;
import goodman.gm.p_mobile.Model.BinhLuan;
import goodman.gm.p_mobile.Model.QuanAn;
import goodman.gm.p_mobile.R;

public class Admin_Comment_List_Adapter extends BaseAdapter {
    private Admin_Comment_MaBinhLuan context;
    private int layout;
    private List<BinhLuan> lst_BinhLuan;

    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("binhluans");
    DatabaseReference xoaBinhLuan = FirebaseDatabase.getInstance().getReference();


    public Admin_Comment_List_Adapter(Admin_Comment_MaBinhLuan context, int layout, List<BinhLuan> lst_BinhLuan) {
        this.context = context;
        this.layout = layout;
        this.lst_BinhLuan = lst_BinhLuan;
    }

    @Override
    public int getCount() {
        return lst_BinhLuan.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class ViewHolder {
        TextView tvMaBl, tvTieuDe, tvNoiDung;
        Button btnXoa;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
            holder = new ViewHolder();
            holder.tvMaBl = convertView.findViewById(R.id.tvMaBinhLuan);
            holder.tvTieuDe = convertView.findViewById(R.id.tvTieuDeBinhLuan);
            holder.tvNoiDung = convertView.findViewById(R.id.tvNoiDungBinhLuan);
            holder.btnXoa = convertView.findViewById(R.id.btnXoaBinhLuan);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        BinhLuan binhLuan = lst_BinhLuan.get(position);
        holder.tvMaBl.setText(binhLuan.getManbinhluan());
        holder.tvTieuDe.setText(binhLuan.getmTieuDe());
        holder.tvNoiDung.setText(binhLuan.getmNoiDung());
        holder.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context,
                        android.R.style.Theme_DeviceDefault_Light_Dialog);
                builder.setTitle("Bạn có muốn xóa không ?");
                builder.setCancelable(false);
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // xóa trên firebase
                        String maBinhLuan = lst_BinhLuan.get(position).getManbinhluan();
                        deleteOnFireBase(maBinhLuan);

                        // xóa trên listview
                        context.DeleteComment(position);
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.show();


            }
        });

        return convertView;
    }

    public void deleteOnFireBase(String maBl) {
        xoaBinhLuan.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataSnapshot snapshotQuanAn = snapshot.child("quanans");
                for (DataSnapshot value : snapshotQuanAn.getChildren()) {
                    QuanAn quanAn = new QuanAn();
                    quanAn.setmMaQuanAn(value.getKey());
                    DataSnapshot snapshotBinhLuan = snapshot.child("binhluans").child(quanAn.getmMaQuanAn());
                    for (DataSnapshot valueBinhLuan : snapshotBinhLuan.getChildren()) {
                        BinhLuan binhLuan = new BinhLuan();
                        binhLuan.setManbinhluan(valueBinhLuan.getKey());
                        if (maBl.equals(valueBinhLuan.getKey())) {
                            reference.child(quanAn.getmMaQuanAn()).child(maBl).removeValue();
                            Toast.makeText(context, "Xóa " + maBl + " Thành Công", Toast.LENGTH_SHORT).show();

                        }
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}
