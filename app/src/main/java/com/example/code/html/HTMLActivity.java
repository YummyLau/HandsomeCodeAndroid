package com.example.code.html;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.effective.android.base.activity.BaseActivity;
import com.example.code.R;
import com.example.code.databinding.ActivityHtmlLayoutBinding;


/**
 * HTML富文本
 * Html#handleStartTag 支持标签
 * Created by yummyLau on 2018/6/22.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class HTMLActivity extends BaseActivity<ActivityHtmlLayoutBinding> {

    public static void start(Context context) {
        Intent intent = new Intent(context, HTMLActivity.class);
        context.startActivity(intent);
    }

    @NonNull
    @Override
    public int getLayoutRes() {
        return R.layout.activity_html_layout;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }


    private void initView() {
        HtmlParser.buildSpannedTextByHtml(binding.text,getString(R.string.html_sample2));
    }

}
