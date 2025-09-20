package core;

import java.util.ArrayList;

class Professor extends User {
    private String subject;
    private ArrayList<Course> classList;
    // TODO อาจารย์สร้าง Course ได้
    // แล้วก็ add Course ลงใน classList ด้วย

    public Professor(String id, String password, String name, String subject) {
        super(id, password, name);
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    // ยังไม่มี function นี้ใน course

    // public void viewStudentsInCourse(Course course) {
    // System.out.println("Professor " + getName() + " | Course: " +
    // course.getCourseName());
    // for (Student s : course.getStudents()) {
    // System.out.println("Student: " + s.getName());
    // }
    // }

    @Override
    public String getUserInfo() {
        return "Professor: " + getName() +
                " (ID: " + getId() +
                ", Subject: " + subject + ")";
    }

}
