package goodman.gm.p_mobile.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import goodman.gm.p_mobile.Model.BinhLuan;
import goodman.gm.p_mobile.R;

public class BinhLuan_Adapter extends RecyclerView.Adapter<BinhLuan_Adapter.ViewHolder> {
    private Context context;
    int layout;
    List<BinhLuan> list_BinhLuan;
    List<String> list_HinhAnh;

    public BinhLuan_Adapter(Context context, int layout, List<BinhLuan> list_BinhLuan) {
        this.context = context;
        this.layout = layout;
        this.list_BinhLuan = list_BinhLuan;
        list_HinhAnh = new ArrayList<>();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
        TextView txtTieuDeBinhLuan, txtNoiDungBinhLuan, txtSoDiem;
        RecyclerView recyclerViewBinhLuan;


        public ViewHolder(View itemView) {
            super(itemView);
            recyclerViewBinhLuan = itemView.findViewById(R.id.lstHinhAnhBinhLuan);
            circleImageView = itemView.findViewById(R.id.cicleImageUser);
            txtTieuDeBinhLuan = itemView.findViewById(R.id.txtTieudebinhluan);
            txtNoiDungBinhLuan = itemView.findViewById(R.id.txtNoidungbinhluan);
            txtSoDiem = itemView.findViewById(R.id.txtChamDiemBinhLuan);
        }
    }

    @Override
    public BinhLuan_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final BinhLuan_Adapter.ViewHolder holder, int position) {
        BinhLuan binhLuan = list_BinhLuan.get(position);
        holder.txtTieuDeBinhLuan.setText(binhLuan.getmTieuDe());
        holder.txtNoiDungBinhLuan.setText(binhLuan.getmNoiDung());
        holder.txtSoDiem.setText(binhLuan.getmChamDiem());

        for (String value : binhLuan.getHinhanhBinhLuanList()) {
            list_HinhAnh.add(value);
            if (list_HinhAnh.size() == binhLuan.getHinhanhBinhLuanList().size()) {
                HinhBinhLuan_Adapter adapter = new HinhBinhLuan_Adapter(context, R.layout.custom_hinh_binh_luan, list_HinhAnh,binhLuan,false);
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 2);
                holder.recyclerViewBinhLuan.setLayoutManager(layoutManager);
                holder.recyclerViewBinhLuan.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

        }

    }

    @Override
    public int getItemCount() {
        return list_BinhLuan.size();
    }
}



