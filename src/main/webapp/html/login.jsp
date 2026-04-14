<%@ page language="java" contentType="text/html ; charset=UTF-8 " pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン | My portfolio </title>

<%--スタイルシートは後ほど設定すhref --%>

<link rel="stylesheet" href="">

</head>
<body>
	<div class="container">
	<h2>ログイン</h2>
	
	<%--エラーメッセージの表示（登録画面と同じ仕組み） --%>
	<%
	 String error = request.getParameter("error");
	if("1".equals(error)){	
	%>	
	<p style="color:red;">メールアドレスまたはパスワードが違います</p>
	<%
	}
	%>
	<form action="../login" method="post">
		<div class="form-group">
			<label for="email">メールアドレス:</label>
			<input type="email" id="email" name="email" required>
	</div>
		<div class="form-group">
			<label for="password" >パスワード:</label>
			<input type="password" id="password" name="password" required>
		</div>
	
	<button type="submit">ログイン</button>
	</form>
 <p><a href="register.jsp">新規登録はこちら</a></p>
 
 </div>
</body>
</html>
