package goodman.gm.p_mobile.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import goodman.gm.p_mobile.Controller.BlogActivity;
import goodman.gm.p_mobile.Controller.ChiTietBlog;
import goodman.gm.p_mobile.Model.Blog;
import goodman.gm.p_mobile.R;

public class Blog_Adapter extends BaseAdapter {
    private BlogActivity context;
    private int layout;
    private List<Blog> lstBlog;

    public Blog_Adapter(BlogActivity context, int layout, List<Blog> lstBlog) {
        this.context = context;
        this.layout = layout;
        this.lstBlog = lstBlog;
    }

    @Override
    public int getCount() {
        return lstBlog.size();
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
        TextView tvBlogTieuDe, tvBlogTenQuan, tvBlogDiem;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvBlogTenQuan = convertView.findViewById(R.id.tvBlogTenQuan);
            viewHolder.tvBlogTieuDe = convertView.findViewById(R.id.tvBlogTieuDe);
            viewHolder.tvBlogDiem = convertView.findViewById(R.id.tvBlogDiem);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }
        Blog blog = lstBlog.get(position);
        viewHolder.tvBlogDiem.setText(blog.getmPoint().toString());
        viewHolder.tvBlogTenQuan.setText(blog.getmTenQuan());
        viewHolder.tvBlogTieuDe.setText(blog.getmTieuDe());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChiTietBlog.class);
                intent.putExtra("chitietblog", lstBlog.get(position));
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}
