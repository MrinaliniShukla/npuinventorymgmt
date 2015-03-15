<header>
	<a href="index.html"><img src="styles/images/logo_inventory.png" alt="" class="home_img"></a>
	<div class="sitename">Ecart</div>
	<div class="accountlinks">
	<c:choose>
	<c:when test="${sessionScope.user!= null}">
		Welcome ${sessionScope.user.firstName} 
		<a href="logout.html">logout</a>
	</c:when>
	<c:otherwise>
	<a href="registration.html">Register</a> |
	<a href="login.html">login</a>
	</c:otherwise>
	</c:choose>
	</div>
</header>
<div class="viewcartDiv"><a href="index.html?action=viewCart">View Cart</a></div>
<c:if test="${sessionScope.user!= null}">
	<div id="nav">
	<ul class="tabs">
	<c:if test="${sessionScope.user.inventoryManager=='Y'}">
		<c:choose>
		<c:when test="${pageName == 'ViewInventory' }">
			<li class="active"><a href="viewInventory.html" >View Inventory</a></li>
		</c:when>
		<c:otherwise>
			<li><a href="viewInventory.html" >View Inventory</a></li>
		</c:otherwise>
		</c:choose>
		<c:choose>
		<c:when test="${pageName == 'AddInventory' }">
			<li class="active"><a href="addInventory.html">Add Inventory</a></li>
		</c:when>
		<c:otherwise>
			<li><a href="addInventory.html">Add Inventory</a></li>
		</c:otherwise>
		</c:choose>
	</c:if>
	<c:if test="${sessionScope.user.onlineLogin eq 'Y'}">
		<c:choose>
		<c:when test="${pageName == 'Order' }">
			<li class="active"><a href="index.html">Order</a></li>
		</c:when>
		<c:otherwise>
			<li><a href="index.html">Order</a></li> 
		</c:otherwise>
		</c:choose>
	</c:if>
	</ul>
	</div>
</c:if>