package core;

import java.util.ArrayList;

public class University {
    private String universityName;
    private String universityPassword;

    private ArrayList<Student> students;
    private ArrayList<Professor> professors;
    private ArrayList<Faculty> faculties;

    // Constructor
    public University(String universityName, String universityPassword) {
        this.universityName = universityName;
        this.universityPassword = universityPassword;
        this.students = new ArrayList<>();
        this.professors = new ArrayList<>();
        this.faculties = new ArrayList<>();
    }

    // เช็ครหัสผ่าน
    public Boolean checkPassword(String password) {
        return this.universityPassword.equals(password);
    }

    // Getter & Setter
    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public String getUniversityPassword() {
        return universityPassword;
    }

    public void setUniversityPassword(String universityPassword) {
        this.universityPassword = universityPassword;
    }

    // Method: เพิ่ม Faculty
    public void addFaculty(Faculty faculty) {
        faculties.add(faculty);
    }

    // Method: ลบ Faculty ตามชื่อ
    public void removeFaculty(String facultyName) {
        faculties.removeIf(f -> f.getName().equals(facultyName));
    }

    // Method: สร้าง Department ใน Faculty ตามชื่อ
    public void addDepartmentToFaculty(String facultyName, Department department) {
        for (Faculty f : faculties) {
            if (f.getName().equals(facultyName)) {
                f.addDepartment(department);
                break;
            }
        }
    }

    // Method: ลบ Department ใน Faculty ตามชื่อ
    public void removeDepartmentFromFaculty(String facultyName, Department departmentName) {
        for (Faculty f : faculties) {
            if (f.getName().equals(facultyName)) {
                f.removeDepartment(departmentName);
                break;
            }
        }
    }

    // Method: แสดงนักเรียนทั้งหมด
    public void showAllStudents() {
        for (Student s : students) {
            System.out.println(s);
        }
    }

    // Method: แสดงอาจารย์ทั้งหมด
    public void showAllProfessors() {
        for (Professor p : professors) {
            System.out.println(p);
        }
    }

    // Method: เพิ่ม student
    public void addStudent(Student student) {
        students.add(student);
    }

    // Method: เพิ่ม professor
    public void addProfessor(Professor professor) {
        professors.add(professor);
    }
}
