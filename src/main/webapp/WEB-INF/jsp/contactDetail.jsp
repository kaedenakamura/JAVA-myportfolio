<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>お問合せ詳細</title>
    </head>
<body>	
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
	max-width:600px;
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
	color:rgb(255, 255, 0);
	text-decoration:none;
}
.back-link:hover{
	text-decoration: underline;
}
table{width:100%; min-width:500px; border-collapse:collapse; margin-top: 20px;}
th,td{border:1px solid rgb(0,255,128); padding: 10px; text-align:left; }
th {background-color:rgb(255,255,128);}
	
</style>
<div class="container">
<h2 class="header">お問い合わせ詳細</h2>
<%--category(数字) ではなく getcategoryName(contact.java)カスタムゲッター を使う --%>
<p>カテゴリー：${contact.categoryName}</p>
<%-- getformattedBody(改行反映)のカスタムゲッターを使用 --%>
<p>本文：<br>
	<span>${contact.formattedBody}</span>
	</p>
<form class="form-group" action="contact" method="post">
	<input type="hidden" name="id" value="${contact.id}">
	
	<label>ステータス></label>
	<select name="status">
		<option value="0" ${contact.status == 0 ?'selected' : ''}>未対応</option>
		<option value="1" ${contact.status == 1 ?'selected' : ''}>対応中</option>
		<option value="2" ${contact.status == 2 ?'selected' : ''}>対応済み</option> 
	</select>
	
	<button class="btn-submit" type="submit">更新</button>
</form>
</body>
</html>