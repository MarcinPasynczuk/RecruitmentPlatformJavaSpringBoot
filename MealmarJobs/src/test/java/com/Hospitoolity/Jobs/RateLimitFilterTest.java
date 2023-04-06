package com.Hospitoolity.Jobs;
mport static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import static org.junit.jupiter.api.Assertions.*;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;

import com.Hospitoolity.Jobs.security.RateLimitFilter;

class RateLimitFilterTest {

	private RateLimitFilter rateLimitFilter;

	@Before(value = "")
	public void setUp() {
	    rateLimitFilter = new RateLimitFilter();
	}

	@Test
	public void testDoFilterInternalCallsFilterChainDoFilterWhenRequestIsAllowed() throws ServletException, IOException {
	    String ipAddress = "127.0.0.1";
	    
	    HttpServletRequest request = mock(HttpServletRequest.class);
	    HttpServletResponse response = mock(HttpServletResponse.class);
	    FilterChain filterChain = mock(FilterChain.class);
	    
	    when(request.getHeader("X-Forwarded-For")).thenReturn(null);
	    when(request.getRemoteAddr()).thenReturn(ipAddress);
	    
	    assertTrue(rateLimitFilter.isAllowedRequest(ipAddress));
	    
	    rateLimitFilter.doFilterInternal(request, response, filterChain);
	    
	    assertFalse(rateLimitFilter.requestCounts.isEmpty());
	    assertEquals(rateLimitFilter.requestCounts.get(ipAddress).get(), 1);
	    assertTrue(rateLimitFilter.isAllowedRequest(ipAddress));
	    
	    verify(filterChain).doFilter(request, response);
	}

	@Test
	public void testDoFilterInternalSetsResponseStatusToTooManyRequestsWhenRequestIsNotAllowed() throws ServletException, IOException {
	    String ipAddress = "127.0.0.1";
	    
	    HttpServletRequest request = mock(HttpServletRequest.class);
	    HttpServletResponse response = mock(HttpServletResponse.class);
	    FilterChain filterChain = mock(FilterChain.class);
	    
	    when(request.getHeader("X-Forwarded-For")).thenReturn(null);
	    when(request.getRemoteAddr()).thenReturn(ipAddress);
	    
	    for (int i = 0; i < 10; i++) {
	        assertTrue(rateLimitFilter.isAllowedRequest(ipAddress));
	    }
	    assertFalse(rateLimitFilter.isAllowedRequest(ipAddress));
	    
	    rateLimitFilter.doFilterInternal(request, response, filterChain);
	    
	    assertFalse(rateLimitFilter.requestCounts.isEmpty());
	    assertEquals(rateLimitFilter.requestCounts.get(ipAddress).get(), 10);
	    
	    verify(response).setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
	    verify(response.getWriter()).write("Too many requests.");
	}
	}