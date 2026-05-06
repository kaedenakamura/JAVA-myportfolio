package myportfolio;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/like")
public class LikeServlet extends HttpServlet {
	
	
	protected void doGet(HttpServletRequest request ,HttpServletResponse response) throws ServletException, IOException {
		UserDao userDao = new UserDao();
		LikeDao likeDao = new LikeDao();
		//表示するユーザーの一覧を取得
		List<User> userList =userDao.findAll();
		
		//ユーザーをいいねしているかチェック
		HttpSession session = request.getSession();
		User loginUser =(User) session.getAttribute("LoginUser");
		
		//ログインしているときのみ自分がいいねしたかチェックする
		if (loginUser != null) {
	        int fromUserId = loginUser.getId();
	        for (User user : userList) {
	            try {
	                boolean liked = likeDao.isLiked(fromUserId, user.getId());
	                user.setLiked(liked);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    }
		// JSPにデータを渡して画面を表示する
	    request.setAttribute("userList", userList);
	    request.getRequestDispatcher("/WEB-INF/jsp/userLikeList.jsp").forward(request, response);
	    
	}
	//いいね機能実装。いいねつけたり削除したり（取り消ししたり）
	protected void doPost(HttpServletRequest request ,HttpServletResponse response)
			throws ServletException , IOException {
		//設定の決まり
		request.setCharacterEncoding("UTF-8");
		
		//セッションから自分のID取得
		HttpSession session = request.getSession();
		User loginUser =(User)session.getAttribute("LoginUser");
		
		//未ログインならログイン画面へ
		/*if(loginUser == null) {
			response.sendRedirect(request.getContextPath()+"/login");
			System.out.println("ログインユーザーを受け取れませんでした");
			return;
		}
		*/
		//自身のID取得
		int fromUserId = loginUser.getId();
		
		//jspよりto_user_id受け取り
		int toUserId = Integer.parseInt(request.getParameter("toUserId"));
		
		//Dao呼び出して状態確認後いいねかいいねとりけしかを決めるif
		LikeDao likeDao = new LikeDao();
		//追記：今の状態保存する変数currentStatus
		boolean currentStatus = false;
		//新しいいいね数を保存する変数
		int newCount= 0;
		
		try {
			//isLikedメソッドでtrue→いいねがすでにある場合はいいねとりけし
			if(likeDao.isLiked(fromUserId, toUserId)) {
				likeDao.delete(fromUserId, toUserId);
				//追加：解除したのでfalse
				currentStatus =false;
			//いいねがないときはinsertメソッドでいいね追加
			}else {
				likeDao.likeInsert(fromUserId,toUserId);
				//追加：登録したのでtrue
				currentStatus = true; 
			}
			//追加：最新のいいね数を取得する
			newCount = likeDao.countLikesByToUserId(toUserId);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		//jsでasync await使うからデータのみ返す
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		String json = String.format("{\"isLiked\": %b, \"newCount\": %d}", 
		                            currentStatus, newCount);
		response.getWriter().write(json);
	}
}

