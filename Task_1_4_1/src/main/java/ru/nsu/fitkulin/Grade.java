package ru.nsu.fitkulin;

/**
 * class fot holding all info about one grade from GradBook.
 */
public class Grade {
    private GradeEnum grade;
    private int semester;
    private String date;
    private String subject;
    private String teacher;
    private AssessmentType assessmentType;

    /**
     * constructor.
     */
    public Grade(GradeEnum grade, int semester, String date,
                 String subject, String teacher, AssessmentType assessmentType) {
        this.grade = grade;
        this.semester = semester;
        this.date = date;
        this.subject = subject;
        this.teacher = teacher;
        this.assessmentType = assessmentType;
    }

    public GradeEnum getGrade() {
        return grade;
    }

    public int getSem() {
        return semester;
    }

    public AssessmentType getAssessmentType() {
        return assessmentType;
    }

    public String getDate() {
        return date;
    }

    public String getSubject() {
        return subject;
    }

    public String getTeacher() {
        return teacher;
    }
}