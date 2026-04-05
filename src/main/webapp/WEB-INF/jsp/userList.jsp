<%--1. 設定；javaリストやUserクラスを使用できるようにする--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, myportfolio.User" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ユーザ一一覧画面</title>
    </head>
    <body>	
    <h1>ユーザ一一覧</h1>
    <style>
     table {whidth: 100% border-collapse: collapse; margin-top: 20px;}
     th, td {border:1px solid rgb(0, 255, 128); padding: 10px; text-align; left;}
     th    {background-color: rgb(255, 255, 128);}
      {
	
}
    </style>
    <table border="1">
    	<tr>
    	<th>名前</th>
    	<th>メールアドレス</th>
    	</tr>
    	<%
    	
    	//Servletから飛ばしたユーザーリストの取得
    	List<User> userList = (List<User>) request.getAttribute("userList");
    	
    	if(userList !=null && userList.size() != 0){
    		for(User user : userList){
    	%>
    	<tr> 
    	<td><%= user.getName() %></td>
    	<td><%= user.getEmail() %></td>
    	</tr>
    	<%
    		}
    		}else{
    			%>
    			<tr>
    			<td colspan="2" style="text-align: center;">
    			ユーザーは存在しません。データが見つかりませんでした。</td>
    			</tr>
    	 <%
    		}
    	 %>
    	 </table>
    	 
    	 <p><a href="/myportfolio/html/register.html">新規登録画面へ戻る</a>
    	 	
    
    
    </body>
    </html>