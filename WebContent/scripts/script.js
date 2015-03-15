function submitEditForm(){
	document.editForm.submit();
}
function submitAddForm(){
	document.addForm.submit();
}
function addtocart(itemQuantityId,quant,actualQuant){
	orderedQuant=document.getElementById(quant).value;
	if(orderedQuant>actualQuant){
		alert("Request not completed as requested quantity is more than available.");
		return false;
	}
	document.addcartForm.itemQuantityId.value=itemQuantityId;
	document.addcartForm.quantity.value=orderedQuant;
	document.addcartForm.submit();
}

function updateCart(quantityidName,quantityId,availableQuant){
	document.viewcartForm.action.value="updatecart";
	document.viewcartForm.itemQuantityId.value=quantityidName.value;
	document.viewcartForm.quantity.value=quantityId.value;
	document.viewcartForm.submit();
}


