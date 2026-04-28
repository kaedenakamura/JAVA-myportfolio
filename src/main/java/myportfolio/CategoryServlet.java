package myportfolio;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/category")
public class CategoryServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request ,HttpServletResponse response)
		throws ServletException , IOException{
		//文字化けの対策
		request.setCharacterEncoding("UTF-8");
		CategoryDao dao =new CategoryDao();
		
		//action id を取得
		String action = request.getParameter("action");
		
			
			 if ("edit".equals(action)) {
				//findByidにてIDを探して、編集画面へ飛ばす
				String ids    = request.getParameter("id");
				int id        = Integer.parseInt(ids);
				Category cat = dao.findById(id);
				request.setAttribute("category",cat);
				request.getRequestDispatcher("/WEB-INF/jsp/categoryEdit.jsp")
				.forward(request,response);
			}else if ("delete".equals(action)) {
				//削除処理：IDを受け取ってDAOに渡す
				String ids    = request.getParameter("id");
				int id        = Integer.parseInt(ids);
				dao.delete(id);
				//完了したら一覧に戻る
				response.sendRedirect("category");
			}else if ("create".equals(action)) {
				request.getRequestDispatcher("WEB-INF/jsp/categoryForm.jsp")
				.forward(request, response);
			}else {
				//findAllメソッド用いてカテゴリーリストをcategoryList.jspへ
				List<Category> list =dao.findAll();
				request.setAttribute("categoryList", list);
				request.getRequestDispatcher("/WEB-INF/jsp/categoryList.jsp")
				.forward(request,response);
		}
	}
protected void doPost(HttpServletRequest request ,HttpServletResponse response) 
		throws ServletException ,IOException{
		//文字化け対策
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		CategoryDao dao =new CategoryDao();
		
		//フォームからcategory_group とnameの両方を受け取る
		String categoryGroup =request.getParameter("category_group");
		String name = request.getParameter("name");
		
		if("update".equals(action)) {
		//更新処理,画面から送られてきたIDと新しい名前を取得
		int id  = Integer.parseInt(request.getParameter("id"));
		//名前のバリデーション
		if(categoryGroup == null || categoryGroup.isEmpty() ||name == null || name.isEmpty() || name.length() >255) {
			HttpSession session = request.getSession();
			session.setAttribute("errormsg","名前を255文字以内で入力してください");
			//エラーで下の画面に戻す
			response.sendRedirect("category");
			return;
		}
		
		//Daoメソッド起動 
		Category category =new Category(id,categoryGroup,name);
		dao.update(category);
		//Edit.jspよりPOST
		//サクセス取得時（更新成功時）カテゴリー一覧へ再び戻る
			response.sendRedirect("category");
		}else {
			//新規登録(value=insert)処理（action=updateではないとき）
			//categoryForm.jspよりPOST
			dao.insert(categoryGroup,name);
			response.sendRedirect("category");
		}
		}
		}
		
	
	
