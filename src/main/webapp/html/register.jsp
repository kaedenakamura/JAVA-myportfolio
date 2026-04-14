<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%--register.jsp--%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>my portfolio</title>
</head>
<body>
	
	<h1>ユーザー登録</h1>
	<% 
	//URLのerror=1 を受け取る
	String error = request.getParameter("error");
	if(error != null && error.equals("1")){
	
	%>
	<p style="color:red; font-weight:bold;">
		⚠️ 全ての項目を入力してください
	</p>
	<% 
	//URLのerror=2 を受け取る
	}else if ("2".equals(error)){
	%>
	<p>メールアドレスもしくは名前を225文字以下で設定してください</p>
	<%
	//URLのerror=3 を受け取る
	}else if ("3".equals(error)){
	%>
	<p>パスワードは8文字以上、３２文字以下で入力してください</p>
	<%-- register.jsp --%>
	<% 
	}else if("4".equals(error)){%>
	<p style="color:red;">  そのメールアドレスはすでに登録されています</p>
	<%
	}
	%>
	<form action="../register" method="post">
	<label for="name" class="common-label">名前:</label>
		<input type="text"id="name" name="name" placeholder="名前">
	
	<label for="email" class="common-label">メールアドレス:</label>
		<input type="email"id="email" name="email" placeholder="メールアドレス">
	<label for="password" class="common-label">パスワード:</label>
		<input type="password"id="password" name="password" placeholder="パスワード">
	<button type="submit">登録する</button>
	</form>
	<div>
	<a	href="../list">ユーザー一覧へ</a></div>
	<div>
	<a href="login.jsp">ログイン画面へ</a>
	</div>
</body>
</html>


