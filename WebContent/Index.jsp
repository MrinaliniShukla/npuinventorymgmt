<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
<div class="searchDiv">
	<form name="searchForm" action="index.html" method="post" >
		<label>Search Items: </label><input type="text" name="searchValue" value="${param.searchValue}" />
		<input type="submit" value="Search" />
	</form>
</div>
<div class="message">${message}</div>
<c:remove var="message"/>
<form name="addcartForm" action="index.html" method="post">
<input type="hidden" name="action" value="addtocart" />
<input type="hidden" name="itemQuantityId" value="" />
<input type="hidden" name="quantity" value="" />
<div class="itemrow">
<c:forEach items="${inventoryList}" var="inventory" varStatus="counter">

<div class="itemcell">
<div class="fullwidth"><img src="styles/images/item_image.jpg" width="50%" height="100px" /></div>
<div class="title">${inventory.itemName}</div>
<div class="fullwidth">
	<div class="data description">${inventory.description}</div>
</div>
<div class="fullwidth">
	<div class="data upc">UPC # ${inventory.upcCode}</div>
</div>
<div class="fullwidth">
<div class="data itemprice">
	<c:choose>
	<c:when test="${inventory.quantity > 0}">
		<fmt:formatNumber value="${inventory.unitPrice }" type="currency"/>
	</c:when>
	<c:otherwise>&nbsp;</c:otherwise>
	</c:choose>
</div>
</div>
<div class="fullwidth">
	In Stock : ${inventory.quantity}
</div>
<div class="fullwidth">
	Quantity : <input type="text" name="quantity${counter.index}" id="quantity${counter.index}" value="1" size="3"/>
</div>

<c:choose>
<c:when test="${inventory.quantity > 0}">
<div class="fullwidth buttonDiv">
	<a href="#" onclick="addtocart(${inventory.itemQuantityId},'quantity${counter.index}',${inventory.quantity})"><img src="styles/images/addtocart1.jpg" width="75%" height="30px" class="addtocart" />
	</a>
</div>
</c:when>
<c:otherwise>
<div class="fullwidth outofstock">
	OUT OF STOCK
</div>
</c:otherwise>
</c:choose>


</div>
</c:forEach>
</div>
</form>
<%@ include file="footer.jsp" %>
</body>
</html>