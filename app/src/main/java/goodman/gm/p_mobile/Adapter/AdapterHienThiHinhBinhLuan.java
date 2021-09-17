package goodman.gm.p_mobile.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import goodman.gm.p_mobile.R;

public class AdapterHienThiHinhBinhLuan extends RecyclerView.Adapter<AdapterHienThiHinhBinhLuan.ViewHolderHinhBinhLuan> {

    Context context;
    int layout;
    List<String> list;

    public AdapterHienThiHinhBinhLuan(Context context, int layout, List<String> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;

    }

    @NonNull
    @Override
    public ViewHolderHinhBinhLuan onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        ViewHolderHinhBinhLuan viewHolderHinhBinhLuan = new ViewHolderHinhBinhLuan(view);
        return viewHolderHinhBinhLuan;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderHinhBinhLuan holder, int position) {
        Uri uri = Uri.parse(list.get(position));
        holder.imageView.setImageURI(uri);
        holder.imageViewDelete.setTag(position);
        holder.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int vitri = (int) v.getTag();
                list.remove(vitri);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolderHinhBinhLuan extends RecyclerView.ViewHolder {
        ImageView imageView, imageViewDelete;

        public ViewHolderHinhBinhLuan(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgChonHinhBinhLuan);
            imageViewDelete = itemView.findViewById(R.id.ImageViewDelete);
        }
    }
}
