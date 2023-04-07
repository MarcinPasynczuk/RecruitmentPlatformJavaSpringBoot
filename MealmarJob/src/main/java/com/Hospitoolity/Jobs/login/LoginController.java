package com.Hospitoolity.Jobs.login;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("name")
public class LoginController {

	@GetMapping(value="/")
	public String gotoWelcomePage(ModelMap model) {
		model.put("name", getLoggedinUsername());
		return "welcome";
	}
	
	@GetMapping(value="/contactus")
	public String gotoContactUs(ModelMap model) {
		model.put("name", getLoggedinUsername());
		return "contactus";
	}
	
	@GetMapping(value="/thanksmail")
	public String gotoSendMail(ModelMap model) {
		model.put("name", getLoggedinUsername());
		return "thanksmail";
	}
	
	@GetMapping(value="/jobs")
	public String gotoJobsPage(ModelMap model) {
		model.put("name", getLoggedinUsername());
		return "jobs";
	}
	
	@GetMapping(value="/checkcv")
	public String gotoCheckCv(ModelMap model) {
		model.put("name", getLoggedinUsername());
		return "checkcv";
	}
	
	@GetMapping(value="/getjob")
	public String gotoGetJob(ModelMap model) {
		model.put("name", getLoggedinUsername());
		return "getjob";
	}
	
	
	@GetMapping(value="/secondtimeapplication")
	public String gotosecondtimeapplication(ModelMap model) {
		model.put("name", getLoggedinUsername());
		return "secondtimeapplication";
	}
	private String getLoggedinUsername() {
		Authentication authentication = 
				SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}
}