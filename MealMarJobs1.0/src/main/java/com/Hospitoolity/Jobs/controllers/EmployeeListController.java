package com.Hospitoolity.Jobs.controllers;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.Hospitoolity.Jobs.entities.Employee;
import com.Hospitoolity.Jobs.repositories.EmployeeRepository;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class EmployeeListController {

    private final EmployeeRepository employeeRepository;

    public EmployeeListController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("/employee-list")
    public String getEmployeeList(Model model) {
    	List<Employee> allEmployees = employeeRepository.findAll();
        List<Employee> employees = allEmployees.stream()
                .filter(employee -> !employee.isCvMovedNextStage())
                .collect(Collectors.toList());
        List<Employee> nextStageEmployees = allEmployees.stream()
                .filter(Employee::isCvMovedNextStage)
                .collect(Collectors.toList());
        employees.sort((emp1, emp2) -> Boolean.compare(emp1.isCvChecked(), emp2.isCvChecked()));
        model.addAttribute("employees", employees);
        model.addAttribute("nextStageEmployees", nextStageEmployees);
        return "employee-list";
    }
    
    @GetMapping("/edit-employee")
    public String editEmployee(@RequestParam("id") Long id, Model model) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee Id:" + id));
        model.addAttribute("employee", employee);
        return "edit-employee";
    }

    @PostMapping("/update-employee")
    public String updateEmployee(@RequestParam("id") Long id, Employee updatedEmployee) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee Id:" + id));
        employee.setName(updatedEmployee.getName());
        employee.setEmail(updatedEmployee.getEmail());
        employee.setPhoneNumber(updatedEmployee.getPhoneNumber());
        employee.setPosition(updatedEmployee.getPosition());
        employee.setCoverLetter(updatedEmployee.getCoverLetter());
        employee.setCvChecked(updatedEmployee.isCvChecked());
        employee.setAgreeToTerms(updatedEmployee.isAgreeToTerms());
        employeeRepository.save(employee);
        return "redirect:/employee-list";
    }

    @GetMapping("/delete-employee")
    public String deleteEmployee(@RequestParam("id") Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee Id:" + id));
        employeeRepository.delete(employee);
        return "redirect:/employee-list";
    }
    
    @GetMapping("/check-cv")
    public String checkCv(@RequestParam("id") Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee Id:" + id));
        employee.setCvChecked(true);
        employeeRepository.save(employee);
        return "redirect:/employee-list";
    }
    @GetMapping("/download-cover-letter/{employeeId}")
    public void downloadCoverLetter(@PathVariable Long employeeId, HttpServletResponse response) throws IOException {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new IllegalArgumentException("Employee not found with ID: " + employeeId));

        String coverLetter = employee.getCoverLetter();
        if (coverLetter == null || coverLetter.isEmpty()) {
            throw new IllegalArgumentException("No cover letter found for employee with ID: " + employeeId);
        }

        response.setContentType("text/plain");
        response.setHeader("Content-Disposition", "attachment; filename=cover_letter_" + employeeId + ".txt");

        try (InputStream is = new ByteArrayInputStream(coverLetter.getBytes(StandardCharsets.UTF_8));
             OutputStream os = response.getOutputStream()) {

            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        }
    }
    
    @GetMapping("/x")
    public String showHomePage(Model model) {
        List<Employee> employees = employeeRepository.findAll();
        List<Employee> nextStageEmployees = employees.stream()
                .filter(Employee::isCvMovedNextStage)
                .collect(Collectors.toList());
        model.addAttribute("employees", employees);
        model.addAttribute("nextStageEmployees", nextStageEmployees);
        return "index";
    }
    
    @GetMapping("/move-to-next-stage")
    public String moveToNextStage(@RequestParam("id") Long id, Model model) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee Id:" + id));
        employee.setCvMovedNextStage(true);
        employeeRepository.save(employee);
        return "redirect:/employee-list";
    }
}