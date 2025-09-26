package core;

import java.util.ArrayList;

public class Course {
    private final int MAX_SCORE = 100; // กันคะแนนเกิน

    // คะแนน
    private int MAX_MIDTERM;
    private int MAX_FINAL;
    private int MAX_ASSIGNMENT; // Assignments Score

    private String grade = "F";// start at F

    private int midtermScore;
    private int finalScore;
    private int assignmentScore;
    private int currentScore;

    private String courseID; // เก็บเป็น String จะทำให้อ่านง่ายกว่า
    private String courseName;
    private ArrayList<Student> studentInCourse;
    private Professor professorInCourse;
    private Department onDepartment; // course อยู่ใน department ไหน

    public Course(String CourseID, String CourseName, int MID, int FINAL, int ASSIGN, Professor professor,
            Department department) {
        this.courseID = CourseID;
        this.courseName = CourseName;
        this.MAX_MIDTERM = MID;
        this.MAX_FINAL = FINAL;
        this.MAX_ASSIGNMENT = ASSIGN;
        this.studentInCourse = new ArrayList<>();
        this.professorInCourse = professor;
        this.onDepartment = department;
        professorInCourse.addCourse(this);
        onDepartment.addCourseToDepartment(this);
        this.midtermScore = 0;
        this.finalScore = 0;
        this.assignmentScore = 0;
        this.currentScore = 0;
    }

    // --------------- GETTER SETTER ---------------
    public void setMidtermScore(int score) {
        this.midtermScore = score;
    }

    public void setFinalScore(int score) {
        this.finalScore = score;
    }

    public void setAssignmentScore(int score) {
        this.assignmentScore = score;
    }

    public String getCourseGrade() {
        return this.grade;
    }

    public int getMidtermScore() {
        return this.midtermScore;
    }

    public int getFinalScore() {
        return this.finalScore;
    }

    public int getAssignmentScore() {
        return this.assignmentScore;
    }

    public void setProfessor(Professor professor) {
        this.professorInCourse = professor;
    }

    public void overallScore() {
        System.out.println("If score over is more than 100 it will be cut off.");
        this.currentScore += assignmentScore;
        this.currentScore += midtermScore;
        this.currentScore += finalScore;
        if (this.currentScore > MAX_SCORE) {
            this.currentScore = MAX_SCORE;
        }
        gradeCalculation(this.currentScore);
    }

    private void gradeCalculation(int score) {
        if (score >= 80)
            this.grade = "A";
        else if (score >= 75)
            this.grade = "B+";
        else if (score >= 70)
            this.grade = "B";
        else if (score >= 65)
            this.grade = "C+";
        else if (score >= 60)
            this.grade = "C";
        else if (score >= 55)
            this.grade = "D+";
        else if (score >= 50)
            this.grade = "D";
        else
            this.grade = "F";
    }

    public int getCurrentScore() {
        return this.currentScore;
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
        if (!studentInCourse.contains(student)) {
            studentInCourse.add(student);
            student.addCourse(this);
        }
    }

    public void removeStudent(Student student) {
        studentInCourse.remove(student);
    }

    public Professor getProfessor() {
        return this.professorInCourse;
    }

    public ArrayList<Student> getStudentInCourse() {
        return this.studentInCourse;
    }

    public void printCourseInfo() {
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
