package core;

import java.util.ArrayList;

public class Faculty {
  private String facultyName;

  private ArrayList<Department> departments; // เก็บข้อมูล Department
  private ArrayList<Student> studentInFaculty;

  public Faculty(String facultyName) {
    this.facultyName = facultyName;
    this.departments = new ArrayList<>();
    this.studentInFaculty = new ArrayList<>();
  }

  /**
   * เพิ่มนักเรียน ลงใน Faculty หลังจากตรวจสอบแล้ว
   * 
   * @param newStudent
   */
  public void addStudentInFaculty(Student newStudent) {
    this.studentInFaculty.add(newStudent);
  }

  // Getter สำหรับ facultyName
  public String getFacultyName() {
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
  public void showAllDepartments() {
    for (Department department : getDepartments()) {
      System.out.println("-- " + department.getDepart_name());
    }
  }

  // เมธอดลบ department
  public void removeDepartment(Department department) {
    this.departments.remove(department);
  }

  public ArrayList<Department> getDepartments() {
    return departments;
  }

  public ArrayList<Student> getStudentInFaculty() {
    return studentInFaculty;
  }

  public void createDepartment(String departmentId, String departmentName) {
    Department newDepartment = new Department(departmentId, departmentName);
    this.departments.add(newDepartment);
  }
}