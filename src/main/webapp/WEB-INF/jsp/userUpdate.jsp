<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="myportfolio.User" %>
<%
	//Servletから渡されたユーザー情報
	User user =(User)request.getAttribute("user");	
	if (user== null){
		response.sendRedirect("list");
		return;
	}
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>アカウント編集</title>
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
	color:rgb(0, 128, 255);
	text-decoration:none;
}
.back-link:hover{text-decoration: underline;}
	
	
	</style>
<div class="form-container">
	<h2 class="header">アカウント編集</h2>
	<% String errorMsg = (String)session.getAttribute("errorMsg");
	if(errorMsg != null){
	%>
	<div style="color:#d9534f;backgroundcolor:#f2dede; border:1px solid #ebccd1;
	 padding: 10px margin-bottom: 20px; border-radius:4px;">
	 <%=errorMsg %>
	</div>
	<% 
	session.removeAttribute("errorMsg");
	}
	%>
	 <div class="form-group">
		<%--画像ファイルやファイルが含まれるバイナリーデータの場合、エンクタイプ（エンコードタイプ）設定"multipart/form-data"--%>
		<form action="update" method="post" enctype="multipart/form-data">
			<%--「重要」誰を更新するか判別するための隠しID --%>
			<input type="hidden" name="id" value="${user.id}">
			<div>
			<label>権限変更</label>
			<%--現在のロールに合わせてcheckedを切り替える --%>
			<input type="radio" name="role" value="1" onclick="toggleFields()" ${user.role == 1 ? 'checked' : ""}>管理者
			<input type="radio" name="role" value="0" onclick="toggleFields()" ${user.role == 0 ? 'checked' : ""}>一般
			</div>
			<%--一般ユーザーの時だけ見せたい項目--%>
			<div id="general-fields" style="display: ${user.role == 0 ? 'block' :'none'};">
			<p><label for="ruby">ふりがな：</label>
			<input type="text" name="ruby"id="ruby" value="${user.ruby}"><br>
			</p>
			<p><label>性別:</label>
				<input type="radio" name="gender" value="male" id="male" ${user.gender == "male" ? "checked" : ""}><label for="male">男</label>
				 <input type="radio" name="gender" value="female" id="female"${user.gender == 'female' ? 'checked' : ""}><label for="female">女</label>
			</p>
			<p><label for="age">年齢</label>
				<input type="number" name="age" min="0" max="999"id="age" value="${user.age}"><br>
			</p>
			<p><label for="bio">自己紹介</label>
				<textarea name="bio"id="bio" maxlength="1500">${user.bio}</textarea><br>
			</p>
			<p><label for="profileImage">プロフィール画像URL</label>
				<input type="file" name="profileImage"id="profileImage" value="${user.profileImage}"><br>
				<span>現在のファイル名: ${user.profileImage}</span>
			</p>
			</div>
		<%--共通項目--%>
			<p>
				<label for="name"  class="common-label">名前:</label><br>
				<input type="text" id="name" name="name" value="${user.name}"placeholder="255文字以下で入力してください" required>
			</p>
			<p>
				<label for="email"  class="common-label">メールアドレス:</label><br>
				<input type="email" id="email" name="email" value="${user.email}"placeholder="メールアドレスの形式で255文字以下で入力してください"required>
			</p>
			<p>
				<%--パスワードの入力 --%>
				<label for="password" class="common-label">パスワード(変更する場合入力してください)</label><br>
				<input type="password" id="password" name="password" placeholder="8-32文字の半角英数字で入力してください">
			</p>
			
			<button class="btn-submit" type="submit">更新する</button>
			<a class="back-link" href="list">キャンセル</a>
		</form>
	</div>
</div>
	<script>
	//jsにてフィールド切り替え（admin general）
	function toggleFields(){
    const role = document.querySelector('input[name="role"]:checked').value;
    const generalFields = document.getElementById("general-fields");
    // 文字列 "0" が一般 block表示 none非表示
    generalFields.style.display = (role === "0") ? "block" : "none";
}
	// 画面の読み込み（リロードや戻る）が完了した時も実行する
	window.onload = toggleFields;
</script>
</body>
</html>