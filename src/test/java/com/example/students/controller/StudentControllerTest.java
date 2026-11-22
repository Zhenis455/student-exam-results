package com.example.students.controller;

import com.example.students.StudentsApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest(classes = StudentsApplication.class)
@AutoConfigureMockMvc
class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void listDisplaysHtmlAndGradeScale() throws Exception {
        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(view().name("students/list"))
                .andExpect(content().string(containsString("Student Exam Results")))
                .andExpect(content().string(containsString("90–100")));
    }

    @Test
    void createStudentAndRejectOutOfRange() throws Exception {
        mockMvc.perform(post("/students")
                        .param("name", "New")
                        .param("surname", "Student")
                        .param("exam", "75"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/students"));

        mockMvc.perform(get("/students").param("q", "New Student"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("New Student")));

        mockMvc.perform(post("/students")
                        .param("name", "Bad")
                        .param("surname", "Score")
                        .param("exam", "101"))
                .andExpect(status().isOk())
                .andExpect(view().name("students/form"))
                .andExpect(model().attributeHasFieldErrors("studentForm", "exam"));
    }
}
