<%@ page language="java" contentType="text/html ; charset=UTF-8 " pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン | My portfolio </title>


</head>
<style>
	/*ボックスサイズ*/
*{box-sizing:border-box;}
/*画面幅が768以下（スマホ・タブレット）になったら適応 ヘッダーの高さを低くする*/
@media screen and (max-width:768px){
.header{height:60px;}
.item{width:100%;}
}
h2{
color: rgb(0, 0, 0);
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
	background:rgb(255, 255, 255);
	max-width:1000px;
	margin:40px auto;
	padding:30px;
	border-radius:12px;
	/*上下０左右４へ15影をつける　rgba色と透明度(000)は黒 0.05は透明度５％*/
	box-shadow:0 4px 15px rgba(0,0,0,0.05);
}

/*入力フォーム*/
.form-group{margin-bottom:20px;}
label{display:block; font-weight: bold; margin-bottom:8px;}
input[type="text"],[type="email"],[type="password"]{
	width:500px;
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
	color:rgb(0, 0, 255);
	text-decoration:none;
}
.back-link:hover{
	text-decoration: underline;
}
</style>
<body>
	<div class="form-container">
		<div class="header">
			<h2>ログイン</h2>
		</div>
			
<%--エラーメッセージの表示（登録画面と同じ仕組み） --%>
	<%
	 String error = request.getParameter("error");
	if("1".equals(error)){	
	%>	
	<p style="color:red;">メールアドレスまたはパスワードが違います</p>
	<%
	}
	%>
	<form  action="${pageContext.request.contextPath}/login" method="post">
		<div class="form-group">
			<label for="email">メールアドレス:</label>
			<input type="email" id="email" name="email" value="${fn:escapeXml(email)}"  required >
	</div>
		<div class="form-group">
			<label for="password" >パスワード:</label>
			<input type="password" id="password" name="password" required>
		</div>
	
	<button class="btn-submit" type="submit">ログイン</button>
	</form>
<p>まずは雰囲気を知りたい方はこちら↓</p>
<a href="${pageContext.request.contextPath}/like">公開アカウント一覧を見る</a><br>
<p>いいねランキングはこちら↓</p>
 <a href="${pageContext.request.contextPath}/Ranking">いいねランキングを見る</a><br>
 <p>お問い合わせはこちら↓</p>
 <a href="${pageContext.request.contextPath}/contact?action=new">お問い合わせ</a><br>
 <p><a class="back-link" href="register.jsp">新規登録はこちら</a></p>
</div> 
</body>
</html>
