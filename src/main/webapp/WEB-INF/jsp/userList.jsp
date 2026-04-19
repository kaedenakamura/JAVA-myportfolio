<%--1. 設定；javaリストやUserクラスを使用できるようにする--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, myportfolio.User" %>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ユーザ一一覧画面</title>
    </head>
    <body>	
    <h1>ユーザ一一覧</h1>
    <%--メッセージがあれば表示する（DeleteメソッドやUpdateメソッド起動完了時に出現） --%>
    <%
    	String message = (String)session.getAttribute("msg");
    	if(message !=null){
    %>
       <div style="color=:blue ; font-weight:bold; backgroundcolor:#f8f9fa;; padding; 10px border-radius:5px margin-bottom:10px;">
       <%= message %>
       </div>
       
    <%
    //1度表示したら消すsession.removeAttribute
    	session.removeAttribute("msg");
    }
    %>
     <style>
     table {whidth: 100% border-collapse: collapse; margin-top: 20px;}
     th, td {border:1px solid rgb(0, 255, 128); padding: 10px; text-align; left;}
     th    {background-color: rgb(255, 255, 128);}
     

    </style>
    <table border="1">
    	<tr>
    	<th>id</th>
    	<th>名前</th>
    	<th>メールアドレス</th>
    	<th>ステータス</th>
    	<th>操作</th>
    	</tr>
    	<%
    	//Servletから飛ばしたユーザーリストの取得
    	List<User> userList = (List<User>) request.getAttribute("userList");
    	
    	if(userList !=null && userList.size() != 0){
    		for(User user : userList){
    			System.out.println(user.getId());
    			System.out.println(user.getName());
    			System.out.println(user.getEmail());
    	%>
    	<tr> 
    		<td><%= user.getId()   %> </td>
    		<td><%= user.getName() %></td>
    		<td><%= user.getEmail() %></td>
    		<td>
    			<%--管理者と一般の切り替え--%>
           	<%
       			int role = user.getRole(); // DBから持ってきた値をセット
       			if (role == 1) {
   			%>
        		<span style="color: blue; font-weight: bold;">管理者</span>
   			<%
        		} else {
    		%>
           		 <span style="color: gray;">一般</span>
    <%
        }
    %>
    		</td>
    		<td>
				<%--編集リンク --%>
    			<a href="update?id=<%= user.getId() %>" >編集</a>
				<%--削除リンク --%>    		
    			<a href="delete?id=<%= user.getId() %>" 
       				onclick="return checkDelete('<%= user.getName() %>')" 
       				style="color:red;">削除</a>
       			<%--ステータス切替ボタン（新規追加）--%>
       			<form action="toggleStatus" method="post" style="display:inline;">
       				<input type="hidden" name="id" value="<%= user.getId() %>">
       			<%--現在のロールが1なら0へ、0なら１へ切り替える値を送信--%>
       			<input type="hidden" name="newRole" value="<%=user.getRole() == 1 ? 0 : 1%>" >
       			<button type="submit" style="cursor:pointer;">権限変更</button>
       			</form>
    		<%-- --<a href="delete?id=1" 
    		onclick ="return checkDelete('<%=user.getName() %>')"
    		style="color:red;" >テスト削除リンク</a>
    		--%>
    		</td>
    		</tr>
    	<%
    		}//for文閉じ
    		}else{
    			%>
    			<tr>
    			<td colspan="4" style="text-align: center;">
    			ユーザーは存在しません。データが見つかりませんでした。</td>
    			</tr>
    	 <%
    		}
    	 %>
    	 </table>
    	 
    	 <div style="margin-top:20px">
    	 <% 
    	 	//サーブレットから渡された「現在のページ番号」を受け取る
    	 	int currentPage =(int)request.getAttribute("currentPage");
    	 	//サーブレットから受け取る
    	 	int maxPage = (int)request.getAttribute("maxPage");
    	 %>
    	 <%--前ページのリンク（１ページ目より大きいときだけ表示） --%>
    	 <% if(currentPage > 1){ %>
    	 <a href="list?page=<%=currentPage - 1 %>">前へ</a>
    	 <% 
    	 }
    	 %>
    	 <span>現在のページ:<%=currentPage %></span>
    	 <%--次のページへリンク--%>
    	 <a href="list?page=<%= currentPage +1  %>">次へ→</a>
    	 
    	 </div>
    	 <%--ページネーションの土台 --%>
    	 <div style="margin-top: 20px;">
    	 <% if(currentPage < maxPage){ %>
    	 	<a href="list?page=<%= currentPage + 1 %>">次へ→</a>
    	 <% }%>
    	 </div>    	 
    	 <div style="margin-top: 10px;">
    	 <p><a href="${pageContext.request.contextPath}/deletedList">ユーザー削除済一覧へ</a></p>
    	 </div>
    	 <p><a href="/myportfolio/html/register.jsp">新規登録画面へ戻る</a></p>
    	
    	 	
    	 <script>
    	 function checkDelete(name){
        	 //confirmの結果(true/false)そのまま返す
        	 return confirm(name +"さんを本当に削除しますか？")
        	 }	    
    	 </script>
    
    </body>
    </html>