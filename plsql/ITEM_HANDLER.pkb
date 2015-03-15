CREATE OR REPLACE PACKAGE BODY ITEM_HANDLER AS

FUNCTION DELETE_ITEM (P_ITEM_QTY_ID NUMBER) 
RETURN NUMBER IS

l_return number := 0;

BEGIN

	--delete from npu.item_quantity
	--where item_qty_id = p_item_qty_id;

	update npu.item_quantity
	set unit_price 	= 0,
		tax 		= 0,
		quantity 	= 0,
		total_price = 0,
		last_update_date = sysdate,
		last_updated_by = -1
	where item_qty_id = p_item_qty_id;	

	if sql%rowcount > 0 then 
		l_return := 0;
	else
		l_return := 1;
	end if;

	commit;

return l_return;

EXCEPTION WHEN OTHERS THEN 
	return -1;
END DELETE_ITEM;


FUNCTION UPDATE_ITEM (P_ITEM_ID NUMBER, 
					  P_ITEM_CODE VARCHAR2,
					  P_ITEM_NAME VARCHAR2,
					  P_ITEM_QTY_ID NUMBER, 
					  P_UPC_CODE VARCHAR2, 
					  P_UNIT_PRICE NUMBER, 
					  P_TAX NUMBER, 
					  P_UOM VARCHAR2, 
					  P_QUANTITY NUMBER) 
RETURN NUMBER IS

l_return number := 0;

BEGIN

if P_ITEM_QTY_ID is NULL or P_ITEM_QTY_ID = 0 then 
	l_return := ITEM_HANDLER.INSERT_ITEM(P_ITEM_ID, 
										 P_ITEM_CODE,
										 P_ITEM_NAME,
										 P_UPC_CODE, 
										 P_UNIT_PRICE,
										 P_TAX, 
										 P_UOM, 
										 P_QUANTITY);
	return l_return;

elsif P_QUANTITY < 0 then 
	update npu.item_quantity
	set quantity 	= quantity + P_QUANTITY,
		last_update_date = sysdate,
		last_updated_by = -1
	where item_qty_id = p_item_qty_id;

	--if sql%rowcount > 0 then 
		l_return := 0;
	--else
	--	l_return := 1;
	--end if;

else
	update npu.item_quantity
	set unit_price 	= NVL(P_UNIT_PRICE, unit_price),
		tax 		= NVL(P_TAX, tax),
		quantity 	= NVL(P_QUANTITY, quantity),
		total_price = (NVL(P_UNIT_PRICE, unit_price) + NVL(P_TAX, tax)) * NVL(P_QUANTITY, quantity),
		last_update_date = sysdate,
		last_updated_by = -1
	where item_qty_id = p_item_qty_id;

	if sql%rowcount > 0 then 
		l_return := 0;
	else
		l_return := 1;
	end if;

end if;

commit;

return l_return;

EXCEPTION WHEN OTHERS THEN 
	rollback;
	return -1;
END UPDATE_ITEM;


FUNCTION INSERT_ITEM (P_ITEM_ID NUMBER, 
					  P_ITEM_CODE VARCHAR2,
					  P_ITEM_NAME VARCHAR2,
					  P_UPC_CODE VARCHAR2, 
					  P_UNIT_PRICE NUMBER, 
					  P_TAX NUMBER, 
					  P_UOM VARCHAR2, 
					  P_QUANTITY NUMBER) 
RETURN NUMBER IS

l_return 		number := 0;
l_item_qty_id	number;
l_item_id 		number;
l_item_code 	varchar2(30);
l_item_name 	varchar2(30);
l_UPC_Code 		varchar2(60);
l_UOM 			varchar2(10);
l_Unit_Price 	number(10,2);
l_tax 			number(10,2);
l_Quantity 		number(10,2);
l_total_price 	number(10,2);

BEGIN

select npu.item_qty_id_seq.nextval 
into l_item_qty_id from dual;

l_item_id 	:= P_item_id;
l_item_code := p_item_code;
l_item_name := p_item_name;
l_UPC_Code  := p_UPC_Code;
l_Unit_Price := p_Unit_Price;
l_UOM := NVL(P_UOM, 'EA');
l_Quantity := P_Quantity;

	if l_unit_price = 0 then 
		l_tax := 0;
	else
		l_tax := NVL(P_TAX,0);
	end if;

	if l_quantity = 0 then 
		l_total_price := 0;
	else
		l_total_price := (l_unit_price + l_tax) * l_quantity;
	end if;


	insert into npu.item_quantity
	(	Item_Qty_Id,
		Item_Id,
		Item_Code,
		Item_Name,
		UPC_Code,
		Unit_Price,
		Tax,
		UOM,
		Quantity,
		Total_Price,
		creation_date,
		created_by,
		last_update_date,
		last_updated_by
	)
	values
	(	l_item_qty_id,
		l_Item_Id,
		l_item_code,
		l_item_name,
		l_UPC_Code,
		l_Unit_Price,
		l_Tax,
		l_UOM,
		l_Quantity,
		l_Total_Price,
		sysdate,
		-1,
		sysdate,
		-1
	);

	if sql%rowcount > 0 then 
		l_return := 0;
	else
		l_return := 1;
	end if;

	commit;

return l_return;

EXCEPTION WHEN OTHERS THEN 
	rollback;
	return -1;
END INSERT_ITEM;


FUNCTION INSERT_ITEM_DETAILS (P_ITEM_CODE 	VARCHAR2, 
					  	  P_ITEM_NAME 	VARCHAR2, 
					  	  P_DESCRIPTION VARCHAR2,
					  	  P_TYPE	 	VARCHAR2,
					  	  P_UPC_CODE  	VARCHAR2,
					  	  P_UNIT_PRICE 	NUMBER, 
					  	  P_TAX 		NUMBER, 
					  	  P_UOM 		VARCHAR2, 
					  	  P_QUANTITY 	NUMBER) 
RETURN NUMBER IS

l_return 		number := 0;
l_item_id 		number;
l_item_qty_id	number;
l_item_code 	varchar2(30);
l_item_name 	varchar2(30);
l_total_price 	number;
l_tax 			number;

BEGIN

select npu.item_id_seq.nextval 
into l_item_id from dual;

	insert into npu.item_details
	(	Item_Id,
		Item_Code,
		Item_Name,
		Description,
		Item_Type,
		creation_date,
		created_by,
		last_update_date,
		last_updated_by
	)
	values
	(	l_item_id,
		P_Item_code,
		P_Item_name,
		P_Description,
		P_type,
		sysdate,
		-1,
		sysdate,
		-1
	);

	commit;


	l_return := ITEM_HANDLER.INSERT_ITEM (l_item_id,
							  P_ITEM_CODE,
							  P_ITEM_NAME, 
					 		  P_UPC_CODE, 
							  P_UNIT_PRICE, 
							  P_TAX, 
							  P_UOM, 
							  P_QUANTITY);

	return l_return;

EXCEPTION WHEN OTHERS THEN 
	return -1;
END INSERT_ITEM_DETAILS;


END ITEM_HANDLER;

EXIT;