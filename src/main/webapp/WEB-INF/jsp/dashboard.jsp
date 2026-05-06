<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--UserクラスとUserDaoクラスが使えるように... インポート--%>
<%@ page import="myportfolio.User" %>
<%@ page import="myportfolio.UserDao" %>
<%@ page import="java.util.List" %>

<%

//セッションから「LoginUser」という名前の荷物を取り出す
//取り出すときは(User)で「user型のデータ」と認識させる
User loginUser =(User)session.getAttribute("LoginUser");
if (loginUser == null) {
	response.sendRedirect("login.jsp");
	return;
}
%>

<% 
//System.out.println("--- デバッグ ---");
//System.out.println("loginUserの中身: " + loginUser);
//System.out.println("検索に使うID: " + loginUser.getId());
//セッションIDを使って、findByIdメソッドを使い自身のDBにある情報を取得
UserDao dao = new UserDao();
User latestUser =dao.findById(loginUser.getId());
//安全対策
System.out.println(latestUser);
if (latestUser == null ){
	session.invalidate();
	response.sendRedirect("login.jsp");
	return;
}

%>


<!DOCTYPE	html>
<html>
<head>
<meta charset="UTF-8">
<title>ダッシュボード | My Portfolio</title>
<style>
 	*{box-sizing:border-box;}
	body { display:flex; font-family: sans-serif; margin:0;}
	/* サイドバーのスタイルposition fixdで場所固定 上から左固定高さ画面いっぱい */
	.sidebar{width:250px; background: #333; color:#fff; height:100vh; padding:20px; 
	position: fixed; top:0; left:0; height:100vh;}
	.sidebar a {color: #fff; text-decoration: none; display:block; padding:10px 0;}
	.sidebar a:hover{color: #ffeb3b;}
	/* メインコンテンツのスタイル marginleftはサイドバーの為  flex-growで最大幅 box-sizingでパディング含めた幅計算*/
	.main-content{margin-left:290px ;padding: 20px;background: #f4f4f4; min-height:100vh;
	flex-grow:1; box-sizing: border-box;}
	.card {background: #fff; padding:15px; margin-bottom: 20px ; border-radius: 8px ; box-shadow: 0 2px 5px rgba(0,0,0,1);}
	table {width: 100%; border-collapse: collapse;}
	th , td {border-bottom: 1px solid #ddd; padding: 10px; text-align: left; }
	/*ランキングエリアの横並びコンテナ*/
	.ranking-container{
		display:flex;
		gap:20px;/* カードとカードの間の隙間 */
		width:100%;/*メインコンテンツ幅いっぱい*/
	}
	/*各ランキングカードの横幅を半分に*/
	.ranking-card{
		flex: 1; 
	}
</style>
</head>
<body>
<%--サイドバーの設置 --%>
<aside class="sidebar">
	<h2>Menu</h2>
	<a href="${pageContext.request.contextPath}/list">アカウント一覧</a>
	<a href="${pageContext.request.contextPath}/contact">お問い合わせ一覧</a>
	<a href="${pageContext.request.contextPath}/category">カテゴリー一覧</a>
	<a href="${pageContext.request.contextPath}/contact?action=new">新規お問合せ</a>
	<a href="${pageContext.request.contextPath}/like">公開アカウント一覧を見る</a>
</aside
>
<main class="main-content">
	<h1>管理者ダッシュボード</h1>
	<p>ようこそ、<%= latestUser.getName() %> さん！</p>
	<%
	//Servletから届いた「activeCount」を取り出して変数に入れる
	Integer activeCount = (Integer)request.getAttribute("activeCount");
	if (activeCount == null){ 
		activeCount = 0;
	}
	%>
	<div class="card">
		<h3>📊統計情報</h3>
		<p>現在のアクティブユーザー数:<span style="font-size:1.5em; color: #2980b9; font-weight:bold;">
		<%= activeCount %></span> 名</p>
	</div>

	<div class ="ranking-container">
	<div class="card ranking-card">
		<h3>　今月のいいねランキング</h3>
			<table>
				<thead>
					<tr>
						<th>順位</th>
						<th>名前</th>
						<th>獲得いいね数</th>
					</tr>
				</thead>
			<tbody>
	<% 
	//Servletから送られてきたリストを受け取る
	List<User> ranking = (List<User>)request.getAttribute("monthlyRanking");
	if(ranking !=null && !ranking.isEmpty()){
		int rank = 1;
		for(User u : ranking){
			
	%>
	<tr>
		<td><%= rank++ %>位</td>
		<td><%= u.getName()%></td>
		<td><%= u.getLikeCount() %></td>
	</tr>
	<% 
		}
	}else {
	
	%>	
		<tr>
			<td colspan="3" style="text-align: center; color: #999;">まだいいねデータがありません</td>
		</tr>
	<% 
	}
	%>
	</tbody>
</table>
</div>

<div class="card ranking-card">
	<h3>📊年間いいねランキング</h3>
	<table>
		<thead>
			<tr>
				<th>順位</th>
				<th>名前</th>
				<th>獲得いいね数</th>
			</tr>
		</thead>
		<tbody>
		<%
		List<User> yearly = (List<User>)request.getAttribute("yearlyRanking");
		if(yearly != null && !yearly.isEmpty()){
			int rank = 1;
			for(User u : yearly){	
		%>
		<tr>
			<td><%=rank++ %>位</td>
			<td><%=u.getName() %></td>
			<td><%=u.getLikeCount() %></td>
		</tr>
		<% }}
		else {
		%>
			<tr>
			<td colspan="3" style="text-align: center; color: #999;">まだいいねデータがありません</td>
			</tr>
		<% 
			}
		%>
	</tbody>
</table>
</div>
</div>
<a href="${pageContext.request.contextPath}/logout" style="margin-top:20px; display: block; ">ログアウト </a>
</main>
</body>
</html>

