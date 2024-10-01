package com.spring_jwt.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring_jwt.entity.ManajementAksesEntity;
import com.spring_jwt.entity.RoleEntity;
import com.spring_jwt.service.ManajementAksesService;
import com.spring_jwt.service.RoleService;
import com.spring_jwt.service.UserDetailsServiceImpl;
import com.spring_jwt.utility.JwtUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticatedFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private ManajementAksesService manajementAksesService;

    @Autowired
    private RoleService roleService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
   
        try {
            
            if(request.getRequestURI().startsWith("/auth")) {
                filterChain.doFilter(request, response);
                return;
            }

            String authorizationHeader  = request.getHeader("Authorization");
            if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer")) { 
                sendJsonError(response, 401, "Missing Authorization Header");
                return;
            }
            
            String token = authorizationHeader.substring(7);
            Claims claims = jwtUtil.extractAllClaims(token);
            String username = claims.get("username", String.class);
            String role = claims.get("role", String.class);

            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) { 
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if(jwtUtil.validatedToken(username, userDetails)) {
                    SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role);
                    UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(claims, null, List.of(simpleGrantedAuthority));
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
                else {
                    sendJsonError(response, 401, "Invalid Token");
                    return;
                }
            }
            else {
                sendJsonError(response, 401, "Invalid Token, Username not found");
                return;
            }

            Optional<RoleEntity> findRole = roleService.findByRole(role);
            if(!findRole.isPresent()) {
                sendJsonError(response, 401, "Role Tidak Ditemukan");
                return;
            }

            Optional<ManajementAksesEntity> findAkses = manajementAksesService.findAuthorization(role, request.getRequestURI());
            if(!findAkses.isPresent()) {
                sendJsonError(response, 403, "Anda tidak diberikan izin untuk akses uri ini");
                return;
            }
  
        } 
        catch (ExpiredJwtException e) {
            e.printStackTrace();
            sendJsonError(response, 401, "Token Expired");
            return;
        }
        catch (JwtException e) {
            e.printStackTrace();
            sendJsonError(response, 401, e.getMessage());
            return;
        }
        catch (Exception e) {
            e.printStackTrace();
            sendJsonError(response, 500, e.getMessage());
            return;
        }       
        filterChain.doFilter(request, response);
    }

    private void sendJsonError(HttpServletResponse response, int status, String message) throws IOException {
        response.setContentType("application/json");
        response.setStatus(status);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("message", message);
        errorResponse.put("status", "failed");
        errorResponse.put("code", status);
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
    
}
 