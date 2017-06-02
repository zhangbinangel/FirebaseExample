package com.mushan.firebase.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mushan.firebase.R;
import com.mushan.firebase.entity.BlogSimpleEntity;
import com.mushan.firebase.fragment.blogadd.BlogAddStep;
import com.mushan.firebase.fragment.blogadd.BlogAddStepOne;
import com.mushan.firebase.fragment.blogadd.BlogAddStepThree;
import com.mushan.firebase.fragment.blogadd.BlogAddStepTwo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;
import static com.mushan.firebase.R.id.next;

/**
 * Created by ZhangBin on 17/6/2.
 */

public class BlogAddActivity extends Activity{

    private List<BlogAddStep> mFragmentList = new ArrayList<>();
    private int nextIndex = 0;
    private BlogAddStep mCurrentFragment;
    private Map<String,String>mBlogContext = new HashMap<>();
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

        BlogAddStep fragment = new BlogAddStepOne();
        mFragmentList.add(fragment);
        fragment = new BlogAddStepTwo();
        mFragmentList.add(fragment);
        fragment = new BlogAddStepThree();
        mFragmentList.add(fragment);



        nextStep();
        ImageButton next = (ImageButton)findViewById(R.id.next);
        next.setOnClickListener(mClickListener);
    }
    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.cancel:
                    finish();
                    break;
                case next:
                    nextStep();
                    break;
                default:break;
            }
        }
    };


    private void nextStep()
    {
        if(mCurrentFragment != null)
        {
            Map.Entry entry = mCurrentFragment.getInputText();
            mBlogContext.put(entry.getKey().toString(),entry.getValue().toString());
            Log.i(TAG,"getText:"+entry.toString());
        }
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        int index = Math.min(nextIndex++,mFragmentList.size()-1);
        transaction.replace(R.id.id_content,mFragmentList.get(index));
        transaction.commit();
        mCurrentFragment = mFragmentList.get(index);
        if(index == mFragmentList.size()-1)
        {
            ImageButton next = (ImageButton)findViewById(R.id.next);
            next.setBackground(getResources().getDrawable(R.drawable.finish_selector));
        }
        if(nextIndex > mFragmentList.size())
        {
            Log.i(TAG,"finish...");
            addFinish();
        }
    }

    private void addFinish()
    {
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = mDatabase.getReference("blogs");
        BlogSimpleEntity entity = new BlogSimpleEntity();
        entity.setLike(0);
        entity.setUnlike(0);
        Date date = new Date();
        entity.setName(date.toString());
        entity.setType(BlogSimpleEntity.Type.AD);
        entity.setMessage(mBlogContext.get("message"));
        entity.setTitle(mBlogContext.get("title"));
        entity.setUrl(mBlogContext.get("url"));
        reference.push().setValue(entity);
        finish();
    }
}

