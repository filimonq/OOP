package ru.nsu.fitkulin;
import java.util.List;

/**
 * class for storing grade book.
 */
public class GradeBook {
    private List<Grade> grades;
    private FormOfStudy formOfStudy;
    private GradeEnum qualifyingWorkGrade;

    public GradeBook(List<Grade> grades, FormOfStudy formOfStudy, GradeEnum qualifyingWorkGrade) {
        this.grades = grades;
        this.formOfStudy = formOfStudy;
        this.qualifyingWorkGrade = qualifyingWorkGrade;
    }


    public void addGrade(Grade newGrade) {
        if (newGrade.getGrade().getValue() < 2 || newGrade.getGrade().getValue() > 5
                || newGrade.getSem() > 8 || newGrade.getSem() < 1) {
            throw new IllegalArgumentException("Invalid grade or semester value");
        }
        grades.add(newGrade);
    }

    public double calculateAverageScore() {
        return grades.stream()
                .mapToInt(g -> g.getGrade().getValue())
                .average()
                .orElse(0.0);
    }

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


    public boolean canGetRedDiploma() {
        long excellentCount = grades.stream()
                .filter(g -> g.getGrade() == GradeEnum.EXCELLENT)
                .count();

        boolean noSatisfactory = grades.stream()
                .noneMatch(g -> g.getGrade() == GradeEnum.SATISFACTORY);

        return (excellentCount / (double) grades.size()) >= 0.75
                && noSatisfactory && qualifyingWorkGrade == GradeEnum.EXCELLENT;
    }
}