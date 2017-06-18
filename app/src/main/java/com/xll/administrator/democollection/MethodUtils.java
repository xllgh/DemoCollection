package com.xll.administrator.democollection;

import android.content.Context;

import java.util.Locale;

/**
 * Created by Administrator on 2017/2/26.
 */

public class MethodUtils {

    public String getLocale(Context context){
        Locale locale=context.getResources().getConfiguration().locale;
        String lauguage=locale.getLanguage();
        return lauguage;


    }

}
