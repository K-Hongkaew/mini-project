
// Interface: IUser
interface IUser {
    String getUserInfo(); // บังคับทุก user ต้อง implement
}

// Abstract Class: User implements IUser
abstract class User implements IUser {
    private String id;
    private String password;
    private String name;
    private String department;
    private String faculty;

    // Constructor
    public User(String id, String password, String name, String department, String faculty) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.department = department;
        this.faculty = faculty;
    }

    // Encapsulation: getter
    public String getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public String getFaculty() { return faculty; }

    // check
    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

@Override
public String toString() {
    String info = "Name: " + name + "\nID: " + id;
// ME or Mime
    if (department != null && !department.isEmpty()) {
        info += "\nDepartment: " + department;
    }
// ME or Mime
    if (faculty != null && !faculty.isEmpty()) {
        info += "\nFaculty: " + faculty;
    }

    return info;
}

} 

