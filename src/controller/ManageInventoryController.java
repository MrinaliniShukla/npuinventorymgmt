package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.Inventory;

import model.InventoryService;

/**
 * Servlet implementation class ManageItemsController
 */
@WebServlet("/ManageItemsController")
public class ManageInventoryController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private InventoryService inventoryService=null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManageInventoryController() {
        super();
        try{
    		inventoryService=new InventoryService();
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
		RequestDispatcher view=null;
		try{
			if(session.getAttribute("user")== null){
				view = request.getRequestDispatcher("login.jsp");
			}
			else{
				String action=request.getParameter("action");
				int itemId=0;
				int itemQuantityId=0;
				if(request.getParameter("itemId")!=null){
					itemId=Integer.parseInt(request.getParameter("itemId"));
				}
				if(request.getParameter("itemQuantityId")!=null){
					itemQuantityId=Integer.parseInt(request.getParameter("itemQuantityId"));
				}
				if(action.equalsIgnoreCase("delete")){
					int result=inventoryService.deleteInventory(itemQuantityId);
					ArrayList<Inventory> inventoryList=inventoryService.getInventoryList();
					String message=result==0?"Record deleted Successfully":"Error in deleteing Record";
					request.setAttribute("message",message);
					request.setAttribute("inventoryList",inventoryList);
					request.setAttribute("pageName","ViewInventory");
					view = request.getRequestDispatcher("View.jsp");
			    }
				else{
					Inventory inventoryItem=inventoryService.getInventoryItem(itemId,itemQuantityId);
					request.setAttribute("inventory", inventoryItem);
					request.setAttribute("pageName","ViewInventory");
					view = request.getRequestDispatcher("EditInventory.jsp");
			        
				}
				view.forward(request, response);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Inventory inventory=new Inventory();
		inventory.setItemId(Integer.parseInt(request.getParameter("itemId")));
		inventory.setItemQuantityId(Integer.parseInt(request.getParameter("itemQtyId")));
		inventory.setItemCode(request.getParameter("itemCode"));
		inventory.setItemName(request.getParameter("itemName"));
		inventory.setItemType(request.getParameter("itemType"));
		inventory.setDescription(request.getParameter("description"));
		inventory.setUpcCode(request.getParameter("upcCode"));
		inventory.setTax(Double.parseDouble(request.getParameter("tax")));
		inventory.setUnitPrice(Double.parseDouble(request.getParameter("unitPrice")));
		inventory.setQuantity(Integer.parseInt(request.getParameter("quantity")));
		inventory.setUom(request.getParameter("uom"));
		try{
			int result=inventoryService.updateInventory(inventory);
			String message=result==0?"Record updated Successfully":"Error in updating Record";
			request.setAttribute("message",message);
			ArrayList<Inventory> inventoryList=inventoryService.getInventoryList();
			request.setAttribute("inventoryList",inventoryList);
			request.setAttribute("pageName","ViewInventory");
			RequestDispatcher view = request.getRequestDispatcher("View.jsp");
	        view.forward(request, response);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}

}
