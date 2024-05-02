// package com.woo.boardback.filter;

// import java.io.IOException;

// import org.springframework.core.Ordered;
// import org.springframework.core.annotation.Order;
// import org.springframework.stereotype.Component;

// import jakarta.servlet.FilterChain;
// import jakarta.servlet.FilterConfig;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.ServletRequest;
// import jakarta.servlet.ServletResponse;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;

// @Component
// @Order(Ordered.HIGHEST_PRECEDENCE)
// public class CorsFilter {
//     public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
//         throws IOException, ServletException
//     {
//         HttpServletResponse response = (HttpServletResponse) res;
//         HttpServletRequest request = (HttpServletRequest) req;
        
//         // 강제로 CORS 헤더 추가
//         response.setHeader("Access-Control-Allow-Origin", "*");
//         response.setHeader("Access-Control-Allow-Credentials", "true");
//         response.setHeader("Access-Control-Allow-Methods", "*");
//         response.setHeader("Access-Control-Max-Age", "3600");
//         response.setHeader("Access-Control-Allow-Headers",
//             "Origin, X-Requested-With, Content-Type, Accept, Key, Authorization");
 
//         if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
//             response.setStatus(HttpServletResponse.SC_OK);
//         } else {
//             chain.doFilter(req, res);
//         }
//     }
 
//     public void init(FilterConfig filterConfig) {
//         // not needed
//     }
 
//     public void destroy() {
//         //not needed
//     }
// }
