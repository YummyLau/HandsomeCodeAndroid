package com.example.code.list.vh;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.text.TextPaint;
import android.view.View;
import android.widget.TextView;

import com.effective.android.base.util.LogUtils;
import com.effective.android.base.util.system.DisplayUtils;
import com.effective.android.base.util.system.ScreenUtils;
import com.effective.android.base.view.textview.StaticLayoutHelper;
import com.effective.android.base.view.textview.StaticLayoutView;
import com.effective.android.skin.SkinHelper;
import com.effective.router.core.ui.UIRouter;
import com.example.code.R;
import com.example.code.anno.ItemType;
import com.example.code.list.item.FeatureItem;

public class FeatureItemHolder extends RecyclerView.ViewHolder {

//    private StaticLayoutView mTextView;
    private TextView mTextView;
    private TextPaint textPaint;
    private Context context;

    public FeatureItemHolder(View itemView) {
        super(itemView);
        mTextView = itemView.findViewById(R.id.text);
        context = itemView.getContext();
    }

    public void bindData(final FeatureItem featureItem) {
        long time = System.currentTimeMillis();

//        if (textPaint == null) {
//            textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
//            textPaint.density = context.getResources().getDisplayMetrics().density;
//            textPaint.setTextSize(DisplayUtils.dip2px(context, 20f));
//        }
//        mTextView.setLayout(StaticLayoutHelper.getStaticLayout(textPaint,
//                ScreenUtils.getScreenSize(context).x - DisplayUtils.dip2px(context, 10f),
//                Layout.Alignment.ALIGN_CENTER,
//                featureItem.text));
        mTextView.setText(featureItem.text);
        LogUtils.d(FeatureItemHolder.class, System.currentTimeMillis() - time + "ms");
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