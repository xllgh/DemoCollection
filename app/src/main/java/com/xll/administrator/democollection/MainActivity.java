package com.xll.administrator.democollection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.Arrays;

public class MainActivity extends Activity implements MyRecylerAdapter.RecyclerItemClickListener {
    private RecyclerView mRecyclerView;
    private MyRecylerAdapter adapter;
    private  String[] array={"DownloadActivity","SpannableStringActivity","SugarOrmDemo"};
    String testFlag ="111";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        String url=BuildConfig.API_HOST;
        Log.e("xll","url+"+url);
    }

    private void initView(){

        mRecyclerView= (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        adapter=new MyRecylerAdapter(MainActivity.this, Arrays.asList(array));
        adapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(adapter);
    }


    @Override
    public void onRecyclerItemClick(int position) {
        try {
          Class<?> myClass= Class.forName(getPackageName()+"."+array[position]);
            Intent intent=new Intent();
            intent.setClass(MainActivity.this,myClass);
            MainActivity.this.startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}
