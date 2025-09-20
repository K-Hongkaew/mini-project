package core;

// Interface: IUser
interface IUser {
    String getUserInfo(); // บังคับทุก user ต้อง implement
}

// Abstract Class: User implements IUser
abstract public class User implements IUser {
    private String id;
    private String name;
    private String password;

    // Constructor
    public User(String id, String password, String name) {
        this.id = id;
        this.password = password;
        this.name = name;
    }

    // Encapsulation: getter
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // check
    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

}
