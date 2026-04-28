<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>お問合せユーザー一覧</title>
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
	max-width:800px;
	margin:40px auto;
	padding:30px;
	border-radius:12px;
	/*上下０左右４へ15影をつける　rgba色と透明度(000)は黒 0.05は透明度５％*/
	box-shadow:0 4px 15px rgba(0,0,0,0.05);
}
/* さらにテーブル自体をコンテナいっぱいに広げる */
table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 10px;
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
	color:rgb(255, 128, 128);
	text-decoration:none;
}
.back-link:hover{text-decoration: underline;}
	
</style>
<div class="container">
<h2 class="header">お問い合わせ一覧</h2>
	<div class=form=group>
		<table border="1">
		<tr>
			<th>ID</th>
			<th>名前</th>
			<th>カテゴリー</th>
			<th>本文</th>
			<th>ステータス</th>
			<th>詳細</th>
		</tr>
		
		<%-- forEachにて１つずつリストとして格納 --%>
		<c:forEach var="c" items="${contactList}">
			<tr>
				<td>${c.id}</td>
				<td>${c.name}</td>
				<%--カテゴリーメソッドcontact.javaから取得 --%>
				<td>${c.categoryName}</td>
				<td>${c.shortBody}</td>
			
			<td>
			<%--ステータスメソッドcontact.javaから取得 --%>
				${c.statusName}
			<%--choose(if)when(条件１,2,,,)otherwise(else)の役割jspにて --%>
			<%-- 	<c:choose>
					<c:when test="${c.status == 0 }">未対応</c:when>
					<c:when test="${c.status == 1 }">対応中</c:when>
					<c:otherwise>対応済み</c:otherwise>
				</c:choose>
			--%>
			</td>
			
			<td>
				<a href="contact?action=detail&id=${c.id}">詳細</a>
			</td>
			</tr>
			</c:forEach>
			
			</table>
			<a class="back-link" href="${pageContext.request.contextPath}/dashboard">ダッシュボード画面へ</a>
		</div>
	</div>	
	</body>
	</html>