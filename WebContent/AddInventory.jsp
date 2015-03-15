<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Inventory</title>
<link rel="stylesheet" href="styles/style.css" media="all">
<script type="text/javascript" src="scripts/script.js"></script>
</head>
<body>
<%@ include file="header.jsp" %>
<section id="main" >
<form name="addForm" action="addInventory.html" method="post">
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
<th></th>
<th></th>
</tr>
<tr>
<td><input type="text" name="itemCode" value="" class="fiftypct" /></td>
<td><input type="text" name="itemName" value="" class="fiftypct" /></td>
<td><input type="text" name="description" value="" class="fiftypct" /></td>
<td><input type="text" name="itemType" value="" class="fiftypct" /></td>
<td>
<input type="text" name="upcCode" value="${inventory.upcCode }" class="fiftypct"/>
</td>
<td><input type="text" name="unitPrice" value="" class="fiftypct"/></td>
<td><input type="text" name="tax" value="" class="fiftypct"/></td>
<td><input type="text" name="uom" value="" class="fiftypct"/></td>
<td><input type="text" name="quantity" value="" class="fiftypct" /></td>
<td>
<a href="javascript:submitAddForm()"><img src="styles/images/save.png" class="icons"/></a> 
</td>
<td><a href="viewInventory.html"><img src="styles/images/cancel.png" class="icons"/></a></td>
</tr>
</table>
	
</form>
</section>
<%@ include file="footer.jsp" %>
</body>
</html>