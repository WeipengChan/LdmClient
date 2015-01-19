package com.ldm.ldmclient.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.ldm.ldmclient.R;
import com.ldm.ldmclient.app.AppManager;
import com.ldm.ldmclient.bean.TestBean;
import com.ldm.ldmclient.request.TestRequest;
import com.ldm.myptr.in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * just for demonstration
 * Created by LDM on 2015/1/7. Email : nightkid-s@163.com
 */
public class TestPageAdapter extends PtrPageAdapter<TestBean, TestRequest, TestPageAdapter.Holder>{

    public TestPageAdapter(PtrFrameLayout ptrFrameLayout, ListView listView) {
        this(ptrFrameLayout, listView, new TestRequest());
    }

    public TestPageAdapter(PtrFrameLayout ptrFrameLayout, ListView listView, TestRequest request) {
        super(ptrFrameLayout, listView, request);
        startDelayRefresh();
    }

    static class Holder{
        TextView title;
        TextView content;
    }

    @Override
    protected View getRawView(Context context) {
        int padding = (int)AppManager.getInstance().getFloatPadding();
        LinearLayout layout = new LinearLayout(context);
        layout.setPadding(15 * padding, 15 * padding, 15 * padding, 15 * padding);
        layout.setOrientation(LinearLayout.VERTICAL);
        TextView title = new TextView(context);
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        title.setId(R.id.title_id);
        title.setTextColor(Color.BLACK);
        TextView content = new TextView(context);
        content.setId(R.id.content_id);
        content.setTextColor(Color.BLACK);
        layout.addView(title);
        layout.addView(content);
        return layout;
    }

    @Override
    protected Holder getHolder(View view) {
        Holder holder = new Holder();
        holder.title = (TextView)view.findViewById(R.id.title_id);
        holder.content = (TextView)view.findViewById(R.id.content_id);
        return holder;
    }

    @Override
    protected void getView(Holder holder, int position, View convertView, ViewGroup arg2, TestBean testBean) {
        holder.title.setText(testBean.getTitle() == null ? "" : testBean.getTitle());
        holder.content.setText(testBean.getContent() == null ? "" : testBean.getContent());
    }

}
