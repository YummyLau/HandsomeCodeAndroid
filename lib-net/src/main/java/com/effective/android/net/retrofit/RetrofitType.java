package com.effective.android.net.retrofit;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface RetrofitType {
    int GSON = 0;
    int BTYP = 1;
    int STRING = 2;
}
