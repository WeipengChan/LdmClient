package com.ldm.ldmclient.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * ldm's abstract adapter
 * Created by LDM on 13-12-20. Email : nightkid-s@163.com
 */
public abstract class BaseCusAdapter<T, H> extends BaseAdapter{

    private List<T> dataList = new ArrayList<T>();
    private Context context;
    private OnDataCountListener listener;

    protected abstract View getRawView(Context context);
    protected abstract H getHolder(View view);
    protected abstract void getView(H holder, int position, View convertView, ViewGroup arg2, T t);

    //out put the item count
    public interface OnDataCountListener{
        void onCount(int count);
    }

    public BaseCusAdapter(Context context) {
        this(context, null);
    }

    public BaseCusAdapter(Context context, OnDataCountListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        if(listener != null) listener.onCount(getCount());
    }

    @Override
    public int getCount() {
        return dataList == null ? 0: dataList.size();
    }

    @Override
    public T getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        H holder;
        if(convertView == null){
            convertView = getRawView(context);
            holder = getHolder(convertView);
            convertView.setTag(holder);
        } else
            holder = (H)convertView.getTag();

        getView(holder, position, convertView, parent, getItem(position));
        return convertView;
    }

    public void setOnCountListener(OnDataCountListener listener) {
        this.listener = listener;
    }

    protected void displayToast(String info){
        Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
    }

    public Context getContext() {
        return context;
    }

    public List<T> getDataList(){
        return dataList;
    }
}
