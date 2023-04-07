package com.Hospitoolity.Jobs.controllers;

import com.Hospitoolity.Jobs.JobsApplication;
import com.Hospitoolity.Jobs.MailApi;
import com.Hospitoolity.Jobs.entities.Employee;
import com.Hospitoolity.Jobs.repositories.EmployeeRepository;
import com.Hospitoolity.Jobs.service.EmployeeService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.io.IOException;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private EmployeeService employeeService;
    
    @Autowired
    private MailApi mailapi;

    @PostMapping("/form")
    public String handleForm(@Valid @ModelAttribute("application") Employee application, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model, MultipartFile resume) throws IOException {
    if (bindingResult.hasErrors()) {

    model.addAttribute("errors", bindingResult.getFieldErrors());
    return "getjob";
    }
    if (employeeService.employeeExists(application.getName(), application.getEmail(), application.getPhoneNumber())) {
        model.addAttribute("errorMessage", "An application with the same name, email, or phone number already exists.");
        model.addAttribute("application", application);
        return "secondtimeapplication";
    }

    byte[] resumeData = null;
    if (!resume.isEmpty()) {
        resumeData = resume.getBytes();
    }

    Employee employee = new Employee(null, application.getName(), application.getEmail(), application.getPhoneNumber(), application.getPosition(), resumeData, application.getCoverLetter(), application.isAgreeToTerms(), application.isCvChecked());
    employeeRepository.save(employee);
    mailapi.sendConf(application.getEmail());
    redirectAttributes.addFlashAttribute("message", "Your application has been submitted successfully.");
    return "redirect:/checkcv";
    }
    
    @GetMapping("/download-resume/{id}")
    public ResponseEntity<byte[]> downloadResume(@PathVariable("id") Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee id: " + id));

        byte[] resumeData = employee.getResumeData();

        if (resumeData == null) {
            throw new IllegalArgumentException("No resume available for employee id: " + id);
        }

        String fileName = employee.getName().replaceAll("\\s+", "_") + "_Resume.pdf";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", fileName);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return ResponseEntity.ok()
                .headers(headers)
                .body(resumeData);
    }
}