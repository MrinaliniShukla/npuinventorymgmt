
whenever sqlerror continue;

drop table npu.item_details;

create table npu.item_details  (
Item_Id		number,
Item_Code	varchar2(30),
Item_Name	varchar2(60),
Description	varchar2(150),
Item_Type	varchar2(60),
Attribute1	varchar2(240),
Attribute2	varchar2(240),
Attribute3	varchar2(240),
Attribute4 	varchar2(240),
Attribute5	varchar2(240),
Attribute6	varchar2(240),
Attribute7	varchar2(240),
Attribute8	varchar2(240),
Attribute9	varchar2(240),
Attribute10 varchar2(240),
Attribute11 varchar2(240),
Attribute12 varchar2(240),
Attribute13 varchar2(240),
Attribute14 varchar2(240),
Attribute15 varchar2(240),
nAttribute1	number,
nAttribute2	number,
nAttribute3	number,
nAttribute4 number,
nAttribute5	number,
nAttribute6	number,
nAttribute7	number,
nAttribute8	number,
nAttribute9	number,
nAttribute10 number,
nAttribute11 number,
nAttribute12 number,
nAttribute13 number,
nAttribute14 number,
nAttribute15 number,
creation_date date,
created_by number,
last_update_date date,
last_updated_by number);


Drop table NPU.ITEM_QUANTITY;

Create table NPU.ITEM_QUANTITY (
Item_Qty_Id	number,
Item_Id		number,
Item_Code	varchar2(30),
Item_Name	varchar2(30),
UPC_Code	varchar2(60),
Unit_Price	number(10,2),
Tax			number(10,2),
UOM			varchar2(10),
Quantity	number(10,2),
Total_Price	number(10,2),
Attribute1	varchar2(240),
Attribute2	varchar2(240),
Attribute3	varchar2(240),
Attribute4 	varchar2(240),
Attribute5	varchar2(240),
Attribute6	varchar2(240),
Attribute7	varchar2(240),
Attribute8	varchar2(240),
Attribute9	varchar2(240),
Attribute10 varchar2(240),
Attribute11 varchar2(240),
Attribute12 varchar2(240),
Attribute13 varchar2(240),
Attribute14 varchar2(240),
Attribute15 varchar2(240),
nAttribute1	number,
nAttribute2	number,
nAttribute3	number,
nAttribute4 number,
nAttribute5	number,
nAttribute6	number,
nAttribute7	number,
nAttribute8	number,
nAttribute9	number,
nAttribute10 number,
nAttribute11 number,
nAttribute12 number,
nAttribute13 number,
nAttribute14 number,
nAttribute15 number,
creation_date date,
created_by number,
last_update_date date,
last_updated_by number);


Create or replace view npu.item_dtls_qty_v as (
select id.item_id, iq.item_qty_id, id.item_code, id.item_name, id.description, id.item_type, iq.upc_code, iq.unit_price, iq.tax, iq.uom,
iq.quantity, iq.total_price
from item_details id, item_quantity iq
where id.item_id = iq.item_id(+));

create table item_lookup (lookup_type varchar2(30), lookup_code varchar2(100), meaning varchar2(100), description varchar2(240));


select * from npu.item_dtls_qty_v;


ITEM_HANDLER.DELETE_ITEM( P_ITEM_QTY_ID );



drop sequence npu.item_id_seq;

CREATE SEQUENCE npu.item_id_seq
 START WITH     1000
 INCREMENT BY   1
 NOCACHE
 NOCYCLE;
 


drop sequence npu.item_qty_id_seq;

CREATE SEQUENCE npu.item_qty_id_seq
 START WITH     1000
 INCREMENT BY   1
 NOCACHE
 NOCYCLE;
 

drop sequence npu.user_id_seq;

CREATE SEQUENCE npu.user_id_seq
 START WITH     1000
 INCREMENT BY   1
 NOCACHE
 NOCYCLE;


Create table npu.user_details
(user_id 		number,
 login_name 	varchar2(30),
 password		varchar2(30),
 first_name		varchar2(30),
 last_name		varchar2(30),
 user_type		varchar2(10),
 user_manager	varchar2(1),
 billing_manager	varchar2(1),
 inventory_manager	varchar2(1),
 Online_Login 	varchar2(1)
);


Drop table npu.save_cart;

Create table npu.save_cart 
(	user_id number,
	session_id varchar2(100),
	item_quantity_id number, 
	quantity number, 
	creation_date date, 
	active varchar2(1)
);



create or replace view npu.view_cart as (
select item_id, item_qty_id, item_code, item_name, description, item_type, upc_code, unit_price, tax, sum(sc.quantity) quantity, 
				(v.tax * sum(sc.quantity)) total_tax, ((v.unit_price + v.tax)*sum(sc.quantity)) total_price , v.quantity inv_quantity
				, sc.active, sc.user_id, sc.session_id from item_dtls_qty_v v, save_cart sc 
				where v.item_qty_id = sc.item_quantity_id 
group by  item_id, item_qty_id, item_code, item_name, description, item_type, upc_code, unit_price, 
			tax, v.quantity, sc.active, sc.user_id, sc.session_id );





Create table npu.Order_Headers (
Header_id number,
Order_Number number,
Order_Date date,
First_Name varchar2(30),
Last_Name varchar2(30),
Address varchar2(100),
City varchar2(30),
State varchar2(30),
Country varchar2(30),
Zip_Code number,
Contact_Number varchar2(20));


Create table npu.Order_Lines (
Line_id number,
Header_id number,
Item_Qty_Id number,
Quantity number,
Unit_Price number,
Tax number,
Total_Price number );
			
Create table npu.Order_Payments (										
Payment_Id 	number,
Header_Id 	number,
Line_Id 		number,
Payment_Type 	varchar2(30),
Credit_Card_Number varchar2(30),
CC_Expiry_Date varchar2(10),
Due_Date 		date,
Item_Amount 	number,
Tax_Amount 		number,
Total_Amount 	number,
Discount_Amount number,
Total_Payment 	number,
Payment_Date 	date );


drop sequence npu.order_header_id_seq;

CREATE SEQUENCE npu.order_header_id_seq
 START WITH     1000
 INCREMENT BY   1
 NOCACHE
 NOCYCLE;


drop sequence npu.order_line_id_seq;

CREATE SEQUENCE npu.order_line_id_seq
 START WITH     1000
 INCREMENT BY   1
 NOCACHE
 NOCYCLE;


drop sequence npu.order_payment_id_seq;

CREATE SEQUENCE npu.order_payment_id_seq
 START WITH     1000
 INCREMENT BY   1
 NOCACHE
 NOCYCLE;




/*														
Create table npu.Customers (
Customer_Id number,
First_Name number,
Last_Name number,
Address1 varchar2(100),
Address2 varchar2(100),
City varchar2(50),
State varchar2(50),
Country varchar2(50),
Contact_Number varchar2(20),
Email_Address varchar2(50) );

										
										
create table npu.Customer_Payments (										
Customer_Id number,
Credit_Card_Type varchar2(20),
Credit_Card_Number varchar2(30),
Expiry_Date varchar2(10)		
);


drop sequence npu.customer_id_seq;

CREATE SEQUENCE npu.customer_id_seq
 START WITH     1000
 INCREMENT BY   1
 NOCACHE
 NOCYCLE;
*/


