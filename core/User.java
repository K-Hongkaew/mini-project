package core;

// Interface: IUser
interface IUser {
    public String getUserInfo(); // บังคับทุก user ต้อง implement
}

// Abstract Class: User implements IUser
abstract public class User implements IUser {
    private String id;
    private String name;
    private String password;

    // Constructor
    public User(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    // Encapsulation: getter

    public String getName() {
        return name;
    }

    public String getId() {
        return this.id;
    }

    public String getPassword() {
        return this.password;
    }

    // Setter
    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public void setId(String newId) {
        this.id = newId;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    // check
    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

}
