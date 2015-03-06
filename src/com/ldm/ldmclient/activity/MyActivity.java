package com.ldm.ldmclient.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import com.ldm.ldmclient.R;
import com.ldm.ldmclient.adapter.TestPageAdapter;
import com.ldm.myptr.in.srain.cube.views.ptr.PtrClassicFrameLayout;

/**
 * main activity
 * Created by LDM on 15-1-4. Email : nightkid-s@163.com
 */
public class MyActivity extends BaseFragmentActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        PtrClassicFrameLayout ptrFrameLayout = (PtrClassicFrameLayout)findViewById(R.id.rotate_header_list_view_frame);
        ptrFrameLayout.setLastUpdateTimeRelateObject(this);
        ListView listView = (ListView)findViewById(R.id.rotate_header_list_view);
        listView.setVisibility(View.VISIBLE);

        //start from here~
        new TestPageAdapter(ptrFrameLayout, listView);
    }

}
