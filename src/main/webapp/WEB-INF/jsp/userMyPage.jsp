<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, myportfolio.User" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>マイページ</title>
</head>
<style>
 	*{box-sizing:border-box;}
	body { display:flex; font-family: sans-serif; margin:0;}
	/* サイドバーのスタイルposition fixdで場所固定 上から左固定高さ画面いっぱい */
	/*.sidebar{width:250px; background: #333; color:#fff; height:100vh; padding:20px; 
	position: fixed; top:0; left:0; height:100vh;}
	.sidebar a {color: #fff; text-decoration: none; display:block; padding:10px 0;}
	.sidebar a:hover{color: #ffeb3b;}*/
	/* メインコンテンツのスタイル  flex-growで最大幅 box-sizingでパディング含めた幅計算*/
	.main-content{margin:0 auto; background: rgb(192, 192, 192); min-height:100vh;
	flex-grow:1; box-sizing: border-box;}
	.card {background: #fff; padding:15px; margin-bottom: 20px ; border-radius: 8px ; box-shadow: 0 2px 5px rgba(0,0,0,1);}
	/*table {width: 100%; border-collapse: collapse;}
	th , td {border-bottom: 1px solid #ddd; padding: 10px; text-align: left; }
	*//*ランキングエリアの横並びコンテナ*/
	.likes-container{
		display:flex;
		gap:20px;/* カードとカードの間の隙間 */
		width:100%;}/*メインコンテンツ幅いっぱい*/
	/*各カードの横幅を半分に*/
	.flex-card{
		flex: 1;
		 }
	.user-status-bar{
		display:block; align-imtems: center; gap:15px; padding:10px; background:rgb(255, 255, 255);
		border-bottom:1px solid rgb(224, 231, 233);
	}
	/* アイコンを囲う枠 */
	.profile-icon-small{
		width:200px;
		height:100px;
		border-radius:80%;
		overflow:hidden;/* 枠からはみ出た画像を隠す */
		border:2px solid rgb(0, 255, 0);
	}
	/* アイコンの中の画像 */
	.profile-icon-small img{
		width:100%;
		height:100%;
		object-fit: cover;/* 歪まないように自動調整 */
	}
	.user-info-text{
		margin:0;
		font-weight:bold
	}
</style>
<body>
<main class="main-content">
	<header>
		<h1>マイページ</h1>
	</header>
	<div class="user-status-bar">
		<%--プロフィール画像の表示--%>
		<div class="profile-icon-small">
		<c:choose>
			<%--画像が設定されている場合--%>
			<c:when test="${not empty user.profileImage}">
				<img src="${pageContext.request.contextPath}/uploads/${LoginUser.profileImage}"
				alt="icon">
			</c:when>
			<c:otherwise>
				<%--画像がないときの代わりの文字とアイコン --%>
				<div class="no-image">NO Image</div>
			</c:otherwise>
		</c:choose>
		</div>	
	<%--ステータス表示(loginservletよりsession.setAttribute→LoginUser)--%>
	<div class="user-info-text">		
		<section class="card">
			<h2>${LoginUser.name}さんのステータス</h2>
		<div class="user-info">
			<p>メールアドレス:${LoginUser.email}</p>
			<p>権限:${LoginUser.role == 1 ? "管理者" : "一般ユーザー" }</p>
		</div>
		<%-- プロフィール確認画面へのリンク profieleView.jsp --%>
        <a href="${pageContext.request.contextPath}/profileView" 
        style="font-size: 0.8em; color: #007bff; text-decoration: none;">
        プロフィール詳細を見る</a>
	</div>
	
	
		<div class="likes-container" >
			<div class="flex-card" style="padding:20px; border:1px solid rgb(128, 128, 128);border-radius:8px;">
			<h2>今月のいいね数</h2>
			<p style="font-size:24px; color:rgb(0, 0, 0)">${monthlyLikes}</p>
		</div>
		<div class="flex-card" style="padding: 20px; border: 1px solid rgb(128, 128, 128); border-radius: 8px;">
			<h2>年間のいいね数</h2>
			<p style="font-size:24px; color:rgb(0, 0, 0)">${yearlyLikes}</p>
		</div>
		</div>
		</section>
		
		<div  style="margin-top: 30px">
		<a href="${pageContext.request.contextPath}/like">公開アカウント一覧を見る</a><br>
		 <a href="${pageContext.request.contextPath}/Ranking">いいねランキングを見る</a><br>
		<a href="${pageContext.request.contextPath}/accountSettings">アカウント設定更新画面へ</a><br>
		<a href="${pageContext.request.contextPath }/logout" class="back-link">ログアウト</a>
		</div>
	</main>

</body>
</html>