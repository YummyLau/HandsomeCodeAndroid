package com.example.code;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.effective.android.base.fragment.BaseFragment;
import com.example.code.databinding.FragmentFeatureListBinding;
import com.example.code.exoplayer.ExoActivity;
import com.example.code.html.HTMLActivity;
import com.example.code.keeplive.KeepLiveDemoActivity;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class FeatureListFragment extends BaseFragment<FragmentFeatureListBinding> {


    public static FeatureListFragment newInstance() {
        return new FeatureListFragment();
    }

    @NonNull
    @Override
    public int getLayoutRes() {
        return R.layout.fragment_feature_list;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        binding.featureList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.refreshLayout.setOnRefreshListener((RefreshLayout refreshLayout) -> {

        });
        binding.refreshLayout.setOnLoadMoreListener((RefreshLayout refreshLayout) -> {

        });
    }


    private void initData() {
        List<HolderData> list = new ArrayList<>();
        list.add(new HolderData(Holder.Type.EXOPLAYER, "ExoPlayer 例子"));
        list.add(new HolderData(Holder.Type.HTML, "HTML解析 例子"));
        list.add(new HolderData(Holder.Type.kEEPALIVE, "Keep Alive"));
        binding.featureList.setAdapter(new Adapter(getContext(), list));
    }

    public static class Adapter extends RecyclerView.Adapter<Holder> {

        private Context mContext;
        private List<HolderData> mDataList;

        public Adapter(Context context, List<HolderData> dataList) {
            this.mContext = context;
            if (dataList == null) {
                dataList = new ArrayList<>();
            }
            this.mDataList = dataList;
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.holder_demo_layout, parent, false);
            return new Holder(view);
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            holder.bindData(mDataList.get(position));
        }

        @Override
        public int getItemCount() {
            return mDataList.size();
        }


    }

    public static class Holder extends RecyclerView.ViewHolder {

        @IntDef({Holder.Type.EXOPLAYER})
        public @interface Type {
            int EXOPLAYER = 0;
            int HTML = 1;
            int kEEPALIVE = 4;
        }

        private TextView mTextView;

        public Holder(View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.text);
        }

        public void bindData(final HolderData holderData) {
            mTextView.setText(holderData.text);
            mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (holderData.type) {
                        case Holder.Type.EXOPLAYER: {
                            ExoActivity.start(v.getContext());
                            break;
                        }
                        case Holder.Type.HTML: {
                            HTMLActivity.start(v.getContext());
                            break;
                        }
                        case Holder.Type.kEEPALIVE: {
                            KeepLiveDemoActivity.start(v.getContext());
                            break;
                        }
                    }
                }
            });
        }
    }

    public static class HolderData {

        @Holder.Type
        public int type;
        public String text;

        public HolderData(int type, String text) {
            this.type = type;
            this.text = text;
        }
    }
}
