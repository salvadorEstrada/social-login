<%@page import="mx.com.develop.controller.GoogleConnection"%>
<%@page import="mx.com.develop.controller.FBConnection"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	GoogleConnection googleCn = new GoogleConnection();
        FBConnection fbConnection = new FBConnection();
        
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    
   <meta name="google-signin-client_id" content="COPY YOUR CLIENT ID HERE">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Java Google Login</title>
</head>
<body style="text-align: center; margin: 0 auto;"> 
        <h1>Welcome, you could be login or register</h1>
	<div>  
          
              
		<a href="<%=googleCn.buildLoginUrl()%>"> <img
                        style="margin-top: 15px;" src="./img/2dywpzc.png" height="80px" width="350" />
		</a>  
              
	</div> 
        <div>       
        <a href="<%=fbConnection.getFBAuthUrl()%>"> <img
			
                 style="margin-top: 20px;" src="./img/facebook_btn.png" height="50px" width="320" /></a>        
        </div>   
       <div>
            
           <a href="signin"><img
			
                 style="margin-top: 20px;" src="./img/btn_twitter.png" height="60px" width="330" /></a>
         
       </div>
</body>

</html>
    