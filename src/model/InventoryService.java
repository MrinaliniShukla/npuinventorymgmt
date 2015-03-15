package model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import data.Inventory;
import data.Order;
import data.User;

public class InventoryService {
	private PreparedStatement preparedStatement= null;
	private Connection connection = null;
	public InventoryService() throws SQLException{
		connection=OracleJDBCConnection.getDBConnection();
	}
	public ArrayList<Inventory> getInventoryList() throws SQLException{
		ArrayList<Inventory> inventoryList=new ArrayList<Inventory>();
		String sql="select * from npu.item_dtls_qty_v";
		ResultSet rs=null;
		try{
			preparedStatement=connection.prepareStatement(sql);
			rs=preparedStatement.executeQuery();
			while(rs.next()){
				Inventory inventory=new Inventory();
				inventory.setItemId(rs.getInt(1));
				inventory.setItemQuantityId(rs.getInt(2));
				inventory.setItemCode(rs.getString(3));
				inventory.setItemName(rs.getString(4));
				inventory.setDescription(rs.getString(5));
				inventory.setItemType(rs.getString(6));
				inventory.setUpcCode(rs.getString(7));
				inventory.setUnitPrice(rs.getDouble(8));
				inventory.setTax(rs.getDouble(9));
				inventory.setUom(rs.getString(10));
				inventory.setQuantity(rs.getInt(11));
				inventory.setTotalPrice(rs.getDouble(12));
				inventoryList.add(inventory);
			}
		}catch(SQLException ex){
			throw ex;
		}
		finally{
			if(null != rs){
				rs.close();
			}
			if(null != preparedStatement){
				preparedStatement.close();
			}
		}
		
		return inventoryList;
	}
	public ArrayList<Inventory> searchInventoryList(String searchValue, String instock, String outstock) throws SQLException{
		ArrayList<Inventory> inventoryList=new ArrayList<Inventory>();
		if(searchValue==null){
			searchValue="";
		}
		searchValue= "%"+searchValue.toUpperCase()+"%";
		
		
		if (instock == null && outstock == null){
			instock = "inStock";
			outstock = "outStock";
		}
		String sql;
			sql="select * from npu.item_dtls_qty_v where upper(item_name) like ? " +
					"and (('inStock'  = ? and nvl(quantity,0) > 0) or " +
				    "     ('outStock' = ? and nvl(quantity,0) = 0))  " +
					"union " +
					"select * from npu.item_dtls_qty_v where upper(item_code) like ? " +
					"and (('inStock'  = ? and nvl(quantity,0) > 0)  or " +
				    "     ('outStock' = ? and nvl(quantity,0) = 0))  " ;
		ResultSet rs=null;
		try{		
			preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(1,searchValue);
			preparedStatement.setString(2,instock);
			preparedStatement.setString(3,outstock);
			preparedStatement.setString(4,searchValue);
			preparedStatement.setString(5,instock);
			preparedStatement.setString(6,outstock);
			
			rs=preparedStatement.executeQuery();
			while(rs.next()){
				Inventory inventory=new Inventory();
				inventory.setItemId(rs.getInt(1));
				inventory.setItemQuantityId(rs.getInt(2));
				inventory.setItemCode(rs.getString(3));
				inventory.setItemName(rs.getString(4));
				inventory.setDescription(rs.getString(5));
				inventory.setItemType(rs.getString(6));
				inventory.setUpcCode(rs.getString(7));
				inventory.setUnitPrice(rs.getDouble(8));
				inventory.setTax(rs.getDouble(9));
				inventory.setUom(rs.getString(10));
				inventory.setQuantity(rs.getInt(11));
				inventory.setTotalPrice(rs.getDouble(12));
				inventoryList.add(inventory);
			}
		}catch(SQLException ex){
			throw ex;
		}
		finally{
			if(null != rs){
				rs.close();
			}
			if(null != preparedStatement){
				preparedStatement.close();
			}
		}
		return inventoryList;
	}
	
	public Inventory getInventoryItem(int itemId,int itemQuantityId) throws SQLException{
		String sql;
		int queryParam;
		if(itemQuantityId > 0){
			sql="select * from npu.item_dtls_qty_v where item_qty_id= ?" ;
			queryParam=itemQuantityId;
		}
		else{
			sql="select * from npu.item_dtls_qty_v where item_id= ?" ;
			queryParam=itemId;
		}
		ResultSet rs=null;
		Inventory inventory=new Inventory();
		try{		
			preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setInt(1,queryParam);
			rs=preparedStatement.executeQuery();
			while(rs.next()){
				inventory.setItemId(rs.getInt(1));
				inventory.setItemQuantityId(rs.getInt(2));
				inventory.setItemCode(rs.getString(3));
				inventory.setItemName(rs.getString(4));
				inventory.setDescription(rs.getString(5));
				inventory.setItemType(rs.getString(6));
				inventory.setUpcCode(rs.getString(7));
				inventory.setUnitPrice(rs.getDouble(8));
				inventory.setTax(rs.getDouble(9));
				inventory.setUom(rs.getString(10));
				inventory.setQuantity(rs.getInt(11));
				inventory.setTotalPrice(rs.getDouble(12));
			}
		}catch(SQLException ex){
			throw ex;
		}
		finally{
			if(null != rs){
				rs.close();
			}
			if(null != preparedStatement){
				preparedStatement.close();
			}
		}
		return inventory;
	}
	public String registerUser(User user) throws SQLException{
		String sqlInsert,sqlSelect,message, sqlSequence;
		sqlSelect="select * from user_details  where upper(LOGIN_NAME)= ?";
		sqlSequence="select user_id_seq.nextval from dual";
		sqlInsert="insert into user_details(USER_ID,LOGIN_NAME,PASSWORD,FIRST_NAME,LAST_NAME,EMAIL,USER_TYPE,USER_MANAGER,BILLING_MANAGER,INVENTORY_MANAGER,ONLINE_LOGIN,PHONE_NUMBER) values(?,?,?,?,?,?,?,?,?,?,?,?)" ;
		ResultSet rs=null;
		try{		
			preparedStatement=connection.prepareStatement(sqlSelect);
			preparedStatement.setString(1,user.getLoginName().toUpperCase());
			rs=preparedStatement.executeQuery();
			if(rs.next()){
				message="Registration failed.User Name already exists.";
			}
			else{
				int nextSeq=0;
				preparedStatement=connection.prepareStatement(sqlSequence);
				rs=preparedStatement.executeQuery();
				if(rs.next()){
					nextSeq=rs.getInt(1);
				}
				connection.setAutoCommit(false);
				preparedStatement=connection.prepareStatement(sqlInsert);
				preparedStatement.setInt(1,nextSeq);
				preparedStatement.setString(2,user.getLoginName());
				preparedStatement.setString(3,user.getPassword());
				preparedStatement.setString(4,user.getFirstName());
				preparedStatement.setString(5,user.getLastName());
				preparedStatement.setString(6,user.getEmail());
				preparedStatement.setString(7,"CUSTOMER");
				preparedStatement.setString(8,"N");
				preparedStatement.setString(9,"N");
				preparedStatement.setString(10,"N");
				preparedStatement.setString(11,"Y");
				preparedStatement.setString(12,user.getPhoneNumber());
				int rowsUpdated=preparedStatement.executeUpdate();
				connection.commit();
				if(rowsUpdated>0){
					message="Registration sucessful.Please login.";
				}
				else{
					message="Registration failed due to technical issues.Please try later";
				}
			}
			
		}catch(SQLException ex){
			connection.rollback();
			throw ex;
		}
		finally{
			if(null != rs){
				rs.close();
			}
			if(null != preparedStatement){
				preparedStatement.close();
			}
		}
		return message;
	}
	
	public int deleteInventory(int itemQuantityId) throws SQLException{
		String call = "{ ? = call ITEM_HANDLER.DELETE_ITEM( ? ) }";
        CallableStatement cstmt=null;
        int returnCode;
		try{	
			cstmt = connection.prepareCall(call);
	        cstmt.setQueryTimeout(1800);
	        cstmt.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMBER);
	        cstmt.setInt(2, itemQuantityId);
	        cstmt.executeUpdate();
	        returnCode = cstmt.getInt(1);
		}
		catch(SQLException ex){
			throw ex;
		}
		finally{
			if(null != preparedStatement){
				preparedStatement.close();
			}
		}
		System.out.println("Returned Value: "+returnCode);
		return returnCode;
	}
	
	public int updateInventory(Inventory inventoryItem) throws SQLException{
		String call = "{ ? = call ITEM_HANDLER.UPDATE_ITEM( ?,?,?,?,?,?,?,?,?) }";
        CallableStatement cstmt=null;
        int returnCode;
		try{	
			cstmt = connection.prepareCall(call);
	        cstmt.setQueryTimeout(1800);
	        cstmt.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMBER);
	        cstmt.setInt(2, inventoryItem.getItemId());
	        cstmt.setString(3, inventoryItem.getItemCode());
	        cstmt.setString(4, inventoryItem.getItemName());
	        cstmt.setInt(5, inventoryItem.getItemQuantityId());
	        cstmt.setString(6, inventoryItem.getUpcCode());
	        cstmt.setDouble(7, inventoryItem.getUnitPrice());
	        cstmt.setDouble(8, inventoryItem.getTax());
	        cstmt.setString(9, inventoryItem.getUom());
	        cstmt.setInt(10, inventoryItem.getQuantity());
	        //cstmt.setInt(2, -1);
	        cstmt.executeUpdate();
	        returnCode = cstmt.getInt(1);
		}
		catch(SQLException ex){
			throw ex;
		}
		finally{
			if(null != preparedStatement){
				preparedStatement.close();
			}
		}
		System.out.println("Returned Value: "+returnCode);
		return returnCode;
	}
	
	public int insertInventory(Inventory inventoryItem) throws SQLException{
		String call = "{ ? = call ITEM_HANDLER.INSERT_ITEM_DETAILS( ?,?,?,?,?,?,?,?,?) }";
        CallableStatement cstmt=null;
        int returnCode;
		try{	
			cstmt = connection.prepareCall(call);
	        cstmt.setQueryTimeout(1800);
	        cstmt.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMBER);
	        cstmt.setString(2, inventoryItem.getItemCode());
	        cstmt.setString(3, inventoryItem.getItemName());
	        cstmt.setString(4, inventoryItem.getDescription());
	        cstmt.setInt(5, inventoryItem.getItemQuantityId());
	        cstmt.setString(6, inventoryItem.getUpcCode());
	        cstmt.setDouble(7, inventoryItem.getUnitPrice());
	        cstmt.setDouble(8, inventoryItem.getTax());
	        cstmt.setString(9, inventoryItem.getUom());
	        cstmt.setInt(10, inventoryItem.getQuantity());
	        //cstmt.setInt(2, -1);
	        cstmt.executeUpdate();
	        returnCode = cstmt.getInt(1);
		}
		catch(SQLException ex){
			throw ex;
		}
		finally{
			if(null != preparedStatement){
				preparedStatement.close();
			}
		}
		System.out.println("Returned Value: "+returnCode);
		return returnCode;
	}
	public User validateLogin(String username,String password) throws SQLException{
		String sql = "select * from user_details where login_name=? and password= ?";
		ResultSet rs=null;
		User user=null;
		try{
			preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(1,username);
			preparedStatement.setString(2,password);
			rs=preparedStatement.executeQuery();
			while(rs.next()){
			  user=new User();
			  user.setUserId(rs.getInt(1));
			  user.setLoginName(rs.getString(2));
			  user.setPassword(rs.getString(3));
			  user.setFirstName(rs.getString(4));
			  user.setLastName(rs.getString(5));
			  user.setUserType(rs.getString(6));
			  user.setUserManager(rs.getString(7));
			  user.setBillingManager(rs.getString(8));
			  user.setInventoryManager(rs.getString(9));
			  user.setOnlineLogin(rs.getString(10));
			  user.setPhoneNumber(rs.getString(11));
			  user.setEmail(rs.getString(12));
			}
		}
		catch(SQLException ex){
			throw ex;
		}
		finally{
			if(null != preparedStatement){
				preparedStatement.close();
			}
		}
		return user;
	}
	
	
	public void saveCart(int userId,String sessionId,int itemQuantityId,int quantity) throws SQLException{
		String sql = "MERGE INTO save_cart sc "+
		   "USING (SELECT ? user_id, ? session_id, ? item_quantity_id, ? quantity FROM dual) addin "+
		   "ON ( sc.item_quantity_id = addin.item_quantity_id and "+
		       "((sc.user_id = addin.user_id and sc.user_id <> 0) "+
		       "or (sc.session_id = addin.session_id and sc.user_id = 0))) "+
		   "WHEN MATCHED THEN "+
		   	"UPDATE SET sc.quantity = sc.quantity + addin.quantity "+
		   "WHEN NOT MATCHED THEN "+ 
		   	"INSERT (user_id, session_id, item_quantity_id, quantity, active) "+
		     	"VALUES (?, ?, ?, ?, 'Y')" ;
		
		ResultSet rs=null;
		User user=null;
		try{
			connection.setAutoCommit(false);
			preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setInt(1,userId);
			preparedStatement.setString(2,sessionId);
			preparedStatement.setInt(3,itemQuantityId);
			preparedStatement.setInt(4,quantity);
			preparedStatement.setInt(5,userId);
			preparedStatement.setString(6,sessionId);
			preparedStatement.setInt(7,itemQuantityId);
			preparedStatement.setInt(8,quantity);
			int result=preparedStatement.executeUpdate();
			if(result>0){
				connection.commit();
			}else{
				connection.rollback();
			}
			
		}
		catch(SQLException ex){
			connection.rollback();
			throw ex;
			
		}
		finally{
			if(null != preparedStatement){
				preparedStatement.close();
			}
		}
	}
	
	public void updateCart(int userId,String sessionId,int itemQuantityId,int quantity) throws SQLException{
		
		System.out.println("updateCart********");
		String sqlUpdate ="update save_cart set quantity=? where user_id=? and item_quantity_id=?";
		String sqlDelete="delete from save_cart where user_id=? and item_quantity_id=?";
		
		if(userId<=0){
			sqlUpdate ="update save_cart set quantity=? where session_id=? and item_quantity_id=?";
			sqlDelete="delete from save_cart where session_id=? and item_quantity_id=?";
			
		}
		try{
			connection.setAutoCommit(false);
			if(quantity>0){
				preparedStatement=connection.prepareStatement(sqlUpdate);
				preparedStatement.setInt(1,quantity);
				if(userId<=0){
					preparedStatement.setString(2,sessionId);
				}
				else{
					preparedStatement.setInt(2,userId);
				}
				preparedStatement.setInt(3,itemQuantityId);
				
			}else{
				preparedStatement=connection.prepareStatement(sqlDelete);
				if(userId<=0){
					preparedStatement.setString(1,sessionId);
				}
				else{
					preparedStatement.setInt(1,userId);
				}
				preparedStatement.setInt(2,itemQuantityId);
			}
			int result=preparedStatement.executeUpdate();
			if(result>0){
				connection.commit();
			}else{
				connection.rollback();
			}
			
		}
		catch(SQLException ex){
			connection.rollback();
			throw ex;
			
		}
		finally{
			if(null != preparedStatement){
				preparedStatement.close();
			}
		}
	}
	
	
	
	public void updateCartSession(int userId,String sessionId) throws SQLException{
		String sql = "update save_cart set user_id=? where user_id=0 and session_id=?" ;
		
		ResultSet rs=null;
		User user=null;
		try{
			connection.setAutoCommit(false);
			preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setInt(1,userId);
			preparedStatement.setString(2,sessionId);
			int result=preparedStatement.executeUpdate();
			if(result>0){
				connection.commit();
			}else{
				connection.rollback();
			}
			
		}
		catch(SQLException ex){
			connection.rollback();
			throw ex;
			
		}
		finally{
			if(null != preparedStatement){
				preparedStatement.close();
			}
		}
	}
	
	public ArrayList<Inventory> viewCart(int userId,String sessionId) throws SQLException{
		String sql = " select * from npu.view_cart sc "+
					 " where (( sc.user_id = ? and  sc.user_id<>0 ) " +
					 " or ( sc.session_id = ? and user_id = 0))";
		
		ArrayList<Inventory> inventoryList=new ArrayList<Inventory>();
		ResultSet rs=null;
		try{
			connection.setAutoCommit(false);
			preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setInt(1,userId);
			preparedStatement.setString(2,sessionId);
			rs=preparedStatement.executeQuery();
			while(rs.next()){
				Inventory inventory=new Inventory();
				inventory.setItemId(rs.getInt(1));
				inventory.setItemQuantityId(rs.getInt(2));
				inventory.setItemCode(rs.getString(3));
				inventory.setItemName(rs.getString(4));
				inventory.setDescription(rs.getString(5));
				inventory.setItemType(rs.getString(6));
				inventory.setUpcCode(rs.getString(7));
				inventory.setUnitPrice(rs.getDouble(8));
				inventory.setTax(rs.getDouble(9));
				inventory.setQuantity(rs.getInt(10));
				inventory.setTotalPrice(rs.getDouble(12));
				inventoryList.add(inventory);
			}
		}
		catch(SQLException ex){
			throw ex;
			
		}
		finally{
			if(null != preparedStatement){
				preparedStatement.close();
			}
		}
		return inventoryList;
	}
	
	public int placeOrder(Order order) throws SQLException{
		String query="select item_qty_id,quantity from view_cart where user_id= ?";
		String updateCall = "{ ? = call ITEM_HANDLER.UPDATE_ITEM( ?,?,?,?,?,?,?,?,?)}";
		String call = "{ ? = call ORDER_HANDLER.Insert_Order( ?,?,?,?,?,?,?,?,?,?,?,?) }";
        CallableStatement cstmt=null;
		ResultSet rs=null;
		int returnCode=-1;
		boolean status=false;
		try{	
			preparedStatement=connection.prepareStatement(query);
			preparedStatement.setInt(1,order.getUser().getUserId());
			rs=preparedStatement.executeQuery();
			connection.setAutoCommit(false);
			while(rs.next()){
				System.out.println( "Item Quantity Id: "+rs.getInt(1));
				System.out.println( "Item Quantity: "+rs.getInt(2));
				cstmt = connection.prepareCall(updateCall);
				cstmt.setQueryTimeout(1800);
		        cstmt.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMBER);
		        cstmt.setString(2,"");
		        cstmt.setString(3, "");
		        cstmt.setString(4,"");
		        
		        cstmt.setInt(5, rs.getInt(1));
		        cstmt.setString(6, "");
		        cstmt.setString(7, "");
		        cstmt.setString(8, "");
		        cstmt.setString(9, "");
		        cstmt.setInt(10, (rs.getInt(2)*-1));
		        cstmt.executeUpdate();
		        returnCode = cstmt.getInt(1);
		        System.out.println("Inside update inventory: "+returnCode);
		        if(returnCode!=0){
		        	connection.rollback();
		        	status=true;
		        	break;
		        }
		    }
			System.out.println("Status: "+status);
			if(!status){
				System.out.println("Inside Place Order");
				cstmt = connection.prepareCall(call);
		        cstmt.setQueryTimeout(1800);
		        cstmt.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMBER);
		        cstmt.setInt(2, order.getUser().getUserId());
		        cstmt.setString(3,order.getUser().getFirstName());
		        cstmt.setString(4, order.getUser().getLastName());
		        cstmt.setString(5, order.getAddress());
		        cstmt.setString(6, order.getCity());
		        cstmt.setString(7, order.getState());
		        cstmt.setString(8, order.getCountry());
		        cstmt.setInt(9, order.getZip());
		        cstmt.setString(10, order.getPaymentType());
		        cstmt.setString(11, order.getCreditNo());
		        cstmt.setString(12, order.getExpDate());
		        cstmt.setString(13, order.getUser().getPhoneNumber());
		        cstmt.executeUpdate();
		        returnCode = cstmt.getInt(1);
		        System.out.println("Inside Place Order returnCode: "+returnCode);
		        if(returnCode<=0){
		        	connection.rollback();
		        }else{
		        	connection.commit();
		        }
			}
		}
		catch(SQLException ex){
			connection.rollback();
			throw ex;
			
		}
		finally{
			if(null != preparedStatement){
				preparedStatement.close();
			}
		}
		System.out.println("Returned Value: "+returnCode);
		return returnCode;
	}
	
	
}
