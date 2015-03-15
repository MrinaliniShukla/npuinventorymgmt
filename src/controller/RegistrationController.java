package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.InventoryService;

import data.User;

/**
 * Servlet implementation class RegistrationController
 */
@WebServlet("/RegistrationController")
public class RegistrationController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private InventoryService inventoryService=null; 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrationController() {
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
		RequestDispatcher view=request.getRequestDispatcher("Registration.jsp");
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username=request.getParameter("userName");
		String password=request.getParameter("password");
		String firstName=request.getParameter("firstName");
		String lastName=request.getParameter("lastName");
		String phoneNumber=request.getParameter("phoneNumber");
		String email=request.getParameter("email");
		String message="";
		User user=new User();
		user.setLoginName(username);
		user.setPassword(password);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setPhoneNumber(phoneNumber);
		user.setEmail(email);
		try{
			message=inventoryService.registerUser(user);
			request.setAttribute("message", message);
			RequestDispatcher view=request.getRequestDispatcher("Registration.jsp");
			view.forward(request, response);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

}
