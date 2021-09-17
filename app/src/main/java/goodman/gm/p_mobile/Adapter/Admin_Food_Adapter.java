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

import goodman.gm.p_mobile.Controller.Admin_ChiTiet_Food;
import goodman.gm.p_mobile.Controller.Admin_Food;
import goodman.gm.p_mobile.Model.QuanAn;
import goodman.gm.p_mobile.R;

public class Admin_Food_Adapter extends BaseAdapter {

    private Admin_Food context;
    private int layout;
    private List<QuanAn> list_quanan;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("quanans");


    public Admin_Food_Adapter(Admin_Food context, int layout, List<QuanAn> list_quanan) {
        this.context = context;
        this.layout = layout;
        this.list_quanan = list_quanan;
    }

    @Override
    public int getCount() {
        return list_quanan.size();
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
        TextView tvTenQuanAn, tvDiaChi, tvMaQA;
        Button btnXoa;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
            holder = new ViewHolder();
            holder.tvMaQA = convertView.findViewById(R.id.tvMaQA);
            holder.tvTenQuanAn = convertView.findViewById(R.id.tvTenQA);
            holder.tvDiaChi = convertView.findViewById(R.id.tvDC);
            holder.btnXoa = convertView.findViewById(R.id.btnXoa);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        QuanAn quanAn = list_quanan.get(position);
        holder.tvMaQA.setText(quanAn.getmMaQuanAn());
        holder.tvTenQuanAn.setText(quanAn.getmTenQuanAn());
        holder.tvDiaChi.setText(quanAn.getmDiaChiQuan());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Admin_ChiTiet_Food.class);
                intent.putExtra("adminFoods", list_quanan.get(position));
                context.startActivity(intent);

            }
        });

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
                        String maQuanAn = list_quanan.get(position).getmMaQuanAn();
                        deleteOnFireBase(maQuanAn);

                        // xóa trên listview
                        context.DeleteFood(position);
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
