package core;

import static utils.Ansi.clearScreen;

import java.util.ArrayList;
import java.util.Scanner;

import helper.InputHelper;

public class Student extends User {
    private Department department;
    private Faculty faculty;

    private ArrayList<Course> enrolledCourses = new ArrayList<>();

    public Student(String id, String name, String password, University university) {
        super(id, name, password);
        this.department = null;
        this.faculty = null;
        university.addStudentToUniversity(this);
    }

    public Student(String id, String name, String password, Department department, Faculty faculty,
            University university) {
        super(id, name, password);
        this.department = department;
        this.faculty = faculty;
        department.addStudent(this);
        faculty.addStudentInFaculty(this);
        university.addStudentToUniversity(this);
    }

    /*------------ */
    /* CONSOLE RUN */
    /*-------------*/

    public void studentConsole(Scanner usrInput) {
        boolean Back = false;

        while (!Back) {
            System.out.printf("------------------ WELCOME %s  ------------------\n", getName());
            System.out.println("1. Enroll in a Course");
            System.out.println("2. View My Enrolled Courses");
            System.out.println("3. View My Grades");
            System.out.println("4. View My Info");
            System.out.println("0. Back to Main Menu");
            System.out.println("----------------------------------------------------");
            System.out.print("Choose an option: ");

            try {
                int choice = InputHelper.choiceHandler(usrInput);
                usrInput.nextLine();
                clearScreen();

                switch (choice) {
                    case 1:
                        enrollToCourse(usrInput, this.getDepartment());
                        break;

                    case 2:
                        viewEnrolledCourses();
                        usrInput.nextLine();
                        break;

                    case 3:
                        viewGrades();
                        usrInput.nextLine();
                        break;

                    case 4:
                        System.out.println(getUserInfo());
                        usrInput.nextLine();
                        break;

                    case 0:
                        Back = true;
                        break;
                    default:
                        System.out.println("Invalid command");
                        break;
                }
                // clearScreen();
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                usrInput.nextLine(); // clear invalid input
            }
        }
    }

    /*------------------*/
    /* ENROLL TO COURSE */
    /*------------------*/
    /**
     * ลงทะเบียนเข้าคอร์สในภาควิชาที่กำหนด
     *
     * วิธีการทำงาน:
     * 1. ตรวจสอบว่านักศึกษามีการกำหนด Faculty และ Department หรือไม่
     * - ถ้าไม่มี จะแจ้งให้กำหนดก่อน
     * 2. ดึงรายชื่อคอร์สทั้งหมดใน Department ที่กำหนด
     * - ถ้าไม่มีคอร์ส จะแจ้งว่าไม่มีคอร์สให้ลงทะเบียน
     * 3. แสดงรายชื่อคอร์สทั้งหมดพร้อมรหัสและชื่ออาจารย์ผู้สอน
     * 4. ให้ผู้ใช้เลือกคอร์สที่จะลงทะเบียน
     * 5. ตรวจสอบความถูกต้องของการเลือก
     * - ถ้าเลือก 0 หรือค่าที่ไม่ถูกต้อง จะแจ้งยกเลิกหรือ invalid selection
     * 6. ทำการลงทะเบียนโดย:
     * - เพิ่มคอร์สใน student’s enrolled courses
     * - เพิ่ม student ในคอร์สที่เลือก
     * 7. แสดงข้อความยืนยันการลงทะเบียนสำเร็จ
     *
     * @param usrInput   Scanner สำหรับรับค่าจากผู้ใช้
     * @param department Department ที่นักศึกษาต้องการลงทะเบียนคอร์ส
     */
    public void enrollToCourse(Scanner usrInput, Department department) {
        System.out.println("--------------- ENROLL IN A COURSE ---------------");

        if (this.getFaculty() == null || this.getDepartment() == null) {
            System.out.println("You must be assigned to a faculty and department before enrolling in courses.");
            return;
        }

        ArrayList<Course> departmentCourse = department.getCoursesInDepartment();

        if (departmentCourse.isEmpty()) {
            System.out.println("No courses available for enrollment.");
            return;
        }

        System.out.println("Available Courses:");

        for (int i = 0; i < departmentCourse.size(); i++) {
            Course c = departmentCourse.get(i);
            System.out.printf("%d. %s (%s) | Professor: %s\n", i + 1, c.getCourseName(), c.getCourseID(),
                    (c.getProfessor() != null ? c.getProfessor().getName() : "TBA"));
        }

        int choice = InputHelper.selectorHelper(usrInput,
                "Select a course to enroll : ",
                departmentCourse.size());

        if (choice == 0) {
            System.out.println("Enrollment cancelled.");
            return;
        }
        if (choice < 1 || choice > departmentCourse.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        Course selectedCourse = departmentCourse.get(choice - 1);
        enroll(selectedCourse);
        selectedCourse.enrollStudent(this);
        System.out.println("Enroll this course complete.");
        usrInput.nextLine();
        clearScreen();
    }

    /*---------------*/
    /* VIEW MY GRADE */
    /*---------------*/

    public void viewGrades() {
        System.out.printf("--------------- %s GRADE ---------------\n", getName());
        for (int i = 0; i < enrolledCourses.size(); i++) {
            Course c = enrolledCourses.get(i);
            System.out.printf("------------ Course %s ------------\n", c.getCourseName());
            System.out.println("Midterm score : " + c.getMidtermScore());
            System.out.println("Final score : " + c.getFinalScore());
            System.out.println("Assignment score : " + c.getAssignmentScore());
            System.out.println("Overall score : " + c.getCurrentScore());
            System.out.println("Grade : " + c.getCourseGrade());
        }
    }

    // ---------------- GETTER SETTER ---------------

    public void setFacultyOnStudent(Faculty setFaculty) {
        this.faculty = setFaculty;
    }

    public void setDepartmentOnStudent(Department setDepartment) {
        this.department = setDepartment;
    }

    // Check if there's no same enrollment
    private boolean sameEnroll(Course newCourse) {
        for (Course e : enrolledCourses) {
            if (e.getCourseID() == newCourse.getCourseID()) {
                return true;
            }
        }
        return false;
    }

    public void enroll(Course course) {
        if (sameEnroll(course)) {
            System.out.println("You're already enroll this class.");
            return;
        }
        enrolledCourses.add(course);
    }

    public void viewEnrolledCourses() {
        System.out.println("--------------- MY ENROLLED COURSES ---------------");
        if (enrolledCourses.isEmpty()) {
            System.out.println("You are not enrolled in any courses.");
        } else {
            for (Course e : enrolledCourses) {
                System.out.println(e.getCourseName());
            }
        }
        System.out.println("----------------------------------------------------");
    }

    public void addCourse(Course course) {
        if (!enrolledCourses.contains(course)) {
            enrolledCourses.add(course);
        }
    }

    public Course matchCourse(String courseId) {
        for (Course c : enrolledCourses) {
            if (c.getCourseID().equals(courseId)) {
                return c; // return the Course object itself
            }
        }
        return null;
    }

    public ArrayList<Course> getEnrollments() {
        return this.enrolledCourses;
    }

    public Department getDepartment() {
        return department;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    @Override
    public String getUserInfo() {
        return "Student ID: " + getId() +
                ", Name: " + getName() +
                ", Department: " + getDepartment().getDepart_name() +
                ", Faculty: " + getFaculty().getFacultyName();
    }
}