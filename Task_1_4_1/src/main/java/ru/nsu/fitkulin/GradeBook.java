package ru.nsu.fitkulin;

import java.util.List;

/**
 * class for storing grade book.
 */
public class GradeBook {
    private List<Grade> grades;
    private FormOfStudy formOfStudy;
    private GradeEnum qualifyingWorkGrade;

    /**
     * constructor.
    */
    public GradeBook(List<Grade> grades, FormOfStudy formOfStudy, GradeEnum qualifyingWorkGrade) {
        this.grades = grades;
        this.formOfStudy = formOfStudy;
        this.qualifyingWorkGrade = qualifyingWorkGrade;
    }

    /**
     * method for add new grade.
     */
    public void addGrade(Grade newGrade) {
        if (newGrade.getGrade().getValue() < 2 || newGrade.getGrade().getValue() > 5
                || newGrade.getSem() > 8 || newGrade.getSem() < 1) {
            throw new IllegalArgumentException("Invalid grade or semester value");
        }
        grades.add(newGrade);
    }

    /**
     * calculates the average of all grades.
     *
     * @return avg of grades
     */
    public double calculateAverageScore() {
        return grades.stream()
                .mapToInt(g -> g.getGrade().getValue())
                .average()
                .orElse(0.0);
    }

    /**
     * check checking for the possibility of transferring to a budget.
     *
     * @return truth if possible, otherwise - false.
     */
    public boolean canTransferToBudget() {
        if (formOfStudy != FormOfStudy.PAID) {
            return false;
        }

        List<Integer> examSemesters = grades.stream()
                .filter(g -> g.getAssessmentType() == AssessmentType.EXAM)
                .map(Grade::getSem)
                .distinct()
                .sorted()
                .toList();

        if (examSemesters.size() < 2) {
            return false;
        }

        int secondLastExamSemester = examSemesters.get(examSemesters.size() - 2);

        boolean hasSatisfactory = grades.stream()
                .filter(g -> g.getAssessmentType() == AssessmentType.EXAM)
                .filter(g -> g.getSem() >= secondLastExamSemester)
                .anyMatch(g -> g.getGrade() == GradeEnum.SATISFACTORY);

        return !hasSatisfactory;
    }

    /**
     * checking the possibility of receiving a red diploma.
     *
     * @return truth if possible, otherwise - false.
     */
    public boolean canGetRedDiploma() {
        long excellentCount = grades.stream()
                .filter(g -> g.getGrade() == GradeEnum.EXCELLENT)
                .count();

        boolean noSatisfactory = grades.stream()
                .noneMatch(g -> g.getGrade() == GradeEnum.SATISFACTORY);

        return (excellentCount / (double) grades.size()) >= 0.75
                && noSatisfactory && qualifyingWorkGrade == GradeEnum.EXCELLENT;
    }

    /**
     * checking the possibility of receiving an increased scholarship.
     *
     * @return truth if possible, otherwise - false.
     */
    public boolean increasedScholarship() {
        int currentSemester = grades.stream()
                .mapToInt(Grade::getSem)
                .max()
                .orElse(0);

        List<Grade> currentExamGrades = grades.stream()
                .filter(g -> g.getSem() == currentSemester
                        && g.getAssessmentType() == AssessmentType.EXAM)
                .toList();

        if (currentExamGrades.isEmpty()) {
            return true;
        }

        return currentExamGrades.stream()
                .allMatch(g -> g.getGrade() == GradeEnum.EXCELLENT);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Grade Book:\n");
        sb.append("Form of Study: ").append(formOfStudy).append("\n");
        sb.append("Qualifying Work Grade: ").append(qualifyingWorkGrade).append("\n");
        sb.append("Grades:\n");
        sb.append("---------------------------------\n");

        for (Grade grade : grades) {
            sb.append("  Subject: ").append(grade.getSubject()).append("\n");
            sb.append("  Grade: ").append(grade.getGrade()).append("\n");
            sb.append("  Semester: ").append(grade.getSem()).append("\n");
            sb.append("  Date: ").append(grade.getDate()).append("\n");
            sb.append("  Teacher: ").append(grade.getTeacher()).append("\n");
            sb.append("  Assessment Type: ").append(grade.getAssessmentType()).append("\n");
            sb.append("---------------------------------\n");
        }

        return sb.toString();
    }

}