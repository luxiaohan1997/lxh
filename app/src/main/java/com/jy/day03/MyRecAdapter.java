package com.jy.day03;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class MyRecAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<AetBean.DataBean.DatasBean> list;

    public MyRecAdapter(Context context, List<AetBean.DataBean.DatasBean> datas) {
        this.context = context;
        this.list = datas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i==0){
         /*   Context context = viewGroup.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.item1, viewGroup, false);*/
            View view=View.inflate(this.context,R.layout.item1,null);
            return new ViewHolder1(view);
        }else {
           /* Context context = viewGroup.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.item2, viewGroup, false);*/
            View view=View.inflate(context,R.layout.item2,null);
            return new ViewHolder2(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        int type = getItemViewType(i);
        if (type==0){
            ViewHolder1 holder1= (ViewHolder1) viewHolder;
            holder1.name1.setText(list.get(i).getId()+"");
            Glide.with(context).load(list.get(i).getEnvelopePic()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(holder1.img1);
        }else {
            ViewHolder2 holder2= (ViewHolder2) viewHolder;
            holder2.name2.setText(list.get(i).getId()+"");
            Glide.with(context).load(list.get(i).getEnvelopePic()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(holder2.img2);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getId()%5>0){
            return 0;
        }else {
            return 1;
        }
    }

    private class ViewHolder1 extends RecyclerView.ViewHolder {
        ImageView img1;
        TextView name1;
        public ViewHolder1(View view) {
            super(view);
            img1=view.findViewById(R.id.img1);
            name1=view.findViewById(R.id.name1);
        }
    }

    private class ViewHolder2 extends RecyclerView.ViewHolder {
        ImageView img2;
        TextView name2;
        public ViewHolder2(View view) {
            super(view);
            img2=view.findViewById(R.id.img2);
            name2=view.findViewById(R.id.name2);
        }
    }
}
