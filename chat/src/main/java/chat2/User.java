package chat2;

public class User {
    public String id = null;
    public String nikname = null;
    public String sex = null;
    public String isWatching = null;
    public String cam = null;

    public User(String id, String nikname, String sex, String isWatching, String cam) {
        this.id = id;
        this.nikname = nikname;
        this.sex = sex;
        this.isWatching = "false";
        this.cam = cam;
    }
}