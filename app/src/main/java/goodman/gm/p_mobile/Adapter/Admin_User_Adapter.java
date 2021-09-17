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

import goodman.gm.p_mobile.Controller.Admin_ChiTiet_User;
import goodman.gm.p_mobile.Controller.Admin_User;
import goodman.gm.p_mobile.Model.User;
import goodman.gm.p_mobile.R;

public class Admin_User_Adapter extends BaseAdapter {
    private Admin_User context;
    private int layout;
    private List<User> lstUser;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("thanhviens");


    public Admin_User_Adapter(Admin_User context, int layout, List<User> lstUser) {
        this.context = context;
        this.layout = layout;
        this.lstUser = lstUser;
    }


    @Override
    public int getCount() {
        return lstUser.size();
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
        TextView tvFullName, tvSdt;
        Button btnDelete;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.btnDelete = convertView.findViewById(R.id.btnDeleterAdmin);
            viewHolder.tvFullName = convertView.findViewById(R.id.tvTenDayDu);
            viewHolder.tvSdt = convertView.findViewById(R.id.tvSDT);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        User user = lstUser.get(position);

        viewHolder.tvSdt.setText(user.getmPhoneNumber());
        viewHolder.tvFullName.setText(user.getmFullName());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Admin_ChiTiet_User.class);
                intent.putExtra("adminUsers", lstUser.get(position));
                context.startActivity(intent);

            }
        });

        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
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
                        String userName = lstUser.get(position).getmUserName();
                        deleteOnFireBase(userName);


                        // xóa trên listview
                        context.DeleteUser(position);
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

    private void deleteOnFireBase(String username) {
        reference.child(username).removeValue();
        Toast.makeText(context, "Xóa " + username + " Thành Công", Toast.LENGTH_SHORT).show();
    }

}
