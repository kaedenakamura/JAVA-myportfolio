<%--設定、java リストやUserクラスを使用できるようにする--%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="java.util.List,myportfolio.User" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>カテゴリー一覧画面</title>
	</head>
	<div class="container">
	<div>
		<h2>カテゴリー一覧</h2>
	</div>
	<style>
	/*ボックスサイズ*/
	* {box-sizing: border-box;}
	
	/*画面幅が768以下（スマホ・タブレット）になったら適応 ヘッダーの高さを低くする*/
	@media screen and (max-width: 768px){
	.header{height:60px;}
	.item{width:100%;}/**/
	/*画像設定一応おいておく 横幅に合わせて高さ調整*/
	}
	/*横並びとflex-wrapで幅が足りないと折り返し*/
	.container{flex-wrap:wrap;
	background:rgb(255, 255, 255);
	max-width:800px;
	margin:40px auto;
	padding:30px;
	border-radius:12px;
	/*上下０左右４へ15影をつける　rgba色と透明度(000)は黒 0.05は透明度５％*/
	box-shadow:0 4px 15px rgba(0,0,0,0.05);
	}
	/* 5. テーブルのスクロール対応 over-frow(スクロールバー出す)*/
	.table-container{width:100px; overflow:auto; -webkit-over-scrolling:touch;
	margin-top:20px;}
	img{max-width:100%; height:auto;}
	/*orverflow-x→横方向にはみ出したらスクロールバー出す。webkit→スマホでぬるぬる動かしやすくする*/
	.table-container{width:100%;overflow-x:auto; -webkit-overflow-scrolling:touch; margin-top:20px;}
	/*min-width:　画面が小さくなっても500pxで止める*/
	table{width:100%; min-width:500px; border-collapse:collapse; margin-top: 20px;}
	th,td{border:1px solid rgb(0,255,128); padding: 10px; text-align:left; }
	th {background-color:rgb(255,255,128);}
	.btn-create{display:inline-block; border:1px solid rgb(192, 192, 192); margin:10px 0 10px 0; text-decoration:none; color:rgb(0, 255, 128); border-radius:5px; background:rgb(128, 128, 128);transition:0.3s;}
	.btn-create:hover{background:rgb(0, 255, 255)}
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
	text-align:center;
	color:rgb(255, 128, 128);
	text-decoration:none;
}
.back-link:hover{
	text-decoration: underline;
}
	</style>
	<div class="item">
	 <a href="category?action=create" class="btn-create">新規カテゴリー追加</a>
		<div class="table-container">
			<table border="1">
				<tr>
					<th>ID</th>
					<th>カテゴリーグループ</th>
					<th>操作</th>
				</tr>
				<c:forEach var="cat" items="${categoryList}">
					<tr>
						<td>${cat.id}</td>
						<td>${cat.categoryGroup}</td>
						<td>
							<a href="category?action=edit&id=${cat.id}">編集</a>
							<a class="back-link" href="category?action=delete&id=${cat.id}"
								onclick="return confirm('本当に削除しますか？');">削除</a>
						</td>
					</tr>
				</c:forEach>
			</table>
			<a href="${pageContext.request.contextPath}/dashboard"
			style="display:inline-block;margin-top:40px">ダッシュボードへ戻る</a>
			</div>
		</div>
</div>


	