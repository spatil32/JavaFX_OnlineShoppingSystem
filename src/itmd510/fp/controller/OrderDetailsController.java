package itmd510.fp.controller;

import itmd510.fp.dao.AddToCartDAO;
import itmd510.fp.dao.CustomerDAO;
import itmd510.fp.dao.OrderDetailsDAO;
import itmd510.fp.model.Basket;
import itmd510.fp.model.Customer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class OrderDetailsController
{
	private Stage dialogStage;
	@FXML private Label greetUser;
	@FXML private Label userAddress;
	@FXML private Label totalBill;
	@FXML private Button logOut;
	@FXML private Button feedbackBtn;
	@FXML private Label placedOrder;
	@FXML private PasswordField updatePassword;
	@FXML private Button updateAccount;
	@FXML private Label passwordUpdatedLbl;
	//Method to set the parent stage of the current view
	public void setDialogStage(Stage dialogStage) 
	{
		this.dialogStage = dialogStage;
	}
	
	public void GetAllOrderDetails()
	{
		Customer c = new CustomerDAO().GetCustomerById(CustomerDAO.CustomerIdentity);
		Basket b = new OrderDetailsDAO().GetBillAmount(c.getCustomer_Id());
		if(b.getTotalBillAmount() == 0)
		{
			this.greetUser.setText("Hello, "+ c.getFirst_Name());
			this.totalBill.setText("You dont have any delivery of product now.");
			this.userAddress.setText(c.getAddress());		
		}
		else
		{
			this.greetUser.setText("Hello, "+ c.getFirst_Name());
			this.userAddress.setText(c.getAddress());
			this.placedOrder.setText("Your Order has been placed.");
			this.totalBill.setText("Your total bill for the shopping is Rs." + b.getTotalBillAmount() + " payable at delivery date " + b.getDeliveryDate() + ".");
			new OrderDetailsDAO().AddOrderDetailsInfo(b);
		}
 	}
	
	public void LogOutCustomer()
	{
		new AddToCartDAO().DeleteBasketitems(CustomerDAO.CustomerIdentity);
		CustomerDAO.CustomerIdentity = 0;
        dialogStage.fireEvent(new WindowEvent(dialogStage,WindowEvent.WINDOW_CLOSE_REQUEST));
        
        try
		{
        	Stage primaryStage = new Stage();
			//Create a loader for the UI components
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/itmd510/fp/view/Login.fxml"));
			//Inflate the view using the loader
            AnchorPane root = (AnchorPane) loader.load();
            //Set window title
            primaryStage.setTitle("Login Page");
            //Create a scene with the inflated view
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/itmd510/fp/view/application.css").toExternalForm());
            //Set the scene to the stage
            primaryStage.setScene(scene);
            //Get the controller instance from the loader
            LoginController controller = loader.getController();
            //Set the parent stage in the controller
            controller.setDialogStage(primaryStage);
            //Show the view
            primaryStage.show();
	        dialogStage.fireEvent(new WindowEvent(dialogStage,WindowEvent.WINDOW_CLOSE_REQUEST));
		}
		catch(Exception e)
		{
			System.out.println("Error occured while inflating view: " + e);
		}
	}
	
	public void GiveFeedback()
	{
		try 
		{
			Stage primaryStage = new Stage();
			//Create a loader for the UI components
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/itmd510/fp/view/GiveFeedback.fxml"));
			//Inflate the view using the loader
            AnchorPane root = (AnchorPane) loader.load();
            //Set window title
            primaryStage.setTitle("Give A Feedback Page");
            //Create a scene with the inflated view
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/itmd510/fp/view/application.css").toExternalForm());
            //Set the scene to the stage
            primaryStage.setScene(scene);
            //Get the controller instance from the loader
            GiveFeedbackController controller = loader.getController();
            //Set the parent stage in the controller
            controller.setDialogStage(primaryStage);
	        //Show the view
	        primaryStage.show();
	        //controller.SubmitAFeedback();
	        dialogStage.fireEvent(new WindowEvent(dialogStage,WindowEvent.WINDOW_CLOSE_REQUEST));
		}
		catch(Exception e)
		{
			System.out.println("Error occured while inflating view: " + e);
		}
	}
	
	public void UpdateAccount()
	{
		String newPassword = this.updatePassword.getText();
		new Customer().EditCustomerDetails(newPassword, CustomerDAO.CustomerIdentity);
		this.passwordUpdatedLbl.setText("Password Updated Successfully.");
	}
}