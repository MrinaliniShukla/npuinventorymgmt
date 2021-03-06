CREATE OR REPLACE PACKAGE ITEM_HANDLER AS

	FUNCTION DELETE_ITEM (P_ITEM_QTY_ID NUMBER) RETURN NUMBER ;

	FUNCTION UPDATE_ITEM (P_ITEM_ID NUMBER, 
						  P_ITEM_CODE VARCHAR2,
					  	  P_ITEM_NAME VARCHAR2,
						  P_ITEM_QTY_ID NUMBER, 
						  P_UPC_CODE VARCHAR2, 
						  P_UNIT_PRICE NUMBER, 
						  P_TAX NUMBER, 
						  P_UOM VARCHAR2, 
						  P_QUANTITY NUMBER) RETURN NUMBER;

	FUNCTION INSERT_ITEM (P_ITEM_ID 	NUMBER, 
						  P_ITEM_CODE 	VARCHAR2,
						  P_ITEM_NAME 	VARCHAR2,
						  P_UPC_CODE 	VARCHAR2, 
						  P_UNIT_PRICE 	NUMBER, 
						  P_TAX 		NUMBER, 
						  P_UOM 		VARCHAR2, 
						  P_QUANTITY 	NUMBER) RETURN NUMBER ;

	FUNCTION INSERT_ITEM_DETAILS (P_ITEM_CODE 	VARCHAR2, 
					  	  P_ITEM_NAME 	VARCHAR2, 
					  	  P_DESCRIPTION VARCHAR2,
					  	  P_TYPE	 	VARCHAR2,
					  	  P_UPC_CODE  	VARCHAR2,
					  	  P_UNIT_PRICE 	NUMBER, 
					  	  P_TAX 		NUMBER, 
					  	  P_UOM 		VARCHAR2, 
					  	  P_QUANTITY 	NUMBER) RETURN NUMBER;

END ITEM_HANDLER;
/
