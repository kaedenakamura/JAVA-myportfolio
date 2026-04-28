<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
</style>
<body>
<main class="main-content">
	<header>
		<h1>マイページ</h1>
	</header>
		
		<section class="card">
			<h2>${user.name}さんのステータス</h2>
		<div class="user-info">
			<p>メールアドレス:${user.email}</p>
			<p>権限:${user.role == 1 ? "管理者" : "一般ユーザー" }</p>
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
		<a href="${pageContext.request.contextPath}/accountSettings">アカウント設定更新画面へ</a><br>
		<a href="${pageContext.request.contextPath }/logout" class="back-link">ログアウト</a>
		</div>
	</main>

</body>
</html>