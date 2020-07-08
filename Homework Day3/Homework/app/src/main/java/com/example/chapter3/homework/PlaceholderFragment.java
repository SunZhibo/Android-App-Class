package com.example.chapter3.homework;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.airbnb.lottie.LottieAnimationView;

import recycle_support.MessageData;
import recycle_support.MessageDataSet;
import recycle_support.MyAdapter;

public class PlaceholderFragment extends Fragment {

    private RecyclerView recyclerView;
    private LottieAnimationView loadAnimationView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO ex3-3: 修改 fragment_placeholder，添加 loading 控件和列表视图控件
        return inflater.inflate(R.layout.fragment_placeholder, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        intiView();

        getView().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 这里会在 5s 后执行
                // TODO ex3-4：实现动画，将 lottie 控件淡出，列表数据淡入
                recyclerView.setVisibility(View.VISIBLE);
                ObjectAnimator animator1 = ObjectAnimator.ofFloat(loadAnimationView, "Alpha", 1f, 0f);
                animator1.setDuration(300);
                ObjectAnimator animator2 = ObjectAnimator.ofFloat(recyclerView, "Alpha", 0f, 1f);
                animator2.setDuration(100);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playSequentially(animator1, animator2);
                animatorSet.start();
            }
        }, 5000);
    }

    private void intiView() {
        recyclerView = getView().findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        loadAnimationView = getView().findViewById(R.id.load_animation_view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        MyAdapter mAdapter = new MyAdapter(MessageDataSet.getData());
        recyclerView.setAdapter(mAdapter);

        Log.d("recycle", "create");
    }
}
