package utils;

import static utils.Ansi.clearScreen;

import java.util.InputMismatchException;
import java.util.Scanner;
import helper.InputHelper;

import core.University;
import core.Professor;

public class ModifyProfessor {

    private University university;

    public ModifyProfessor(University university) {
        this.university = university;
    }

    public void professorModConsole(Scanner usrInput) {
        boolean isBack = false;
        while (!isBack) {
            System.out.println("---------------- MODIFY PROFESSOR ----------------");
            System.out.println("1. Create Professor");
            System.out.println("2. Update Professor");
            System.out.println("3. Delete Professor");
            System.out.println("4. Show All Professor");
            System.out.println("0. Back");
            System.out.println("--------------------------------------------------");
            System.out.print("Enter your choice: ");
            try {
                int choice = InputHelper.choiceHandler(usrInput);
                usrInput.nextLine();
                clearScreen();

                switch (choice) {
                    case 1:
                        prepareProfessorInfo(usrInput);
                        break;

                    case 2:
                        updateProfessorInfo(usrInput);
                        break;

                    case 3:
                        deleteProfessor(usrInput);
                        break;

                    case 4:
                        university.showAllProfessors();
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

    /**
     * ตรวจสอบว่ารหัสอาจารย์ที่ระบุมีอยู่ในระบบแล้วหรือไม่
     *
     * <p>
     * วิธีการทำงาน:
     * </p>
     * <ol>
     * <li>วนลูปตรวจสอบอาจารย์ทั้งหมดในมหาวิทยาลัย</li>
     * <li>เปรียบเทียบรหัสอาจารย์แต่ละคนกับรหัสที่ระบุ
     * (ไม่สนใจตัวพิมพ์เล็ก-ใหญ่)</li>
     * <li>หากพบว่ารหัสมีอยู่แล้ว จะคืนค่า true</li>
     * <li>หากไม่มีรหัสซ้ำ จะคืนค่า false</li>
     * </ol>
     *
     * @param id รหัสอาจารย์ที่ต้องการตรวจสอบ
     * @return true หากรหัสอาจารย์มีอยู่แล้ว, false หากยังไม่มี
     */
    private boolean isProfessorIdExists(String id) {
        for (Professor p : university.getArrayProfessors()) {
            if (p.getId().equalsIgnoreCase(id)) {
                return true; // ID is Exist
            }
        }
        return false;
    }

    /*------------------*/
    /* CREATE PROFESSOR */
    /*------------------*/
    /**
     * <pre>
     * เตรียมข้อมูลเพื่อสร้างอาจารย์ใหม่ในระบบ
     * </pre>
     *
     * <p>
     * วิธีการทำงาน:
     * </p>
     * <ol>
     * <li>แสดงหัวข้อ "CREATE PROFESSOR"
     * เพื่อแจ้งผู้ใช้ว่ากำลังอยู่ในโหมดสร้างอาจารย์</li>
     * <li>รับรหัสอาจารย์จากผู้ใช้โดยใช้ <code>InputHelper.readPrefixedId()</code>
     * ซึ่งจะตรวจสอบความยาวและเพิ่ม prefix "T" ให้โดยอัตโนมัติ</li>
     * <li>ตรวจสอบว่ารหัสอาจารย์นั้นมีอยู่แล้วหรือไม่
     * หากมี จะแจ้งผู้ใช้และหยุดการทำงาน</li>
     * <li>รับชื่อและรหัสผ่านของอาจารย์จากผู้ใช้</li>
     * <li>สร้างอาจารย์ใหม่ในระบบโดยเรียก
     * <code>university.createNewProfessor()</code></li>
     * </ol>
     *
     * @param usrInput Scanner สำหรับรับข้อมูลจากผู้ใช้
     */
    public void prepareProfessorInfo(Scanner usrInput) {
        System.out.println("------------------ CREATE PROFESSOR ------------------");

        // this is function already checkng length and add prefix "T"
        String profID = InputHelper.readPrefixedId(usrInput, "T");

        if (isProfessorIdExists(profID)) {
            System.out.println("This Professor ID already exists.");
            return;
        }

        System.out.print("Enter Professor Name: ");
        String name = usrInput.nextLine();

        System.out.print("Enter Professor Password: ");
        String password = usrInput.nextLine();

        clearScreen();
        university.createNewProfessor(profID, name, password, university);
    }

    /*------------------*/
    /* UPDATE PROFESSOR */
    /*------------------*/

    /**
     * <pre>
     * อัปเดตข้อมูลของอาจารย์
     * </pre>
     *
     * <p>
     * วิธีการทำงาน:
     * </p>
     * <ol>
     * <li>แสดงรายชื่ออาจารย์ทั้งหมดในระบบให้ผู้ใช้เลือก</li>
     * <li>ผู้ใช้เลือกอาจารย์ที่ต้องการอัปเดต</li>
     * <li>ผู้ใช้สามารถแก้ไขรหัสอาจารย์ (Professor ID) และชื่ออาจารย์ (Professor
     * Name)</li>
     * <li>หากรหัสอาจารย์ที่แก้ไขแล้วซ้ำกับรหัสที่มีอยู่
     * ระบบจะแจ้งเตือนและไม่ทำการแก้ไข</li>
     * <li>หากการแก้ไขถูกต้อง ระบบจะอัปเดตข้อมูลและแสดงข้อความยืนยัน</li>
     * </ol>
     *
     * <p>
     * การจัดการรหัสอาจารย์:
     * </p>
     * <ul>
     * <li>เพิ่ม prefix "T" ให้กับรหัสอาจารย์เสมอ</li>
     * <li>ตรวจสอบความยาวขั้นต่ำและตัดเกินความยาวสูงสุด</li>
     * </ul>
     *
     * @param usrInput Scanner สำหรับรับข้อมูลจากผู้ใช้
     */
    public void updateProfessorInfo(Scanner usrInput) {
        System.out.println("------------------ UPDATE PROFESSOR ------------------");
        university.showAllProfessors();

        if (university.getArrayProfessors().isEmpty()) {
            System.out.println("No professors to update.");
            return;
        }

        int chooseProf = InputHelper.selectorHelper(usrInput,
                "Select professor to update (1-" + university.getArrayProfessors().size() + "): ",
                university.getArrayProfessors().size()) - 1;

        if (chooseProf < 0 || chooseProf >= university.getArrayProfessors().size()) {
            System.out.println("Invalid selection.");
            return;
        }

        Professor selected = university.getArrayProfessors().get(chooseProf);
        System.out.println("Updating info for: " + selected.getId() + " (" + selected.getName() + ")");

        // Checking information
        System.out.print("Create new Professor ID (Force add : T");
        String newId = usrInput.nextLine().trim();
        if (!newId.isEmpty()) {

            String prefix = "T"; // force this prefix
            int requiredLength = 8 - prefix.length(); // rest of the ID after prefix

            // Too short → reject
            if (newId.length() < requiredLength) {
                System.out
                        .println("Professor ID must have at least " + requiredLength
                                + " characters (excluding prefix)!");
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
            if (isProfessorIdExists(newId)) {
                System.out.println("This Professor ID already exists.");
                return;
            }
            selected.setId(newId);
        }

        System.out.print("Enter new Name (leave blank to keep " + selected.getName() + "): ");
        String newName = usrInput.nextLine().trim();
        if (!newName.isEmpty()) {
            selected.setName(newName);
        }

        System.out.println("Professor updated: " + selected.getId() + " - " + selected.getName());
        usrInput.nextLine();
        clearScreen();
    }

    /*------------------*/
    /* DELETE PROFESSOR */
    /*------------------*/

    /**
     * <pre>
     * ลบอาจารย์จากระบบ
     * </pre>
     *
     * <p>
     * วิธีการทำงาน:
     * </p>
     * <ol>
     * <li>แสดงรายชื่ออาจารย์ทั้งหมดในระบบให้ผู้ใช้เลือก</li>
     * <li>ผู้ใช้เลือกอาจารย์ที่ต้องการลบ</li>
     * <li>ก่อนลบ จะมีการยืนยันการลบ (y/n)</li>
     * <li>ถ้ายืนยัน ระบบจะลบอาจารย์ออกจากรายการและแสดงข้อความยืนยัน</li>
     * <li>ถ้าไม่ยืนยัน การลบจะถูกยกเลิก</li>
     * </ol>
     *
     * @param usrInput Scanner สำหรับรับข้อมูลจากผู้ใช้
     */

    public void deleteProfessor(Scanner usrInput) {
        System.out.println("------------------ DELETE PROFESSOR ------------------");
        university.showAllProfessors();

        if (university.getArrayProfessors().isEmpty()) {
            System.out.println("No professors to delete.");
            return;
        }

        int chooseProf = InputHelper.selectorHelper(usrInput,
                "Select professor to delete (1-" + university.getArrayProfessors().size() + "): ",
                university.getArrayProfessors().size()) - 1;

        if (chooseProf < 0 || chooseProf >= university.getArrayProfessors().size()) {
            System.out.println("Invalid selection.");
            return;
        }

        Professor selected = university.getArrayProfessors().get(chooseProf);
        System.out.print("Are you sure you want to delete Professor " + selected.getName() + " (ID: "
                + selected.getId() + ")? (y/n): ");
        String confirmation = usrInput.nextLine().trim().toLowerCase();

        if (confirmation.equals("y")) {
            university.getArrayProfessors().remove(selected);
            System.out.println("Professor deleted: " + selected.getId() + " - " + selected.getName());
        } else {
            System.out.println("Deletion cancelled.");
        }
        usrInput.nextLine();
        clearScreen();
    }
}