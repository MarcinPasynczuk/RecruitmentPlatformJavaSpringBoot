package com.Hospitoolity.Jobs;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.Hospitoolity.Jobs.repositories.EmployeeRepository;
import com.Hospitoolity.Jobs.security.SpringSecurityConfiguration;
import com.Hospitoolity.Jobs.service.EmployeeService;

@SpringBootTest
class JobsApplicationTests {

	@Test
	void contextLoads() {
	}

	private SpringSecurityConfiguration springSecurityConfiguration;
	private EmployeeService employeeService;
	private EmployeeRepository employeeRepositoryMock;

	@Test
	public void testEmployeeExistsReturnsTrueWhenEmployeeExists() {
	    String name = "John";
	    String email = "john@example.com";
	    String phoneNumber = "1234567890";
	    
	    when(employeeRepositoryMock.existsByNameOrEmailOrPhoneNumber(name, email, phoneNumber)).thenReturn(true);
	    
	    assertTrue(employeeService.employeeExists(name, email, phoneNumber));
	}

	@Test
	public void testEmployeeExistsReturnsFalseWhenEmployeeDoesNotExist() {
	    String name = "John";
	    String email = "john@example.com";
	    String phoneNumber = "1234567890";
	    
	    when(employeeRepositoryMock.existsByNameOrEmailOrPhoneNumber(name, email, phoneNumber)).thenReturn(false);
	    
	    assertFalse(employeeService.employeeExists(name, email, phoneNumber));
	}
	

@Before(value = "")
public void setUp() {
    springSecurityConfiguration = new SpringSecurityConfiguration();
}

@Test
public void testCreateUserDetailsManagerReturnsInMemoryUserDetailsManager() {
    assertNotNull(springSecurityConfiguration.createUserDetailsManager());
    assertTrue(springSecurityConfiguration.createUserDetailsManager() instanceof InMemoryUserDetailsManager);
}

@Test
public void testCreateNewUserReturnsUserDetails() {
    String username = "test";
    String password = "password";
    UserDetails userDetails = springSecurityConfiguration.createNewUser(password, username);
    
    assertNotNull(userDetails);
    assertEquals(username, userDetails.getUsername());
    assertEquals(password, userDetails.getPassword());
    assertTrue(userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("USER")));
    assertTrue(userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN")));
}

@Test
public void testPasswordEncoderReturnsBCryptPasswordEncoder() {
    assertTrue(springSecurityConfiguration.passwordEncoder() instanceof BCryptPasswordEncoder);
}

@Test
public void testFilterChainReturnsSecurityFilterChain() throws Exception {
    HttpSecurity http = mock(HttpSecurity.class);
    SecurityFilterChain filterChain = springSecurityConfiguration.filterChain(http);
    
    assertNotNull(filterChain);
    assertTrue(filterChain instanceof SecurityFilterChain);
}

@Test
public void testRateLimitFilterReturnsFilterRegistrationBean() {
    assertNotNull(springSecurityConfiguration.rateLimitFilter());
    assertTrue(springSecurityConfiguration.rateLimitFilter() instanceof FilterRegistrationBean);
}
	
}
