package app.core.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;

import app.core.jwt.JwtUtil;

public class LoginFilter implements Filter {

	private JwtUtil jwt;

	public LoginFilter(JwtUtil jwt) {
		super();
		this.jwt = jwt;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;	
		String method = req.getMethod();	
		String token = req.getHeader("token");
		String email = req.getHeader("email");
		String acrh = req.getHeader("access-control-request-headers");
		String url = req.getRequestURI();
		if(url.contains("/login")){
			chain.doFilter(request, response);
			return;
		}
		if (token != null ) {
			if (!jwt.validateToken(token, email)) {
				res.sendError(HttpStatus.UNAUTHORIZED.value(), "you are not uthorized");
			} else {
				chain.doFilter(request, response);
			}
		} else {
			if(acrh != null && method.equals("OPTIONS")) {
		    	 res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
		    	 res.setHeader("Access-Control-Allow-Origin", "*");
		         res.setHeader("Access-Control-Allow-Headers", "*");
		         res.sendError(HttpStatus.OK.value(), "preflight");
		    } else {
		    	res.sendError(HttpStatus.UNAUTHORIZED.value(), "you are not logged in");
		    }
		}
	}
}
