package ru.nsu.fitkulin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static ru.nsu.fitkulin.AssessmentType.DIFFERENTIAL_CREDIT;
import static ru.nsu.fitkulin.AssessmentType.EXAM;
import static ru.nsu.fitkulin.GradeEnum.EXCELLENT;
import static ru.nsu.fitkulin.GradeEnum.GOOD;
import static ru.nsu.fitkulin.GradeEnum.SATISFACTORY;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



class GradeBookTest {
    private GradeBook gradeBook;

    List<Grade> grades = new ArrayList<>();
    @BeforeEach
    void setUp() {
        grades.add(new Grade(GOOD, 1, "12-01-2024",
                "Matlog", "Palch", EXAM));
        grades.add(new Grade(EXCELLENT, 1, "17-01-2024",
                "Matan", "Ters", EXAM));
        grades.add(new Grade(GOOD, 3, "16-01-2024",
                "OS", "Papa", EXAM));
        grades.add(new Grade(SATISFACTORY, 2, "25-05-2024",
                "Imperativka", "TV", DIFFERENTIAL_CREDIT));
        grades.add(new Grade(EXCELLENT, 4, "20-06-2025",
                "OOP", "AA", EXAM));

        gradeBook = new GradeBook(grades, FormOfStudy.PAID, EXCELLENT);
    }

    @Test
    void testCalculateAverageScore() {
        double averageScore = gradeBook.calculateAverageScore();
        assertEquals(4.2, averageScore);
    }

    @Test
    void testBudget() {
        assertTrue(gradeBook.canTransferToBudget());
        gradeBook.addGrade(new Grade(SATISFACTORY, 4, "20-06-2025",
                "Modeli....", "AA", EXAM));
        assertFalse(gradeBook.canTransferToBudget());
    }

    @Test
    void testRedDiplome() {
        assertFalse(gradeBook.canGetRedDiploma());
    }

    @Test
    void testIncreasedScholarship() {
        assertTrue(gradeBook.increasedScholarship());
        gradeBook.addGrade(new Grade(GOOD, 5, "20-06-2025",
                "aaaaa ny TV&MS", "AA", EXAM));
        assertFalse(gradeBook.increasedScholarship());
    }

    @Test
    void testToString() {
        String expected = "Grade Book:\n"
                + "Form of Study: PAID\n"
                + "Qualifying Work Grade: EXCELLENT\n"
                + "Grades:\n"
                + "---------------------------------\n"
                + "  Subject: Matlog\n"
                + "  Grade: GOOD\n"
                + "  Semester: 1\n"
                + "  Date: 12-01-2024\n"
                + "  Teacher: Palch\n"
                + "  Assessment Type: EXAM\n"
                + "---------------------------------\n"
                + "  Subject: Matan\n"
                + "  Grade: EXCELLENT\n"
                + "  Semester: 1\n"
                + "  Date: 17-01-2024\n"
                + "  Teacher: Ters\n"
                + "  Assessment Type: EXAM\n"
                + "---------------------------------\n"
                + "  Subject: OS\n"
                + "  Grade: GOOD\n"
                + "  Semester: 3\n"
                + "  Date: 16-01-2024\n"
                + "  Teacher: Papa\n"
                + "  Assessment Type: EXAM\n"
                + "---------------------------------\n"
                + "  Subject: Imperativka\n"
                + "  Grade: SATISFACTORY\n"
                + "  Semester: 2\n"
                + "  Date: 25-05-2024\n"
                + "  Teacher: TV\n"
                + "  Assessment Type: DIFFERENTIAL_CREDIT\n"
                + "---------------------------------\n"
                + "  Subject: OOP\n"
                + "  Grade: EXCELLENT\n"
                + "  Semester: 4\n"
                + "  Date: 20-06-2025\n"
                + "  Teacher: AA\n"
                + "  Assessment Type: EXAM\n"
                + "---------------------------------\n";

        assertEquals(expected, gradeBook.toString());
    }
}