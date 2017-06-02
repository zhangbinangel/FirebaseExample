package com.mushan.firebase.fragment.blogadd;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.mushan.firebase.R;

import java.util.Map;

/**
 * Created by ZhangBin on 17/6/2.
 */

public class BlogAddStepThree extends BlogAddStep {
    private EditText mEditText;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blog_add_3, container,false);
        mEditText = (EditText)view.findViewById(R.id.message);
        return view;
    }

    @Override
    public Map.Entry getInputText() {
        Map.Entry entry = new Map.Entry() {
            @Override
            public Object getKey() {
                return "url";
            }

            @Override
            public Object getValue() {
                return mEditText.getText().toString();
            }

            @Override
            public Object setValue(Object value) {
                return null;
            }
        };
        return entry;
    }
}
