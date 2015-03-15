<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
<link rel="stylesheet" href="styles/style.css" media="all">
<script type="text/javascript" src="scripts/script.js"></script>
</head>
<body>
<%@ include file="header.jsp" %>
<div class="message">${message}</div>
<c:remove var="message"/>
<section id="main">
<form name="RegistrationForm" action="registration.html" method="post">
  <fieldset class="register">
    <legend>Register:</legend>
    <div class="fullwidth">
	    <div  class="labelDiv">
	    	<label>User Name:</label>
	    </div>
	    <div class="inputDiv">
	    <input type="text" name="userName" value="" required=required/>
	    </div >
    </div>
    <div class="fullwidth">
	    <div  class="labelDiv">
		<label >Password:</label>
		</div>
		<div class="inputDiv">
		<input type="password" name="password" value="" required=required/>
		</div>
	</div>
    <div class="fullwidth">
	    <div  class="labelDiv">
	    	<label>First Name:</label>
	    </div>
	    <div class="inputDiv">
	    <input type="text" name="firstName" value="" title="First Name should be at max 20 characters" pattern="[A-Za-z]{1,20}" required="required"/>
	    </div >
    </div>
    <div class="fullwidth">
	    <div  class="labelDiv">
	    	<label>Last Name:</label>
	    </div>
	    <div class="inputDiv">
	    <input type="text" name="lastName" value="" title="Last Name should be at max 25 characters" pattern="[A-Za-z]{1,25}"  required="required"/>
	    </div >
    </div>
    <div class="fullwidth">
    <div  class="labelDiv">
    	<label>Phone Number:</label>
    </div>
    <div class="inputDiv">
    <input type="tel" name="phoneNumber" value="" title="XXX-XXX-XXXX" pattern="^\+?\d{3}[- ]?\d{3}[- ]?\d{4}$" required="required"/>
    </div >
    </div>
    <div class="fullwidth">
    <div  class="labelDiv">
    	<label>Email:</label>
    </div>
    <div class="inputDiv">
    <input type="text" name="email" value="" title="example@domain.com" pattern="^[-0-9a-zA-Z.+_]+@[-0-9a-zA-Z.+_]+\.[a-zA-Z]{2,4}$" required="required"/>
    </div >
    </div>
    <div  class="labelDiv">
    	<label>&nbsp;</label>
    </div>
    <div class="inputDiv">
	<input type="submit" name="submit" value="Submit" />
	</div>
 </fieldset>
</form> 
</section>
<%@ include file="footer.jsp" %>
</body>
</html>