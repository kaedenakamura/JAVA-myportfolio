<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="myportfolio.User" %>
<%@ page import="myportfolio.UserDao" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
<mata charset="UTF-8">
<title>設定画面</title>
</head>
<body>
<style>
	* { box-sizing: border-box; }
	body{font-family: sans-serif; margin:0; background:rgb(192, 192, 192); display:flex; justify-content:center; align-items:center; min-height:100vh;}
	.main-content{width:100% ; min-width:600px; padding:20px;}
	.card{background:rgb(255, 255, 255); padding:30px; border-radius:8px; box-shadow:0 2px 5px rgba(0,0,0,0.2);}
	h1 {font-size:24px; margin-bottom:20px; text-align:center;}
	.form-group{margin-bottom:20px;}
	label{display:block; margin-bottom:8px; font-weight:bold;}
	
	input[type="email"],input[type="password"]{
		width:100%; padding:12px; border:1px solid rgb(192, 192, 192); border-radius:4px; font-size:16px;
	}
	.error-list{color:rgb(255, 0, 0); padding:15px; border-radius: 4px; margin-bottom:20px; list-style:none;}
	.error-list li {font-size:14px; margin-bottom:5px;}
	
	.btn-submit{
		width:100%; padding:12px; background:rgb(100,100,100); color:rgb(255, 255, 0);border:none; border-radius:4px; cursor:pointer; font-size:16px; transition:0.3s;
	}
	.btn-submit:hover{background:rgb(192, 192, 192);}
	
	.back-link{display:block; text-align:center; margin-top:20px; color: rgb(128, 64, 0); text-decoration:none;}
	.back-link:hover{background:rgb(255, 128, 0);text-decoration:underline;}
	
	
</style>
<main class="content">
	<div class="card">
		<h1>アカウント設定</h1>

	<%--エラーメッセージがある場合に表示--%>
	<c:if test="${not empty errors}">
	<ul class="error-list">
		<c:forEach var="error-list" items="${errors}">
			<li>${error}</li>
		</c:forEach>
		</ul>
	</c:if>
</main>

		<form action="${pageContext.request.contextPath}/accountSettings" method="post">
			<div class="form-group">
				<label for="email">メールアドレス</label>
				<%-- 以前の入力値があればそれを、なければ現在の登録値を表示 --%>
				<input type="email" id="email" name="email"
					value="${not empty parm.email ? param.email : user.email}" required>
				<p style="font-size:12px ; color: rgb(0, 0, 0)">※255文字以内で入力してください</p>
			</div>
			
			<div class="form-group">
				<label for="password">新しいパスワード</label>
				<input type="password" id="password" name="password" placeholder="変更する場合のみ入力してください">
				<p style="font-size:12px; color:rgb(0, 0, 0);">※8~32文字の半角英数字、ハイフン、アンダースコアが使えます</p>
			</div>
			
			<button type="submit" class="btn-submit">設定を保存する</button>
			
		</form>
		
			<a href="${pageContext.request.contextPath}/userMyPage" class="back-link">マイページへ戻る</a>
	</div>
</body>
</html>