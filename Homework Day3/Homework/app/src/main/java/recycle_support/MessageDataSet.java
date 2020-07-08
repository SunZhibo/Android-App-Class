package recycle_support;

import com.example.chapter3.homework.R;

import java.util.ArrayList;
import java.util.List;

public class MessageDataSet {

    public static List<MessageData> getData() {
        List<MessageData> result = new ArrayList();
        result.add(new MessageData(R.drawable.p2, "小火锅", "刚刚", "好吃的"));
        result.add(new MessageData(R.drawable.p3, "西瓜", "1分钟前", "西瓜"));;
        result.add(new MessageData(R.drawable.p2, "小火锅", "2分钟前", "好吃的"));;
        result.add(new MessageData(R.drawable.p3, "西瓜", "3分钟前", "西瓜"));;
        result.add(new MessageData(R.drawable.p2, "小火锅", "4分钟前", "好吃的"));;
        result.add(new MessageData(R.drawable.p3, "西瓜", "5分钟前", "西瓜"));;
        result.add(new MessageData(R.drawable.p2, "小火锅", "6分钟前", "好吃的"));;
        result.add(new MessageData(R.drawable.p3, "西瓜", "7分钟前", "西瓜"));;
        result.add(new MessageData(R.drawable.p2, "小火锅", "8分钟前", "好吃的"));;
        result.add(new MessageData(R.drawable.p3, "西瓜", "8分钟前", "西瓜"));;
        result.add(new MessageData(R.drawable.p2, "小火锅", "9分钟前", "好吃的"));;
        result.add(new MessageData(R.drawable.p3, "西瓜", "10分钟前", "西瓜"));;
        return result;
    }

}
