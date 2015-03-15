<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit Inventory</title>
<link rel="stylesheet" href="styles/style.css" media="all">
<script type="text/javascript" src="scripts/script.js"></script>
</head>
<body>
<%@ include file="header.jsp" %>
<section id="main" >
<h3>Edit Inventory</h3>
<form name="editForm" action="manageInventory.html" method="post">
<input type="hidden" name="itemId" value="${inventory.itemId}" />
<input type="hidden" name="itemQtyId" value="${inventory.itemQuantityId}" />
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
<tr>
<td><input type="text" name="itemCode" value="${inventory.itemCode}" class="fiftypct noborder" readonly="readonly" /></td>
<td><input type="text" name="itemName" value="${inventory.itemName}" class="fiftypct noborder" readonly="readonly" /></td>
<td><input type="text" name="description" value="${inventory.description}" class="fiftypct noborder" readonly="readonly" /></td>
<td><input type="text" name="itemType" value="${inventory.itemType}" class="fiftypct noborder" readonly="readonly" /></td>
<td>
<c:choose>
<c:when test="${inventory.quantity eq 0}">
<input type="text" name="upcCode" value="${inventory.upcCode }" class="fiftypct noborder" readonly="readonly"/>
</c:when>
<c:otherwise>${inventory.upcCode }</c:otherwise>
</c:choose>

</td>
<td><input type="text" name="unitPrice" value="${inventory.unitPrice }" class="fiftypct"/></td>
<td><input type="text" name="tax" value="${inventory.tax }" class="fiftypct"/></td>
<td><input type="text" name="uom" value="${inventory.uom }" class="fiftypct"/></td>
<td><input type="text" name="quantity" value="${inventory.quantity }" class="fiftypct" /></td>
<td><input type="text" name="totalPrice" value="${inventory.totalPrice }" class="fiftypct noborder"/></td>
<td>
<a href="javascript:submitEditForm()"><img src="styles/images/save.png" class="icons"/></a> 
</td>
<td><a href="viewInventory.html"><img src="styles/images/cancel.png" class="icons"/></a></td>
</tr>
</table>
	
</form>
</section>
<%@ include file="footer.jsp" %>
</body>
</html>