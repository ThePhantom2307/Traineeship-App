package myy803.traineeship.config;

import java.util.Collection;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class CustomSecuritySuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	@Override
	protected void handle(HttpServletRequest request,
						  HttpServletResponse response,
						  Authentication authentication) throws java.io.IOException {
		String targetUrl = determineTargetUrl(authentication);
		if (response.isCommitted()) return;
		
		RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
        redirectStrategy.sendRedirect(request, response, targetUrl);
	}
	
	protected String determineTargetUrl(Authentication authentication) {
	    String targetUrl = "/login?error=true";
	    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
	    
	    for (GrantedAuthority authority : authorities) {
	        String role = authority.getAuthority();
	        if ("STUDENT".equals(role)) {
	            targetUrl = "/student/dashboard";
	            break;
	        } else if ("PROFESSOR".equals(role)) {
	            targetUrl = "/professor/dashboard";
	            break;
	        } else if ("COMPANY".equals(role)) {
	            targetUrl = "/company/dashboard";
	            break;
	        } else if ("TRAINEESHIP_COMMITEE_MEMBER".equals(role)) {
	            targetUrl = "/trainee_commitee/dashboard";
	            break;
	        }
	    }
	    
	    return targetUrl;
	}
}
