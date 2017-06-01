package com.mushan.firebase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mushan.firebase.activity.BlogDetailActivity;
import com.mushan.firebase.adapter.MyListAdapter;
import com.mushan.firebase.entity.BlogSimpleEntity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity{
    private final String TAG = getClass().getName().toString();
    private List<BlogSimpleEntity>mDataList = new ArrayList<>();
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;


        MobileAds.initialize(this, getResources().getString(R.string.banner_ad_unitId));


        init();
    }

    private void init()
    {
        /*Button showBanner = (Button)findViewById(R.id.show_banner);
        showBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mAdView.isLoading())
                {
                    mAdView.loadAd(mAdRequest);
                }
            }
        });



        WebView webView = (WebView)findViewById(R.id.blog_detail);
        webView.loadUrl("file:///android_asset/blog_detail.html");*/

        final ListView listView = (ListView)findViewById(R.id.blog_list);
        listView.setOnItemClickListener(blogItemClickListener);
        final FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = mDatabase.getReference("blogs");
        databaseReference.orderByValue().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.i(TAG,"The "+dataSnapshot.getKey() + " dinosaur's score is "+dataSnapshot.getValue());

                BlogSimpleEntity value = dataSnapshot.getValue(BlogSimpleEntity.class);
                mDataList.add(value);
                listView.setAdapter(new MyListAdapter(mDataList,mContext));

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private AdapterView.OnItemClickListener blogItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if(position < mDataList.size()) {
                BlogSimpleEntity blog = mDataList.get(position);
                String url = blog.getUrl();
                Intent intent = new Intent(MainActivity.this, BlogDetailActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);
            }
        }
    };
}
