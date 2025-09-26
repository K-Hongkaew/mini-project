import java.util.InputMismatchException;
import java.util.Scanner;

import core.Department;
import core.Faculty;
import core.Professor;
import core.Student;
import core.University;
import core.Course;
import helper.InputHelper;
import static utils.Ansi.*;

public class Main {
    public static void main(String[] args) {
        boolean isExit = false;
        Scanner usrInput = new Scanner(System.in);

        /*-------------------------------------------- */
        /* HARD CODE on university, one is pain enough */
        /*-------------------------------------------- */

        /*--------------------------------------------------------------------*/
        /* PREPAREATION DATA I HATE YOUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU */
        /*------------------------------------------------------------------- */

        University myUniver = new University("AnyUniversity", "1234");

        // ---------------- Professors ----------------
        Professor John = new Professor("T1", "prof-john", "1234", myUniver); // Science
        Professor Jane = new Professor("T7654321", "prof-jane", "1234", myUniver); // ICT
        Professor Mike = new Professor("T1111222", "prof-mike", "1234", myUniver); // Business
        Professor Sara = new Professor("T2222333", "prof-sara", "1234", myUniver); // Arts

        // ---------------- Faculties ----------------
        Faculty ICT = new Faculty("ICT");
        Faculty Science = new Faculty("Science");
        Faculty Business = new Faculty("Business");
        Faculty Arts = new Faculty("Arts");

        myUniver.addFaculty(ICT);
        myUniver.addFaculty(Science);
        myUniver.addFaculty(Business);
        myUniver.addFaculty(Arts);

        // ---------------- Departments ----------------
        // ICT
        Department SE = new Department("SE", "Software Engineering");
        Department CS = new Department("CS", "Computer Science");
        Department DS = new Department("DS", "Data Science");
        ICT.addDepartment(SE);
        ICT.addDepartment(CS);
        ICT.addDepartment(DS);

        // Science
        Department BI = new Department("BI", "Bioinformatics");
        Department BT = new Department("BT", "Biotechnology");
        Department CH = new Department("CH", "Chemistry");
        Science.addDepartment(BI);
        Science.addDepartment(BT);
        Science.addDepartment(CH);

        // Business
        Department AC = new Department("AC", "Accounting");
        Department MK = new Department("MK", "Marketing");
        Business.addDepartment(AC);
        Business.addDepartment(MK);

        // Arts
        Department EN = new Department("EN", "English Literature");
        Department MU = new Department("MU", "Music");
        Arts.addDepartment(EN);
        Arts.addDepartment(MU);

        // ---------------- Students ----------------
        Student Minnie = new Student("S1", "Minnie", "1234", SE, ICT, myUniver); // HARD CODE
        Student Alice = new Student("S2", "Alice", "1234", BI, Science, myUniver); // HARD CODE

        Student Max = new Student("S6432101", "Max", "1234", BT, Science, myUniver);
        Student Tom = new Student("S5432103", "Tom", "1234", CS, ICT, myUniver);
        Student Rose = new Student("S5432104", "Rose", "1234", MK, Business, myUniver);
        Student Leo = new Student("S5432105", "Leo", "1234", AC, Business, myUniver);
        Student Mia = new Student("S5432106", "Mia", "1234", EN, Arts, myUniver);

        // ---------------- Courses ----------------
        // ICT courses (Jane)
        Course SE101 = new Course("SE101", "Intro to Software Engineering", 30, 30, 40, Jane, SE);
        Course CS101 = new Course("CS101", "Intro to Computer Science", 30, 30, 40, Jane, CS);
        Course DS101 = new Course("DS101", "Data Science Fundamentals", 25, 35, 40, Jane, DS);

        // Science courses (John)
        Course BI101 = new Course("BI101", "Intro to Bioinformatics", 30, 30, 40, John, BI);
        Course BT101 = new Course("BT101", "Intro to Biotechnology", 25, 35, 40, John, BT);
        Course CH101 = new Course("CH101", "General Chemistry", 20, 40, 40, John, CH);

        // Business courses (Mike)
        Course AC101 = new Course("AC101", "Accounting Principles", 30, 30, 40, Mike, AC);
        Course MK101 = new Course("MK101", "Marketing Basics", 20, 30, 50, Mike, MK);

        // Arts courses (Sara)
        Course EN101 = new Course("EN101", "English Literature I", 25, 25, 50, Sara, EN);
        Course MU101 = new Course("MU101", "Music Theory I", 30, 30, 40, Sara, MU);

        // ---------------- Assign courses to professors ----------------
        // ICT → Jane
        Jane.addCourse(SE101);
        Jane.addCourse(CS101);
        Jane.addCourse(DS101);

        // Science → John
        John.addCourse(BI101);
        John.addCourse(BT101);
        John.addCourse(CH101);

        // Business → Mike
        Mike.addCourse(AC101);
        Mike.addCourse(MK101);

        // Arts → Sara
        Sara.addCourse(EN101);
        Sara.addCourse(MU101);

        // ---------------- Add courses to university ----------------
        myUniver.addCourseToUniverity(SE101);
        myUniver.addCourseToUniverity(CS101);
        myUniver.addCourseToUniverity(DS101);
        myUniver.addCourseToUniverity(BI101);
        myUniver.addCourseToUniverity(BT101);
        myUniver.addCourseToUniverity(CH101);
        myUniver.addCourseToUniverity(AC101);
        myUniver.addCourseToUniverity(MK101);
        myUniver.addCourseToUniverity(EN101);
        myUniver.addCourseToUniverity(MU101);

        // ---------------- Enroll Students into Courses ----------------
        // ICT courses
        SE101.enrollStudent(Minnie); // SE department
        CS101.enrollStudent(Tom); // CS department
        DS101.enrollStudent(Minnie); // SE student can also take DS
        DS101.enrollStudent(Tom);

        // Science courses
        BI101.enrollStudent(Alice); // BI department
        BT101.enrollStudent(Max); // BT department
        CH101.enrollStudent(Alice); // cross-course
        CH101.enrollStudent(Max);

        // Business courses
        AC101.enrollStudent(Leo); // AC department
        AC101.enrollStudent(Rose); // cross-course
        MK101.enrollStudent(Rose); // MK department
        MK101.enrollStudent(Leo); // cross-course

        // Arts courses
        EN101.enrollStudent(Mia); // EN department
        MU101.enrollStudent(Mia); // MU department
        /*-----*/
        /* END */
        /*-----*/

        while (!isExit) {
            clearScreen();
            System.out.println("---------------- Login Menu ----------------");
            System.out.println("1. University (ROOT)");
            System.out.println("2. Professor");
            System.out.println("3. Student");
            System.out.println("0. Exit");
            System.out.println("--------------------------------------------");

            System.out.print("Choose: ");
            try {
                int choice = InputHelper.choiceHandler(usrInput);
                usrInput.nextLine(); // clear scanner

                switch (choice) {
                    case 1: // Enter ROOT
                        System.out.print("Enter ROOT password: ");
                        String rootPw = usrInput.nextLine();

                        if (myUniver.checkPassword(rootPw)) {
                            clearScreen();
                            myUniver.univerConsole(usrInput, myUniver); // call ROOT menu
                        } else {
                            System.out.println("Wrong password!");
                            usrInput.nextLine();
                            clearScreen();
                        }
                        break;

                    case 2:
                        System.out.print("Enter Professor ID: ");
                        String profId = usrInput.nextLine();
                        Professor prof = myUniver.getProfessorById(profId);
                        if (prof != null) {
                            System.out.print("Enter password: ");
                            String profPw = usrInput.nextLine();

                            if (prof.getPassword().equals(profPw)) {
                                clearScreen();
                                prof.professorConsole(usrInput); // call Professor menu
                            } else {
                                System.out.println("Wrong password!");
                                usrInput.nextLine();
                                clearScreen();
                            }

                        } else {
                            System.out.println("Professor ID not found!");
                            usrInput.nextLine();
                            clearScreen();
                        }
                        break;

                    case 3:
                        System.out.print("Enter Student ID: ");
                        String studentId = usrInput.nextLine();
                        Student student = myUniver.getStudentById(studentId);
                        if (student != null) {
                            System.out.print("Enter password: ");
                            String studentPw = usrInput.nextLine();

                            if (student.getPassword().equals(studentPw)) {
                                clearScreen();
                                student.studentConsole(usrInput); // call Student menu
                            } else {
                                System.out.println("Wrong password!");
                                usrInput.nextLine();
                                clearScreen();
                            }

                        } else {
                            System.out.println("Student ID not found!");
                            usrInput.nextLine();
                            clearScreen();
                        }
                        break;

                    case 0:
                        isExit = true;
                        break;

                    default:
                        System.out.println("Command is invalid");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Input must be an integer!");
                usrInput.nextLine();
            }

        }
        usrInput.close(); // memory leakkkkkk
    }
}
