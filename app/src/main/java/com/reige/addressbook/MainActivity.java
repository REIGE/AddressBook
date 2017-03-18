package com.reige.addressbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<ContactsBean> mDatas;
    private ContactsAdapter mAdapter;
    private LinearLayoutManager mManager ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_data);

        //设置垂直滑动
        recyclerView.setLayoutManager(mManager = new LinearLayoutManager(this));

        recyclerView.setAdapter(mAdapter = new ContactsAdapter(this, mDatas));

        //设置item装饰
        recyclerView.addItemDecoration(new IndicatorDecoration(this,mDatas,IndicatorDecoration.VERTICAL_LIST));

    }

    private void initData() {
        mDatas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            ContactsBean contactsBean = new ContactsBean("hahhaha", "");
            mDatas.add(contactsBean);
        }
    }

}
