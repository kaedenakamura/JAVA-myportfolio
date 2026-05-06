<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${user.name}さんの詳細</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .profile-card {
            max-width: 600px;
            margin: 50px auto;
            padding: 30px;
            background: #fff;
            border-radius: 15px;
            box-shadow: 0 4px 20px rgba(0,0,0,0.1);
            text-align: center;
        }
        .bio-box {
            text-align: left;
            background: #fdfdfd;
            border: 1px solid #eee;
            padding: 20px;
            border-radius: 10px;
            margin: 20px 0;
            white-space: pre-wrap;
        }
        .like-btn {
            cursor: pointer;
            border: 2px solid #ff4b5c;
            background: none;
            border-radius: 25px;
            padding: 10px 25px;
            font-size: 1.1rem;
            transition: all 0.2s;
        }
        .like-btn.is-active {
            background-color: #ff4b5c;
            color: white;
        }
    </style>
</head>
<body>

    <div class="profile-card">
        <%-- 【要件：プロフィール情報表示】 --%>
        <h1>${user.name}</h1>
        <p>${user.gender == 'male' ? '男性' : '女性'}</p>
        
        <div class="bio-box">
            ${user.bio}
        </div>

        <p>現在の獲得数：<strong id="count-${user.id}">${user.likeCount}</strong></p>

        <%-- 【要件：非同期いいねボタン】 --%>
        <button type="button" 
                class="like-btn ${user.liked ? 'is-active' : ''}" 
                data-user-id="${user.id}">
            <span class="heart-icon">${user.liked ? '♥' : '♡'}</span>
            <span class="like-label">いいね！</span>
        </button>

        <div style="margin-top: 30px;">
            <a href="like">一般アカウント紹介一覧に戻る</a>
        </div>
    </div>

    <script>
    document.querySelectorAll('.like-btn').forEach(btn => {
        btn.addEventListener('click', async () => {
            const islogin = ${not empty sessionScope.LoginUser ? 'true' : 'false'};
            if(!islogin) {
                if(confirm('いいねするにはログインが必要です。ログイン画面へ移動しますか？')){
                    window.location.href = '${pageContext.request.contextPath}/login';
                }
                return;
            }

            const toUserId = btn.getAttribute("data-user-id");
            const heartIcon = btn.querySelector(".heart-icon");
            
            const params = new URLSearchParams();
            params.append('toUserId', toUserId);

            try {
                // UserDetailのdoPostへリクエスト
                const response = await fetch("${pageContext.request.contextPath}/UserDetail", {
                    method: "POST",
                    headers: { "Content-Type": "application/x-www-form-urlencoded" },
                    body: params
                });

                if(response.ok) {
                    const result = await response.json();
                    
                    // ボタン表示の切替
                    if(result.isLiked) {
                        btn.classList.add('is-active');
                        heartIcon.textContent = '♥';
                    } else {
                        btn.classList.remove('is-active');
                        heartIcon.textContent = '♡';
                    } 
            
                    // いいねの数リアルタイム更新
                    document.getElementById(`count-${toUserId}`).textContent = result.newCount;
                }
            } catch (error) {
                console.error("通信エラー:", error);
            }
        });
    });
    </script>
</body>
</html>