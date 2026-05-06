<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%--register.jsp--%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>my portfolio</title>
</head>
<body>
<div class="container">
	<h2>ユーザー登録</h2>
	<% 
	//URLのerror=1 を受け取る
	String error = request.getParameter("error");
	if(error != null && error.equals("1")){
	
	%>
	<p style="color:red; font-weight:bold;">
		⚠️ 全ての項目を入力してください
	</p>
	<% 
	//URLのerror=2 を受け取る
	}else if ("2".equals(error)){
	%>
	<p>メールアドレスもしくは名前(ふりがな)を225文字以下で設定してください</p>
	<%
	//URLのerror=3 を受け取る
	}else if ("3".equals(error)){
	%>
	<p>パスワードは8文字以上、３２文字以下で入力してください</p>
	<%-- register.jsp --%>
	<% 
	//URLのerror4を受け取る
	}else if("4".equals(error)){%>
	<p style="color:red;">  ふりがなをひらがな入力してください</p>
	<% 
	//URLのerror5を受け取る
	}else if("5".equals(error)){%>
	<p style="color:red;">  そのメールアドレスはすでに登録されています</p>
	<% 
	//URLのerror=6 を受け取る
	}else if ("6".equals(error)){
	%>
	<p>年齢を3桁以内で入力してください</p>
	<%-- register.jsp --%>
	<% 
	//URLのerror7を受け取る
	}else if("7".equals(error)){%>
	<p style="color:red;">自己紹介を1500文字以内で入力してください</p>
	<% 
	//URLのerror8を受け取る
	}else if("8".equals(error)){%>
	<p style="color:red;"> 性別を男性もしくは女性を選んでください</p>
	<% 
	//URLのerror9を受け取る
	}else if("9".equals(error)){%>
	<p style="color:red;">プロフィール画像の容量を２MB以内で収めてください</p>
	<% 
	//追加：URLのerror10を受け取る
	}else if("10".equals(error)){%>
	<p style="color:red;">メールアドレスの形式が違います。</p>
	<%
	}
	%>
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
	max-width:1000px;
	margin:40px auto;
	padding:30px;
	border-radius:12px;
	/*上下０左右４へ15影をつける　rgba色と透明度(000)は黒 0.05は透明度５％*/
	box-shadow:0 4px 15px rgba(0,0,0,0.05);
}
textarea{
 margin-top:10px ;
}
/* さらにテーブル自体をコンテナいっぱいに広げる */
table {
    width: 100%; 
    border-collapse: collapse;
    margin-top: 10px;
}
/*入力フォーム*/
.form-group{margin-bottom:20px;}
label{font-weight:bold; display:block; margin-top:15px;}
input[type="text"],
input[type="email"],
input[type="password"],
textarea, select{
width:100%; padding:10px; margin-top:5px; border:1px solid rgb(192, 192, 192);
 border-radius:4px; box-sizing:border-box;
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
.back-link:hover{
	text-decoration: underline;
}	
</style>
<%--画像ファイルやファイルが含まれるバイナリーデータの場合、エンクタイプ（エンコードタイプ）設定"multipart/form-data"--%>
	<form class="form-group" action="${pageContext.request.contextPath}/register" method="post" enctype="multipart/form-data">
		<div>
		<label>権限</label>
		<%--現在のロールに合わせてcheckedを切り替える --%>
		<input type="radio" name="role" id="role_admin" value="1" onclick="toggleFields()" ${user.role == 1 ? 'checked' : ""} <label for="role_admin">管理者</label>
		<label for="role_admin">管理者</label>
		<input type="radio" name="role"id="role_general" value="0" onclick="toggleFields()" ${user.role == 0 ? 'checked' : ""} <label for="role_general">一般</label>
		</div>
		<%--一般ユーザーの時だけ見せたい項目--%>
		<div id="general-fields" style="display:none;">
		<p>
            <label for="ruby">ふりがな：</label>
			<input type="text" name="ruby" id="ruby" value="${fn:escapeXml(ruby)}">
		</p>
		<p>
           	<label>性別:</label>
			<input type="radio" name="gender" value="male"id="male" ${gender == 'male' ? 'checked' : ''}  <label for="male"> 男</label>
			<input type="radio" name="gender" value="female" id="female" ${gender == 'female' ? 'checked' : ''} <label for="female"> 女</label>
		</p>
		<label for="age">年齢:</label>
		<input type="number" name="age"min="0" max="999" id="age" value="${fn:escapeXml(age)}"><br>
		<label for="bio">自己紹介:</label>
		<textarea name="bio"id="bio" maxlength="1500"  >${fn:escapeXml(bio)}</textarea><br>
		<label for="profileImage" >プロフィール画像を選択</label>
		<input type="file" id="profileImage" name="profileImage" ><br>
		</div>
	<%--共通項目--%>
	<p>	
		<label for="name" class="common-label">名前:</label>
		<input type="text"id="name" name="name" placeholder="名前" value="${fn:escapeXml(name)}">
	</p>
	<p>
		<label for="email" class="common-label">メールアドレス:</label>
		<input type="email"id="email" name="email" placeholder="メールアドレス" value="${fn:escapeXml(email)}">
	</p>
	<p>
		<label for="password" class="common-label">パスワード:</label>
		<input type="password"id="password" name="password" placeholder="パスワード" value="password">
	</p>
	<button type="btn-submit">登録する</button>
	</form>
<div>
	<a class="btn-submit" href="../list">ユーザー一覧へ</a>
</div>
<div>
	<a class="back-link" href="login.jsp">ログイン画面へ</a>
</div>

</div>
	
	
		<script>
		function toggleFields(){
			//radioボタンの値取得
			const role = document.querySelector('input[name="role"]:checked').value;
			console.log("現在のロールは:"+ role);
			//操作したい「ふりがなの箱」を取得
			const generalFields =document.getElementById("general-fields");
			//デバック確認
			//alert("ボタンが押されました！");
			if (role == "0"){
				//一般なら表示
				generalFields.style.display="block";
				}else{
					//管理者なら隠す（none）
					generalFields.style.display="none";
	
				}
			}
		// 画面の読み込み（リロードや戻る）が完了した時も実行する
		window.onload = toggleFields;
		</script>
	</body>
</html>


