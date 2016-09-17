package itmd510.fp.controller;

import itmd510.fp.dao.CustomerDAO;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class NewProductController 
{
	//This is the parent stage
	private Stage dialogStage;
	@FXML private TextField id;
	@FXML private TextField name;
	@FXML private DatePicker datePicker;
	@FXML private TextField category;
	@FXML private TextField price;
	@FXML private TextField discount;
	@FXML private TextField totalQuantity;
	@FXML private TextField availableQuantity;
	@FXML private Label validProductId;
	@FXML private Label validProductName;
	@FXML private Label validCategory;
	@FXML private Label validPrice;
	@FXML private Label validDiscount;
	@FXML private Label validTotalQuantity;
	@FXML private Label validAvailableQuantity;
	@FXML private Label validDate;
	//Method to set the parent stage of the current view
	public void setDialogStage(Stage dialogStage) 
	{
		this.dialogStage = dialogStage;
	}
	
	public void AddProductToDatabase() throws IOException
	{
		try
		{
			boolean validated = true;
			String pid = this.id.getText();
			String pname = this.name.getText();
			Date mfg = Date.valueOf(this.datePicker.getValue());
			String cat = this.category.getText();
			String price = this.price.getText();
			String disc = this.discount.getText();
			String total = this.totalQuantity.getText();
			String available = this.availableQuantity.getText();
			
			//validations
			if(pid.equals(""))
			{
				validProductId.setText("Blank Product Id");
				this.validDate.setText("");
				validated = false;
			}
			else
			{
				validProductId.setText("");
				this.validDate.setText("");
			}
			for(int i=0; i<pid.length(); i++)
			{
				if(Character.isLetter(pid.charAt(i)))
				{
					validProductId.setText("Product Id cannot have letters.");
					validated = false;
				}
				else
					validProductId.setText("");
			}
			
			if(cat.equals(""))
			{
				validCategory.setText("Blank Category");
				validated = false;
			}
			else
				validCategory.setText("");	
			
			for(int i=0; i<cat.length(); i++)
			{
				if(Character.isDigit(cat.charAt(i)))
				{
					validCategory.setText("Category cannot have numbers.");
					validated = false;
				}
				else
					validCategory.setText("");
			}
							
			if(pname.equals(""))
			{
				validProductName.setText("Blank Product Name");
				validated = false;
			}
			else
				validProductName.setText("");	
	
			if(price.equals(""))
			{
				validPrice.setText("Blank Price");
				validated = false;
			}
			else
				validPrice.setText("");	
	
			
			for(int i=0; i<price.length(); i++)
			{
				if(Character.isLetter(price.charAt(i)))
				{
					validPrice.setText("Price cannot have letters.");
					validated = false;
				}
				else
					validPrice.setText("");
			}
			
			if(disc.equals(""))
			{
				validDiscount.setText("Blank Discount");
				validated = false;
			}
			else
				validDiscount.setText("");	
	
			for(int i=0; i<disc.length(); i++)
			{
				if(Character.isLetter(disc.charAt(i)))
				{
					validDiscount.setText("Discount cannot have letters.");
					validated = false;
				}
				else
					validDiscount.setText("");
			}
			
			if(total.equals(""))
			{
				validTotalQuantity.setText("Blank Total Quantity");
				validated = false;
			}
			else
				validTotalQuantity.setText("");	
	
			for(int i=0; i<total.length(); i++)
			{
				if(Character.isLetter(total.charAt(i)))
				{
					validTotalQuantity.setText("Total Quantity cannot have letters.");
					validated = false;
				}
				else
					validTotalQuantity.setText("");
			}
			
			if(available.equals(""))
			{
				validAvailableQuantity.setText("Blank Available Quantity");
				validated = false;
			}
			else
				validAvailableQuantity.setText("");	
	
			for(int i=0; i<available.length(); i++)
			{
				if(Character.isLetter(available.charAt(i)))
				{
					validAvailableQuantity.setText("Available Quantity cannot have letters.");
					validated = false;
				}
				else
					validAvailableQuantity.setText("");
			}
			
			if(validated)
			{
				new CustomerDAO().AddProduct(Integer.parseInt(pid),pname,mfg,cat.charAt(0),Integer.parseInt(price)
						,Integer.parseInt(disc),Integer.parseInt(total),Integer.parseInt(available));
				this.validAvailableQuantity.setText("Product Added Successfully.");
			}
		}
		catch(Exception e)
		{
			this.validDate.setText("Please select date.");
		}
	}
	
	public void GoBackFunction()
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