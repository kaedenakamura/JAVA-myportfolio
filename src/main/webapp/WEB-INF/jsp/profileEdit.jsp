<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="myportfolio.User" %>
<%
	//Servletから渡された最新ユーザー情報を受け取る
	User loginUser =(User)request.getAttribute("LoginUser");
	//情報がない場合はログイン画面へ
	if(loginUser == null){
		response.sendRedirect(request.getContextPath()+"/login");
		return;
	}
%>
<html>
<head>
	<meta charset="UTF-8">
	<title>プロフィール編集</title>
<style>
body{font-family: sans-serif; padding:20px; background-color: rgb(255, 255, 255)}
.form-container{background:white; padding:30px; border-radius:8px; max-width:1000px; margin:auto; box-shadow:0 2px 5px rgba(0,0,0,0.1);}
.error-msg{color:rgb(0, 128, 64); background: rgb(255, 255, 255);padding:10px; border-radius:4px; margin-bottom:20px;}
.success-msg{color:rgb(0, 255, 255); background: rgb(224, 231, 233); padding:10px; border-radius:4px; margin-bottom:20px;}
label{font-weight:bold; display:block; margin-top:15px;}
input[type="text"],input[type="email"],input[type="password"],textarea, select{
width:100%; padding:10px; margin-top:5px; border:1px solid rgb(192, 192, 192); border-radius:4px; box-sizing:border-box;
}
.btn-submit{background-color: rgb(0, 255, 64); color:white; border:none; padding:12px 20px; border-radius:4px; cursor:pointer; width:100%; margin-top:20px; font-size:16px;}
.btn-submit:hover{background-color:rgb(128, 255, 128);}
</style>
</head>
<body>

<div class="form-container">
	<h2>プロフィール編集</h2>
	
	<%--エラーメッセージの表示--%>
	<% String errorMsg =(String)session.getAttribute("errorMsg"); %>
	<% if(errorMsg != null){ %>
		<div class="error-msg"><%= errorMsg %></div>
		<% session.removeAttribute("errorMsg"); %>
		<% } %>
		
	<%--成功メッセージの表示--%>
	<% String msg =(String)session.getAttribute("msg"); %>
	<% if(msg != null){ %>
		<div class="success-msg"><%=msg %></div>
		<% session.removeAttribute("msg"); %>
	<% } %>
	
	<%-- 画像を送るための enctype を設定 --%>
	<form action="profileEdit" method="post" enctype="multipart/form-data">
		<label for="name">名前</label>
		<input type="text" id="name" name="name" value="${LoginUser.name}" required>
		
		<label for="ruby">ふりがな</label>
		<input type="text" id="ruby" name="ruby" value="${LoginUser.ruby}" >
		
		<label for="email">メールアドレス</label>
		<input type="email" id="email" name="email" value="${LoginUser.email}"required>
		
		<label for="password">パスワード(変更する場合入力)</label>
		<input type="password" id="password" name="password" placeholder="8文字以上の英数字">
		
		<label for="gender">性別</label>
		<input type="radio" id="gender" name="gender" value="male" ${LoginUser.gender == "male" ? "checked" : ''}>男性
		<input type="radio" id="gender" name="gender" value="female" ${LoginUser.gender == 'female' ? 'checked' :''}>女性
		
		<label for="age">年齢</label>
		<input type="number" id="age" name="age" value="${LoginUser.age}" min="0" max="150">
		
		<label for="bio">自己紹介</label>
		<textarea id="bio" name="bio" rows="5">${LoginUser.bio}</textarea>
		
		<label for="profileImage">プロフィール画像</label>
		<input type="file" id="profileImage" name="profileImage">
		<p style="font-size:0.8em; color: rgb(192, 192, 192);">現在の画像：${LoginUser.profileImage}
		</p>
		<button type="submit" class="btn-submit">保存する</button>
	</form>
	
	<p style="text-align: center; margin-top:20px;">
		<a href="${pageContext.request.contextPath}/userMyPage">マイページに戻る</a>
	</p>
</div>

</body>
</html>