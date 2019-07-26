package com.jy.day03;

import java.util.List;

public class MainPresenter implements MainModel.GetDataUrl{

    private MainModel mainModel;
    private MainView mainView;
    public MainPresenter(MainView mainView){
        this.mainView=mainView;
        mainModel=new MainModel();
    }

    public void getData(){
        mainModel.getData(this);
    }
    @Override
    public void onSuccess(List<AetBean.DataBean.DatasBean> datas) {
        mainView.AddDataUrl(datas);
    }

    @Override
    public void onFilure(String str) {
        mainView.ShowToast(str);
    }
}
