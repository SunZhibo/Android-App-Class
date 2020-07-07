package com.example.recycleview.recycle_support;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recycleview.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<MessageData> mDataset;

    public MyAdapter(List<MessageData> mDataset) {
        this.mDataset = mDataset;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {

        int imageResource = mDataset.get(position).userImage;
        String userName = mDataset.get(position).userName;
        String lastTime = mDataset.get(position).lastTime;
        String lastMessage = mDataset.get(position).lastMessage;
        myViewHolder.setData(imageResource, userName, lastTime, lastMessage);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView userName;
        private TextView lastTime;
        private TextView lastMessage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.userImage);
            userName = itemView.findViewById(R.id.textUserName);
            lastTime = itemView.findViewById(R.id.textLastTime);
            lastMessage = itemView.findViewById(R.id.textLastMessage);
        }

        private void setData(int imageResource, String userNameText, String lastTimeText, String lastMessageText) {
            imageView.setImageResource(imageResource);
            userName.setText(userNameText);
            lastTime.setText(lastTimeText);
            lastMessage.setText(lastMessageText);
        }
    }

}
