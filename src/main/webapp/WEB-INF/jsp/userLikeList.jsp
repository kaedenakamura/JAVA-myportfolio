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

	<h2>一般アカウント紹介一覧</h2>
	
	<div class="user-grid">
		<c:forEach var="user" items="${userList}">
			<div class="user-card">
			<h3>${user.name}</h3>	
			<p>${user.gender == 'male' ? '男性' : '女性'}</p>
			<p>${user.bio}</p>
			<p><img src="${pageContext.request.contextPath}/uploads/${LoginUser.profileImage}"
			 alt="プロフィール画像" style="width:100px; height:100px; border-radius:50%;"></p>
			
			
			<%--いいねボタン：data属性で相手のIDを保持する --%>	
			<%-- 
                 ボタン部分
                 1. data-user-id：JSが誰へのいいねかを知るため
                 2. user.isLiked()：Servletでセットしたハートを分類
            --%>
			
			<button type="button"
				class="like-btn ${user.isLiked() ? 'is-active' : '' }"
				<%--user.idをdata-user-idへ --%>
				data-user-id="${user.id}"> 
				<span class="heart-icon">${user.isLiked() ? '♥':'♡'}</span>
				<%--<span class="like-count">${user.likeCount}</span>--%>
				</button>
				
				
			<a href="UserDetail?id=${user.id}">詳細を見る</a>
			</div>
		</c:forEach>
	</div>
	
	<%-- JSで非同期処理--%>
	<script>
	// JSPの機能を使って、ログイン状態をJSの変数に代入しておく
	const isLoggedIn = ${not empty sessionScope.LoginUser ? 'true' : 'false'};
	
	//画面の読み込み終わったあと、
	document.addEventListener('DOMContentLoaded',() => {
		//画面にある.like-btnクラスボタンを全部集めてリストにする 
		const allButtons = document.querySelectorAll('.like-btn');
		//集めたボタンを順番に見てループ
		allButtons.forEach (btn => {
			//クリックが押されたとき処理する
			btn.addEventListener('click' , async () => {
				// 追加：通信する前に、ここでチェック
	            if (!isLoggedIn) {
	                if (confirm('いいねをするにはログインが必要です。ログイン画面へ移動しますか？'))
		               { window.location.href = '${pageContext.request.contextPath}/html/login.jsp';
	                }
	                return; //ログインしてない場合はログイン画面へreturn; 
	            }
				//相手のIDを読み取る
				const toUserId = btn.getAttribute('data-user-id');
				const heartIcon =btn.querySelector('.heart-icon');
				const count =btn.querySelector('.like-count');

			try{
				//LikeServletへapi通信
				const response = await fetch('like',{
					method:'POST',
					headers:{'Content-Type':'application/x-www-form-urlencoded'},
					body:'toUserId='+ toUserId
					
				});
				//Javaから帰ってきた最新情報を読み込み
				const data =await response.json();

				//javaからの返信(data)を下に画面表示を切替
				if(data.isLiked){
					btn.classList.add('is-active');
					heartIcon.innerText='♥';
					}else{
						btn.classList.remove('is-active');//背景を白に
						heartIcon.innerText = '♡';//ハートを白抜きに
						}
				//最新の合計数に変換
				//count.innerText = data.newCount;
			}catch(error){
				if(confilm('いいねするにはログインが必要です。ログイン画面に移動しますか')){
					//はいが押されたらログイン画面へリダイレクト
					window.location.href = "${pageContext.request.contextPath}/login";
					}
				}
			});
			});
		});
		</script>
		<div style="margin-top:20px"></div>
		 <a href="userMyPage" class="back-link">マイページへ戻る</a>
		</body>
		</html>