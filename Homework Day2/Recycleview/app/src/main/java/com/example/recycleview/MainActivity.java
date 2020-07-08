package com.example.recycleview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recycleview.recycle_support.MessageData;
import com.example.recycleview.recycle_support.MessageDataSet;
import com.example.recycleview.recycle_support.MyAdapter;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        myAdapter = new MyAdapter(MessageDataSet.getData());
        myAdapter.setOnItemClickListener(new MyAdapter.IOnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                final String TAG = "Item Click Position";
                Log.i("Click Log", "Click" + position);
                Intent intent = new Intent(MainActivity.this, ItemCount.class);
                Bundle bundle = new Bundle();
                bundle.putInt(TAG , position);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(myAdapter);

    }

}