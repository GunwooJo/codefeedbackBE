package cloudweb.codefeedbackBE.config;

import cloudweb.codefeedbackBE.dto.UserDTO2;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserDTO2 loggedInUser = (UserDTO2) request.getSession().getAttribute("loggedInUser");
        if (loggedInUser == null) {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("로그인이 필요합니다.");
            return false;
        }
        return true;
    }
}
