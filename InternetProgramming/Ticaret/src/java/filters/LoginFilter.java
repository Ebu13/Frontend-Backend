package filters;

import beans.LoginBean;
import jakarta.inject.Inject;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "LoginFilter", urlPatterns = {"/faces/admin/*", "/faces/domesticsupplier/*", "/faces/foreignbuyer/*"})
public class LoginFilter implements Filter {

    @Inject
    private LoginBean loginBean;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // No initialization needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String contextPath = httpRequest.getContextPath();
        String requestURI = httpRequest.getRequestURI();

        // Check if the user is logged in
        if (loginBean == null || loginBean.getLoggedInUser() == null) {
            // Redirect to login page if user is not logged in
            httpRequest.getRequestDispatcher(contextPath + "/faces/login/index.xhtml").forward(request, response);
        } else {
            // Check role and redirect accordingly
            String userType = loginBean.getLoggedInUser().getUserType();
            if (requestURI.contains("/admin/") && !"Admin".equals(userType)) {
                httpRequest.getRequestDispatcher(contextPath + "/faces/login/index.xhtml").forward(request, response);
            } else if (requestURI.contains("/domesticsupplier/") && !"DomesticSupplier".equals(userType)) {
                httpRequest.getRequestDispatcher(contextPath + "/faces/login/index.xhtml").forward(request, response);
            } else if (requestURI.contains("/foreignbuyer/") && !"ForeignBuyer".equals(userType)) {
                httpRequest.getRequestDispatcher(contextPath + "/faces/login/index.xhtml").forward(request, response);
            } else {
                // User has appropriate role, continue with the request
                chain.doFilter(request, response);
            }
        }
    }

    @Override
    public void destroy() {
        // No cleanup needed
    }
}
