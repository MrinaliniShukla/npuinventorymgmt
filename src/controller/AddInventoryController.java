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

import model.InventoryService;

import data.Inventory;

/**
 * Servlet implementation class AddInventoryController
 */
@WebServlet("/AddInventoryController")
public class AddInventoryController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private InventoryService inventoryService=null;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddInventoryController() {
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
				request.setAttribute("pageName","AddInventory");
				view = request.getRequestDispatcher("AddInventory.jsp");
			}
			view.forward(request, response);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Inventory inventory=new Inventory();
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
			int result=inventoryService.insertInventory(inventory);
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
