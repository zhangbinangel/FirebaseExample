package com.mushan.firebase.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.mushan.firebase.R;
import com.mushan.firebase.fragment.blogadd.BlogAddStepOne;

/**
 * Created by ZhangBin on 17/6/2.
 */

public class BlogAddActivity extends Activity{

    private BlogAddStepOne mBlogAddStepOne;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_add);
        init();
    }
    private void init()
    {
        TextView cancel = (TextView)findViewById(R.id.cancel);
        cancel.setOnClickListener(mClickListener);

        TextView send = (TextView)findViewById(R.id.send);
        send.setOnClickListener(mClickListener);


        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        mBlogAddStepOne = new BlogAddStepOne();
        transaction.replace(R.id.id_content,mBlogAddStepOne);
        transaction.commit();
    }
    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.cancel:
                    finish();
                    break;
                case R.id.send:

                    break;
                default:break;
            }
        }
    };
}

