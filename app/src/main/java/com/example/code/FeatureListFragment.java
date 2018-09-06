package com.example.code;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.effective.android.base.fragment.BaseFragment;
import com.effective.android.base.util.system.StatusBarUtils;
import com.effective.android.skin.SkinUtils;
import com.example.code.anno.ItemType;
import com.example.code.databinding.AppFragmentFeatureListBinding;
import com.example.code.list.adapter.FeatureAdapter;
import com.example.code.list.item.FeatureItem;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

import skin.support.widget.SkinCompatSupportable;

public class FeatureListFragment extends BaseFragment<AppFragmentFeatureListBinding> {


    public static FeatureListFragment newInstance() {
        return new FeatureListFragment();
    }

    @NonNull
    @Override
    public int getLayoutRes() {
        return R.layout.app_fragment_feature_list;
    }

    @Override
    public void applySkin() {
        if (getUserVisibleHint()) {
            if (StatusBarUtils.meetLightColor(SkinUtils.getColor(getContext(), R.color.app_colorPrimary))) {
                StatusBarUtils.setStatusBarLightMode(getActivity());
            } else {
                StatusBarUtils.setStatusBarDarkMode(getActivity());
            }
        }
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
        List<FeatureItem> list = new ArrayList<>();
        FeatureAdapter featureAdapter = new FeatureAdapter(getContext(), list);
        binding.featureList.setAdapter(featureAdapter);
        featureAdapter.add(new FeatureItem(ItemType.TO_FEATURE_ROUTER, "跳转到组件router"));
        featureAdapter.add(new FeatureItem(ItemType.CHANGE_SKIN, "切换皮肤"));
    }

}
