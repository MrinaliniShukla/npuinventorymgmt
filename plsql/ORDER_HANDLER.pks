CREATE OR REPLACE PACKAGE ORDER_HANDLER IS

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
						P_Contact_Number varchar2) return number;

END ORDER_HANDLER;
/
