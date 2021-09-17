package goodman.gm.p_mobile.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import goodman.gm.p_mobile.Controller.HienThiChiTietBinhLuan;
import goodman.gm.p_mobile.Model.BinhLuan;
import goodman.gm.p_mobile.R;

public class HinhBinhLuan_Adapter extends RecyclerView.Adapter<HinhBinhLuan_Adapter.ViewHolderHinhBinhLuan> {

    Context context;
    int resource;
    List<String> listHinh;
    BinhLuan binhLuan;
    boolean isChiTietBinhLuan;


    public HinhBinhLuan_Adapter(Context context, int resource, List<String> listHinh, BinhLuan binhLuan, boolean isChiTietBinhLuan) {
        this.context = context;
        this.resource = resource;
        this.listHinh = listHinh;
        this.binhLuan = binhLuan;
        this.isChiTietBinhLuan = isChiTietBinhLuan;

    }

    public class ViewHolderHinhBinhLuan extends RecyclerView.ViewHolder {
        ImageView imageHinhBinhLuan;
        TextView tvSoHinhBinhLuan;
        FrameLayout frameLayout;

        public ViewHolderHinhBinhLuan(View itemView) {
            super(itemView);
            imageHinhBinhLuan = itemView.findViewById(R.id.imageBinhLuan);
            tvSoHinhBinhLuan = itemView.findViewById(R.id.txtSoHinhBinhLuan);
            frameLayout = itemView.findViewById(R.id.khungSoHinhBinhLuan);
        }
    }

    @Override
    public HinhBinhLuan_Adapter.ViewHolderHinhBinhLuan onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        ViewHolderHinhBinhLuan viewHolderHinhBinhLuan = new ViewHolderHinhBinhLuan(view);

        return viewHolderHinhBinhLuan;
    }

    @Override
    public void onBindViewHolder(final HinhBinhLuan_Adapter.ViewHolderHinhBinhLuan holder, final int position) {
        Picasso.get().load(listHinh.get(position)).into(holder.imageHinhBinhLuan);
        if (!isChiTietBinhLuan) {
            if (position == 3) {
                int sohinhconlai = listHinh.size() - 4;
                if (sohinhconlai > 0) {
                    holder.frameLayout.setVisibility(View.VISIBLE);
                    holder.tvSoHinhBinhLuan.setText("+" + sohinhconlai);
                    holder.imageHinhBinhLuan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, HienThiChiTietBinhLuan.class);
                            intent.putExtra("binhluanmodel", binhLuan);
                            context.startActivity(intent);
                        }
                    });
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        if (!isChiTietBinhLuan) {
            if (listHinh.size() < 4) {
                return listHinh.size();
            } else {
                return 4;
            }

        } else {
            return listHinh.size();
        }

    }


}