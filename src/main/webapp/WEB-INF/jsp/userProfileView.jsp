<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE>
<html>
<head>
	<mata charset="UTF-8">
	<title>プロフィール詳細</title>
</head>
<style>
body{font-family:sans-serif; background-color:rgb(255, 255, 255) padding:40px}
.profile-card{background:white; maxwidth:1000px; margin:auto; border-radius: 12px; overflow:hidden;
			box-shadow:0 4px 12px rgba(0,0,0,0.1);}
.profile-header{background:rgb(128, 255, 0);}
.profile-body{  
	padding:30px; text-align:center; position:relative;}/*ポジションの基準レイアウト崩れ回避*/
.profile-img-large{
	width: 100%; height:1000px; border-radius:20%; border:5px solid white;
	margin-top:-75px;object-fit:cover; background:rgb(255, 255, 255);
}
.bio-section{  text-align: center; background: rgb(224, 231, 233) padding:20px;
			border-radius:8px; margin-top:20px; border:1px solid ;
}
.bio-text{/* 改行を反映 */
		white-space:pre-wrap; word-wrap:break-word; color: rgb(0, 0, 0); line-height:1.6;}
.info-table{
		width:auto; margin:20px auto; min-width:300px; border-collapse:collapse; text-align:left;
}
.info-table td{
		 border-bottom:1px solid rgb(245, 231, 244);
		 padding:12px 15px; 
}
.label {font-weight:bold; color:rgb(192, 192, 192); text-align:right;}
.btn-edit{display:inline-block; margin-top:30px; padding:10px 25px; 
		background:rgb(128, 255, 128);color:white; text-decoration:none;
		border-radius:5px;}
.back-link{
	display:block; margin-top:15px; color:#666; text-decoration:none;
}
</style>

<body>

<div class="profile-card">
	<div class="profile-header"></div>
	<div class="profile-body">
	<%--プロフィール画像--%>
	<c:choose>
		<c:when test="${not empty LoginUser.profileImage}">
			<img src="${pageContext.request.contextPath}/uploads/${LoginUser.profileImage}" class="profile-img-large">
		</c:when>
		<c:otherwise>
			<div class="profile-img-large" style="display:flex; align-items:center; justify-content:center; margin: auto;">No Image</div>
		</c:otherwise>
	</c:choose>
	
	<h2>${LoginUser.name}<small style="font-size:0.5em; color: rgb(192, 192, 192)">(${LoginUser.ruby})</small></h2>
	
	<table class="info-table">
		<tr><td class="label">メール</td><td>${LoginUser.email}</td></tr>
		<tr><td class="label">性別</td><td>${LoginUser.gender == 'male' ? '男性' : '女性'}</td></tr>
		<tr><td class="label">年齢</td><td>${LoginUser.age}歳</td></tr>
	</table>
	
	<div class="bio-section">
		<p style="font-weight:bold; margin-top: 0 ;">自己紹介</p>
		<div class="bio-text">${LoginUser.bio}</div>
	</div>
	
	<a href="${pageContext.request.contextPath}/profileEdit" class="btn-edit">プロフィールを編集する</a>
	
	<a href="${pageContext.request.contextPath}/userMyPage" class="back-rink">マイページに戻る</a>
	
	</div>
</div>

</body>
</html>