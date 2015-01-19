package com.ldm.ldmclient.request;

import java.util.ArrayList;
import java.util.List;

/**
 * when there are many loaders in a class, we want to distinguish each loader from them in a callback,
 * so, each loader needs to hold a constant int id, and the id will be returned following the callback interfaces,
 * we can just distinguish each loader by this id.
 * those callback interface with id include such as :
 * {@link com.ldm.ldmclient.request.OnParseObserver2}
 * {@link com.ldm.ldmclient.request.OnFailSessionObserver2}
 * {@link com.ldm.ldmclient.request.OnLoadObserver2}
 *
 * due to this MultiLoader use tow serious interfaces, so we need use adapter pattern to transform the interface to another interface
 *
 * Created by LDM on 14-4-9.Email : nightkid-s@163.com
 */
public abstract class MultiLoader<T> extends Loader<T> implements OnFailSessionObserver, OnParseObserver<T>, BaseRequest.LoadObserver{

    private List<OnFailSessionObserver2> failListeners = new ArrayList<OnFailSessionObserver2>();
    private List<OnParseObserver2<? super T>> parseObservers = new ArrayList<OnParseObserver2<? super T>>();
    private List<OnLoadObserver2> loadObservers = new ArrayList<OnLoadObserver2>();
    private int id; //unique id

    public MultiLoader(int id, OnFailSessionObserver2 failListener, OnLoadObserver2 loadObserver, OnParseObserver2<? super T> parseObserver) {
        this(id, failListener, loadObserver, parseObserver, true, false);
    }

    public MultiLoader(int id, OnFailSessionObserver2 failListener, OnLoadObserver2 loadObserver, OnParseObserver2<? super T> parseObserver, boolean reLogin, boolean cache) {
        super(reLogin, cache);
        if(failListener != null)
            failListeners.add(failListener);
        this.id = id;
        registerFailObserver(this);
        registerParserObserver(this);
        registerLoadObserver(this);
        registerLoadObserver2(loadObserver);
        registerParserObserver(parseObserver);
    }

    @Override
    public void onFailSession(String errorInfo, int failCode) {
        for (OnFailSessionObserver2 observer2 : failListeners)
            if(observer2 != null) observer2.onFailSession(errorInfo, failCode, id, this);
    }

    @Override
    public void onParseSuccess(T t) {
        for(OnParseObserver2<? super T> parseObserver : parseObservers)
            parseObserver.onParseSuccess(t, id, this);
    }

    @Override
    public void onPreLoadObserver() {
        for(OnLoadObserver2 observer : loadObservers)
            observer.onPreLoadObserver(id);
    }

    @Override
    public void onLoadFinishObserver() {
        for(OnLoadObserver2 observer : loadObservers)
            observer.onLoadFinishObserver(id, this);
    }

    public void registerParserObserver(OnParseObserver2<? super T> parser){
        if(parser != null) parseObservers.add(parser);
    }

    public void unRegisterParserObserver(OnParseObserver2<? super T> parser){
        if(parser != null) parseObservers.remove(parser);
    }

    public void registerLoadObserver2(OnLoadObserver2 observer){
        if(observer != null) loadObservers.add(observer);
    }

    public void unregisterLoadObserver2(OnLoadObserver2 observer){
        if(observer != null) loadObservers.remove(observer);
    }

    public void registerFailObserver(OnFailSessionObserver2 failObserver) {
        if(failObserver != null) failListeners.add(failObserver);
    }

    public void unRegisterFailObserver(OnFailSessionObserver2 failObserver) {
        if(failObserver != null) failListeners.remove(failObserver);
    }

    public int getId() {
        return id;
    }

}
