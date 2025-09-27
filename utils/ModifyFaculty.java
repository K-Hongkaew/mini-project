package utils;

import java.util.InputMismatchException;
import java.util.Scanner;

import core.Department;
import core.Faculty;
import core.University;
import helper.InputHelper;

import static utils.Ansi.*;

public class ModifyFaculty {
    private University university;

    public ModifyFaculty(University university) {
        this.university = university;
    }

    /*-------------*/
    /* CONSOLE RUN */
    /*-------------*/

    public void facultyConsole(Scanner usrInput) {
        boolean isBack = false;
        while (!isBack) {
            System.out.println("---------------- MODIFY STUDENT ----------------");
            System.out.println("1. Create Faculty");
            System.out.println("2. Update Faculty");
            System.out.println("3. Delete Faculty");
            System.out.println("4. Create Department to Faculty");
            System.out.println("5. Update Department in Faculty");
            System.out.println("6. Delete Department in Faculty");
            System.out.println("7. Print all Faculty");
            System.out.println("8. Print all Faculty and Department");
            System.out.println("0. Back");
            System.out.println("------------------------------------------------");
            try {
                int choice = InputHelper.choiceHandler(usrInput);
                usrInput.nextLine();
                clearScreen();

                switch (choice) {
                    case 1:
                        createFaculty(usrInput);
                        break;

                    case 2:
                        updateFaculty(usrInput);
                        break;

                    case 3:
                        deleteFaculty(usrInput);
                        break;

                    case 4:
                        createDepartmentToFaculty(usrInput);
                        break;

                    case 5:
                        updateDepartmentInFaculty(usrInput);
                        break;

                    case 6:
                        deleteDepartmentInFaculty(usrInput);
                        break;

                    case 7:
                        university.showAllFaculties();
                        break;

                    case 8:
                        university.printFaultyWithDepartment();
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
    /* CREATE FACULTY */
    /*----------------*/

    /**
     * สร้าง Faculty ใหม่ในระบบ โดยตรวจสอบว่าชื่อ Faculty ไม่ซ้ำกับที่มีอยู่แล้ว
     * 
     * <p>
     * วิธีการทำงาน:
     * </p>
     * <ol>
     * <li>แสดงหัวข้อสร้าง Faculty</li>
     * <li>รับชื่อ Faculty ใหม่จากผู้ใช้</li>
     * <li>ตรวจสอบว่าชื่อ Faculty ที่ป้อนไม่ซ้ำกับในระบบ หากซ้ำ
     * จะแจ้งผู้ใช้และหยุดทำงาน</li>
     * <li>สร้าง Faculty ใหม่และเพิ่มเข้าในระบบมหาวิทยาลัย</li>
     * <li>แจ้งผู้ใช้ว่าการสร้างสำเร็จ</li>
     * </ol>
     * 
     * @param usrInput Scanner object สำหรับรับค่าจากผู้ใช้
     */
    public void createFaculty(Scanner usrInput) {
        System.out.println("---------------- CREATE FACULTY ----------------");
        System.out.print("Enter Faculty name: ");

        String facultyName = usrInput.nextLine().toUpperCase().trim();

        // Check if faculty already exists
        if (university.checkExistFaculty(facultyName)) {
            System.out.println("Faculty " + facultyName + " already exists.");
            return;
        }

        // Create and add new faculty
        Faculty newFaculty = new Faculty(facultyName);
        university.addFaculty(newFaculty);
        System.out.println("Faculty " + facultyName + " created successfully.");

        usrInput.nextLine(); // Pause
        clearScreen();
    }

    /*--------------- */
    /* UPDATE FACULTY */
    /*----------------*/

    /**
     * อัปเดตชื่อของ Faculty ที่เลือกจากรายการ
     * 
     * <p>
     * วิธีการทำงาน:
     * </p>
     * <ol>
     * <li>แสดงรายชื่อ Faculty ทั้งหมดในระบบ</li>
     * <li>ผู้ใช้เลือก Faculty ที่ต้องการแก้ไข</li>
     * <li>ตรวจสอบความถูกต้องของการเลือก หากเลือกไม่ถูกต้อง จะยกเลิกการทำงาน</li>
     * <li>แสดงชื่อ Faculty ที่เลือกและรับค่าชื่อใหม่จากผู้ใช้</li>
     * <li>อัปเดตชื่อ Faculty ในระบบตามค่าที่ผู้ใช้ป้อน</li>
     * </ol>
     *
     * @param usrInput Scanner object สำหรับรับค่าจากผู้ใช้
     */
    public void updateFaculty(Scanner usrInput) {
        System.out.println("---------------- UPDATE FACULTY ----------------");
        university.showAllFaculties();
        int chooseFaculty = InputHelper.selectorHelper(usrInput,
                "Select exist faculty to update (1-" + university.getArrayFaculties().size() + "): ",
                university.getArrayFaculties().size());

        // Update faculty name
        Faculty selected = university.getArrayFaculties().get(chooseFaculty - 1);
        System.out.println("Selected faculty: " + selected.getFacultyName());
        System.out.print("Enter new faculty name: ");

        String newFacultyName = usrInput.nextLine().trim();
        selected.setFacultyName(newFacultyName);
        System.out.println("Faculty updated to: " + selected.getFacultyName());

        usrInput.nextLine();
        clearScreen();
    }

    /*--------------- */
    /* DELETE FACULTY */
    /*----------------*/

    /**
     * ลบ Faculty โดยการเลือกจากรายการที่แสดง
     * 
     * <p>
     * วิธีการทำงาน:
     * </p>
     * <ol>
     * <li>แสดงรายชื่อ Faculty ทั้งหมดในระบบ</li>
     * <li>ผู้ใช้เลือก Faculty ที่ต้องการลบ</li>
     * <li>ตรวจสอบความถูกต้องของการเลือก หากเลือกไม่ถูกต้อง จะยกเลิกการทำงาน</li>
     * <li>แสดงชื่อ Faculty ที่เลือกและขอการยืนยันจากผู้ใช้ก่อนลบ</li>
     * <li>หากผู้ใช้ยืนยันด้วย 'y' จะลบ Faculty นั้นออกจากระบบ</li>
     * <li>หากผู้ใช้ไม่ยืนยัน จะยกเลิกการลบ</li>
     * </ol>
     *
     * @param usrInput Scanner object สำหรับรับค่าจากผู้ใช้
     */
    public void deleteFaculty(Scanner usrInput) {
        university.showAllFaculties();
        int chooseFaculty = InputHelper.selectorHelper(usrInput,
                "Select exist faculty to delete (1-" + university.getArrayFaculties().size() + "): ",
                university.getArrayFaculties().size());

        // selected Faculty
        Faculty selected = university.getArrayFaculties().get(chooseFaculty - 1);
        System.out.println("Selected faculty " + selected.getFacultyName());
        System.out.print("Confirm to delete faculty (y/n): ");

        String confirm = usrInput.nextLine().trim().toLowerCase();
        if (confirm.equals("y")) {
            university.removeFaculty(selected);
            System.out.println("Faculty " + selected.getFacultyName() + " deleted.");

        } else {
            System.out.println("Deletion cancelled.");
        }

        usrInput.nextLine(); // Pause
        clearScreen();
    }

    /*------------------------------*/
    /* CREATE DEPARTMENT IN FACULTY */
    /*------------------------------*/

    /**
     * <pre>
     * เพิ่ม Department ใหม่เข้าไปใน Faculty ที่เลือก
     * </pre>
     *
     * <p>
     * วิธีการทำงาน:
     * </p>
     * <ol>
     * <li>แสดงรายการ Faculty ทั้งหมดในระบบ</li>
     * <li>เลือก Faculty ที่ต้องการเพิ่ม Department</li>
     * <li>ป้อนรหัส Department ใหม่และชื่อเต็มของ Department</li>
     * <li>สร้าง Department ใหม่และเพิ่มเข้าไปใน Faculty ที่เลือก</li>
     * <li>แสดงข้อความยืนยันการเพิ่ม Department</li>
     * </ol>
     *
     * @param usrInput Scanner สำหรับรับข้อมูลจากผู้ใช้
     */
    public void createDepartmentToFaculty(Scanner usrInput) {
        university.showAllFaculties();
        int chooseFaculty = InputHelper.selectorHelper(usrInput,
                "Select exist faculty (1-" + university.getArrayFaculties().size() + "): ",
                university.getArrayFaculties().size());

        if (chooseFaculty < 1 || chooseFaculty > university.getArrayFaculties().size()) {
            System.out.println("No such Faculty.");
            return;
        }

        // selected Faculty
        Faculty selected = university.getArrayFaculties().get(chooseFaculty - 1);
        System.out.println("Selected faculty " + selected.getFacultyName());
        System.out.print("Enter new Department name: ");
        String departmentId = usrInput.nextLine().trim();

        System.out.print("Enter new Department full name: ");
        String departmentName = usrInput.nextLine().trim();

        // Create and add new department
        selected.createDepartment(departmentId, departmentName);
        System.out.println("Department " + departmentId + " added to Faculty " + selected.getFacultyName() + ".");

        usrInput.nextLine(); // Pause
        clearScreen();
    }

    /*------------------------------*/
    /* UPDATE DEPARTMENT IN FACULTY */
    /*------------------------------*/

    /**
     * <pre>
     * อัปเดตข้อมูล Department ใน Faculty
     * </pre>
     *
     * <p>
     * วิธีการทำงาน:
     * </p>
     * <ol>
     * <li>แสดงรายการ Faculty ทั้งหมดพร้อม Department ภายในแต่ละ Faculty</li>
     * <li>เลือก Faculty ที่ต้องการแก้ไข Department</li>
     * <li>แสดงรายการ Department ใน Faculty ที่เลือก</li>
     * <li>เลือก Department ที่ต้องการอัปเดต</li>
     * <li>ป้อนรหัส Department ใหม่และชื่อเต็มใหม่</li>
     * <li>ระบบอัปเดตข้อมูล Department และแสดงข้อความยืนยัน</li>
     * </ol>
     *
     * @param usrInput Scanner สำหรับรับข้อมูลจากผู้ใช้
     */
    public void updateDepartmentInFaculty(Scanner usrInput) {
        System.out.println("---------------- UPDATE DEPARTMENT IN FACULTY ----------------");
        university.printFaultyWithDepartment();

        // Choose faculty
        int chooseFaculty = InputHelper.selectorHelper(usrInput,
                "Select exist faculty (1-" + university.getArrayFaculties().size() + "): ",
                university.getArrayFaculties().size());

        if (chooseFaculty < 1 || chooseFaculty > university.getArrayFaculties().size()) {
            System.out.println("No such Faculty.");
            return;
        }
        Faculty selectedFaculty = university.getArrayFaculties().get(chooseFaculty - 1);
        System.out.println("Selected faculty " + selectedFaculty.getFacultyName());
        selectedFaculty.showAllDepartments();

        // Choose department
        int chooseDepartment = InputHelper.selectorHelper(usrInput,
                "Select exist department (1-" + selectedFaculty.getDepartments().size() + "): ",
                selectedFaculty.getDepartments().size());

        Department selectDepartment = selectedFaculty.getDepartments().get(chooseDepartment - 1);
        System.out.println("Selected department " + selectDepartment.getDepart_name());
        System.out.print("Enter new Department name: ");

        String newDepartmentId = usrInput.nextLine().trim();
        System.out.print("Enter new Department full name: ");
        String newDepartmentName = usrInput.nextLine().trim();

        selectDepartment.setDepartmentId(newDepartmentId);
        selectDepartment.setDepart_name(newDepartmentName);
        System.out.println("Department updated to: " + selectDepartment.getDepartID() + " - "
                + selectDepartment.getDepart_name());

        usrInput.nextLine();
        clearScreen();
    }

    /*------------------------------*/
    /* DELETE DEPARTMENT IN FACULTY */
    /*------------------------------*/

    /**
     * <pre>
     * ลบ Department ออกจาก Faculty ที่เลือก
     * </pre>
     *
     * <p>
     * วิธีการทำงาน:
     * </p>
     * <ol>
     * <li>แสดงรายการ Faculty ทั้งหมดพร้อม Department ที่อยู่ภายในแต่ละ Faculty</li>
     * <li>เลือก Faculty ที่ต้องการลบ Department</li>
     * <li>แสดงรายการ Department ใน Faculty ที่เลือก</li>
     * <li>เลือก Department ที่ต้องการลบ</li>
     * <li>ยืนยันการลบ (y/n)</li>
     * <li>หากยืนยัน ระบบจะลบ Department ออกจาก Faculty</li>
     * </ol>
     *
     * <p>
     * หลังจากลบ จะมีข้อความยืนยันการลบ หรือยกเลิกหากไม่ยืนยัน
     * </p>
     *
     * @param usrInput Scanner สำหรับรับข้อมูลจากผู้ใช้
     */
    public void deleteDepartmentInFaculty(Scanner usrInput) {
        System.out.println("---------------- DELETE DEPARTMENT IN FACULTY ----------------");
        university.printFaultyWithDepartment();

        // Choose faculty
        int chooseFaculty = InputHelper.selectorHelper(usrInput,
                "Select exist faculty to delete department (1-" + university.getArrayFaculties().size() + "): ",
                university.getArrayFaculties().size());

        Faculty selectedFaculty = university.getArrayFaculties().get(chooseFaculty - 1);
        System.out.println("Selected faculty " + selectedFaculty.getFacultyName());
        selectedFaculty.showAllDepartments();

        // Choose department
        int chooseDepartment = InputHelper.selectorHelper(usrInput,
                "Select exist faculty (1-" + selectedFaculty.getDepartments().size() + "): ",
                selectedFaculty.getDepartments().size());

        Department selectDepartment = selectedFaculty.getDepartments().get(chooseDepartment - 1);
        System.out.println("Selected department " + selectDepartment.getDepart_name());
        System.out.print("Confirm to delete department (y/n): ");

        String confirm = usrInput.nextLine().trim().toLowerCase();
        if (confirm.equals("y")) {
            selectedFaculty.removeDepartment(selectDepartment);
            System.out.println("Department " + selectDepartment.getDepart_name() + " deleted.");

        } else {
            System.out.println("Deletion cancelled.");
        }

        usrInput.nextLine(); // Pause
        clearScreen();
    }
}
