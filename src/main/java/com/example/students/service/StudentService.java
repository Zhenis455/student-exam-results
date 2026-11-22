package com.example.students.service;

import com.example.students.model.Student;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/** Thread-safe in-memory store: no external database is needed. */
@Service
public class StudentService {
    private final Map<Long, Student> students = new LinkedHashMap<>();
    private final AtomicLong nextId = new AtomicLong(1);

    public StudentService() {
        // Demo data. Because this is an in-memory project, these return on restart.
        add("Amina", "Sadykova", 96);
        add("Daniel", "Kim", 84);
        add("Alina", "Petrova", 73);
        add("Timur", "Nurgali", 58);
        add("Miras", "Askar", 38);
        add("Sofia", "Lee", 91);
    }

    public synchronized Student add(String name, String surname, int exam) {
        String grade = GradeCalculator.fromScore(exam);
        Long id = nextId.getAndIncrement();
        Student student = new Student(id, name.trim(), surname.trim(), exam, grade);
        students.put(id, student);
        return student;
    }

    public synchronized Student update(long id, String name, String surname, int exam) {
        String grade = GradeCalculator.fromScore(exam);
        Student student = students.get(id);
        if (student == null) return null;
        student.setName(name.trim());
        student.setSurname(surname.trim());
        student.setExam(exam);
        student.setMark(grade);
        return student;
    }

    public synchronized Student get(long id) {
        return students.get(id);
    }

    public synchronized boolean delete(long id) {
        return students.remove(id) != null;
    }

    public synchronized List<Student> search(String query) {
        String term = query == null ? "" : query.strip().toLowerCase(Locale.ROOT);
        List<Student> result = new ArrayList<>();
        for (Student student : students.values()) {
            String fullName = (student.getName() + " " + student.getSurname())
                    .toLowerCase(Locale.ROOT);
            if (fullName.contains(term)
                    || student.getSurname().toLowerCase(Locale.ROOT).contains(term)
                    || student.getName().toLowerCase(Locale.ROOT).contains(term)) {
                result.add(student);
            }
        }
        return result;
    }

    public synchronized int count() {
        return students.size();
    }

    public synchronized long passedCount() {
        return students.values().stream().filter(s -> !"F".equals(s.getMark())).count();
    }

    public synchronized double average() {
        return students.values().stream().mapToInt(Student::getExam).average().orElse(0.0);
    }
}
