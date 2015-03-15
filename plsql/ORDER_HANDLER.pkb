CREATE OR REPLACE PACKAGE BODY ORDER_HANDLER IS

Function Insert_Order (P_User_Id 		number, 
						P_First_Name 	varchar2,
						P_Last_Name  	varchar2,
						P_Address 		varchar2,
						P_City 			varchar2,
						P_State 		varchar2,
						P_Country 		varchar2,
						P_Zip_Code 		number,
						P_Payment_Type 	Varchar2,
						P_Credit_Card_Number varchar2,
						P_Expiry_Date 	varchar2,
						P_Contact_Number varchar2) return number as

l_header_id		number;
l_line_id		number;
l_payment_id 	number;
l_Item_Amount	number;
l_Tax_Amount 	number;
l_Total_Amount	number;
l_Total_Payment number;
l_return 		number;

Cursor get_view_cart is 
Select * from npu.view_cart 
where user_id = p_user_id;

Begin 

l_return := -1;

	select order_header_id_seq.nextval into l_header_id from dual;

l_return := -2;

	Insert into npu.Order_Headers (
		Header_id,
		Order_Number,
		Order_Date,
		First_Name,
		Last_Name,
		Address,
		City,
		State,
		Country,
		Zip_Code,
		Contact_Number)
	values (
		l_header_id,
		l_header_id,
		sysdate,
		P_First_Name,
		P_Last_Name,
		P_Address,
		P_City,
		P_State,
		P_Country,
		P_Zip_Code,
		P_Contact_Number);

	l_return := -3;

		l_Item_Amount 	:= 0;
		l_Tax_Amount 	:= 0;
		l_Total_Amount 	:= 0;

	FOR c1 in get_view_cart loop

		l_return := 4;
		select order_line_id_seq.nextval into l_line_id from dual;

		l_return := -5;

		Insert into npu.Order_Lines (
			Line_Id,
			Header_Id,
			Item_qty_Id,
			Quantity,
			Unit_Price,
			Tax,
			Total_Price)
		values(
			l_header_id,
			l_line_id,
			c1.Item_Qty_Id,
			c1.Quantity,
			c1.Unit_Price,
			c1.Total_Tax,
			c1.Total_Price);

		l_return := -6;

		l_Item_Amount 	:= l_Item_Amount + (c1.Quantity * c1.Unit_Price);
		l_Tax_Amount 	:=  l_Tax_Amount + c1.Total_Tax;
		l_Total_Amount 	:= l_Total_Amount + c1.Total_Price;

	END LOOP;

	l_return := -7;
	select order_payment_id_seq.nextval into l_payment_id from dual;

	l_return := -8;

	l_Total_Payment := l_Total_Amount;

	Insert into npu.Order_Payments (
		Payment_Id,
		Header_id,
		Line_Id,
		Payment_Type,
		Credit_Card_Number,
		CC_Expiry_Date,
		Due_Date,
		Item_Amount,
		Tax_Amount,
		Total_Amount,
		Discount_Amount,
		Total_Payment,
		Payment_Date)
	values (
		l_payment_id,
		l_header_id,
		Null,
		P_Payment_Type,
		P_Credit_Card_Number,
		P_Expiry_Date,
		Sysdate,
		l_Item_Amount,
		l_Tax_Amount,
		l_Total_Amount,
		Null,
		l_Total_Payment,
		Sysdate);

	l_return := -9;

	Delete from npu.save_cart 
	where user_id = p_user_id;

	COMMIT;

	return l_header_id;

EXCEPTION WHEN OTHERS THEN 
	ROLLBACK;
	return l_return;

END;

END ORDER_HANDLER;
/
