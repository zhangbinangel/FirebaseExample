package com.mushan.firebase.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mushan.firebase.R;
import com.mushan.firebase.entity.BlogSimpleEntity;

/**
 * Created by ZhangBin on 17/5/31.
 */

public class BlogDetailActivity extends Activity{
    private AdView mAdView;/*横幅广告*/
    private AdRequest mAdRequest;
    private BlogSimpleEntity mBlog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MobileAds.initialize(this, getResources().getString(R.string.app_Id));

        setContentView(R.layout.blog_detail);
        mAdView = (AdView) findViewById(R.id.ad_view);
        mAdRequest = new AdRequest.Builder()
                /*.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)*/
                .addTestDevice("BB2011217CD316B7429C42FAEB1F15F1")
                .build();
        mAdView.loadAd(mAdRequest);

        mBlog = (BlogSimpleEntity)getIntent().getSerializableExtra("detail");
        WebView webView = (WebView)findViewById(R.id.blog_detail);
        webView.loadUrl(mBlog.getUrl());
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
        like.setOnClickListener(mClickListener);
        ImageView unlike = (ImageView)findViewById(R.id.unlike);
        unlike.setOnClickListener(mClickListener);


        TextView likeTxt = (TextView)findViewById(R.id.likeTxt);
        likeTxt.setText(String.valueOf(mBlog.getLike()));

        TextView unlikeTxt = (TextView)findViewById(R.id.unlikeTxt);
        unlikeTxt.setText(String.valueOf(mBlog.getUnlike()));
    }
    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.like:
                    postEvaluate(true);
                    break;
                case R.id.unlike:
                    postEvaluate(false);
                    break;
                default:break;
            }
        }
    };

    private void postEvaluate(boolean like)
    {
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = mDatabase.getReference("blogs");
        int current;
        if(like)
        {
            current = mBlog.getLike();
            current++;
            reference.child(mBlog.getName()).child("like").setValue(current);
            TextView likeTxt = (TextView)findViewById(R.id.likeTxt);
            likeTxt.setText(String.valueOf(current));
            mBlog.setLike(current);
        }else
        {
            current = mBlog.getUnlike();
            current++;
            reference.child(mBlog.getName()).child("unlike").setValue(current);
            TextView unlikeTxt = (TextView)findViewById(R.id.unlikeTxt);
            unlikeTxt.setText(String.valueOf(current));
            mBlog.setUnlike(current);
        }
    }
}
