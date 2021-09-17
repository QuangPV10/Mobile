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

import androidx.appcompat.app.AlertDialog;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import goodman.gm.p_mobile.Controller.Admin_ChiTiet_Near;
import goodman.gm.p_mobile.Controller.Admin_Near;
import goodman.gm.p_mobile.Model.DiaChi;
import goodman.gm.p_mobile.R;

public class Admin_Near_Adapter extends BaseAdapter {

    private Admin_Near context;
    private int layout;
    private List<DiaChi> list_diachi;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("gantois");

    public Admin_Near_Adapter(Admin_Near context, int layout, List<DiaChi> list_diachi) {
        this.context = context;
        this.layout = layout;
        this.list_diachi = list_diachi;
    }

    @Override
    public int getCount() {
        return list_diachi.size();
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
        TextView adminTenQuan, adminDiaChi, adminMaQuanAn;
        Button btnXoaNear;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
            holder = new ViewHolder();
            holder.adminMaQuanAn = convertView.findViewById(R.id.tvMaQANear);
            holder.adminTenQuan = convertView.findViewById(R.id.adminTenQuan);
            holder.adminDiaChi = convertView.findViewById(R.id.adminDiaChi);
            holder.btnXoaNear = convertView.findViewById(R.id.btnXoaNear);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        DiaChi diaChi = list_diachi.get(position);
        holder.adminMaQuanAn.setText(diaChi.getmMaQuanAn());
        holder.adminTenQuan.setText(diaChi.getmTenQuanAn());
        holder.adminDiaChi.setText(diaChi.getmDiaChi());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Admin_ChiTiet_Near.class);
                intent.putExtra("adminNear", list_diachi.get(position));
                context.startActivity(intent);
            }
        });
        holder.btnXoaNear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context,
                        android.R.style.Theme_DeviceDefault_Light_Dialog);
                builder.setTitle("Bạn có muốn xóa không ?");
                builder.setCancelable(false);
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String maQuanAn = list_diachi.get(position).getmMaQuanAn();
                        deleteOnFireBase(maQuanAn);

                        // xóa trên listview
                        context.DeleteDiaChi(position);
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

    private void deleteOnFireBase(String maQuanAn) {
        reference.child(maQuanAn).removeValue();
        Toast.makeText(context, "Xóa " + maQuanAn + " Thành Công", Toast.LENGTH_SHORT).show();
    }
}
