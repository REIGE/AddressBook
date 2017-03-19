package com.reige.addressbook;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements IndexBar.OnSlideListener{

    ArrayList<ContactsBean> mDatas;
    private ContactsAdapter mAdapter;
    private LinearLayoutManager mManager;
    private IndexBar mIndexBar;
    private RecyclerView mRecyclerView;
    private TextView mTv_letter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_data);
        IndexBar mIndexBar = (IndexBar) findViewById(R.id.indexBar);
        mTv_letter = (TextView)findViewById(R.id.textView);

        //设置垂直滑动
        mRecyclerView.setLayoutManager(mManager = new LinearLayoutManager(this));

        mRecyclerView.setAdapter(mAdapter = new ContactsAdapter(this, mDatas));

        //设置item装饰
        mRecyclerView.addItemDecoration(new IndicatorDecoration(this, mDatas));

        mIndexBar.setOnSlideListener(this);
    }

    private void initData() {
        String[] stringArray = getResources().getStringArray(R.array.ContactsBean);
        mDatas = new ArrayList<>();

        for(int i = 0 ;i<26 ;i++) {
            for(int j = 0;j<10;j++){
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

    @Override
    public void onSlide(String letter) {
        int position = -1;
        for(int i = 0;i<mDatas.size();i++){
            if(mDatas.get(i).index.equals(letter) ){
                position = i;
                ((LinearLayoutManager)mRecyclerView.getLayoutManager()).scrollToPositionWithOffset(position,0);
                break;
            }
        }
        if (position != -1){

            showLetterText(letter);

        }

    }

    Handler handler = new Handler();
    private void showLetterText(String letter) {
        mTv_letter.setVisibility(View.VISIBLE);
        handler.removeCallbacksAndMessages(null);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mTv_letter.setVisibility(View.INVISIBLE);
            }
        },1000);

        mTv_letter.setText(letter);
    }
}
