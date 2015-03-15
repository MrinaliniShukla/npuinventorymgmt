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
import data.User;

import model.InventoryService;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private InventoryService inventoryService=null; 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
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
		RequestDispatcher view = request.getRequestDispatcher("Login.jsp");
        view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		RequestDispatcher view=null;
		String message="";
		User user=inventoryService.validateLogin(username, password);
		HttpSession session=request.getSession();
		if(null!= user){
			session.setAttribute("user",user);
			inventoryService.updateCartSession(user.getUserId(), session.getId());
			if(request.getParameter("previousPage")!=null && request.getParameter("previousPage").equalsIgnoreCase("checkout")){
				request.setAttribute("pageName","Order");
				view = request.getRequestDispatcher("Checkout.jsp");
			}
			else{
				ArrayList<Inventory> inventoryList=inventoryService.getInventoryList();
				request.setAttribute("inventoryList",inventoryList);
				if(user.getInventoryManager().equalsIgnoreCase("y")){
					request.setAttribute("pageName","ViewInventory");
					view = request.getRequestDispatcher("View.jsp");
				}
				else if(user.getOnlineLogin().equalsIgnoreCase("y")){
					request.setAttribute("pageName","Order");
					view = request.getRequestDispatcher("Index.jsp");
				}
			}
			
		}
		else{
			message="Invalid username or password.";
			request.setAttribute("message",message);
			view = request.getRequestDispatcher("Login.jsp");
		}
		view.forward(request, response);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

}
