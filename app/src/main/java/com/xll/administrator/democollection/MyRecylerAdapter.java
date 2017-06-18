package com.xll.administrator.democollection;

import android.content.Context;
import android.database.ContentObserver;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/12.
 */

public class MyRecylerAdapter extends RecyclerView.Adapter {
    Context context;
    List<String> list;
    RecyclerItemClickListener listener;

    MyRecylerAdapter(Context context, List<String> list){
        this.context=context;
        this.list=list;
    }

    public void setOnItemClickListener(RecyclerItemClickListener listener){
        this.listener=listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.menu_item,null);
        return new MyRecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        ((MyRecyclerHolder)holder).textView.setText(list.get(position));
        ((MyRecyclerHolder)holder).view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onRecyclerItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

   public  interface RecyclerItemClickListener{
         void onRecyclerItemClick(int postion);
    }


    class MyRecyclerHolder extends RecyclerView.ViewHolder{
       public TextView textView;
        public View view;

        public MyRecyclerHolder(View itemView) {
            super(itemView);
            view=itemView;
            textView= (TextView) itemView.findViewById(R.id.menuItem);
        }
    }

}

