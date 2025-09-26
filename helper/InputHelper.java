package helper;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputHelper {

    /**
     * ตรวจสอบการป้อนค่าของผู้ใช้ เพื่อกันข้อผิดพลาดหากใส่สิ่งที่ไม่ใช่ตัวเลข
     * ป้องกันไม่ให้โปรแกรมหยุดทำงานทันที
     * 
     * @param usrInput Scanner object สำหรับรับค่าจากผู้ใช้
     * @return int ค่าที่ผู้ใช้ป้อน
     * @throws InputMismatchException เมื่อผู้ใช้ป้อนค่าที่ไม่ใช่ตัวเลข
     */
    public static int choiceHandler(Scanner usrInput) throws InputMismatchException {
        return usrInput.nextInt();
    }

    /**
     * ตัวช่วย เช่น การเลือก Student หรือ Professor สำหรับ update หรือ delete
     * เลือกใส่ข้อมูลพลาดแล้ว error จะขึ้น loop ใหม่โดยที่ไม่ต้องกลับไปหน้า menu
     * สามารถเขียน prompt เพื่อบอก user ว่าจะให้ใส่อะไร
     * 
     * @param usrInput
     * @param prompt
     * @return ตัวเลขที่เลือก
     */
    public static int selectorHelper(Scanner usrInput, String prompt, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = usrInput.nextInt();
                usrInput.nextLine();

                if (value < 1 || value > max) { // 1-based
                    throw new IndexOutOfBoundsException("Value must be between 1 and " + max);
                }

                return value;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input: must be an integer!");
                usrInput.nextLine(); // clear the invalid token

            } catch (IndexOutOfBoundsException e) {
                System.out.println("Invalid selection: " + e.getMessage());
                usrInput.nextLine();
            }
        }
    }

    /**
     * อ่าน ID ที่มี prefix กำหนด เช่น อาจารย์ ID ขึ้นต้นด้วย T
     * ตัวอย่าง: prefix="T" → ID จะเป็น "T1234567"
     * 
     * วิธีการทำงาน:
     * 1. แสดงข้อความให้ผู้ใช้กรอก ID
     * 2. ตรวจสอบความยาว หากสั้นเกินแจ้งเตือนและให้กรอกใหม่
     * 3. หากยาวเกิน จะตัดให้พอดี
     * 4. คืนค่า ID พร้อม prefix ที่กำหนด
     *
     * @param scanner Scanner object สำหรับรับค่าจากผู้ใช้
     * @param prefix  prefix ที่ต้องการ เช่น "T" สำหรับอาจารย์
     * @return String ID ที่มี prefix
     */
    public static String readPrefixedId(Scanner scanner, String prefix) {
        int requiredLength = 8 - prefix.length(); // e.g., 7 if prefix is "T"

        while (true) {
            System.out.print("Enter ID (" + requiredLength + " characters, prefix '" + prefix + "' will be added): ");
            String input = scanner.nextLine().trim();

            // Too short
            if (input.length() < requiredLength) {
                System.out.println("Input must have at least " + requiredLength + " characters.");
                continue; // ask again
            }

            // Too long → cut it
            if (input.length() > requiredLength) {
                input = input.substring(0, requiredLength);
                System.out.println("Input too long, trimming to: " + input);
            }

            // Return prefixed ID
            return prefix + input;
        }
    }

    /**
     * รับค่าคะแนนหรือจำนวนจากผู้ใช้
     * ตรวจสอบกรณีที่ผู้ใช้กรอกสิ่งที่ไม่ใช่ตัวเลข
     * หากกรอกไม่ถูกต้อง จะใช้ค่า default แทน
     *
     * @param usrInput     Scanner object สำหรับรับค่าจากผู้ใช้
     * @param defaultValue ค่าที่ใช้เมื่อผู้ใช้กรอกไม่ถูกต้อง
     * @return int ค่าที่ผู้ใช้กรอก หรือค่า default
     */
    public static int courseHandler(Scanner usrInput, int defaultValue) {
        int value;
        try {
            value = usrInput.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Using default value " + defaultValue + ".");
            value = defaultValue;
        } finally {
            usrInput.nextLine(); // clear the buffer (newline or bad input)
        }
        return value;
    }
}
