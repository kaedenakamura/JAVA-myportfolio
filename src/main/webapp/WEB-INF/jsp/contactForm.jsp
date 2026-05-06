<%@page language="java" contentType="text/html ; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE>
<html>
<head>
	<meta charset="UTF-8">
<title>お問合せフォーム</title>
</head>
<style>
/*ボックスサイズ*/
*{box-sizing:border-box;}
/*画面幅が768以下（スマホ・タブレット）になったら適応へ*/
@media screen and (max-width:768px){
.header{height:60px;}
.item{width:100%;}
}
h2{
color:rgb(0,0,0);
border-bottom:2px solid rgb(100,100,100);
padding-bottom:10px;
margin-top:25px;
}
/*全体のリセットと背景*/
body{
	font-family:"sans-serif";
	background-color:rgb(255,255,255);
	color:rgb(0, 0, 0);
	margin:0;
	padding:20px;
}
/*コンテナ*/
.form-container , .container{
	background:rgb(255,255,255);
	max-width:600px;
	margin:40px auto;
	padding:30px;
	border-radius:12px;
/*上下０左右４へ１５影をつける rgba 0.05(透明度5%) */
box-shadow: 0 4px 15px rgba(0,0,0,0.05);
}

/*入力フォーム*/
.form-group{margin-bottom:20px;}
label{display:block; font-weight:bold;  margin-bottom:8px; }
input[type="text"]{
	width:100%;
	padding:12px;
	border:1px solid rgb(0,255,255);
	border-radius:6px;
	transition:border-color 0.3s;
}
/*テキストエリア縦方向へ広げる*/
textarea{
	resize:vartical;
}
input[type="text"]{
	border-color:rgb(0,128,255);
	outline: none;
}
/*ボタン（青系）*/
.btn-submit{
	background-color:rgb(0,64,128);
	color:white;
	padding:12px 20px;
	border:none;
	border-radius:6px;
	cursor:pointer;
	width:100%;
	font-size:16px;
	font-weight:bold;
	transition:background:0.3s;
}
/*transitionに対してのホバー色*/
.btn-submit:hover{
background-color: rgb(0,128,255);
}
/*戻るリンク*/
.back-link{
	display:block;
	text-align:center;
	margin-top:20px;
	color:rgb(0,0,255);
	text-decoration: none;
}
.back-link:hover{
	background-color: rgb(128, 255, 255);
	text-decoration: underline;
}
</style>

<body>
<div class="container item">
	<h2 class="header">お問合せフォーム</h2>
	<%--IDはauto_increment statusはDeffault 0未対応で自動登録 SQL文--%>
	<form  action="${pageContext.request.contextPath}/contact?action=insert" method="post">
	<div class="form-group">
	<label for="name">お名前</label>
	<input type="text" name="name" id="name" required placeholder="名前">
	</div>
	<div class="form-group">
	<label for="name">メールアドレス</label>
	<input type="email" id="email" name="email" required placeholder="example@mail.com">
	</div>
	<div class="form-group">
	<label for="category">問合せカテゴリー</label>
	<select id="category" name="category" required>
		<option value="">選択してください</option>
		<%--サーブレットから届いたリストを回す--%>
		<c:forEach var="cat" items="${categoryList}">
			<option value="${cat.id}">${cat.categoryGroup}</option>
		</c:forEach>
	</select>
	</div>
	
	<div class="form-group">
	<label for="body">お問合せ内容</label>
	<textarea name="body" id="body" rows="10" required ></textarea>
	</div>
	<button type="submit" class="btn-submit">送信する</button>
	</div>
	</form>
	<a class="back-link" href="${pageContext.request.contextPath}/login">ログイン画面に戻る</a>
</div>
</body>
</html>