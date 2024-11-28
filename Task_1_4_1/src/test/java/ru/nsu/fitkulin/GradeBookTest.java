package ru.nsu.fitkulin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static ru.nsu.fitkulin.GradeEnum.EXCELLENT;
import static ru.nsu.fitkulin.GradeEnum.GOOD;
import static ru.nsu.fitkulin.GradeEnum.SATISFACTORY;
import static ru.nsu.fitkulin.AssessmentType.EXAM;
import static ru.nsu.fitkulin.AssessmentType.DIFFERENTIAL_CREDIT;


class GradeBookTest {
    private GradeBook gradeBook;

    @BeforeEach
    void setUp() {
        List<Grade> grades = new ArrayList<>();

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
        assertEquals(4.0, averageScore);
    }

    @Test
    void testCanTransferToBudget() {
        assertTrue(gradeBook.canTransferToBudget());
    }

    @Test
    void testRedDiplome() {
        boolean canGetRedDiploma = gradeBook.canGetRedDiploma();
        assertFalse(canGetRedDiploma);
    }
}