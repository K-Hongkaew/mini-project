package utils;

import java.util.InputMismatchException;
import java.util.Scanner;

import core.Student;
import core.University;
import helper.InputHelper;
import static utils.Ansi.*;

public class ModifyStudent {

    private University university;

    public ModifyStudent(University university) {
        this.university = university;
    }

    /*------------ */
    /* CONSOLE RUN */
    /*-------------*/

    public void studentModConsole(Scanner usrInput) {
        boolean isBack = false;
        while (!isBack) {
            System.out.println("---------------- MODIFY STUDENT ----------------");
            System.out.println("1. Create Student");
            System.out.println("2. Update Student");
            System.out.println("3. Delete Student");
            System.out.println("4. Add Faculty and Department to Student");
            System.out.println("5. Show All Student");
            System.out.println("0. Back");
            System.out.println("------------------------------------------------");
            System.out.print("Enter your choice: ");
            try {
                int choice = InputHelper.choiceHandler(usrInput);
                usrInput.nextLine();
                clearScreen();

                switch (choice) {
                    case 1:
                        prepareStudentInfo(usrInput);
                        break;

                    case 2:
                        updateStudentInfo(usrInput);
                        break;

                    case 3:
                        deleteStudent(usrInput);
                        break;

                    case 4:
                        assignFacultyAndDepartmentToStudent(usrInput);
                        break;

                    case 5:
                        university.showAllStudents();
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

    /*--------------- */
    /* CREATE STUDENT */
    /*----------------*/

    /**
     * <pre>
     * เตรียมข้อมูลเพื่อสร้างนักศึกษาใหม่ในระบบ
     * </pre>
     *
     * <p>
     * วิธีการทำงาน:
     * </p>
     * <ol>
     * <li>รับรหัสนักศึกษาโดยเรียกใช้ <code>InputHelper.readPrefixedId()</code>
     * โดย prefix จะเป็น "S" และตรวจสอบให้แน่ใจว่ารหัสมีความยาว 8 ตัวอักษร</li>
     * <li>ตรวจสอบว่ารหัสนักศึกษานี้มีอยู่แล้วในระบบหรือไม่
     * หากมี จะแจ้งผู้ใช้และหยุดการทำงาน</li>
     * <li>รับชื่อและรหัสผ่านของนักศึกษาจากผู้ใช้
     * โดยชื่อไม่สามารถเว้นว่างได้</li>
     * <li>สร้างนักศึกษาใหม่ในระบบโดยเรียก
     * <code>university.createNewStudent()</code></li>
     * </ol>
     *
     * @param usrInput Scanner สำหรับรับข้อมูลจากผู้ใช้
     */
    public void prepareStudentInfo(Scanner usrInput) {
        System.out.println("---------------- PREPARING STUDENT INFOMATION ----------------");

        String studentId = InputHelper.readPrefixedId(usrInput, "S");

        if (ifStudentIdExist(studentId)) {
            System.out.println("This student ID already exists.");
            return; // stop
        }
        System.out.print("Enter Student Name: ");
        String name = usrInput.nextLine();
        if (name.isEmpty()) {
            System.out.println("Student name should atleast 1 letter");
            return;
        }

        System.out.print("Enter Student Password: ");
        String password = usrInput.nextLine();

        clearScreen();
        university.createNewStudent(studentId, name, password, university);
    }

    /**
     * ตรวจสอบว่ารหัสนักศึกษาที่ส่งเข้ามา มีอยู่ในระบบแล้วหรือไม่
     *
     * <p>
     * วิธีการทำงาน:
     * </p>
     * <ol>
     * <li>วนลูปตรวจสอบนักศึกษาทุกคนในระบบ
     * (<code>university.getArrayStudents()</code>)</li>
     * <li>เปรียบเทียบรหัสนักศึกษาที่ส่งเข้ามากับรหัสนักศึกษาที่มีอยู่
     * (ไม่สนใจตัวพิมพ์เล็ก/ใหญ่)</li>
     * <li>หากพบรหัสซ้ำ คืนค่า <code>true</code>
     * เพื่อบ่งชี้ว่ารหัสนี้มีอยู่แล้ว</li>
     * <li>หากไม่พบรหัสซ้ำ คืนค่า <code>false</code></li>
     * </ol>
     *
     * @param newStudentId รหัสนักศึกษาที่ต้องการตรวจสอบ
     * @return <code>true</code> หากรหัสนักศึกษามีอยู่แล้ว, <code>false</code>
     *         หากไม่มี
     */
    private boolean ifStudentIdExist(String newStudentId) {
        for (Student s : university.getArrayStudents()) {
            if (s.getId().equalsIgnoreCase(newStudentId)) {
                return true; // ID is Exist
            }
        }
        return false;
    }

    /*--------------- */
    /* UPDATE STUDENT */
    /*----------------*/

    /**
     * <pre>
     * เรียกข้อมูลนิสิตทั้งหมดออกมาแล้ว ให้เลือกว่าจะอัปเดตคนไหน
     * หลังจากเลือกแล้ว จะเข้าเป็น Class Student คน นั้น
     * </pre>
     * <p>
     * 1 ระบบ จะถามว่าเปลี่ยน StudentId ไหม ถ้าไม่ปล่อย ว่าง
     * </p>
     * <p>
     * 2 ระบบ จะถามว่าเปลี่ยน StudentName ไหท ถ้าไม่ปล่อยว่าง
     * หลังจากเสร็จสิ้น ระบบจะปริ้น ข้อมูลนิสิตคนนั้น StudentId + StudentName
     * </p>
     * ถ้า Id ซ้ำจะ ออกจากการ update
     * 
     * @param usrInput
     */
    public void updateStudentInfo(Scanner usrInput) {
        // Printing all student with StudentID and StudentName
        university.showAllStudents();

        // handler not to let error crash the program
        int chooseStudent = InputHelper.selectorHelper(usrInput,
                "Select students to update (1-" + university.getArrayStudents().size() + "): ",
                university.getArrayStudents().size());

        // this is pain
        if (chooseStudent < 1 || chooseStudent > university.getArrayStudents().size()) {
            System.out.println("No such student.");
            return;
        }

        Student selected = university.getArrayStudents().get(chooseStudent - 1);
        System.out.println("Updating info for: " + selected.getId() + " (" + selected.getName() + ")");

        // Checking information
        System.out.print("Create new Student ID (Force add : S");
        String newId = usrInput.nextLine().trim();
        if (!newId.isEmpty()) {

            String prefix = "S"; // force this prefix
            int requiredLength = 8 - prefix.length(); // rest of the ID after prefix

            // Too short → reject
            if (newId.length() < requiredLength) {
                System.out
                        .println("Student ID must have at least " + requiredLength + " characters (excluding prefix)!");
                return;
            }
            // Too long → trim
            if (newId.length() > requiredLength) {
                newId = newId.substring(0, requiredLength);
                System.out.println("Input was too long, trimming to: " + newId);
            }

            // Add prefix
            newId = prefix + newId;

            // Check duplicates
            if (ifStudentIdExist(newId)) {
                System.out.println("This student ID already exists.");
                return;
            }
            selected.setId(newId);
        }

        // Update name to Student
        System.out.print("Enter new Name (leave blank to keep " + selected.getName() + "): ");
        String newName = usrInput.nextLine().trim();
        if (!newName.isEmpty()) {
            selected.setName(newName);
        }

        System.out.println("Student updated: " + selected.getId() + " - " + selected.getName());
        usrInput.nextLine();
        clearScreen();
    }

    /*--------------- */
    /* DELETE STUDENT */
    /*----------------*/

    /**
     * ลบข้อมูลนักศึกษาออกจากระบบ
     *
     * <p>
     * วิธีการทำงาน:
     * </p>
     * <ol>
     * <li>แสดงรายชื่อนักศึกษาทั้งหมดในระบบโดยเรียก
     * <code>university.showAllStudents()</code></li>
     * <li>ให้ผู้ใช้เลือกนักศึกษาที่ต้องการลบจากรายการ (1 ถึง จำนวนทั้งหมด)</li>
     * <li>ตรวจสอบความถูกต้องของตัวเลือก หากไม่ถูกต้อง
     * แจ้งผู้ใช้และหยุดการทำงาน</li>
     * <li>แสดงนักศึกษาที่เลือก พร้อมขอให้ผู้ใช้ยืนยันการลบโดยพิมพ์ ID</li>
     * <li>หากยืนยันด้วย "y" ทำการลบนักศึกษาจากระบบด้วย
     * <code>university.removeStudent()</code></li>
     * <li>หากยกเลิกหรือพิมพ์ค่าอื่น แจ้งว่าการลบถูกยกเลิก</li>
     * </ol>
     *
     * @param usrInput Scanner สำหรับรับข้อมูลจากผู้ใช้
     */
    public void deleteStudent(Scanner usrInput) {
        System.out.println("------------------ DELETE STUDENT ------------------");
        university.showAllStudents();

        if (university.getArrayStudents().isEmpty()) {
            System.out.println("No students to delete.");
            return;
        }

        int chooseStudent = InputHelper.selectorHelper(usrInput,
                "Select students to delete (1-" + university.getArrayStudents().size() + "): ",
                university.getArrayStudents().size());

        Student selected = university.getArrayStudents().get(chooseStudent - 1);
        System.out.println("Selected student " + selected.getId() + " " + selected.getName());

        System.out.print("Are you sure you want to delete Professor " + selected.getName() + " (ID: "
                + selected.getId() + ")? (y/n): ");
        String confirmation = usrInput.nextLine().trim().toLowerCase();

        if (confirmation.equals("y")) {
            university.removeStudent(selected);
            System.out.println("Student " + selected.getId() + " - " + selected.getName() + " has been deleted.");
        } else {
            System.out.println("Deletion cancelled.");
        }
        usrInput.nextLine();
        clearScreen();
    }

    /*------------------------------------------*/
    /* ASSIGN FACULTY AND DEPARTMENT TO STUDENT */
    /*------------------------------------------*/

    /**
     * กำหนดคณะและภาควิชาให้กับนักศึกษา
     *
     * <p>
     * วิธีการทำงาน:
     * </p>
     * <ol>
     * <li>แสดงรายชื่อนักศึกษาทั้งหมดในระบบให้ผู้ใช้เลือก</li>
     * <li>ให้ผู้ใช้เลือกนักศึกษาที่ต้องการมอบหมายคณะและภาควิชา</li>
     * <li>แสดงรายชื่อคณะทั้งหมด และให้ผู้ใช้เลือกคณะ</li>
     * <li>กำหนดคณะที่เลือกให้กับนักศึกษา</li>
     * <li>แสดงรายชื่อภาควิชาในคณะที่เลือก และให้ผู้ใช้เลือกภาควิชา</li>
     * <li>กำหนดภาควิชาที่เลือกให้กับนักศึกษา</li>
     * <li>แจ้งผลการมอบหมายคณะและภาควิชาเรียบร้อย</li>
     * </ol>
     *
     * @param usrInput Scanner object สำหรับรับข้อมูลจากผู้ใช้
     */
    public void assignFacultyAndDepartmentToStudent(Scanner usrInput) {
        System.out.println("------------- ASSIGN FACULTY AND DEPARTMENT TO STUDENT ------------");
        university.showAllStudents();

        int chooseStudent = InputHelper.selectorHelper(usrInput,
                "Choose student number to assign: ",
                university.getArrayStudents().size());

        if (chooseStudent < 1 || chooseStudent > university.getArrayStudents().size()) {
            System.out.println("No such student.");
            return;
        }

        Student selectedStudent = university.getArrayStudents().get(chooseStudent - 1);
        System.out.println("Selected student " + selectedStudent.getId() + " " + selectedStudent.getName());

        // Select Faculty
        university.showAllFaculties();
        int chooseFaculty = InputHelper.selectorHelper(usrInput,
                "Choose faculty number to assign: ",
                university.getArrayFaculties().size());

        if (chooseFaculty < 1 || chooseFaculty > university.getArrayFaculties().size()) {
            System.out.println("No such faculty.");
            return;
        }

        selectedStudent.setFacultyOnStudent(university.getArrayFaculties().get(chooseFaculty - 1));
        System.out.println("Assigned faculty to student successfully.");

        // Select Department
        university.getArrayFaculties().get(chooseFaculty - 1).showAllDepartments();

        int chooseDepartment = InputHelper.selectorHelper(usrInput,
                "Choose department number to assign: ",
                university.getArrayFaculties().get(chooseFaculty - 1).getDepartments().size());

        if (chooseDepartment < 1 || chooseDepartment > university.getArrayFaculties().get(chooseFaculty - 1)
                .getDepartments().size()) {
            System.out.println("No such department.");
            return;
        }

        selectedStudent.setDepartmentOnStudent(university.getArrayFaculties().get(chooseFaculty - 1)
                .getDepartments().get(chooseDepartment - 1));

        System.out.println("Assigned department to student successfully.");
        usrInput.nextLine();
        clearScreen();
    }
}
