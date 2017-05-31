package com.mushan.firebase.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.NativeExpressAdView;
import com.mushan.firebase.R;
import com.mushan.firebase.entity.BlogSimpleEntity;

import java.io.IOException;
import java.util.List;

/**
 * Created by ZhangBin on 17/5/31.
 */

public class MyListAdapter extends BaseAdapter{
    private Context mContext;
    private List<BlogSimpleEntity>mDataList;
    private AdRequest mAdRequest;
    private NativeExpressAdView mNativeAdView;

    public MyListAdapter(List<BlogSimpleEntity>list,Context context)
    {
        this.mDataList = list;
        this.mContext = context;
        mAdRequest = new AdRequest.Builder()
                //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("BB2011217CD316B7429C42FAEB1F15F1")
                .build();

    }
    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        if(mDataList == null)
            return 0;
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        Holder holder;
        BlogSimpleEntity entity = mDataList.get(position);


        if (entity.getType().toString().equals(BlogSimpleEntity.Type.AD.toString())) {

            view = View.inflate(mContext,
                    R.layout.blog_list_item2,
                    null);

            holder = new ViewHolder2();
            ((ViewHolder2) holder).nativeAdView = (NativeExpressAdView) view.findViewById(R.id.nativeAdView);
            mNativeAdView = ((ViewHolder2) holder).nativeAdView;
            mNativeAdView.loadAd(mAdRequest);
            mNativeAdView.setAdListener(mAdListener);
            //view.setTag(holder);
        }
        else
        {
            view = View.inflate(mContext,
                    R.layout.blog_list_item,
                    null);

            holder = new ViewHolder();
            ((ViewHolder) holder).message = (TextView) view.findViewById(R.id.message);
            ((ViewHolder) holder).title = (TextView) view.findViewById(R.id.title);
            ((ViewHolder) holder).imageView = (ImageView) view.findViewById(R.id.cover);

            ((ViewHolder)holder).message.setText(mDataList.get(position).getMessage());
            ((ViewHolder)holder).title.setText(mDataList.get(position).getTitle());
            Bitmap bitmap = getImageView();
            ((ViewHolder)holder).imageView.setImageBitmap(bitmap);

            //view.setTag(holder);
        }



        /*{
            view = convertView;
            if(entity.getType().toString().equals(BlogSimpleEntity.Type.AD.toString()))
            {
                holder = (ViewHolder2) view.getTag();

                ((ViewHolder2) holder).nativeAdView.setAdListener(mAdListener);
                ((ViewHolder2) holder).nativeAdView.loadAd(mAdRequest);
                mNativeAdView = ((ViewHolder2) holder).nativeAdView;
            }
            else
            {

                holder = (ViewHolder)view.getTag();
                ((ViewHolder)holder).message.setText(mDataList.get(position).getMessage());
                ((ViewHolder)holder).title.setText(mDataList.get(position).getTitle());
                Bitmap bitmap = getImageView();
                ((ViewHolder)holder).imageView.setImageBitmap(bitmap);

            }
        }*/
        return view;
    }

    private AdListener mAdListener = new AdListener() {
        @Override
        public void onAdLoaded() {
            super.onAdLoaded();
            mNativeAdView.loadAd(mAdRequest);
        }
    };
    @Override
    public int getItemViewType(int position) {
        BlogSimpleEntity entity = mDataList.get(position);
        if(entity.getType().equals(BlogSimpleEntity.Type.AD.toString()))
            return 2;

        return 1;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    private Bitmap getImageView()
    {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(mContext.getAssets().open("firebase.webp"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private static class Holder{}
    private static class ViewHolder extends Holder{
        private TextView title;
        private TextView message;
        private ImageView imageView;
    }
    private static class ViewHolder2 extends Holder{
        private NativeExpressAdView nativeAdView;
    }
}
