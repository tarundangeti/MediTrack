package com.clinicdata.cdms.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            switch (authority.getAuthority()) {
                case "ADMIN":
                    response.sendRedirect("/admin/dashboard");
                    return;
                case "DOCTOR":
                    response.sendRedirect("/doctor/dashboard");
                    return;
                case "DATA_ENTRY":
                    response.sendRedirect("/data-entry/dashboard");
                    return;
            }
        }
        response.sendRedirect("/login?error=role");
    }
}
