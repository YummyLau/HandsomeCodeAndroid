package com.example.code.list.item;

import com.example.code.anno.ItemType;

public class FeatureItem {
    @ItemType
    public int type;
    public String text;

    public FeatureItem(int type, String text) {
        this.type = type;
        this.text = text;
    }
}
