package com.example.code;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.code.exoplayer.ExoActivity;
import com.example.code.html.HTMLActivity;
import com.example.code.keeplive.KeepLiveDemoActivity;

import java.util.ArrayList;
import java.util.List;

public class FeatureListFragment extends Fragment {

    private View root;
    private RecyclerView mRecyclerView;

    public static FeatureListFragment newInstance() {
        return new FeatureListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_feature_list, container, false);
        initView();
        initData();
        return root;
    }

    private void initView() {
        mRecyclerView = root.findViewById(R.id.feature_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {

            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDraw(c, parent, state);
                c.drawARGB(0, 0, 0, 0);
            }

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(0, 0, 0, 20);
            }
        });
    }


    private void initData() {
        List<HolderData> list = new ArrayList<>();
        list.add(new HolderData(Holder.Type.EXOPLAYER, "ExoPlayer 例子"));
        list.add(new HolderData(Holder.Type.HTML, "HTML解析 例子"));
        list.add(new HolderData(Holder.Type.kEEPALIVE, "Keep Alive"));
        list.add(new HolderData(Holder.Type.NET, "网络库测试"));
        mRecyclerView.setAdapter(new Adapter(getContext(), list));
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
            int NET = 5;
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
                        case Holder.Type.NET: {
                            NetActivity.start(v.getContext());
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
