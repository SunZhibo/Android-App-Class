package com.example.recycleview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ItemCount<textView> extends AppCompatActivity {

    private TextView itemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_count);

        initView();
    }

    private void initView() {
        final String TAG = "Item Click Position";
        itemCount = findViewById(R.id.textItemCount);
        int position = this.getIntent().getExtras().getInt(TAG);
        itemCount.setText("This is item " + position);
    }
}