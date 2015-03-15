<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
	<form name="searchForm" action="viewInventory.html" method="post" >
		<label>Search Items: </label><input type="text" name="searchValue" value="${param.searchValue}" />
		<c:choose>
		<c:when test="${param.inStock == 'inStock'}">
			<input type="checkbox" value="inStock" name="inStock" checked/><label>In Stock</label>
		</c:when>
		<c:otherwise>
			<input type="checkbox" value="inStock" name="inStock" /><label>In Stock</label>
		</c:otherwise>
		</c:choose>
		<c:choose>
		<c:when test="${param.outStock == 'outStock'}">
			<input type="checkbox" value="outStock" name="outStock"  checked/><label>Out of Stock</label>
		</c:when>
		<c:otherwise>
			<input type="checkbox" value="outStock" name="outStock"  /><label>Out of Stock</label>
		</c:otherwise>
		</c:choose>
		<input type="submit" value="Search" />
	</form>
	
</div>
<div class="message">${message}</div>
<c:remove var="message"/>
<table border="1" cellpadding="10" width="100%">
<tr>
<th>Code</th>
<th>Name</th>
<th>Description</th>
<th>Type</th>
<th>UPC</th>
<th>Unit Price</th>
<th>Tax</th>
<th>Unit of Measure</th>
<th>Quantity</th>
<th>Total Price</th>
<th></th>
<th></th>
</tr>
<c:forEach items="${inventoryList}" var="inventory">
<tr>
<td>${inventory.itemCode }</td>
<td>${inventory.itemName }</td>
<td>${inventory.description }</td>
<td>${inventory.itemType }</td>
<td>${inventory.upcCode }</td>
<td>${inventory.unitPrice }</td>
<td>${inventory.tax }</td>
<td>${inventory.uom }</td>
<td>${inventory.quantity }</td>
<td>${inventory.totalPrice }</td>
<c:choose>

<c:when test="${inventory.quantity eq 0}">
<td><img src="styles/images/deletel.png" class="iconsDisabled"/></td>
</c:when>
<c:otherwise>
<td><a href="manageInventory.html?action=delete&itemQuantityId=${inventory.itemQuantityId}"><img src="styles/images/deletel.png" class="icons"/></a> </td>

</c:otherwise>

</c:choose>
<td><a href="manageInventory.html?action=edit&itemId=${inventory.itemId}&itemQuantityId=${inventory.itemQuantityId}"><img src="styles/images/editlogo.png" class="icons"/></a></td>
</tr>

</c:forEach>
</table>
<%@ include file="footer.jsp" %>
</body>
</html>