package blz.red5demo {
[RemoteClass(alias="blz.red5demo.ChatHistoryItem")]
public class ChatHistoryItem {
    public var user:String;
    public var date:Date;
    public var message:String;
    public var textColor:String;
    public var chatAvatar:String;

    public function ChatHistoryItem() {
    }
}
}