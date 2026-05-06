package myportfolio;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/UserDetail")
public class UserDetailServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request , HttpServletResponse response)
			throws IOException , ServletException {
				
			//jspパラメーターからID取得
			String idStr = request.getParameter("id");
			
			// IDが送られてこなかった場合はランキングへ戻す
	        if (idStr == null || idStr.isEmpty()) {
	            response.sendRedirect(request.getContextPath() + "/Ranking");
	            return;
	        }
			
	           try {
	                int id = Integer.parseInt(idStr);
	                UserDao userDao = new UserDao();
	                User user = userDao.findById(id);

	                // ユーザーが見つからなかった場合
	                if (user == null) {
	                    response.sendRedirect(request.getContextPath() + "/Ranking");
	                    return;
	                }

	                // セッションからログインユーザー情報を取得して、自分自身がこのユーザーを「いいね」しているか判定
	                HttpSession session = request.getSession();
	                User loginUser = (User) session.getAttribute("LoginUser");
	                if (loginUser != null) {
	                	//いいね済チェック
	                	LikeDao likeDao = new LikeDao();
	                	user.setLiked(likeDao.isLiked(loginUser.getId(), user.getId()));
	                }
	                request.setAttribute("user", user);
	                request.getRequestDispatcher("/WEB-INF/jsp/userDetail.jsp").forward(request, response);
	                
	            }catch (NumberFormatException e) {
	                response.sendRedirect(request.getContextPath() + "/Ranking");
	            } catch (Exception e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
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



