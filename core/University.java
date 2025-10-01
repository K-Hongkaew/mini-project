package core;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import helper.InputHelper;
import static utils.Ansi.*;
import utils.ModifyStudent;
import utils.ModifyFaculty;
import utils.ModifyProfessor;

public class University {
    private String universityName;
    private String universityPassword;

    // Data
    private ArrayList<Student> students;
    private ArrayList<Professor> professors;
    private ArrayList<Faculty> faculties;
    private ArrayList<Course> courses;

    // Modifiers
    private ModifyStudent studentModifier;
    private ModifyFaculty facultyModifier;
    private ModifyProfessor professorModifier;

    // Constructor
    public University(String universityName, String universityPassword) {
        this.universityName = universityName;
        this.universityPassword = universityPassword;

        this.students = new ArrayList<>();
        this.professors = new ArrayList<>();
        this.faculties = new ArrayList<>();
        this.courses = new ArrayList<>();

        this.studentModifier = new ModifyStudent(this);
        this.facultyModifier = new ModifyFaculty(this);
        this.professorModifier = new ModifyProfessor(this);
    }

    /*------------ */
    /* CONSOLE RUN */
    /*-------------*/

    public void univerConsole(Scanner usrInput, University university) {
        boolean isBack = false;

        while (!isBack) {

            System.out.printf("---------------- ROOT MENU University : %s ----------------\n",
                    university.getUniversityName());
            System.out.println("1. Show all Student");
            System.out.println("2. Show all Professor");
            System.out.println("3. Show all Faculty");
            System.out.println("4. Modify Student");
            System.out.println("5. Modify Professor");
            System.out.println("6. Modify Faculty");
            System.out.println("7. Print Faculty with Department");
            System.out.println("8. Print Faculty with Department and Student");
            System.out.println("9. Print Every User in University");
            System.out.println("10. Print Every Course in University");
            System.out.println("11. Professor with course");
            System.out.println("99. Change University Password");
            System.out.println("0. Back");
            System.out.println("----------------------------------------------------");
            System.out.print("Enter your choice: ");
            try {
                int choice = InputHelper.choiceHandler(usrInput);
                usrInput.nextLine(); // Clear line
                clearScreen();

                switch (choice) {
                    case 1:
                        showAllStudents();
                        usrInput.nextLine();
                        break;

                    case 2:
                        showAllProfessors();
                        usrInput.nextLine();

                        break;

                    case 3:
                        showAllFaculties();
                        usrInput.nextLine();
                        break;

                    case 4:
                        studentModifier.studentModConsole(usrInput);
                        break;

                    case 5:
                        professorModifier.professorModConsole(usrInput);
                        break;

                    case 6:
                        facultyModifier.facultyConsole(usrInput);
                        break;

                    case 7:
                        printFacultyWithDepartment();
                        usrInput.nextLine();
                        break;

                    case 8:
                        printFaultyWithDepartmentAndStudent();
                        usrInput.nextLine();
                        break;

                    case 9:
                        showAllUserInUniversity();
                        usrInput.nextLine();
                        break;

                    case 10:
                        showAllCourses();
                        usrInput.nextLine();
                        break;

                    case 11:
                        showProfessorWithCourse();
                        usrInput.nextLine();
                        break;

                    case 99:
                        setUniversityPassword(usrInput);

                    case 0:
                        isBack = true;
                        break;
                    default:
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Input must be an integer!");
                usrInput.nextLine();
            }
        }
    }

    /*-------------------*/
    /* CHECK ID IF EXIST */
    /*-------------------*/
    
    public Boolean checkExistFaculty(String facultyName) {
        for (Faculty f : getArrayFaculties()) {
            if (f.getFacultyName().equalsIgnoreCase(facultyName)) {
                return true;
            }
        }
        return false;
    }

    public boolean isCourseIdExists(String courseID) {
        for (Course c : getArrayCourses()) {
            if (c.getCourseID().equalsIgnoreCase(courseID)) {
                return true;
            }
        }
        return false;
    }

    public Professor getProfessorById(String profId) {
        for (Professor p : getArrayProfessors()) {
            if (p.getId().equalsIgnoreCase(profId)) {
                return p;
            }
        }
        return null; // Not found
    }

    public Student getStudentById(String studentId) {
        for (Student s : getArrayStudents()) {
            if (s.getId().equalsIgnoreCase(studentId)) {
                return s;
            }
        }
        return null; // Not found
    }

    /**
     * สร้างนักศึกษาใหม่ (Student) และเพิ่มเข้าไปใน Array ของ University
     *
     * วิธีการทำงาน:
     * 1. รับค่า id, name, password จากผู้เรียก
     * 2. สร้าง object Student ใหม่
     * 3. แสดงข้อความยืนยันการสร้างนักศึกษาใหม่
     *
     * @param id         รหัสนักศึกษา
     * @param name       ชื่อนักศึกษา
     * @param password   รหัสผ่านนักศึกษา
     * @param university instance ของ University ที่นักศึกษาจะถูกเพิ่มเข้าไป
     */
    public void createNewStudent(String id, String name, String password, University university) {
        Student student = new Student(id, name, password, this);
        System.out.println("New student created: " + student.getId() + " - " + student.getName());
    }

    /**
     * สร้างอาจารย์ใหม่ (Professor) และเพิ่มเข้าไปใน Array ของ University
     *
     * วิธีการทำงาน:
     * 1. รับค่า id, name, password จากผู้เรียก
     * 2. สร้าง object Professor ใหม่
     * 3. แสดงข้อความยืนยันการสร้างอาจารย์ใหม่
     *
     * @param id         รหัสอาจารย์
     * @param name       ชื่ออาจารย์
     * @param password   รหัสผ่านอาจารย์
     * @param university instance ของ University ที่อาจารย์จะถูกเพิ่มเข้าไป
     */
    public void createNewProfessor(String id, String name, String password, University university) {
        Professor professor = new Professor(id, name, password, this);
        System.out.println("New professor created: " + professor.getProfessorId() + " - " + professor.getName());
    }

    // เช็ครหัสผ่าน
    public Boolean checkPassword(String password) {
        return this.universityPassword.equals(password);
    }

    /*---------------*/
    /* GETTER SETTER */
    /*---------------*/
    public String getUniversityName() {
        return universityName;
    }

    public ArrayList<Student> getArrayStudents() {
        return this.students;
    }

    public ArrayList<Professor> getArrayProfessors() {
        return this.professors;
    }

    public ArrayList<Faculty> getArrayFaculties() {
        return this.faculties;
    }

    public ArrayList<Course> getArrayCourses() {
        return this.courses;
    }

    public ArrayList<Course> getAllCourses() {
        return this.courses;
    }

    // Set new password
    public void setUniversityPassword(Scanner usrInput) {
        System.out.println("Current University password: " + this.universityPassword);
        System.out.print("Enter new University password: ");
        this.universityPassword = usrInput.nextLine();
        System.out.println("University password updated successfully!");
    }

    /*--------------*/
    /* PRINT METHOD */
    /*--------------*/

    public void showAllFaculties() {
        System.out.println("---------------- CURRENT FACULTIES IN UNIVERSITY ----------------");
        if (getArrayFaculties().isEmpty()) {
            System.out.println("No faculties available.");
        } else {
            for (int i = 0; i < getArrayFaculties().size(); i++) {
                Faculty f = getArrayFaculties().get(i);
                System.out.println();
                System.out.println((i + 1) + ". " + f.getFacultyName());
            }
        }
        System.out.println("----------------------------------------------------------------");
    }

    public void printFacultyWithDepartment() {
        for (int i = 0; i < getArrayFaculties().size(); i++) {
            System.out.println(i + 1 + " " + getArrayFaculties().get(i).getFacultyName());

            getArrayFaculties().get(i).showAllDepartments();
        }
    }

    public void printFaultyWithDepartmentAndStudent() {
        for (Faculty f : getArrayFaculties()) {
            System.out.println("Faculty: " + f.getFacultyName());

            for (Department d : f.getDepartments()) {
                System.out.println("  Department: " + d.getDepart_name());
                System.out.println("    Students:");
                for (Student s : f.getStudentInFaculty()) {

                    // Only print students in this department
                    if (s.getDepartment() != null && s.getDepartment().getDepart_name().equals(d.getDepart_name())) {
                        System.out.println("      - " + s.getId() + " : " + s.getName());
                    }
                }
            }
            System.out.println();
        }
        System.out.println();

    }

    public void showAllStudents() {
        System.out.println("---------------- CURRENT STUDENTS IN UNIVERSITY ----------------");
        if (getArrayStudents().isEmpty()) {
            System.out.println("No students available.");
        } else {

            for (int i = 0; i < getArrayStudents().size(); i++) {
                Student s = getArrayStudents().get(i);
                String facultyName = (s.getFaculty() == null) ? "Null" : s.getFaculty().getFacultyName();
                String departmentName = (s.getDepartment() == null) ? "Null" : s.getDepartment().getDepart_name();
                System.out.println();
                System.out.println((i + 1) + ". " + s.getId() + " - " + s.getName()
                        + "| Faculty: " + facultyName
                        + "| Department: " + departmentName);
            }
        }
        System.out.println("----------------------------------------------------------------");
    }

    public void showAllProfessors() {
        System.out.println("---------------- CURRENT PROFESSORS IN UNIVERSITY ----------------");
        if (getArrayProfessors().isEmpty()) {
            System.out.println("No professor available.");
        } else {

            for (int i = 0; i < getArrayProfessors().size(); i++) {
                Professor p = getArrayProfessors().get(i);
                System.out.println();
                System.out.println((i + 1) + ". " + p.getId() + " - " + p.getName());
            }
        }
        System.out.println("----------------------------------------------------------------");
    }

    public void showAllUserInUniversity() {
        System.out.println("--------------- CURRENT USERS IN UNIVERSITY ---------------");
        System.out.println("Students: " + getArrayStudents().size());
        for (Student s : getArrayStudents()) {
            System.out.println("- " + s.getId() + " : " + s.getName());
        }

        System.out.println("Professors: " + getArrayProfessors().size());
        for (Professor p : getArrayProfessors()) {
            System.out.println("- " + p.getId() + " : " + p.getName());
        }
        System.out.println("-----------------------------------------------------------");
    }

    public void showAllCourses() {
        System.out.println("--------------- CURRENT COURSES IN UNIVERSITY ---------------");
        if (getArrayCourses().isEmpty()) {
            System.out.println("No courses available.");
        } else {
            for (Course c : getArrayCourses()) {
                System.out.println();
                System.out.println("- " + c.getCourseID() + " : " + c.getCourseName());
            }
        }
        System.out.println("-----------------------------------------------------------");
    }

    public void showProfessorWithCourse() {
        System.out.println("--------------- PROFESSORS WITH THEIR COURSES ---------------");
        if (getArrayProfessors().isEmpty()) {
            System.out.println("No professors available.");
        } else {
            for (Professor p : getArrayProfessors()) {
                System.out.println("Professor: " + p.getName() + " (ID: " + p.getId() + ")");
                ArrayList<Course> courses = p.getClassList();
                if (courses.isEmpty()) {
                    System.out.println("  No courses assigned.");
                } else {
                    for (Course c : courses) {
                        System.out.println("  - " + c.getCourseID() + ": " + c.getCourseName());
                    }
                }
                System.out.println();
            }
        }
        System.out.println("-----------------------------------------------------------");
    }

    /*----------- */
    /* ADD REMOVE */
    /*------------*/

    public void addProfessorToUniversity(Professor professor) {
        this.professors.add(professor);
    }

    public void addStudentToUniversity(Student student) {
        this.students.add(student);
    }

    public void addCourseToUniverity(Course course) {
        this.courses.add(course);
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void addProfessor(Professor professor) {
        professors.add(professor);
    }

    public void removeStudent(Student studentId) {
        students.remove(studentId);
    }

    public void removeProfessor(Professor professorId) {
        professors.remove(professorId);
    }

    public void removeCourseFromUniversity(Course course) {
        courses.remove(course);
    }

    public void addFaculty(Faculty faculty) {
        faculties.add(faculty);
    }

    public void removeFaculty(Faculty facultyName) {
        faculties.remove(facultyName);
    }

}
