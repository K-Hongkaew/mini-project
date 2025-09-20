package core;

import java.util.ArrayList;

public class Faculty {
  private String facultyName;

  private ArrayList<Department> departments; // เก็บข้อมูล Department
  private ArrayList<Student> studentInFaculty;
  // TODO เก็บนิสิตที่อยู่ใน faculty นั้น

  // Constructor เพื่อกำหนดค่าเริ่มต้น
  public Faculty(String facultyName) {
    this.facultyName = facultyName;
    this.departments = new ArrayList<>(); // สร้าง ArrayList สำหรับเก็บ department
  }

  // Getter สำหรับ facultyName
  public String getName() {
    return facultyName;
  }

  // Setter สำหรับ facultyName
  public void setFacultyName(String facultyName) {
    this.facultyName = facultyName;
  }

  // เมธอดเพิ่ม department
  public void addDepartment(Department department) {
    this.departments.add(department);
  }

  // เมธอดแสดงข้อมูล department ทั้งหมด
  public void displayAllDepartments() {
    for (Department department : departments) {
      System.out.println(department.getDepart_name());
    }
  }

  // เมธอดลบ department
  public void removeDepartment(Department department) {
    this.departments.remove(department);
  }
}
