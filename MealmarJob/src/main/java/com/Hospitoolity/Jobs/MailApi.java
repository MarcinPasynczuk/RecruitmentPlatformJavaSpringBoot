package com.Hospitoolity.Jobs;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Hospitoolity.Jobs.service.MailService;

import jakarta.mail.MessagingException;

@RestController
public class MailApi {

    private MailService mailService;

    public MailApi(MailService mailService) {
        this.mailService = mailService;
    }

    @GetMapping("/sendMail")
    public String sendMail(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("message") String message) throws MessagingException {

    	String to = "office@hospitoolity.com";
    	String subject = "New message from: " + name;
    	String content = "From: " + name + "<br>" +
    	"Email: " + email + "<br>" +
    	"Message: " + message;

    	mailService.sendMail(to, subject, content, true);

    	// Sending a confirmation email to the sender
    	String confirmationSubject = "Confirmation of message submission";
    	String confirmationContent = "Thank you for contacting Hospitoolity! Here is a copy of your message:<br><br>" +
    	content + "<br><br>" +
    	"We will respond to your message as soon as possible.";

    	mailService.sendMail(email, confirmationSubject, confirmationContent, true);

    	return "sent";
    }
    
    public String sendConf(String mail) {
            
    	String confirmationSubject = "Confirmation of CV Submission";
    	String confirmationContent = 
    			"Hello!"
    		+ "<br><br>" +
    					"Thank you for submitting your CV. We have successfully received your application, and we appreciate the time and effort you put into applying for the role."
    	+ "<br><br>" + 
    		"Our recruitment team will review your CV and evaluate your skills and experience. If your qualifications match our requirements, we will reach out to you to schedule an interview. Please note that our review process may take up to two weeks, depending on the number of applications we receive."			
    	+"<br><br>" +
    	"Best regards,"
    	 +"<br><br>" + 
    	"Mealmar Team";
    	
    	try {
			mailService.sendMail(mail, confirmationSubject, confirmationContent, true);
		} catch (MessagingException e) {
			
			e.printStackTrace();
		}

    	return "sent";
    }
}