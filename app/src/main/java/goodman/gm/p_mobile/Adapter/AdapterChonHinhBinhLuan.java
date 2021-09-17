package goodman.gm.p_mobile.Adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import goodman.gm.p_mobile.Model.ChonHinh;
import goodman.gm.p_mobile.R;

public class AdapterChonHinhBinhLuan extends RecyclerView.Adapter<AdapterChonHinhBinhLuan.ViewHolderChonHinh> {

    Context context;
    int resource;
    List<ChonHinh> listDuongDan;

    public AdapterChonHinhBinhLuan(Context context, int resource, List<ChonHinh> listDuongDan) {
        this.context = context;
        this.resource = resource;
        this.listDuongDan = listDuongDan;
    }

    @Override
    public ViewHolderChonHinh onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(resource, parent, false);

        ViewHolderChonHinh viewHolderChonHinh = new ViewHolderChonHinh(view);
        return viewHolderChonHinh;
    }

    @Override
    public void onBindViewHolder(ViewHolderChonHinh holder, final int position) {
        final ChonHinh chonHinhBinhLuan = listDuongDan.get(position);
        Log.d("quang", chonHinhBinhLuan.getDuongdan() + "");
        Uri uri = Uri.parse(chonHinhBinhLuan.getDuongdan());
        Log.d("kiemtra", uri + "");
        holder.imageView.setImageURI(uri);
        holder.checkBox.setChecked(chonHinhBinhLuan.isCheck());
        holder.checkBox.setOnClickListener(v -> {
            CheckBox checkBox = (CheckBox) v;
            listDuongDan.get(position).setCheck(checkBox.isChecked());
        });
    }

    @Override
    public int getItemCount() {
        return listDuongDan.size();
    }

    public class ViewHolderChonHinh extends RecyclerView.ViewHolder {

        ImageView imageView;
        CheckBox checkBox;

        public ViewHolderChonHinh(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgChonHinhBinhLuan);
            checkBox = itemView.findViewById(R.id.checkBoxChonHinhBinhLuan);

        }
    }


}
