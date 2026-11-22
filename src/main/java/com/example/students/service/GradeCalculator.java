package com.example.students.service;

/** The grading scale from the October 2025 Student Exam Results project. */
public final class GradeCalculator {
    private GradeCalculator() { }

    public static String fromScore(int score) {
        if (score < 0 || score > 100) {
            throw new IllegalArgumentException("Exam score must be between 0 and 100");
        }
        if (score >= 90) return "A";
        if (score >= 75) return "B";
        if (score >= 60) return "C";
        if (score >= 50) return "D";
        return "F";
    }
}
