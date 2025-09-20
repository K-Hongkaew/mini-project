package core;

import java.util.ArrayList;

public class Student extends User {
    private String department;
    private String faculty;

    private ArrayList<Course> enrolledCourses = new ArrayList<>();
    private ArrayList<Double> grades = new ArrayList<>();

    // TODO getter setter department, faculty
    // เราแยก ออกมาใส่ใน student อย่างเดียว

    public Student(String id, String password, String name, String department, String faculty) {
        super(id, name, password);
        this.faculty = faculty;
        this.department = department;
    }

    // ยังไม่มี function
    public void enroll(Course course) {
        if (!enrolledCourses.contains(course)) {
            enrolledCourses.add(course);
            // course.addStudent(this);
            System.out.println(getName() + " enrolled in course: " + course.getCourseName());
        } else {
            System.out.println(getName() + " is already enrolled in course: " + course.getCourseName());
        }
    }

    // getProfesssor ?
    public void viewEnrolledCourses() {
        System.out.println("Student: " + getName() + " - Enrolled Courses:");
        for (Course c : enrolledCourses) {
            // System.out.println(" - " + c.getCourseName() + " (Professor: " +
            // c.getProfessor().getName() + ")");
        }
    }

    public void viewGrades() {
        System.out.println("Grades for " + getName() + ":");
        for (int i = 0; i < enrolledCourses.size(); i++) {
            String courseName = enrolledCourses.get(i).getCourseName();
            String gradeStr = (i < grades.size()) ? String.valueOf(grades.get(i)) : "N/A";
            System.out.println(" - " + courseName + ": " + gradeStr);
        }
    }

    public void displayInfo() {
        System.out.println("Student ID: " + getId() +
                ", Name: " + getName());
        // ", Department: " + getDepartment() +
        // ", Faculty: " + getFaculty());
    }

    @Override
    public String getUserInfo() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserInfo'");
    }
}