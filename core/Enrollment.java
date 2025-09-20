package core;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class Enrollment {
    private String subjectName;
    private LocalDateTime enrollmentTime;
    private String grade;
    private Course course;

    private int midtermScore = 0;
    private int finalScore = 0;
    private int assignmentScore = 0;

    // TODO
    // function บวกคะแนนต่อจากคะแนนที่มี ล่าสุด ถ้าเป็นเกิน limit ก็ปรับให้เป้น Max

    public Enrollment(String subjectName, Course course) {
        this.subjectName = subjectName;
        this.enrollmentTime = LocalDateTime.now();

    }

    public Enrollment(String subjectName, LocalDateTime enrollmentTime, double midtermScore, double finalScore,
            double assignmentScore) {
        this.subjectName = subjectName;
        this.enrollmentTime = enrollmentTime != null ? enrollmentTime : LocalDateTime.now();

    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public LocalDateTime getEnrollmentTime() {
        return enrollmentTime;
    }

    public void setEnrollmentTime(LocalDateTime enrollmentTime) {
        this.enrollmentTime = enrollmentTime;
    }

    public double getMidtermScore() {
        return midtermScore;
    }

    public void setMidtermScore(int midtermScore) {
        validate(midtermScore);
        this.midtermScore = midtermScore;
    }

    public double getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(int finalScore) {
        validate(finalScore);
        this.finalScore = finalScore;
    }

    public double getAssignmentScore() {
        validate(assignmentScore);
        return assignmentScore;
    }

    public void setAssignmentScore(int assignmentScore) {
        this.assignmentScore = assignmentScore;
    }

    private void validate(double score) {
        if (score < 0 || score > 100) {
            throw new IllegalArgumentException("Score must be 0..100");
        }
    }

    public double computeWeightedScore() {
        return midtermScore * 0.30 + finalScore * 0.50 + assignmentScore * 0.20;
    }

    public String getLetterGrade() {
        double score = computeWeightedScore();
        if (score >= 80)
            return "A";
        if (score >= 75)
            return "B+";
        if (score >= 70)
            return "B";
        if (score >= 65)
            return "C+";
        if (score >= 60)
            return "C";
        if (score >= 55)
            return "D+";
        if (score >= 50)
            return "D";
        return "F";
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("Course: %s | Enrolled: %s | Midterm=%.2f Final=%.2f Assignment=%.2f | Total=%.2f (%s)",
                subjectName, enrollmentTime.format(fmt),
                midtermScore, finalScore, assignmentScore,
                computeWeightedScore(), getLetterGrade());
    }
}