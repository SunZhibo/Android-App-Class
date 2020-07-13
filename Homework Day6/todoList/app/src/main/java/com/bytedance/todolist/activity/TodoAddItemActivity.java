package com.bytedance.todolist.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bytedance.todolist.R;

import java.util.Date;

public class TodoAddItemActivity extends AppCompatActivity {

    final int RESULT_OK = 1;

    private Button mButton;
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String TAG1 = "Commit_Content";
        final String TAG2 = "Commit_Time";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_item_add_layout);
        mButton = findViewById(R.id.button_commit_cotent);
        mEditText = findViewById(R.id.editText_item_content);
        mButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra(TAG2, System.currentTimeMillis());
                data.putExtra(TAG1, String.valueOf(mEditText.getText()));
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }
}
