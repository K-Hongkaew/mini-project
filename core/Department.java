package core;

import java.util.ArrayList;

class Department {
    private int DepartID;
    private String Depart_name;
    private ArrayList<Student> studentsInDepartment;

    private ArrayList<Student> studentInDepartment;
    // TODO เก็บนิสิตที่อยู่ใน department นั้น

    public Department(int DepartID, String Depart_name) {
        this.DepartID = DepartID;
        this.Depart_name = Depart_name;
        this.studentsInDepartment = new ArrayList<>();
    }

    public void setDeparID(int DepartID) {
        this.DepartID = DepartID;
    }

    public int getDepartID() {
        return DepartID;

    }

    public void setDepart_name(String Depart_name) {
        this.Depart_name = Depart_name;
    }

    public String getDepart_name() {
        return Depart_name;

    }

    public void addStudent(Student student) {
        studentsInDepartment.add(student);
    }

    public void removeStudent(Student student) {
        studentsInDepartment.remove(student);
    }

    public ArrayList<Student> getStudentsInDepartment() {
        return studentsInDepartment;
    }

}