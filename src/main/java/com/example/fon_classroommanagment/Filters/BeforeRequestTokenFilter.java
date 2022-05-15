package com.example.fon_classroommanagment.Filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static com.example.fon_classroommanagment.Configuration.Constants.SECRET;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class BeforeRequestTokenFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    List<String> routesAllowed= Arrays.asList("/login","/register","/logout");
        if(routesAllowed.contains(request.getServletPath())) filterChain.doFilter(request,response);
        else{
            String authHeader=request.getHeader(AUTHORIZATION);
            System.out.println(authHeader);
            if(authHeader!=null && authHeader.startsWith("Bearer ")){
                String token=authHeader.substring("Bearer ".length());
                Algorithm algorithm=Algorithm.HMAC256(SECRET.getBytes());
                JWTVerifier verifier= JWT.require(algorithm).build();
                DecodedJWT decodedJWT=verifier.verify(token);
                String username=decodedJWT.getSubject();
                List<SimpleGrantedAuthority> authorities=new LinkedList<>();
                String[] tempAuthorities=decodedJWT.getClaim("roles").asArray(String.class);
                Arrays.stream(tempAuthorities).forEach(x-> authorities.add(new SimpleGrantedAuthority(x)));
                UsernamePasswordAuthenticationToken token1=new UsernamePasswordAuthenticationToken(username,null,authorities);
                SecurityContextHolder.getContext().setAuthentication(token1);
                filterChain.doFilter(request,response);
            }
        }
    }
}
