<%--1. 設定；javaリストやUserクラスを使用できるようにする--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, myportfolio.User" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>削除済みユーザー一覧</title>
    </head>
    <body>	
    <div class="container">
    	<h2>削除済みユーザ一一覧</h2>
    
    <style>
    *{box-sizing:border-box;}
    /*画面幅が768以下（スマホ・タブレット）になったら適応 ヘッダーの高さを低くする*/
    @media screen and(max-width:786px){
    .header{height:60px;}
    .item{width:100%;}
    /*画像設定一応おいておく 横幅に合わせて高さ調整*/
    }
    /*全体のリセットと背景*/
body{
	font-family:"sans-serif";
	background-color: rgb(255, 255, 255);
	color:rgb(0, 0, 0);
	margin:0;
	padding:20px;
	}
    h2{
	color: rgb(0, 0, 0);
	border-bottom:2px solid rgb(255, 255, 128);
	padding-bottom:10px;
	margin-bottom:25px;
}
    .container{flex-wrap:wrap;
    background:rgb(255, 255, 255);
	max-width:800px;
	margin:40px auto;
	padding:30px;
	border-radius:12px;
	/*上下０左右４へ15影をつける　rgba色と透明度(000)は黒 0.05は透明度５％*/
	box-shadow:0 4px 15px rgba(0,0,0,0.05);
    }
    .table-container{whidth:100%;overflow-x:auto; -webkit-overflow-scrolling:touch; margin-top:20px;}
   
    </style>
       <style>
     table {whidth: 100%; border-collapse: collapse; margin-top: 20px;}
     th, td {border:1px solid rgb(0, 255, 128); padding: 10px; text-align; left;}
     th    {background-color: rgb(255, 255, 128);}
    </style>
    <%--メッセージがあれば表示する（DeleteメソッドやUpdateメソッド起動完了時に出現） --%>
    <%
    	String message = (String)session.getAttribute("msg");
    	if(message !=null){
    %>
       <div style="color=:blue ; font-weight:bold; backgroundcolor:#f8f9fa;; padding; 10px border-radius:5px margin-bottom:10px;">
       <%= message %>
       </div>
       
    <%
    //1度表示したら消すsession.removeAttribute
    	session.removeAttribute("msg");
    }
    %>
  	<div class="item">
  		<div class="table-container">
	    <table border="1">
	    	<tr>
	    	<th>id</th>
	    	<th>名前</th>
	    	<th>メールアドレス</th>
	    	<th>ステータス</th>
	    	<th>操作</th>
	    	</tr>
	    	<%
	    	//Servletから飛ばしたユーザーリストの取得
	    	List<User> deletedList = (List<User>) request.getAttribute("deletedUsers");
	    	
	    	if(deletedList !=null && deletedList.size() != 0){
	    		for(User user : deletedList){
	    			System.out.println(user.getId());
	    			System.out.println(user.getName());
	    			System.out.println(user.getEmail());
	    	%>
	    	<tr> 
	    		<td><%= user.getId()   %> </td>
	    		<td><%= user.getName() %></td>
	    		<td><%= user.getEmail() %></td>
	    		<td>削除済み</td>
	    		<td>
					<%--復元リンク --%>
	    			<a href="restore?id=<%= user.getId() %>" >復元</a>
					<%--削除リンク --%>    		
	    			<a href="physicalDelete?id=<%= user.getId() %>" 
	       				onclick="return checkDelete('<%= user.getName() %>')" 
	       				style="color:red;">完全削除</a>
	    		</td>
	    		</tr>
	    	<%
	    		}//for文閉じ
	    		}else{
	    			%>
	    			<tr>
	    			<td colspan="5" style="text-align: center;">
	    			削除済みユーザーは存在しません。データが見つかりませんでした。</td>
	    			</tr>
	    	 <%
	    		}
	    	 %>
	    	 </table>
	    	     	 
	    	 <p><a href="/myportfolio/html/register.jsp">新規登録画面へ戻る</a></p>
	    	 	
	    	 <script>
	    	 function checkDelete(name){
	        	 //confirmの結果(true/false)そのまま返す
	        	 return confirm(name +"さんを本当に削除しますか？")
	        	 }	    
	    	 </script>
	    </div>
	   </div>
	  </div>
    </body>
    </html>