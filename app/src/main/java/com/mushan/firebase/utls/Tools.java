package com.mushan.firebase.utls;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;

/**
 * Created by ZhangBin on 17/5/31.
 */

public class Tools {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    public static Bitmap getImageView(Context context,String file)
    {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(context.getAssets().open(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    public static String captureName(String name) {
        //     name = name.substring(0, 1).toUpperCase() + name.substring(1);
//        return  name;
        char[] cs=name.toCharArray();
        cs[0]-=32;
        return String.valueOf(cs);

    }
}
