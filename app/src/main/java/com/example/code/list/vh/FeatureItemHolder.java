package com.example.code.list.vh;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.effective.android.skin.SkinHelper;
import com.effective.router.core.ui.UIRouter;
import com.example.code.R;
import com.example.code.anno.ItemType;
import com.example.code.list.item.FeatureItem;

public class FeatureItemHolder extends RecyclerView.ViewHolder {

    private TextView mTextView;

    public FeatureItemHolder(View itemView) {
        super(itemView);
        mTextView = itemView.findViewById(R.id.text);
    }

    public void bindData(final FeatureItem featureItem) {
        mTextView.setText(featureItem.text);
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (featureItem.type) {
                    case ItemType.TO_FEATURE_ROUTER: {
                        Bundle bundle = new Bundle();
                        bundle.putString("content", "this content to a from  app");
                        UIRouter.getInstance().openUri(mTextView.getContext(), "HandsomeCodeAndroid://featureRouter/test", bundle);
                        break;
                    }
                    case ItemType.CHANGE_SKIN: {
                        SkinHelper.getInstance().nextSkin();
                        break;
                    }
                }
            }
        });
    }
}