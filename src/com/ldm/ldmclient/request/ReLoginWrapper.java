package com.ldm.ldmclient.request;


import com.ldm.ldmclient.app.EventCode;

/**
 * handle the situation of client offline.
 * when offline in the fail session, login again will be execute for a new session id.
 * Created by LDM on 2014/8/4. Email : nightkid-s@163.com
 */
public class ReLoginWrapper<T extends BaseRequest> implements OnFailSessionObserver{

    public ReLoginWrapper(T request) {
        request.registerFailObserver(this);
    }

    @Override
    public void onFailSession(String errorInfo, int failCode) {
        switch (failCode){
            case EventCode.CODE_ACCOUNT_OFFLINE: //offline and then login again
                //here do logout action;
                break;
        }
    }

}
