package itmd510.fp.controller;

import java.io.IOException;
import itmd510.fp.dao.CustomerDAO;
import itmd510.fp.model.Customer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import itmd510.fp.controller.AddToCartController;

public class LoginController
{
	//This is the parent stage
	private Stage dialogStage;
	
	//This is the Text box element in the view for name of bank
	@FXML private TextField uname;
	//This is the Text box element in the view for address of bank
	@FXML private PasswordField pwd;
	@FXML private Label userLabel;
	@FXML private Label passwordLabel;
	@FXML private Button loginBtn;
	@FXML private Button registerBtn;
	
	//Method to set the parent stage of the current view
	public void setDialogStage(Stage dialogStage) 
	{
		this.dialogStage = dialogStage;
	}

	public void LoginCustomer() throws IOException
	{
		String user = this.uname.getText();
		String password = this.pwd.getText();
		
		if(user == null || user.trim().equals("")) 
		{
			this.userLabel.setText("Username cannot be empty.");
		}
		if(password == null || password.trim().equals("")) 
		{
			this.passwordLabel.setText("Password cannot be empty.");
		}
		
		Customer loginCustomer = new Customer();
		loginCustomer.setUsername(user);
		loginCustomer.setPassword(password);
		CustomerDAO d = new CustomerDAO();
		loginCustomer = d.Login(loginCustomer, userLabel, passwordLabel);
		if(loginCustomer != null && !loginCustomer.getUsername().equals("admin"))
			showProducts(loginCustomer);
		else if(loginCustomer != null && loginCustomer.getUsername().equals("admin"))
			this.GoToAdminPage();
	}
	
	
	public void showProducts(Customer loginCustomer)
	{
		try 
		{
			Stage primaryStage = new Stage();
			//Create a loader for the UI components
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/itmd510/fp/view/AddToCart.fxml"));
			//Inflate the view using the loader
            AnchorPane root = (AnchorPane) loader.load();
            //Set window title
            primaryStage.setTitle("Add To Cart Page");
            //Create a scene with the inflated view
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/itmd510/fp/view/application1.css").toExternalForm());
            //Set the scene to the stage
            primaryStage.setScene(scene);
            //Get the controller instance from the loader
            AddToCartController controller = loader.getController();
            //Set the parent stage in the controller
            controller.setDialogStage(primaryStage);
	        //Show the view
	        primaryStage.show();
	        controller.GetAllProductsList(loginCustomer);
	        dialogStage.fireEvent(new WindowEvent(dialogStage,WindowEvent.WINDOW_CLOSE_REQUEST));
		}
		catch(Exception e)
		{
			System.out.println("Error occured while inflating view: " + e);
		}
	}
	
	public void RegisterCustomer()
	{
		try 
		{
			Stage primaryStage = new Stage();
			//Create a loader for the UI components
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/itmd510/fp/view/RegisterCustomer.fxml"));
			//Inflate the view using the loader
            AnchorPane root = (AnchorPane) loader.load();
            //Set window title
            primaryStage.setTitle("Registration Page");
            //Create a scene with the inflated view
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/itmd510/fp/view/application.css").toExternalForm());
            //Set the scene to the stage
            primaryStage.setScene(scene);
            //Get the controller instance from the loader
            RegistrationController controller = loader.getController();
            //Set the parent stage in the controller
            controller.setDialogStage(primaryStage);
            //Show the view
            primaryStage.show();
		}
		catch(Exception e)
		{
			System.out.println("Error occured while inflating view: " + e);
		}
	}
	
	public void GoToAdminPage() throws IOException
	{
		try 
		{
			Stage primaryStage = new Stage();
			//Create a loader for the UI components
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/itmd510/fp/view/AdminPage.fxml"));
			//Inflate the view using the loader
            AnchorPane root = (AnchorPane) loader.load();
            //Set window title
            primaryStage.setTitle("Admin Page");
            //Create a scene with the inflated view
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/itmd510/fp/view/application.css").toExternalForm());
            //Set the scene to the stage
            primaryStage.setScene(scene);
            //Get the controller instance from the loader
            AdminPageController controller = loader.getController();
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
}
