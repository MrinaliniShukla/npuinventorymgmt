<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View Inventory</title>
<link rel="stylesheet" href="styles/style.css" media="all">
<script type="text/javascript" src="scripts/script.js"></script>
</head>
<body>
<%@ include file="header.jsp" %>
<section id="main">
<form name="viewcartForm" action="index.html" method="post" class="viewcart">
<input type="hidden" name="itemQuantityId" value="" />
<input type="hidden" name="quantity" value="" />
<input type="hidden" name="action" value="" />

<c:choose>
<c:when test="${fn:length(inventoryList) gt 0}">
<table width="80%">
<c:set var="finalAmount" value="0.0"/>
<c:forEach items="${inventoryList}" var="inventory" varStatus="count">
<input type="hidden" name="itemQuantityId${count.index}" id="itemQuantityId${count.index}" value="${inventory.itemQuantityId}" />
<tr>
	<td><img src="styles/images/item_image.jpg" width="50%" height="100px" /></td>
	<td>
	<div class="title">${inventory.itemName}</div>
	<div class="data description">${inventory.description}</div>
	<div class="data upc">UPC # ${inventory.upcCode}</div>
	</td>
	<td><div class="fullwidth">
	<span class="pct35">Quantity : </span><input type="text" id="quantity${count.index}" name="quantity${count.index}" value="${inventory.quantity}" size="3"/></div>
	<div class="fullwidth"><span class="pct35">&nbsp;</span><a href="#" onclick="updateCart(itemQuantityId${count.index},quantity${count.index})">Update</a></div>
	</td>
	<td>
	<div class="data price"><fmt:formatNumber value="${inventory.totalPrice }" type="currency"/></div>
	<c:set var="finalAmount" value="${finalAmount+inventory.totalPrice }"/>
	</td>
</tr>
</c:forEach>
<tr>
	<td colspan="4" >
		<div class="data price">
			<input type="hidden" name="finalAmount"  value="${finalAmount}"/>
			<fmt:formatNumber value="${finalAmount}" type="currency"/>
		</div>
	</td>
</tr>

</table>
<div class="fullwidth">
	<div class="cartbutton">
		<a href="checkout.html?finalAmount=${finalAmount}"><img src="styles/images/continue-checkout-button.gif" width="200px" /></a>
	</div>
	<div class="cartbutton">
		<a href="index.html">Continue Shopping</a>
	</div>
</div>
</c:when>
<c:otherwise>
<div class="message">Your Cart is Empty</div>
</c:otherwise>
</c:choose>

</form>
</section>
<%@ include file="footer.jsp" %>
</body>
</html>