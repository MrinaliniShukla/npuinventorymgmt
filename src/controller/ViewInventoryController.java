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

import data.Inventory;

import model.InventoryService;

/**
 * Servlet implementation class ViewController
 */
@WebServlet("/ViewController")
public class ViewInventoryController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private InventoryService inventoryService=null;
    private ItemHandler handler = null;
   /**
     * Default constructor. 
     */
    public ViewInventoryController() {
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
				if(null!=inventoryService){
					ArrayList<Inventory> inventoryList=inventoryService.getInventoryList();
					request.setAttribute("inventoryList",inventoryList);
					request.setAttribute("pageName","ViewInventory");
					view = request.getRequestDispatcher("View.jsp");
			        view.forward(request, response);
				}
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		try{
			if(null!=inventoryService){
				SAXParserFactory parserFactory = SAXParserFactory.newInstance();
	    		SAXParser saxParser = parserFactory.newSAXParser();
	    		handler = new ItemHandler();
	    		saxParser.parse(serverLoc, handler);
				ArrayList<Inventory> inventoryList=handler.getInventoryList();
				request.setAttribute("inventoryList",inventoryList);
				request.setAttribute("pageName","ViewInventory");
				RequestDispatcher view = request.getRequestDispatcher("View.jsp");
		        view.forward(request, response);
				
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		
	}

}
