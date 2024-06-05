package com.arise.pharmacy.security.jwt;

import com.arise.pharmacy.security.user.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestHeader = request.getHeader("Authentication");

        if(requestHeader == null || !requestHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }

        String token = requestHeader.substring(7);
        String subject = jwtService.getSubject(token);

        if(subject != null){

            User user = (User) userDetailsService.loadUserByUsername(subject);

            if(jwtService.isValidToken(token)){

                var authToken = UsernamePasswordAuthenticationToken.authenticated(
                        user.getUsername(),
                        null,
                        user.getAuthorities()
                );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

            filterChain.doFilter(request,response);
        }

    }
}