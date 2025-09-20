class Course {
    private int CourseID;
    private String CourseName;
    private double maxGPA;

    public Course(int CourseID, String CourseName) {
        this.CourseID = CourseID;
        this.CourseName = CourseName;
    }

    public void setCourseID(int CourseID) {
        this.CourseID = CourseID;
    }

    public int getCourseID() {
        return CourseID;

    }

    public void setCourseName(String CourseName) {
        this.CourseName = CourseName;
    }

    public String getCourseName() {
        return CourseName;

    }

    public double calculateMaxGPA(double[] gpas) {
        double max = 0.0;
        for (double gpa : gpas) {
            if (gpa > max) {
                max = gpa;
            }
        }
        this.maxGPA = max;
        return max;
    }

    public double getMaxGPA() {
        return maxGPA;
    }
}

