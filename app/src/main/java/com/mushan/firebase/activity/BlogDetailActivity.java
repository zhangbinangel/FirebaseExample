package com.mushan.firebase.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.mushan.firebase.R;
import com.mushan.firebase.utls.Tools;

/**
 * Created by ZhangBin on 17/5/31.
 */

public class BlogDetailActivity extends Activity{
    private AdView mAdView;/*横幅广告*/
    private AdRequest mAdRequest;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MobileAds.initialize(this, getResources().getString(R.string.app_Id));

        setContentView(R.layout.blog_detail);
        mAdView = (AdView) findViewById(R.id.ad_view);
        mAdRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                /*.addTestDevice("BB2011217CD316B7429C42FAEB1F15F1")*/
                .build();
        mAdView.loadAd(mAdRequest);

        String url = getIntent().getStringExtra("url");
        WebView webView = (WebView)findViewById(R.id.blog_detail);
        //webView.loadUrl("file:///android_asset/blog_detail"+blogId+".html");
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });

        init();
    }


    private void init()
    {
        ImageView like = (ImageView)findViewById(R.id.like);
        ImageView unlike = (ImageView)findViewById(R.id.unlike);
        Bitmap likeBitmap = Tools.getImageView(this,"like.webp");
        Bitmap unlikeBitmap = Tools.getImageView(this,"unlike.webp");
        like.setImageBitmap(likeBitmap);
        unlike.setImageBitmap(unlikeBitmap);
    }
}
