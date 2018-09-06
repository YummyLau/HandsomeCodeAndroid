package com.example.code.list.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.code.R;
import com.example.code.list.item.FeatureItem;
import com.example.code.list.vh.FeatureItemHolder;

import java.util.ArrayList;
import java.util.List;

public class FeatureAdapter extends RecyclerView.Adapter<FeatureItemHolder> {

    private Context mContext;
    private List<FeatureItem> mDataList;

    public FeatureAdapter(Context context, List<FeatureItem> dataList) {
        this.mContext = context;
        if (dataList == null) {
            dataList = new ArrayList<>();
        }
        this.mDataList = dataList;
    }

    public void add(FeatureItem item) {
        if (item != null) {
            mDataList.add(item);
            notifyDataSetChanged();
        }
    }

    @Override
    public FeatureItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.app_holder_feature, parent, false);
        return new FeatureItemHolder(view);
    }

    @Override
    public void onBindViewHolder(FeatureItemHolder holder, int position) {
        holder.bindData(mDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }
}
