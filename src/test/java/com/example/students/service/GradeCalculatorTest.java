package com.example.students.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GradeCalculatorTest {
    @Test
    void allGradeBoundariesAreCorrect() {
        assertEquals("F", GradeCalculator.fromScore(0));
        assertEquals("F", GradeCalculator.fromScore(49));
        assertEquals("D", GradeCalculator.fromScore(50));
        assertEquals("D", GradeCalculator.fromScore(59));
        assertEquals("C", GradeCalculator.fromScore(60));
        assertEquals("C", GradeCalculator.fromScore(74));
        assertEquals("B", GradeCalculator.fromScore(75));
        assertEquals("B", GradeCalculator.fromScore(89));
        assertEquals("A", GradeCalculator.fromScore(90));
        assertEquals("A", GradeCalculator.fromScore(100));
    }

    @Test
    void rejectsOutOfRangeScores() {
        assertThrows(IllegalArgumentException.class, () -> GradeCalculator.fromScore(-1));
        assertThrows(IllegalArgumentException.class, () -> GradeCalculator.fromScore(101));
    }
}
