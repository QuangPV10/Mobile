package goodman.gm.p_mobile.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import goodman.gm.p_mobile.Adapter.Admin_Comment_List_Adapter;
import goodman.gm.p_mobile.Model.BinhLuan;
import goodman.gm.p_mobile.Model.QuanAn;
import goodman.gm.p_mobile.R;

public class Admin_Comment_MaBinhLuan extends AppCompatActivity {
    ListView listView;
    Admin_Comment_List_Adapter adapter;
    List<BinhLuan> lst_BinhLuan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__comment__binh_luan);
        init();
        loadData();

    }

    private void loadData() {
        Intent intent = getIntent();
        QuanAn quanAn = (QuanAn) intent.getSerializableExtra("lstQuanans");

        lst_BinhLuan.clear();
        lst_BinhLuan = quanAn.getList_BinhLuan();
        adapter = new Admin_Comment_List_Adapter(Admin_Comment_MaBinhLuan.this, R.layout.custom_list_mabinhluan, lst_BinhLuan);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        Log.d("abcd", lst_BinhLuan.toString());
//        Intent intent2 = new Intent(Admin_Comment_MaBinhLuan.this, Admin_Comment_List_Adapter.class);
//        intent.putExtra("test", maQuan);
//        startActivity(intent2);

    }


    private void init() {
        listView = findViewById(R.id.lstMaBL);
        lst_BinhLuan = new ArrayList<>();

    }

    public void DeleteComment(final int position) {

        lst_BinhLuan.remove(position);
        adapter.notifyDataSetChanged();
    }
}