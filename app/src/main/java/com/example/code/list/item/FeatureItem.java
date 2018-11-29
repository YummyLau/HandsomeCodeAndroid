package com.example.code.list.item;

import com.example.code.anno.ItemType;

public class FeatureItem {
    @ItemType
    public int type;
    public CharSequence text;

    public FeatureItem(int type, CharSequence charSequence) {
        this.type = type;
        this.text = charSequence;
    }
}
