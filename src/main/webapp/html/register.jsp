<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%--register.jsp--%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>my portfolio</title>
</head>
<body>
	<h1>ユーザー登録</h1>
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
	<p>メールアドレスもしくは名前を225文字以下で設定してください</p>
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
	//URLのerror8を受け取る
	}else if("9".equals(error)){%>
	<p style="color:red;">プロフィール画像の容量を２MB以内で収めてください</p>
	<%
	}
	%>
	<%--画像ファイルやファイルが含まれるバイナリーデータの場合、エンクタイプ（エンコードタイプ）設定"multipart/form-data"--%>
	<form action="../register" method="post" enctype="multipart/form-data">
		<div>
		<label>権限</label>
		<%--現在のロールに合わせてcheckedを切り替える --%>
		<input type="radio" name="role" id="role_admin" value="1" onclick="toggleFields()" ${user.role == 1 ? 'checked' : ""}>
		<label for="role_admin">管理者</label>
		<input type="radio" name="role"id="role_general" value="0" onclick="toggleFields()" ${user.role == 0 ? 'checked' : ""}>
		<label for="role_general">一般</label>
		</div>
		<%--一般ユーザーの時だけ見せたい項目--%>
		<div id="general-fields" style="display:none;">
		<p>
            <label for="ruby">ふりがな：</label>
			<input type="text" name="ruby" id="ruby">
		</p>
		<p>
           	<label>性別:</label>
			<input type="radio" name="gender" value="male"id="male" ><label for="male">男</label>
			<input type="radio" name="gender" value="female" id="female"><label for="female">女</label>
		</p>
		<label for="age">年齢</label>
		<input type="number" name="age"min="0" max="999" id="age"><br>
		<label for="bio">自己紹介</label>
		<textarea name="bio"id="bio" maxlength="1500" ></textarea><br>
		<label for="profileImage">プロフィール画像URL</label>
		<input type="file" id="profileImage" name="profileImage" ><br>
		</div>
	<%--共通項目--%>
	<p>	
		<label for="name" class="common-label">名前:</label>
		<input type="text"id="name" name="name" placeholder="名前">
	</p>
	<p>
		<label for="email" class="common-label">メールアドレス:</label>
		<input type="email"id="email" name="email" placeholder="メールアドレス">
	</p>
	<p>
		<label for="password" class="common-label">パスワード:</label>
		<input type="password"id="password" name="password" placeholder="パスワード">
	</p>
	<button type="submit">登録する</button>
	</form>
	<div>
	<a	href="../list">ユーザー一覧へ</a>
	</div>
	<div>
	<a href="login.jsp">ログイン画面へ</a>
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


