package goodman.gm.p_mobile.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

import goodman.gm.p_mobile.Controller.ChiTietQuanAn;
import goodman.gm.p_mobile.Model.QuanAn;
import goodman.gm.p_mobile.R;

public class AnGi_Adapter extends RecyclerView.Adapter<AnGi_Adapter.ViewHolder> {
    private int layout;
    private List<QuanAn> list_QuanAn;
    private Context context;

    public AnGi_Adapter(Context context, int layout, List<QuanAn> list_QuanAn) {
        this.layout = layout;
        this.list_QuanAn = list_QuanAn;
        this.context = context;
    }

    @NonNull
    @Override
    public AnGi_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnGi_Adapter.ViewHolder holder, final int position) {

        QuanAn quanAn = list_QuanAn.get(position);
        holder.tvTenQuanAnAngi.setText(quanAn.getmTenQuanAn());
        Picasso.get().load(quanAn.getmHinhAnh()).into(holder.hinhQuanAnAngi);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChiTietQuanAn.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("listbinhluans", (Serializable) list_QuanAn.get(position).getList_BinhLuan());
                intent.putExtra("quanans", list_QuanAn.get(position));
                intent.putExtra("data", bundle);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list_QuanAn.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenQuanAnAngi;
        ImageView hinhQuanAnAngi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenQuanAnAngi = itemView.findViewById(R.id.tvTenQuanAnAnGi);
            hinhQuanAnAngi = itemView.findViewById(R.id.HinhQuanAnAnGi);


        }
    }
}
