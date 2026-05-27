package myportfolio;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/profileView")
public class ProfileViewServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request , HttpServletResponse response)
	throws IOException , ServletException {
		
	User loginUser = AuthUtil.requireUser(request, response);
	if (loginUser == null) {
		return;
	}
	
	//全情報をDBより取得する。
	UserDao userDao =new UserDao();
	User latestUser =userDao.findById(loginUser.getId());
	
	//情報をsetAttributeしてjspへ
	request.setAttribute("LoginUser",latestUser);
	//ProfileView.jspを表示
	request.getRequestDispatcher("/WEB-INF/jsp/userProfileView.jsp")
	.forward(request, response);
	
	}
}
