package com.mushan.firebase.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mushan.firebase.R;
import com.mushan.firebase.adapter.MyListAdapter;
import com.mushan.firebase.entity.BlogSimpleEntity;
import com.mushan.firebase.utls.Tools;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

        init();
    }

    private void init()
    {
        final ListView listView = (ListView)findViewById(R.id.blog_list);
        listView.setOnItemClickListener(blogItemClickListener);
        final FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = mDatabase.getReference("blogs");
        databaseReference.orderByValue().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.i(TAG,"The "+dataSnapshot.getKey() + " dinosaur's score is "+dataSnapshot.getValue());

                BlogSimpleEntity value = dataSnapshot.getValue(BlogSimpleEntity.class);
                value.setName(dataSnapshot.getKey());
                mDataList.add(value);
                listView.setAdapter(new MyListAdapter(mDataList,mContext));
                databaseReference.child(value.getName()).addChildEventListener(new MyChildEventListener(mDataList.size()-1));
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

        FloatingActionButton actionButton = (FloatingActionButton)findViewById(R.id.bt_auction);
        actionButton.setOnClickListener(mClickListener);

    }

    private void upateLocal(int positon,DataSnapshot snapshot)
    {
        try {
            BlogSimpleEntity blog = mDataList.get(positon);
            Method method = BlogSimpleEntity.class.getMethod("set"+ Tools.captureName(snapshot.getKey()),
                    int.class);
            method.invoke(blog,(int)Integer.valueOf(String.valueOf(snapshot.getValue())));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.bt_auction:
                    Intent intent = new Intent(MainActivity.this,BlogAddActivity.class);
                    startActivity(intent);
                    break;
                default:break;
            }
        }
    };
    private class  MyChildEventListener implements com.google.firebase.database.ChildEventListener {

        private int positon;
        public MyChildEventListener(int positon)
        {
            this.positon = positon;
        }
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            upateLocal(positon,dataSnapshot);
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
    }
    private AdapterView.OnItemClickListener blogItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if(position < mDataList.size()) {
                BlogSimpleEntity blog = mDataList.get(position);
                if(blog.getType().name().equals(BlogSimpleEntity.Type.BLOG.toString())) {
                    String url = blog.getUrl();
                    if(!TextUtils.isEmpty(url)) {
                        Intent intent = new Intent(MainActivity.this, BlogDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("detail",blog);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }
            }
        }
    };
}
