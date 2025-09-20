class Student extends User {
    private ArrayList<Course> enrolledCourses = new ArrayList<>(); 
    private ArrayList<Double> grades = new ArrayList<>(); 

    public Student(String id, String password,String name,  String department, String faculty) {
        super(id, password,  name,  department,faculty);
    }

    public void enroll(Course course) {
        if (!enrolledCourses.contains(course)) {
            enrolledCourses.add(course);
            course.addStudent(this);
            System.out.println(getName() + " enrolled in course: " + course.getCourseName());
        } else {
            System.out.println(getName() + " is already enrolled in course: " + course.getCourseName());
        }
    }

    public void viewEnrolledCourses() {
        System.out.println("Student: " + getName() + " - Enrolled Courses:");
        for (Course c : enrolledCourses) {
            System.out.println(" - " + c.getCourseName() + " (Professor: " + c.getProfessor().getName() + ")");
        }
    }


    public void viewGrades() {
        System.out.println("Grades for " + getName() + ":");
        for (int i = 0; i < enrolledCourses.size(); i++) {
            String courseName = enrolledCourses.get(i).getCourseName();
            String gradeStr = (i < grades.size()) ? String.valueOf(grades.get(i)) : "N/A";
            System.out.println(" - " + courseName + ": " + gradeStr);
        }
    }

    @Override
    public void displayInfo() {
        System.out.println("Student ID: " + getId() +
                ", Name: " + getName() +
                ", Department: " + getDepartment() +
                ", Faculty: " + getFaculty());
    }
}