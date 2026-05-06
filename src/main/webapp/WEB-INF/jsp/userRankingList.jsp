<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>一般アカウント紹介</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
	<style>
	/* 全体のレイアウト設定 */
.user-grid {
    display: grid;
    /* 1列の幅を大きく、画面中央に寄せる */
    grid-template-columns: repeat(auto-fit, minmax(300px, 1fr)); 
    gap: 30px;            /* カード同士の間隔を広くしてゆとりを出す */
    max-width: 1000px;    
    margin:auto;  
    padding: 0 20px;      /* スマホ表示時の左右の余白 */
}

/* ユーザーカード */
.user-card {
    border: 1px solid #eee;
    padding: 25px;
    border-radius: 15px; 
    text-align: center;
    background-color: #fff;
    box-shadow: 0 4px 15px rgba(0,0,0,0.05); 
    transition: transform 0.3s ease; 
}
.user-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 8px 25px rgba(0,0,0,0.1);
}
h2{
	font-size:2rem;
	text-align:center;
}
.user-card h3 {
    font-size: 1.5rem;
	text-align:center;
    margin-bottom: 10px;
    color: #333;
}
/*順位バッチの装飾*/
.rank-badge{
	display: inline-block;
            width: 40px;
            height: 40px;
            line-height: 40px;
            border-radius: 50%;
            background-color: #666;
            color: rgb(255, 255, 255);
            font-weight: bold;
            margin-bottom: 15px;
}


/* 自己紹介文（bio）の調整 */
.user-card p {
    color: #666;
    line-height: 1.6;
    margin-bottom: 15px;
}

/* いいねボタンを大きく押しやすく */
.like-btn {
    cursor: pointer;
    border: 2px solid #ff4b5c;
    background: none;
    border-radius: 25px;
    padding: 10px 20px;
    font-size: 1.1rem;
    transition: all 0.2s;
}

.like-btn.is-active {
    background-color: #ff4b5c;
    color: white;
}

/*戻るリンク*/
.back-link{
	display:block;
	text-align:center;
	margin-top:20px;
	color:rgb(0, 128, 255);
	text-decoration:none;
}
.back-link:hover{
	text-decoration: underline;
}
	</style>
</head>
<body>
	<h2>月のいいねランキング一覧</h2>
	
	<div class="user-grid">
		<c:forEach var="user" items="${rankingList}" varStatus="status">
			<div class="user-card">
			<!--順位の表示-->
			<h3>${user.name}</h3>
			<p>${user.gender == 'male' ? '男性' : '女性'}</p>
			<p>${user.bio}</p>
			<p>今月の獲得数：<strong id="count-${user.id}">${user.likeCount}</strong></p>
			<button type="button"
			      class="like-btn ${user.liked ? 'is-active' : ''}"
			      data-user-id="${user.id}">
			  <span class="heart-icon">${user.liked ? '♥' : '♡'}</span>
			  <span class="like-count">${user.likeCount}</span>
			</button>
			
			<br><br>
                <a href="UserDetail?id=${user.id}">詳細プロフィールを見る</a>
		
			</div>
		</c:forEach>
	</div>
	
	
	<%-- 非同期いいねのJavaScript --%>
		<script>
		// btnを一つ一つ読み込んで処理
		document.querySelectorAll('.like-btn').forEach(btn => {
			btn.addEventListener('click', async () => {
				// ログイン状態の判定(セッションにユーザーあるか)
				const islogin =${not empty sessionScope.LoginUser ? 'true' : 'false'};
				if(!islogin){
					if(confirm('いいねするにはログインが必要です。ログイン画面へ移動しますか？')){
						window.location.href = '${pageContext.request.contextPath}/login';
							}
					return;	
				}
				const toUserId = btn.getAttribute("data-user-id");
				const heartIcon =btn.querySelector(".heart-icon");
				
				const params = new URLSearchParams();
				params.append('toUserId',toUserId);

				try{
					//RankingServletのdoPostにリクエスト送る
					const response = await fetch("${pageContext.request.contextPath}/Ranking",{
							method:"POST",
							headers: { "Content-Type": "application/x-www-form-urlencoded" },
							body:params
							});
					// awaitの処理通信が帰ってきたら、ボタン切り替え最新のものへいいねの数のリアルタイム更新
					if(response.ok){
						const result =await response.json();
						//ボダン表示の切替
						if(result.isLiked){
							btn.classList.add('is-active');
							heartIcon.textContent = '♥';
							}else{
								btn.classList.remove('is-active');
								heartIcon.textContent = '♡';
								} 
				
					//いいねの数リアルタイム更新
						document.getElementById(`count-${toUserId}`).textContent = result.newCount;
					}
			}catch (error){
				console.error("通信エラー:",error);
				}
				
			});
			
		});
		</script>
		<a href="${pageContext.request.contextPath}/userMyPage" class="">マイページに戻る</a>
	</body>
</html>