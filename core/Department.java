package core;

import java.util.ArrayList;

public class Department {
    private String department_id;
    private String department_name;
    private ArrayList<Student> studentsInDepartment;
    private ArrayList<Course> coursesInDepartment;

    public Department(String DepartID, String Depart_name) {
        this.department_id = DepartID;
        this.department_name = Depart_name;
        this.studentsInDepartment = new ArrayList<>();
        this.coursesInDepartment = new ArrayList<>();
    }

    // GETTER SETTER

    public void setDepartmentId(String DepartID) {
        this.department_id = DepartID;
    }

    public String getDepartID() {
        return department_id;

    }

    public void setDepart_name(String Depart_name) {
        this.department_name = Depart_name;
    }

    public String getDepart_name() {
        return department_name;

    }

    public ArrayList<Student> getStudentsInDepartment() {
        return studentsInDepartment;
    }

    public ArrayList<Course> getCoursesInDepartment() {
        return coursesInDepartment;
    }

    // ADD REMOVE

    public void addStudent(Student student) {
        studentsInDepartment.add(student);
    }

    public void removeStudent(Student student) {
        studentsInDepartment.remove(student);
    }

    public void addCourseToDepartment(Course course) {
        coursesInDepartment.add(course);
    }

}