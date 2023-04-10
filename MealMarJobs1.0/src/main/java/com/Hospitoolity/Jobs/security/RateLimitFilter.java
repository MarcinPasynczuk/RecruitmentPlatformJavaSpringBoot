package com.Hospitoolity.Jobs.security;

import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class RateLimitFilter extends OncePerRequestFilter {

    private final int MAX_REQUESTS_PER_IP = 10; 
    private final ConcurrentMap<String, AtomicInteger> requestCounts = new ConcurrentHashMap<>();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String ipAddress = getClientIP(request);

        if (isAllowedRequest(ipAddress)) {
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().write("Too many requests.");
        }
    }

    private boolean isAllowedRequest(String ipAddress) {
        AtomicInteger count = requestCounts.computeIfAbsent(ipAddress, key -> new AtomicInteger(0));

        if (count.incrementAndGet() <= MAX_REQUESTS_PER_IP) {
            return true;
        }

        count.decrementAndGet();
        return false;
    }

    private String getClientIP(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");

        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }

        return ipAddress;
    }

	
}
