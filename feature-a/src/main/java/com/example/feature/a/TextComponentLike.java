package com.example.feature.a;

import com.effective.router.core.IComponentLike;
import com.effective.router.core.ui.UIRouter;

public class TextComponentLike implements IComponentLike{

    UIRouter uiRouter = UIRouter.getInstance();

    @Override
    public void onCreate() {
        uiRouter.registerUI("featurea");
    }

    @Override
    public void onRelease() {
        uiRouter.unregisterUI("featurea");
    }
}
