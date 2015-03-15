function creditDisStatus(){
	var radios=document.checkoutForm.salestype;
	for (var i = 0; i < radios.length; i++) {
	    if (radios[i].checked) {
	        value = radios[i].value;  
	        var creditoption= document.getElementById("credit_option");
	        var creditcard=document.getElementById("credit_card");
	        var expdate=document.getElementById("exp_date");
	        if(value=="credit"){
	        	creditoption.style.display='block';
	        }    
	        else{
	        	creditoption.style.display='none';
	        }
	    }
	}
}
/*
$(function() {
$( "#expdate" ).datepicker();
});*/


var validCharactersRegex = new RegExp(/^\+?\d{3}[- ]?\d{3}[- ]?\d{4}$/);
var tel_invalid = function(value) {
    return validCharactersRegex.test(value);
};
var visaregex=new RegExp(/^\+?\d{4}[- ]?\d{4}[- ]?\d{4}[- ]?\d{4}$/);
var amexregex=new RegExp(/^\+?\d{4}[- ]?\d{6}[- ]?\d{5}$/);
var credit_valid = function(value) {
	var radiovalue=$("input:radio[name=credittype]:checked").val();
	       if(radiovalue=="visa"){
	        	return visaregex.test(value);
	        }    
	        else if(radiovalue=="ax"){
	        	return amexregex.test(value);
	       }
	return false;
};

function creditCardStatus() {
    var radiovalue = $("input:radio[name=credittype]:checked").val();
    var visaCreditCard = document.getElementById("visa_creditcard");
    var amexCreditCard = document.getElementById("amex_creditcard");
    var expdate = document.getElementById("exp_date");
    if (radiovalue == "visa") {
        visaCreditCard.style.display = 'block';
        $("#visacreditno").attr("required", "required");
        $("#visacreditno").attr("title", "VISA Card: XXXX-XXXX-XXXX-XXXX");
        $("#visacreditno").attr("pattern", "^\+?\d{4}[- ]?\d{4}[- ]?\d{4}[- ]?\d{4}$");
        amexCreditCard.style.display = 'none';
        $("#amexcreditno").removeAttr("title");
        $("#amexcreditno").removeAttr("pattern");
        $("#amexcreditno").removeAttr("required");
        expdate.style.display = 'block';
        $("#expdate").attr("required", "required");
        
    }
    else if (radiovalue == "ax") {
        amexCreditCard.style.display = 'block';
        $("#amexcreditno").attr("required", "required");
        $("#amexcreditno").attr("title", "Amex Card: XXXX-XXXXXX-XXXXX");
        $("#amexcreditno").attr("pattern", "^\+?\d{4}[- ]?\d{6}[- ]?\d{5}$");
        visaCreditCard.style.display = 'none';
        $("#visacreditno").removeAttr("title");
        $("#visacreditno").removeAttr("pattern");
        $("#visacreditno").removeAttr("required");
        expdate.style.display = 'block';
        $("#expdate").attr("required", "required");
    }
    else {
        amexCreditCard.style.display = 'none';
        visaCreditCard.style.display = 'none';
        expdate.style.display = 'none';
        $("#visacreditno").removeAttr("title");
        $("#visacreditno").removeAttr("pattern");
        $("#visacreditno").removeAttr("required");
        $("#amexcreditno").removeAttr("title");
        $("#amexcreditno").removeAttr("pattern");
        $("#amexcreditno").removeAttr("required");
        $("#expdate").removeAttr("required");
    }
}

$(document).ready(function () {
    // add span element after each input element
    $(":text").after("<span>*</span>");
    $("#pri_radio2").after("<span>*</span>");
    $("#telno").after("<span>*</span>");
    // move focus to first text box
    $("#firstname").focus();
    $("#checkoutForm").submit(function () {
        alert("Form Sumitted");
    });
});
