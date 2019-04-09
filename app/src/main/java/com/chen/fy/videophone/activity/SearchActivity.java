package com.chen.fy.videophone.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.chen.fy.videophone.R;

public class SearchActivity extends AppCompatActivity{

    private EditText ed_search;
    private ImageView iv_record;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.search_main);

        initView();
    }

    private void initView() {
        ed_search = findViewById(R.id.search_main);
        iv_record = findViewById(R.id.search_record);

        MyOnClickListener myOnClickListener = new MyOnClickListener();
        ed_search.setOnClickListener(myOnClickListener);
        iv_record.setOnClickListener(myOnClickListener);
    }

    class MyOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.search_main:        //搜索
                    Toast.makeText(SearchActivity.this,"搜索",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.search_record:      //录音
                    Toast.makeText(SearchActivity.this,"录音",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
