package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.InventoryService;

import data.Order;
import data.User;

/**
 * Servlet implementation class CheckoutController
 */
@WebServlet("/CheckoutController")
public class CheckoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private InventoryService inventoryService=null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckoutController() {
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
		RequestDispatcher view=null;
		HttpSession session=request.getSession();
		if(session.getAttribute("user")== null){
			request.setAttribute("previousPage","checkout");
			double finalAmount=Double.parseDouble(request.getParameter("finalAmount"));
			request.setAttribute("finalAmount", finalAmount);
			view = request.getRequestDispatcher("Login.jsp");
		}
		else{
			view = request.getRequestDispatcher("Checkout.jsp");
		}
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
		User sessionUser=null;
		int userId=0;
		if(null!=session.getAttribute("user") && session.getAttribute("user") instanceof User){
			sessionUser=(User)session.getAttribute("user");
			userId=sessionUser.getUserId();
		}
		String firstName=request.getParameter("firstname");
		String lastName=request.getParameter("lastname");
		String address=request.getParameter("address");
		String city=request.getParameter("city");
		String state=request.getParameter("state");
		String country=request.getParameter("country");
		int zip=Integer.parseInt(request.getParameter("zip"));
		String phoneNumber=request.getParameter("telno");
		String salesType=request.getParameter("salestype");
		String creditNo=request.getParameter("creditno");
		String expDate=request.getParameter("expdate");
		String message="";
		User user=new User();
		user.setUserId(userId);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setPhoneNumber(phoneNumber);
		Order order=new Order();
		order.setUser(user);
		order.setAddress(address);
		order.setCity(city);
		order.setCountry(country);
		order.setState(state);
		order.setZip(zip);
		order.setPaymentType(salesType);
		order.setCreditNo(creditNo);
		order.setExpDate(expDate);
		try{
			int returnCode=inventoryService.placeOrder(order);
			if(returnCode==-1){
				message="Order cannot be completed due to technical issues";
			}else{
				message="Order Placed Successfully.Order # "+returnCode;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		request.setAttribute("message", message);
		RequestDispatcher view = request.getRequestDispatcher("Checkout.jsp");
        view.forward(request, response);
	}

}
