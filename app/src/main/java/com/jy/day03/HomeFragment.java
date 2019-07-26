package com.jy.day03;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements MainView{

    private View view;
    private RecyclerView mRec;
    private List<AetBean.DataBean.DatasBean> list;
    private MyRecAdapter recAdapter;
    private MainPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mRec = (RecyclerView) view.findViewById(R.id.rec);

        list = new ArrayList<>();
        recAdapter = new MyRecAdapter(getActivity(), list);
        mRec.setAdapter(recAdapter);

        presenter = new MainPresenter(this);
        presenter.getData();

        mRec.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRec.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL));


    }

    @Override
    public void AddDataUrl(List<AetBean.DataBean.DatasBean> datas) {
        list.addAll(datas);
        recAdapter.notifyDataSetChanged();
    }

    @Override
    public void ShowToast(String str) {
        Toast.makeText(getActivity(), "啥也没有！！！", Toast.LENGTH_SHORT).show();
    }
}
