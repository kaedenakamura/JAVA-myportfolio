package myportfolio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet("/accountSettings")
public class AccountSettingsServlet extends HttpServlet{
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException , IOException{
		
		//情報を受け取るloginUser
		HttpSession session = request.getSession();
		User loginUser =(User)session.getAttribute("LoginUser");
		System.out.println(loginUser);
		//ログイン情報がある場合はsettings.jspへ
		//userMyPageからloginUser受け取りちゃんと受け取れているかチェック
		if(loginUser ==null) {
			
			response.sendRedirect(request.getContextPath()+"/login");
			return;
		}else {
		request.setAttribute("loginUser",loginUser);
		request.getRequestDispatcher("/WEB-INF/jsp/settings.jsp")
		.forward(request, response);
		}
	}
		
		//更新処理を書いていく
		protected void doPost(HttpServletRequest request , HttpServletResponse response) 
			throws ServletException , IOException{
			//文字化け防ぎ
			request.setCharacterEncoding("UTF-8");
			
			//user
			HttpSession session = request.getSession();
			User loginUser =(User)session.getAttribute("LoginUser");
			System.out.println(loginUser);
			String email = request.getParameter("email");
			String password =request.getParameter("password");
			
			UserDao dao = new UserDao();
			List<String> errors = new ArrayList<>();
			
			//--バリデーションチェック--
			if(email == null || email.isEmpty() || !email.matches("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")) {
					errors.add("有効なメールアドレスの形式で入力してください");	
				}else if(email.length() > 255) {
					errors.add("メールアドレスは255文字で入力してください");
				}
			//メールアドレスチェック
			if(dao.isEmailExists(email)) {
				errors.add("メールアドレスが2重登録されています");
				
			}
			//パスワードチェック(入力がある場合のみ)バリデート
			if(password != null && !password.isEmpty()) {
				if(!password.matches("^[a-zA-Z0-9_-]{8,32}$")) {
					errors.add("パスワードは8～32文字の半角英数字、ハイフンで記入してください");
				}
			}
			
			// 判定とDB更新
			if(errors.isEmpty()) {
				//エラー無ければDB更新
				//passwordが空ならメアド更新メソッドを起動する
				boolean isSuccess = dao.updateAccount(loginUser.getId(),email,password);
				
				if(isSuccess) {
					loginUser.setEmail(email);
					session.setAttribute("user",loginUser);
					//成功したらマイページへ
					response.sendRedirect(request.getContextPath()+"/userMyPage?success=true");
				}else {
					errors.add("データベースの更新に失敗しました");
					//下にreloadSettingsメソッド作成して、更新失敗の処理作成
					reloadSettings(request,response,loginUser,errors);
				}}
			else {
				//reloadSettingsメソッド
				reloadSettings(request,response,loginUser,errors);	
				}
			}
		//エラーの際にJSPを再表示させるメソッド
		private void reloadSettings(HttpServletRequest request, HttpServletResponse response ,User loginUser , List<String> errors)
					throws ServletException ,IOException{
			UserDao dao =new UserDao();
			request.setAttribute("user", dao.findById(loginUser.getId()));
			request.setAttribute("errors", errors);
			request.getRequestDispatcher("/WEB-INF/jsp/settings.jsp").forward(request, response);
		} 
		
		
		}
		
	

