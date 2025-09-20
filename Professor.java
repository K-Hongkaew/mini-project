class Professor extends User {
    private String subject;

    public Professor(String id, String password, String name , String department , String faculty,  String subject) {
        super(id,password, name,department ,faculty);
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void viewStudentsInCourse(Course course) {
        System.out.println("Professor " + getName() + " | Course: " + course.getCourseName());
        for (Student s : course.getStudents()) {
            System.out.println("Student: " + s.getName());
        }
    }
    
    @Override
    public String getUserInfo() {
        return "Professor: " + getName() + 
               " (ID: " + getId() + 
               ", Department: " + getDepartment() +
               ", Faculty: " + getFaculty() +
               ", Subject: " + subject + ")";
    }
    
}

