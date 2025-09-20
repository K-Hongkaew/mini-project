package core;

import java.util.ArrayList;

class Course {
    private final int MAX_SCORE = 100; // กันคะแนนเกิน

    // คะแนน
    private int MAX_MIDTERM;
    private int MAX_FINAL;
    private int MAX_ASSIGNMENT; // Assignments Score

    private String courseID; // เก็บเป็น String จะทำให้อ่านง่ายกว่า
    private String courseName;
    private Professor assignedProfessor; // เอาไว้หา เจ้าตัว
    private ArrayList<Student> studentInCourse;

    // TODO: function getter setter ค่าต่างๆ ของคะแนน
    // เมื่อ student ลง enrollment เอา student ลงใน studentInCourse
    // print ชื่อนิสิตทุกคนใน Course พร้อมชื่อ อาจารย์ที่สอน

    public Course(String CourseID, String CourseName, Professor assignProfessor, int MID, int FINAL, int ASSIGN) {
        this.courseID = CourseID;
        this.courseName = CourseName;
        this.MAX_MIDTERM = MID;
        this.MAX_FINAL = FINAL;
        this.MAX_ASSIGNMENT = ASSIGN;
    }

    public void setCourseID(String CourseID) {
        this.courseID = CourseID;
    }

    public String getCourseID() {
        return this.courseID;

    }

    public void setCourseName(String CourseName) {
        this.courseName = CourseName;
    }

    public String getCourseName() {
        return this.courseName;

    }

}
