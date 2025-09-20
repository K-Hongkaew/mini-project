package core;

import java.util.ArrayList;

class Department {
    private int DepartID;
    private String Depart_name;

    private ArrayList<Student> studentInDepartment;
    // TODO เก็บนิสิตที่อยู่ใน department นั้น

    public Department(int DepartID, String Depart_name) {
        this.DepartID = DepartID;
        this.Depart_name = Depart_name;
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

}