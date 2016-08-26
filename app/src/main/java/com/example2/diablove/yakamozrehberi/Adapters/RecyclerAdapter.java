package com.example2.diablove.yakamozrehberi.Adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example2.diablove.yakamozrehberi.HelperClasses.Company;
import com.example2.diablove.yakamozrehberi.R;

import java.util.ArrayList;

/**
 * Created by Diablove on 8/4/2016.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>{

    public interface OnItemClickListener {
        void onItemClick(Company item);
    }
    public ArrayList<Company> arrayList = new ArrayList<>();
    private final OnItemClickListener listener;


    public RecyclerAdapter(ArrayList<Company> arrayList, OnItemClickListener onItemClickListener){

        this.arrayList = arrayList;
        this.listener = onItemClickListener;
        Log.d("changable size:", arrayList.size()+"");
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.company_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(arrayList.get(position), listener);
        holder.Name.setText(arrayList.get(position).getCompName());


    }



    @Override
    public int getItemCount() {
        return arrayList.size();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder{


        TextView Name;

        public MyViewHolder(View itemView) {
            super(itemView);
            Name = (TextView) itemView.findViewById(R.id.name);
        }

        public void bind(final Company item, final OnItemClickListener listener) {
            Name.setText(item.compName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

}
