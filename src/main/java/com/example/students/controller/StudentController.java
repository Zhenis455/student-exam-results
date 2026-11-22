package com.example.students.controller;

import com.example.students.model.Student;
import com.example.students.service.StudentService;
import com.example.students.validation.StudentForm;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping
public class StudentController {
    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/students";
    }

    @GetMapping("/students")
    public String list(@RequestParam(defaultValue = "") String q, Model model) {
        String safeQuery = q.length() > 100 ? q.substring(0, 100) : q;
        model.addAttribute("students", service.search(safeQuery));
        model.addAttribute("q", safeQuery);
        model.addAttribute("total", service.count());
        model.addAttribute("passed", service.passedCount());
        model.addAttribute("failed", service.count() - service.passedCount());
        model.addAttribute("average", service.average());
        return "students/list";
    }

    @GetMapping("/students/new")
    public String createForm(Model model) {
        model.addAttribute("studentForm", new StudentForm());
        model.addAttribute("editing", false);
        return "students/form";
    }

    @PostMapping("/students")
    public String create(@Valid @ModelAttribute("studentForm") StudentForm form,
                         BindingResult errors, Model model, RedirectAttributes redirect) {
        if (errors.hasErrors()) {
            model.addAttribute("editing", false);
            return "students/form";
        }
        service.add(form.getName(), form.getSurname(), form.getExam());
        redirect.addFlashAttribute("success", "Студент добавлен, оценка рассчитана автоматически.");
        return "redirect:/students";
    }

    @GetMapping("/students/{id}/edit")
    public String editForm(@PathVariable long id, Model model) {
        Student student = service.get(id);
        if (student == null) throw notFound();
        StudentForm form = new StudentForm();
        form.setName(student.getName());
        form.setSurname(student.getSurname());
        form.setExam(student.getExam());
        model.addAttribute("studentForm", form);
        model.addAttribute("editing", true);
        model.addAttribute("studentId", id);
        return "students/form";
    }

    @PostMapping("/students/{id}")
    public String update(@PathVariable long id,
                         @Valid @ModelAttribute("studentForm") StudentForm form,
                         BindingResult errors, Model model, RedirectAttributes redirect) {
        if (service.get(id) == null) throw notFound();
        if (errors.hasErrors()) {
            model.addAttribute("editing", true);
            model.addAttribute("studentId", id);
            return "students/form";
        }
        service.update(id, form.getName(), form.getSurname(), form.getExam());
        redirect.addFlashAttribute("success", "Результат студента обновлён.");
        return "redirect:/students";
    }

    @PostMapping("/students/{id}/delete")
    public String delete(@PathVariable long id, RedirectAttributes redirect) {
        if (!service.delete(id)) throw notFound();
        redirect.addFlashAttribute("success", "Запись удалена.");
        return "redirect:/students";
    }

    private ResponseStatusException notFound() {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Студент не найден");
    }
}
