package controller;

import handler.ItemHandler;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import model.InventoryService;

import data.Inventory;
import data.User;

/**
 * Servlet implementation class IndexController
 * This class is for the Online Customer Functionality
 */
@WebServlet("/IndexController")
public class IndexController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private InventoryService inventoryService=null;
	private ItemHandler handler = null;
	private ArrayList<Inventory> cartList=null;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexController() {
        super();
        try{
    		inventoryService=new InventoryService();
    		cartList=new ArrayList<Inventory>();
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher view=null;
		try{
			User user=null;
			int userId=0;
			HttpSession session=request.getSession();
			if(session.getAttribute("user")!=null && session.getAttribute("user") instanceof User ){
				user=(User)session.getAttribute("user");
				userId=user.getUserId();
			}
			
			if(null!=inventoryService){
				//For View Cart Functionality
				if(null!=request.getParameter("action")&& request.getParameter("action").equalsIgnoreCase("viewcart")){
					cartList=inventoryService.viewCart(userId, session.getId());
					request.setAttribute("inventoryList",cartList);
					view = request.getRequestDispatcher("ViewCart.jsp");
			    }else{
			    	//For Inventory Listing Functionality
			    	String serverLoc = "http://localhost:8080/InventoryManagement/SearchInventory";
					SAXParserFactory parserFactory = SAXParserFactory.newInstance();
		    		SAXParser saxParser = parserFactory.newSAXParser();
		    		handler = new ItemHandler();
		    		saxParser.parse(serverLoc, handler);
					ArrayList<Inventory> inventoryList=handler.getInventoryList();
					request.setAttribute("inventoryList",inventoryList);
					request.setAttribute("pageName","Order");
					view = request.getRequestDispatcher("Index.jsp");
		      	}
			}
			view.forward(request, response);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user=null;
		int userId=0;
		RequestDispatcher view=null;
		HttpSession session=request.getSession();
		if(session.getAttribute("user")!= null){
			user=(User)session.getAttribute("user");
			userId=user.getUserId();
		}
		try{
			if(null!=request.getParameter("action")){
				int itemQuantityId= Integer.parseInt(request.getParameter("itemQuantityId"));
				int quantity= Integer.parseInt(request.getParameter("quantity"));
				System.out.println("Quantity In Controller"+quantity);
				//For Add to Cart Functionality
				if(request.getParameter("action").equalsIgnoreCase("addtocart")){
					inventoryService.saveCart(userId,session.getId(),itemQuantityId,quantity);
				}
				//For Go to Cart Functionality
				else if(request.getParameter("action").equalsIgnoreCase("updatecart")){
					inventoryService.updateCart(userId, session.getId(),itemQuantityId,quantity);
					
			    }
				cartList=inventoryService.viewCart(userId, session.getId());
				request.setAttribute("inventoryList",cartList);
				view = request.getRequestDispatcher("ViewCart.jsp");
			}
			else{
				//For Search Inventory Functionality
				String serverLoc = "http://localhost:8080/InventoryManagement/SearchInventory";
				String searchValue=request.getParameter("searchValue");
				String instock=request.getParameter("inStock");
				String outstock=request.getParameter("outStock");
				serverLoc+="?searchValue="+searchValue;
				if(instock!=null){
					serverLoc+="&inStock="+instock;
				}
				if(outstock!=null){
					serverLoc+="&outStock="+outstock;
				}
				SAXParserFactory parserFactory = SAXParserFactory.newInstance();
	    		SAXParser saxParser = parserFactory.newSAXParser();
	    		handler = new ItemHandler();
	    		saxParser.parse(serverLoc, handler);
				ArrayList<Inventory> inventoryList=handler.getInventoryList();
				request.setAttribute("inventoryList",inventoryList);
				request.setAttribute("pageName","Order");
				view = request.getRequestDispatcher("Index.jsp");
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		view.forward(request, response);
	}

}
