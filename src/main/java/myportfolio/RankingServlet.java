package myportfolio;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet("/Ranking")
public class RankingServlet extends HttpServlet{
	protected void doGet(HttpServletRequest request ,HttpServletResponse response)throws ServletException , IOException {
		UserDao userDao = new UserDao();
		LikeDao likeDao = new LikeDao();
		//ユーザーをいいねしているかチェック
		HttpSession session = request.getSession();
		User loginUser =(User) session.getAttribute("LoginUser");
		
		try {
	        // 月間ランキングを取得
	        List<User> rankingList = likeDao.getMonthlyLikeRanking();
	        System.out.println("ランキング取得件数: " + (rankingList != null ? rankingList.size() : "nullです"));

	      //ログインしているときのみ自分がいいねしたかチェックする
			if (loginUser != null) {
		        int fromUserId = loginUser.getId();
		        for (User user : rankingList) {
		 
		                boolean liked = likeDao.isLiked(fromUserId, user.getId());
		                user.setLiked(liked);
		            }
			}
	        
	        // JSPへデータをセット
	        request.setAttribute("rankingList", rankingList);

	        // ランキングJSPへフォワード
	        request.getRequestDispatcher("/WEB-INF/jsp/userRankingList.jsp").forward(request, response);

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}


//いいね機能実装。いいねつけたり削除したり（取り消ししたり）
	protected void doPost(HttpServletRequest request ,HttpServletResponse response) 
	throws ServletException , IOException{
		//設定決まり
		request.setCharacterEncoding("UTF-8");
		
		// セッションから自分のIDを取得
		HttpSession session = request.getSession();
		User loginUser = (User)session.getAttribute("LoginUser");
		
		//未ログインならログイン画面へ
				if(loginUser == null) {
					response.sendRedirect(request.getContextPath()+"/login");
					System.out.println("ログインユーザーを受け取れませんでした");
					return;
				}
				
		//自身のID取得
		int fromUserId = loginUser.getId();
		
		
		//jspよりto_user_id受け取り
		int toUserId = Integer.parseInt(request.getParameter("toUserId"));
		
		//Dao呼び出して状態確認後いいねか取り消しかを決めるif
		LikeDao likeDao = new LikeDao();
		
		//追記：今の状態を保存する変数
		boolean currentStatus = false;
		
		//新しいいいね数を保存する変数
		int newCount = 0;
		
		try {
			//isLikedメソッドでtrue →いいねがすでにある場合はいいね取り消し
			if(likeDao.isLiked(fromUserId,toUserId)) {
				likeDao.delete(fromUserId, toUserId);
				//追加、解除したのでfalse
				currentStatus = false;
				//いいねがないときはinsertメソッドでいいね追加
			}else {
				likeDao.likeInsert(fromUserId, toUserId);
				//追加登録したのでtrue
				currentStatus = true;
				
			}
			//追加：最新のいいね数の取得
			newCount = likeDao.countLikesByToUserId(toUserId);
		}catch (Exception e) {
			e.printStackTrace();
		}
		//jsでasync await 使うからデータを返す
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String json = String.format("{\"isLiked\": %b, \"newCount\": %d}", 
                currentStatus, newCount);
		response.getWriter().write(json);
	}
	
}
