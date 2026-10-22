package com.learnspringboot.store.security.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.learnspringboot.store.security.user.StoreUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final StoreUserDetailsService userDetailsService;

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String jwt = parseJwt(request);

        if (jwt != null && jwtUtils.validateToken(jwt)) {
            String username = jwtUtils.getUsernameFromToken(jwt);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null, // xác thực bằng JWT nên không cần password
                    userDetails.getAuthorities());

            // Set thông tin chi tiết của request vào authentication (để lưu trữ IP,
            // session)
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // Set authentication vào SecurityContextHolder
            // Từ đây có thể truy cập authentication từ bất kỳ đâu trong request
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);

    }

}

// Mỗi request đều phải đi qua AuthTokenFilter, nếu request có jwt thì sẽ
// validate token, nếu token hợp lệ thì sẽ set authentication vào
// SecurityContextHolder

// Sử dụng cơ chế ThreadLocal để lưu trữ authentication trong suốt quá trình xử
// lý request (sau khi set authentication vào SecurityContextHolder thì có thể
// truy cập authentication từ bất kỳ đâu trong request)
// Lưu ý: chỉ lưu trữ authentication trong request hiện tại, không lưu trữ qua
// các request khác (mỗi request là một thread khác nhau)
