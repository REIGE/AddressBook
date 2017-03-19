package com.reige.addressbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.promeg.pinyinhelper.Pinyin;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<ContactsBean> mDatas;
    private ContactsAdapter mAdapter;
    private LinearLayoutManager mManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_data);

        //设置垂直滑动
        recyclerView.setLayoutManager(mManager = new LinearLayoutManager(this));

        recyclerView.setAdapter(mAdapter = new ContactsAdapter(this, mDatas));

        //设置item装饰s
        recyclerView.addItemDecoration(new IndicatorDecoration(this, mDatas));

    }

    private void initData() {
        String[] stringArray = getResources().getStringArray(R.array.ContactsBean);
        mDatas = new ArrayList<>();

        for(int i = 0 ;i<24 ;i++) {
            for(int j = 0;j<3;j++){
                int asci = 65 + i;
                char letter = (char)asci;
                ContactsBean contactsBean = new ContactsBean(letter+"", letter+"");
                mDatas.add(contactsBean);
            }
        }
        /*
        for (String name : stringArray){

            char[] chars = name.toCharArray();
            if(Pinyin.isChinese(chars[0])){
                String pinyin = Pinyin.toPinyin(chars[0]);
                CharSequence index = pinyin.subSequence(0, 1);
                mDatas.add(new ContactsBean(name,index+""));
            }else {
                mDatas.add(new ContactsBean(name,"#"));
            }
        }*/

    }
}
