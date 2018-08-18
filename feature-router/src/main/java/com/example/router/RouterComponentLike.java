package com.example.router;

import com.effective.router.core.IComponentLike;
import com.effective.router.core.ui.UIRouter;

public class RouterComponentLike implements IComponentLike{

    UIRouter uiRouter = UIRouter.getInstance();

    @Override
    public void onCreate() {
        uiRouter.registerUI("featureRouter");
    }

    @Override
    public void onRelease() {
        uiRouter.unregisterUI("featureRouter");
    }
}
