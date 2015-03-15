<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="styles/style.css" media="all">
<script type="text/javascript" src="scripts/jquery-1.10.2.js"></script>
<script type="text/javascript" src="scripts/script.js"></script>
<script type="text/javascript" src="scripts/checkoutscript.js"></script>
<title>Checkout</title>
</head>
<body>
<%@ include file="header.jsp" %>
	<section id="main">
		<div class="message">${message}</div>
		<c:remove var="message"/>
		<div id="form">
			<form id="checkoutForm" name="checkoutForm" action="checkout.html" method="post">
				<div class="input_div">
					<div class="input_label">
						<label for="firstname">First Name</label>
					</div>
					<div class="input_field">
						<input type="text" title="First Name should be at max 20 characters" pattern="[A-Za-z]{1,20}" value="${sessionScope.user.firstName}" id="firstname" name="firstname" required="required" />
					</div>
				</div>
				<div class="input_div">
					<div class="input_label">
						<label for="lastname">Last Name</label>
					</div>
					<div class="input_field">
						<input type="text" class="required" title="Last Name should be at max 25 characters" pattern="[A-Za-z]{1,25}" value="${sessionScope.user.lastName}" id="lastname" name="lastname" required="required">
					</div>
				</div>
				<div class="input_div">
					<div class="input_label">
						<label for="address">Delivery Address</label>
					</div>
					<div class="input_field">
						<input type="text" class="required" value="" id="address" name="address" required="required">
					</div>
				</div>
				<div class="input_div">
					<div class="input_label">
						<label for="address">City</label>
					</div>
					<div class="input_field">
						<input type="text" class="required" value="" id="city" name="city" required="required">
					</div>
				</div>
				<div class="input_div">
					<div class="input_label">
						<label for="address">State</label>
					</div>
					<div class="input_field">
						<input type="text" class="required" value="" id="state" name="state" required="required">
					</div>
				</div>
				<div class="input_div">
					<div class="input_label">
						<label for="address">Country</label>
					</div>
					<div class="input_field">
						<input type="text" class="required" value="" id="country" name="country" required="required">
					</div>
				</div>
				<div class="input_div">
					<div class="input_label">
						<label for="address">Zip</label>
					</div>
					<div class="input_field">
						<input type="text" class="required" value="" id="zip" name="zip" title="Zip should be a 6 digit number" pattern="[0-9]{5}" required="required">
					</div>
				</div>
				<div class="input_div">
					<div class="input_label">
						<label for="telno">Telephone No.</label>
					</div>
					<div class="input_field">
						<input type="tel" class="required" title="XXX-XXX-XXXX" pattern="^\+?\d{3}[- ]?\d{3}[- ]?\d{4}$" name="telno" id="telno" value="${sessionScope.user.phoneNumber}" required="required">
					</div>
				</div>
				<div class="input_div">
					<div class="input_label">
						<label for="salestype">Sales Type</label>
					</div>
					<div class="input_field">	
                           <input type="radio" name="salestype" id="radio-choice-1" value="cash" onchange="creditDisStatus()" />
                           <label for="radio-choice-1">Cash</label>
                           <input type="radio" name="salestype" id="radio-choice-2" value="credit" onchange="creditDisStatus()" />
                           <label for="radio-choice-2">Credit</label>
                    </div>
				</div>
				<div class="input_div" id="credit_option" style="display:none">
					<div class="input_label">
						<label for="credittype">Credit Card Type</label>
					</div>
					<div class="input_field">
						<input type="radio" class="required" name="credittype" value="ax" onchange="creditCardStatus()"> : <span>AX</span>
						<input type="radio" class="required" name="credittype" value="visa" onchange="creditCardStatus()"> : <span>VISA</span>
					</div>
				</div>
				<div class="input_div" id="visa_creditcard" style="display:none">
					<div class="input_label" >
						<label for="creditno">Credit Card No.</label>
					</div>
					<div class="input_field">
						<input type="text" name="creditno" id="visacreditno" value="" > 
					</div>
				</div>
                      <div class="input_div" id="amex_creditcard" style="display:none">
					<div class="input_label" >
						<label for="creditno">Credit Card No.</label>
					</div>
					<div class="input_field">
						<input type="text"   name="creditno" id="amexcreditno" value="" > 
					</div>
				</div>
				<div class="input_div" id="exp_date" style="display:none">
					<div class="input_label">
						<label for="expdate">Credit Card Exp. Date</label>
					</div>
					<div class="input_field">
						<input type="date" class="required" name="expdate" id="expdate" value="">
					</div>
				</div>
				<div class="input_div">
					<div class="input_label">
						<label for="amount">Total Amount</label>
					</div>
					<div class="input_field">
							<input type="text" value="${param.finalAmount!=null?param.finalAmount:finalAmount}"  id="amount" name="amount" />
					</div>
				</div>
				<div class="button_div">
					<input type="submit" id="finButton" value="Finalize">
				</div>
			</form>
		</div>
	</section>
	<%@ include file="footer.jsp" %>
</body>
</html>


