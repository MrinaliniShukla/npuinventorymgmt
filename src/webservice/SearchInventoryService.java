package webservice;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.InventoryService;

import data.Inventory;

/**
 * Servlet implementation class SearchInventoryService
 */
@WebServlet("/SearchInventoryService")
public class SearchInventoryService extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PrintWriter out;
	private InventoryService inventoryService=null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchInventoryService() {
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
		response.setContentType("text/xml"); // Content type will be XML
		out = response.getWriter();
		out.println("<?xml version=\"1.0\" ?>");
		out.println("<inventorylist>");
		String searchValue=request.getParameter("searchValue");
		String instock=request.getParameter("inStock");
		String outstock=request.getParameter("outStock");
		try{
			if(null!=inventoryService){
				ArrayList<Inventory> inventoryList=inventoryService.searchInventoryList(searchValue, instock, outstock);
				for(int i=0;i<inventoryList.size();i++){
					out.println("<inventory>");    
					out.println("<code>" + inventoryList.get(i).getItemCode()+ "</code>");
					out.println("<itemid>" + inventoryList.get(i).getItemId()+ "</itemid>");
					out.println("<name>" +inventoryList.get(i).getItemName() + "</name>");
					out.println("<itemquantityid>" + inventoryList.get(i).getItemQuantityId()+ "</itemquantityid>");
					out.println("<description>" + inventoryList.get(i).getDescription()+ "</description>");
					out.println("<type>" + inventoryList.get(i).getItemType()+ "</type>");
					if(inventoryList.get(i).getUpcCode()!=null){
						out.println("<upccode>" +inventoryList.get(i).getUpcCode()+"</upccode>");
						
					}
					else{
						out.println("<upccode></upccode>");
					}
					out.println("<unitprice>" + inventoryList.get(i).getUnitPrice()+ "</unitprice>");
					out.println("<tax>" + inventoryList.get(i).getTax()+ "</tax>");
					if(inventoryList.get(i).getUom()!=null){
						out.println("<uom>" +inventoryList.get(i).getUom()+"</uom>");
					}
					else{
						out.println("<uom></uom>");
					}
					out.println("<quantity>" + inventoryList.get(i).getQuantity()+ "</quantity>");
					out.println("<totalprice>" + inventoryList.get(i).getTotalPrice()+ "</totalprice>");
					out.println("</inventory>");
				}
				out.println("</inventorylist>");
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
		// TODO Auto-generated method stub
	}

}
