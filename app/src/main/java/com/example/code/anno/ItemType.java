package com.example.code.anno;

import android.support.annotation.IntDef;

@IntDef({ItemType.TO_FEATURE_ROUTER, ItemType.CHANGE_SKIN})
public @interface ItemType {
    int TO_FEATURE_ROUTER = 0;
    int CHANGE_SKIN = 1;
}
