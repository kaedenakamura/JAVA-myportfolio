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
	<h1>アカウント編集</h1>
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
	
	<button type="submit">更新する</button>
	<a href="list">キャンセル</a>
	</form>
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