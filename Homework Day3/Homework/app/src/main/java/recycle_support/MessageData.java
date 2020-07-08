package recycle_support;

public class MessageData {
    int userImage;
    String userName;
    String lastTime;
    String lastMessage;

    public MessageData(int userImage, String userName, String lastTime, String lastMessage) {
        this.userImage = userImage;
        this.userName = userName;
        this.lastTime = lastTime;
        this.lastMessage = lastMessage;
    }
}