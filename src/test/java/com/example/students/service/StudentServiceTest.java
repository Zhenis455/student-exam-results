package com.example.students.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StudentServiceTest {
    @Test
    void addSearchUpdateDelete() {
        StudentService service = new StudentService();
        int initialCount = service.count();
        var student = service.add("  Aigerim ", "Testova", 49);
        assertEquals(initialCount + 1, service.count());
        assertEquals("Aigerim", student.getName());
        assertEquals("F", student.getMark());
        assertEquals(1, service.search("aigerim").size());
        assertEquals(1, service.search("TESTOVA").size());
        assertEquals(1, service.search("aigerim testova").size());
        service.update(student.getId(), "Aigerim", "Testova", 90);
        assertEquals("A", service.get(student.getId()).getMark());
        assertTrue(service.delete(student.getId()));
        assertFalse(service.delete(student.getId()));
        assertEquals(initialCount, service.count());
    }
}
