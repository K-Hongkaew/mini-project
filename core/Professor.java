package core;

import static utils.Ansi.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import helper.InputHelper;

public class Professor extends User {

    private ArrayList<Course> classList;
    private University university;

    public Professor(String id, String name, String password, University university) {
        super(id, name, password);
        this.classList = new ArrayList<>();
        this.university = university;
        university.addProfessorToUniversity(this);
    }

    /*------------ */
    /* CONSOLE RUN */
    /*-------------*/

    public void professorConsole(Scanner usrInput) {
        boolean isBack = false;
        while (!isBack) {
            System.out.printf("------------------ WELCOME %s ------------------\n", getName());
            System.out.println("1. View My Courses");
            System.out.println("2. Create My Courses");
            System.out.println("3. Update My Courses");
            System.out.println("4. Delete My Courses");
            System.out.println("5. View Students in My Course");
            System.out.println("6. Edit Students Grades in My Course");
            System.out.println("7. View My Info");
            System.out.println("0. Back");
            System.out.println("----------------------------------------------------");
            System.out.print("Enter your choice: ");
            try {
                int choice = InputHelper.choiceHandler(usrInput);
                usrInput.nextLine();
                clearScreen();

                switch (choice) {
                    case 1:
                        viewMyCourses();
                        usrInput.nextLine();
                        clearScreen();
                        break;
                    case 2:
                        createCourse(usrInput, university);
                        break;

                    case 3:
                        updateCourse(usrInput);
                        break;

                    case 4:
                        deleteCourse(usrInput);
                        break;
                    case 5:
                        viewStudentsInMyCourse(usrInput);
                        break;

                    case 6:
                        editStudentGradeInMyCourse(usrInput);
                        break;

                    case 7: // useless I know
                        System.out.println(getUserInfo());
                        usrInput.nextLine();
                        break;

                    case 0:
                        isBack = true;
                        break;

                    default:
                        System.out.println("Invalid command");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Input must be an integer!");
                usrInput.nextLine();
            }
        }
    }

    /*---------------*/
    /* CREATE COURSE */
    /*---------------*/
    /**
     * สร้างคอร์สใหม่และกำหนดอาจารย์, คณะ, และภาควิชา
     *
     * <p>
     * วิธีการทำงาน:
     * </p>
     * <ol>
     * <li>รับรหัสคอร์สจากผู้ใช้และตรวจสอบว่ามีอยู่แล้วหรือไม่</li>
     * <li>รับชื่อคอร์สจากผู้ใช้</li>
     * <li>รับน้ำหนักคะแนน Midterm, Final และ Assignment โดยมีค่าเริ่มต้น 30, 30, 40
     * ตามลำดับ</li>
     * <li>ตรวจสอบว่าน้ำหนักรวมของ Midterm + Final ไม่เกิน 100</li>
     * <li>แสดงรายชื่อคณะทั้งหมดในมหาวิทยาลัยและให้ผู้ใช้เลือก</li>
     * <li>แสดงรายชื่อภาควิชาภายในคณะที่เลือกและให้ผู้ใช้เลือก</li>
     * <li>สร้างอ็อบเจ็กต์คอร์สใหม่ กำหนดอาจารย์และภาควิชา</li>
     * <li>เพิ่มคอร์สลงในมหาวิทยาลัยและยืนยันการสร้างคอร์สสำเร็จ</li>
     * </ol>
     *
     * @param usrInput   Scanner สำหรับรับค่าจากผู้ใช้
     * @param university อ็อบเจ็กต์มหาวิทยาลัยที่คอร์สจะถูกเพิ่ม
     */
    public void createCourse(Scanner usrInput, University university) {
        System.out.println("------------------ CREATE COURSE ------------------");
        System.out.print("Enter Course ID : ");
        String courseID = usrInput.nextLine().trim();

        if (university.isCourseIdExists(courseID)) {
            System.out.println("This Course ID already exists.");
            return;
        }

        System.out.print("Enter Course Name: ");
        String courseName = usrInput.nextLine();

        System.out.print("Add Midterm weight (default 30): ");
        int midtermWeight = InputHelper.courseHandler(usrInput, 30);

        System.out.print("Add Final weight (default 30): ");
        int finalWeight = InputHelper.courseHandler(usrInput, 30);

        if (midtermWeight + finalWeight > 100) {
            System.out.println("Mid and Final should not have summary over 100");
            return;
        }

        System.out.print("Add Assignment weight (default 40): ");
        int assignmentWeight = InputHelper.courseHandler(usrInput, 40);

        System.out.println("Assign course to Department:");
        university.showAllFaculties();

        int facultyChoice = InputHelper.selectorHelper(
                usrInput,
                "Select Faculty (1-" + university.getArrayFaculties().size() + "): ",
                university.getArrayFaculties().size()) - 1;

        if (facultyChoice < 0 || facultyChoice >= university.getArrayFaculties().size()) {
            System.out.println("Invalid Faculty selection. Course creation cancelled.");
            return;
        }

        Faculty selectedFaculty = university.getArrayFaculties().get(facultyChoice);
        selectedFaculty.showAllDepartments();

        int departmentChoice = InputHelper.selectorHelper(
                usrInput,
                "Select Department (1-" + selectedFaculty.getDepartments().size() + "): ",
                selectedFaculty.getDepartments().size()) - 1;

        if (departmentChoice < 0 || departmentChoice >= selectedFaculty.getDepartments().size()) {
            System.out.println("Invalid Department selection. Course creation cancelled.");
            return;
        }
        Department selectedDepartment = selectedFaculty.getDepartments().get(departmentChoice);

        Course newCourse = new Course(courseID, courseName, midtermWeight, finalWeight, assignmentWeight, this,
                selectedDepartment);
        university.addCourseToUniverity(newCourse);
        newCourse.setProfessor(this);
        System.out.println("Course created successfully!");
        usrInput.nextLine();
        clearScreen();
    }

    /*---------------*/
    /* UPDATE COURSE */
    /*---------------*/
    /**
     * อัปเดตข้อมูลคอร์สของอาจารย์
     *
     * <p>
     * วิธีการทำงาน:
     * </p>
     * <ol>
     * <li>แสดงรายชื่อคอร์สทั้งหมดของอาจารย์</li>
     * <li>ตรวจสอบว่ามีคอร์สให้แก้ไขหรือไม่ หากไม่มีจะยกเลิกการทำงาน</li>
     * <li>ให้ผู้ใช้เลือกคอร์สที่ต้องการอัปเดต</li>
     * <li>อนุญาตให้แก้ไขรหัสคอร์ส (Course ID) และชื่อคอร์ส (Course Name)</li>
     * <li>หากผู้ใช้เว้นว่าง จะใช้ค่าปัจจุบันของคอร์ส</li>
     * <li>ยืนยันการอัปเดตคอร์สสำเร็จ</li>
     * </ol>
     *
     * @param usrInput Scanner สำหรับรับค่าจากผู้ใช้
     */
    public void updateCourse(Scanner usrInput) {
        System.out.println("------------------ UPDATE COURSE ------------------");
        viewMyCourses();

        if (getClassList().isEmpty()) {
            System.out.println("You have no courses to update.");
            return;
        }

        int chooseCourse = InputHelper.selectorHelper(
                usrInput,
                "Select course to update (1-" + getClassList().size() + "): ",
                getClassList().size()) - 1;

        if (chooseCourse < 0 || chooseCourse >= getClassList().size()) {
            System.out.println("Invalid selection.");
        }

        Course selectedCourse = getClassList().get(chooseCourse);
        System.out.println("Updating Course: " + selectedCourse.getCourseID() + " - " + selectedCourse.getCourseName());
        System.out.print("Enter new Course ID (current: " + selectedCourse.getCourseID() + "): ");
        // leave blank to keep current id
        String newId = usrInput.nextLine().trim();
        if (!newId.isEmpty()) {
            selectedCourse.setCourseID(newId);
            System.out.println("New Course ID: " + selectedCourse.getCourseID());
        }

        System.out.print("Enter new Course Name (current: " + selectedCourse.getCourseName() + "): ");
        // leave blank to keep current name
        String newName = usrInput.nextLine().trim();
        if (!newName.isEmpty()) {
            selectedCourse.setCourseName(newName);
        }
        System.out.println("Course updated: " + selectedCourse.getCourseID() + " - " + selectedCourse.getCourseName());
        usrInput.nextLine();
        clearScreen();
    }

    public void viewMyCourses() {
        System.out.println("-----------Professor : " + getName() + " Courses -----------");
        for (int i = 0; i < classList.size(); i++) {
            Course c = classList.get(i);
            System.out.println((i + 1) + ". " + c.getCourseID() + " - " + c.getCourseName());
        }
    }

    /*---------------*/
    /* DELETE COURSE */
    /*---------------*/
    public void deleteCourse(Scanner usrInput) {
        System.out.println("------------------ DELETE COURSE ------------------");
        viewMyCourses();

        if (getClassList().isEmpty()) {
            System.out.println("You have no courses to delete.");
            return;
        }

        int chooseCourse = InputHelper.selectorHelper(usrInput,
                "Select course to delete (1-" + getClassList().size() + "): ",
                getClassList().size()) - 1;

        if (chooseCourse < 0 || chooseCourse >= getClassList().size()) {
            System.out.println("Invalid selection.");
            return;
        }

        Course selectedCourse = getClassList().get(chooseCourse);
        System.out.print("Are you sure you want to delete Course " + selectedCourse.getCourseName() + " (ID: "
                + selectedCourse.getCourseID() + ")? (y/n): ");
        String confirmation = usrInput.nextLine().trim().toLowerCase();

        if (confirmation.equals("y")) {
            classList.remove(selectedCourse);

            university.removeCourseFromUniversity(selectedCourse);

            for (Student s : selectedCourse.getStudentInCourse()) {
                s.getEnrollments().remove(selectedCourse);
            }

            System.out.println("Course deleted: "
                    + selectedCourse.getCourseID() + " - "
                    + selectedCourse.getCourseName());
        } else {
            System.out.println("Deletion cancelled.");
        }

        usrInput.nextLine();
        clearScreen();
    }

    /*------------------------*/
    /* VIEW STUDENT IN COURSE */
    /*------------------------*/
    /**
     * ลบคอร์สของอาจารย์
     *
     * <p>
     * วิธีการทำงาน:
     * </p>
     * <ol>
     * <li>แสดงรายชื่อคอร์สทั้งหมดของอาจารย์</li>
     * <li>ตรวจสอบว่ามีคอร์สให้ลบหรือไม่ หากไม่มีจะยกเลิกการทำงาน</li>
     * <li>ให้ผู้ใช้เลือกคอร์สที่ต้องการลบ</li>
     * <li>ขอการยืนยันจากผู้ใช้ก่อนลบคอร์ส</li>
     * <li>หากยืนยันการลบ:
     * <ul>
     * <li>ลบคอร์สออกจากรายการ classList ของอาจารย์</li>
     * <li>ลบคอร์สออกจากมหาวิทยาลัย</li>
     * <li>ลบคอร์สออกจากรายการ enrollment ของนักเรียนที่ลงทะเบียนคอร์สนี้</li>
     * </ul>
     * </li>
     * <li>หากไม่ยืนยัน การลบจะถูกยกเลิก</li>
     * </ol>
     *
     * @param usrInput Scanner สำหรับรับค่าจากผู้ใช้
     */
    public void viewStudentsInMyCourse(Scanner usrInput) {
        System.out.println("------------------ VIEW STUDENTS IN MY COURSE ------------------");
        viewMyCourses();
        System.out.println();

        if (getClassList().isEmpty()) {
            System.out.println("You have no courses.");
            return;
        }

        for (int i = 0; i < getClassList().size(); i++) {
            Course c = classList.get(i);
            System.out.println(
                    "Students in Course: " + c.getCourseID() + " - " + c.getCourseName());
            ArrayList<Student> studentArray = c.getStudentInCourse();

            if (studentArray.isEmpty()) {
                System.out.println("No students enrolled in this course.");
                System.out.println();
            } else {
                for (Student s : studentArray) {
                    System.out.println("Student: " + s.getName() + " (ID: " + s.getId() + ")");
                }
                System.out.println();
            }
        }
        usrInput.nextLine();
        clearScreen();
    }

    /*------------------------------*/
    /* EDIT STUDENT GRADE IN COURSE */
    /*------------------------------*/

    /**
     * แก้ไขคะแนนของนักเรียนในคอร์สของอาจารย์
     *
     * <p>
     * วิธีการทำงาน:
     * </p>
     * <ol>
     * <li>แสดงรายชื่อคอร์สทั้งหมดของอาจารย์</li>
     * <li>ตรวจสอบว่ามีคอร์สให้แก้ไขหรือไม่ หากไม่มีจะยกเลิกการทำงาน</li>
     * <li>ให้ผู้ใช้เลือกคอร์สที่ต้องการแก้ไขคะแนนนักเรียน</li>
     * <li>ตรวจสอบว่ามีนักเรียนลงทะเบียนในคอร์สนั้นหรือไม่</li>
     * <li>แสดงรายชื่อนักเรียนทั้งหมดในคอร์สและให้ผู้ใช้เลือก</li>
     * <li>ตรวจสอบว่าผู้เรียนลงทะเบียนในคอร์สหรือไม่</li>
     * <li>ให้ผู้ใช้ป้อนคะแนน Midterm, Final และ Assignment</li>
     * <li>ตรวจสอบว่า Midterm และ Final ไม่เกินคะแนนสูงสุดที่กำหนด</li>
     * <li>อัปเดตคะแนนของนักเรียนและคำนวณคะแนนรวมและเกรด</li>
     * <li>แสดงคะแนนปัจจุบันและเกรดของนักเรียน</li>
     * </ol>
     *
     * @param usrInput Scanner สำหรับรับค่าจากผู้ใช้
     */
    public void editStudentGradeInMyCourse(Scanner usrInput) {
        System.out.println("------------------ EDIT STUDENT GRADES IN MY COURSE ------------------");
        viewMyCourses();

        if (getClassList().isEmpty()) {
            System.out.println("You have no courses.");
            return;
        }

        int chooseCourse = InputHelper.selectorHelper(usrInput,
                "Select course to edit student grades (1-" + getClassList().size() + "): ",
                getClassList().size()) - 1;

        if (chooseCourse < 0 || chooseCourse >= getClassList().size()) {
            System.out.println("Invalid selection.");
            return;
        }

        Course selectedCourse = getClassList().get(chooseCourse);
        System.out.println(
                "Editing Grades for Course: " + selectedCourse.getCourseID() + " - " + selectedCourse.getCourseName());

        ArrayList<Student> studentsInCourse = selectedCourse.getStudentInCourse();
        if (studentsInCourse.isEmpty()) {
            System.out.println("No students enrolled in this course.");
            return;
        }

        for (int i = 0; i < studentsInCourse.size(); i++) {
            Student s = studentsInCourse.get(i);
            System.out.println((i + 1) + ". " + s.getName() + " (ID: " + s.getId() + ")");
        }

        int chooseStudent = InputHelper.selectorHelper(usrInput,
                "Select student to edit grades (1-" + studentsInCourse.size() + "): ",
                studentsInCourse.size()) - 1;

        if (chooseStudent < 0 || chooseStudent >= studentsInCourse.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        Student selectedStudent = studentsInCourse.get(chooseStudent);
        Course courseOfStudent = selectedStudent.matchCourse(selectedCourse.getCourseID());

        if (courseOfStudent == null) {
            System.out.println("Student is not enrolled in this course!");
            usrInput.nextLine();
            return;
        }

        System.out.println(
                "Editing grades for Student: " + selectedStudent.getName() + " (ID: " + selectedStudent.getId() + ")");

        System.out.print("Enter Midterm score " + selectedCourse.getMAX_MIDTERM() + " is max: ");
        int midtermScore = InputHelper.courseHandler(usrInput, 0);

        if (midtermScore > selectedCourse.getMAX_MIDTERM()) {
            System.out.println("Score exceeds maximum midterm score of " + selectedCourse.getMAX_MIDTERM());
            return;
        }

        System.out.print("Enter Final score " + selectedCourse.getMAX_FINAL() + " is max: ");
        int finalScore = InputHelper.courseHandler(usrInput, 0);
        if (finalScore > selectedCourse.getMAX_FINAL()) {
            System.out.println("Score exceeds maximum final score of " + selectedCourse.getMAX_FINAL());
            return;
        }

        System.out.print("Enter Assignment score (can exceed max " + selectedCourse.getMAX_ASSIGNMENT() + "): ");
        // assignment score can be more than max because of extra credit
        int assignmentScore = InputHelper.courseHandler(usrInput, 0);

        // Here you would typically update the student's record with these scores.
        // For this example, we'll just print them out.
        System.out.println("Updated grades for " + selectedStudent.getName() + ":");
        System.out.println("Midterm: " + midtermScore);
        System.out.println("Final: " + finalScore);
        System.out.println("Assignment: " + assignmentScore);
        courseOfStudent.setMidtermScore(midtermScore);
        courseOfStudent.setFinalScore(finalScore);
        courseOfStudent.setAssignmentScore(assignmentScore);
        courseOfStudent.overallScore();

        System.out.println("Student score : " + courseOfStudent.getCurrentScore());
        System.out.println("Student grade : " + courseOfStudent.getCourseGrade());

        usrInput.nextLine();
        clearScreen();
    }

    // ---------------- GETTER SETTER ---------------

    public void setPassword(String newPassword) {
        setPassword(newPassword);
    }

    public String getProfessorId() {
        return getId();
    }

    public String getProfessorName() {
        return getName();
    }

    public void setProfessorName(String newName) {
        setName(newName);
    }

    public void setProfessorId(String newId) {
        setId(newId);
    }

    public ArrayList<Course> getClassList() {
        return classList;
    }

    public void addCourse(Course course) {
        if (!classList.contains(course)) {
            classList.add(course);
            System.out.println("Course " + course.getCourseName() + " added to Professor " + getName());
        } else {
            System.out.println("Course " + course.getCourseName() + " is already assigned to Professor " + getName());
        }
    }

    @Override
    public String getUserInfo() {
        return "Professor: " + getName() +
                " (ID: " + getId();
    }
}
