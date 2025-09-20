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
        this.assignedProfessor = assignProfessor;
        this.MAX_MIDTERM = MID;
        this.MAX_FINAL = FINAL;
        this.MAX_ASSIGNMENT = ASSIGN;
        this.studentInCourse = new ArrayList<>(); 
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

    public void setAssignedProfessor(Professor assignProfessor) {
        this.assignedProfessor = assignProfessor;
    }

    public Professor getAssignedProfessor() {
        return this.assignedProfessor;
    }

    public void setMAX_MIDTERM(int MID) {
        if (MID <= MAX_SCORE) {
            this.MAX_MIDTERM = MID;
        }
    }

    public int getMAX_MIDTERM() {
        return this.MAX_MIDTERM;
    }

    public void setMAX_FINAL(int FINAL) {
        if (FINAL <= MAX_SCORE) {
            this.MAX_FINAL = FINAL;
        }
    }

    public int getMAX_FINAL() {
        return this.MAX_FINAL;
    }

    public void setMAX_ASSIGNMENT(int ASSIGN) {
        if (ASSIGN <= MAX_SCORE) {
            this.MAX_ASSIGNMENT = ASSIGN;
        }
    }

    public int getMAX_ASSIGNMENT() {
        return this.MAX_ASSIGNMENT;
    }

    public void enrollStudent(Student student) {
        studentInCourse.add(student);
    }

    public void removeStudent(Student student) {
        studentInCourse.remove(student);
    }

    public ArrayList<Student> getStudentInCourse() {
        return this.studentInCourse;
    }

    public void printCourseInfo() {
        System.out.println("Course: " + courseID + "  " + courseName);
        if (assignedProfessor != null) {
            System.out.println("Professor: " + assignedProfessor.getName());
        }
        if (studentInCourse.isEmpty()) {
            System.out.println("No students enrolled.");
        } else {
            System.out.println("Students in this course:");
            for (Student s : studentInCourse) {
                System.out.println("- " + s.getName());
            }
        }
    }
}
