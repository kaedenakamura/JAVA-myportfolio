<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="myportfolio.Category" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>カテゴリー編集</title>
<style>
/*ボックスサイズ*/
*{box-sizing:border-box;}
/*画面幅が768以下（スマホ・タブレット）になったら適応 ヘッダーの高さを低くする*/
@media screen and (max-width:768px){
.header{height:60px;}
.item{width:100%;}
}
h2{
color: rgb(255, 255, 0);
border-bottom:2px solid rgb(255, 255, 128);
padding-bottom:10px;
margin-bottom:25px;

}
/*全体のリセットと背景*/
body{
	font-family:"sans-serif";
	background-color: rgb(255, 255, 255);
	color:rgb(0, 0, 0);
	margin:0;
	padding:20px;
	}
/*コンテナ*/
.form-container , .container{
	background:rgb(128, 128, 255);
	max-width:800px;
	margin:40px auto;
	padding:30px;
	border-radius:12px;
	/*上下０左右４へ15影をつける　rgba色と透明度(000)は黒 0.05は透明度５％*/
	box-shadow:0 4px 15px rgba(0,0,0,0.05);
}
/*入力フォーム*/
.form-group{margin-bottom:20px;}
label{display:block; font-weight: bold; margin-bottom:8px;}
input[type="text"]{
	width:100px;
	padding:12px;
	border:1px solid rgb(0, 255, 255);
	border-radius:6px;
	transition:border-color 0.3s;
	}
input[type="text"]:focus{
	border-color:rgb(0, 128, 255);
	outline: none;
}
/*ボタン（青系）*/
.btn-submit{
	background-color: rgb(0, 64, 128);
	color:white;
	padding:12px 20px;
	border:none;
	border-radius:6px;
	cursor:pointer;
	width:100%;
	font-size:16px;
	font-weight:bold;
	transition: background 0.3s;
}	
/*transitionに対してのホバー色*/
.btn-submit:hover{
background-color:rgb(0, 128, 255);
}
/*戻るリンク*/
.back-link{
	display:block;
	text-align:center;
	margin-top:20px;
	color:rgb(0, 255, 255);
	text-decoration:none;
}
.back-link:hover{
	text-decoration: underline;
}
</style>
</head>
<body>
<div class="form-container">
<% //エラーメッセージあれば表示
	String errorMsg =(String)session.getAttribute("errormsg");
	if(error != null){
%>	
	<p style="cokor:red"><%= errormsg %></p>
<% 
	//表示したらセッション削除
	session.removeAttribute("errormsg");
	}

%>	
<% 
	//Servletから届いた「category」の取り出し catへ置き換え
	Category cat =(Category)request.getAttribute("category");
	if(cat== null){
		response.sendRedirect("category");
		return;
	}
%>
	
	<h2>カテゴリーの編集</h2>
	
	<%--更新処理なのでmethod=post--%>
	<form class="form-group" action="category" method="post">
	
	<%--誰を更新するのかを伝えるhidden→update,id --%>
	<input type="hidden" name="action" value="update">
	S
	<input type="hidden" name="id" value="<%=cat.getId()%>">
	
	<div class="form-group">
		<label for="name">カテゴリー名</label>
		<%--現在の値を初期値として表示--%>
		<input type="text" id="name" name="name" value="<%=cat.getId()%>" required>
	</div>
	
	<button class="btn-submit" type="submit" class="btn-submit">更新する</button>
	</form>
	
	<a class="back-link" href="category" class="back-link">一覧へ戻る</a>
</div>
</body>
</html>	

	
		
		