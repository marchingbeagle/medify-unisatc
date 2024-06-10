package com.satc.medify.controller;

import com.satc.medify.model.Student.Student;
import com.satc.medify.model.Student.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping()
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    private final String PROJECT_NAME = "Medify";
    private final String PROJECT_THEME = "Saúde e Bem-estar: Gerenciamento de medicamentos, mapeamento de atendimentos médicos, recomendações médicas;";

    @GetMapping("/ajuda")
    public Object getHelp() {
        List<String> students = studentRepository.findAll().stream()
                .map(Student::getName)
                .collect(Collectors.toList());

        return new Object() {
            public List<String> estudantes = students;
            public String projeto = PROJECT_NAME;
            public String tema = PROJECT_THEME;
        };
    }
}