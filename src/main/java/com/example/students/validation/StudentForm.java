package com.example.students.validation;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class StudentForm {
    @NotBlank(message = "Введите имя")
    @Size(max = 80, message = "Имя должно быть короче 81 символа")
    private String name;

    @NotBlank(message = "Введите фамилию")
    @Size(max = 80, message = "Фамилия должна быть короче 81 символа")
    private String surname;

    @NotNull(message = "Введите балл экзамена")
    @Min(value = 0, message = "Минимальный балл — 0")
    @Max(value = 100, message = "Максимальный балл — 100")
    private Integer exam;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }
    public Integer getExam() { return exam; }
    public void setExam(Integer exam) { this.exam = exam; }
}
