<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--UserクラスとUserDaoクラスが使えるように... インポート--%>
<%@ page import="myportfolio.User" %>
<%@ page import="myportfolio.UserDao" %>

<%

//セッションから「LoginUser」という名前の荷物を取り出す
//取り出すときは(User)で「user型のデータ」と認識させる
User loginUser =(User)session.getAttribute("LoginUser");
if (loginUser == null) {
	response.sendRedirect("login.jsp");
	return;
}
%>

<% 
//System.out.println("--- デバッグ ---");
//System.out.println("loginUserの中身: " + loginUser);
//System.out.println("検索に使うID: " + loginUser.getId());
//セッションIDを使って、findByIdメソッドを使い自身のDBにある情報を取得
UserDao dao = new UserDao();
User latestUser =dao.findById(loginUser.getId());
//安全対策
System.out.println(latestUser);
if (latestUser == null ){
	session.invalidate();
	response.sendRedirect("login.jsp");
	return;
}

%>


<!DOCTYPE	html>
<html>
<head>
<meta charset="UTF-8">
<title>ダッシュボード</title>
</head>
<body>

<h1>ダッシュボード</h1>
<p>ようこそ、<%= latestUser.getName() %> さん！</p>
<a href="../logout">ログアウト </a>
<h2>プロフィール画面</h2>
<form action="../update" method="post">
	<input type="hidden"name="id" value="<%= latestUser.getId() %>">
	<p>
	 名前:<br>
	<input type="text" name="name" valuse="<%=latestUser.getName() %>">
	<p>
	 メールアドレス<br>
	 <input type="email" name="email" value="<%=latestUser.getEmail() %>">
	</p>
	
	<button type ="submit">情報を更新する</button>
</form>


	 
	 
</form>
</body>
</html>

