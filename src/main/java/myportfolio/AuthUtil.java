package myportfolio;

import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public final class AuthUtil {

	private AuthUtil() {
	}

	public static User requireLogin(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		HttpSession session = request.getSession(false);
		User loginUser = (session != null) ? (User) session.getAttribute("LoginUser") : null;
		if (loginUser == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return null;
		}
		return loginUser;
	}

	public static User requireAdmin(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		User loginUser = requireLogin(request, response);
		if (loginUser == null) {
			return null;
		}
		if (loginUser.getRole() != 1) {
			response.sendRedirect(request.getContextPath() + "/userMyPage");
			return null;
		}
		return loginUser;
	}

	public static User requireUser(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		User loginUser = requireLogin(request, response);
		if (loginUser == null) {
			return null;
		}
		if (loginUser.getRole() != 0) {
			response.sendRedirect(request.getContextPath() + "/dashboard");
			return null;
		}
		return loginUser;
	}
}
