package itmd510.fp.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import itmd510.fp.dao.CustomerDAO;
import itmd510.fp.dao.SubmitFeedbackDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GiveFeedbackController 
{
	private Stage dialogStage;
	@FXML private TextArea descriptionField;
	@FXML private TextField ratingField;
	@FXML private Button submitFeedback;
	@FXML private Label RatingLbl;
	//Method to set the parent stage of the current view
	public void setDialogStage(Stage dialogStage) 
	{
		this.dialogStage = dialogStage;
	}
	
	public void NavigateToLogoutPage()
	{
		boolean validate = true;
		int custId = CustomerDAO.CustomerIdentity;
		String description = this.descriptionField.getText();
		String rate = this.ratingField.getText();
		for(int i=0; i<rate.length(); i++)
		{
			if(Character.isLetter(rate.charAt(i)))
			{
				this.RatingLbl.setText("Rating should only have digits.");
				validate = false;				
			}
			else
				this.RatingLbl.setText("");
		}
		if(validate)
		{
			int rating = Integer.parseInt(this.ratingField.getText());
			new SubmitFeedbackDAO().FeedbackFormDetails(custId, description, rating);
			try 
			{
				Stage primaryStage = new Stage();
				//Create a loader for the UI components
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/itmd510/fp/view/OrderDetails.fxml"));
				//Inflate the view using the loader
	            AnchorPane root = (AnchorPane) loader.load();
	            //Set window title
	            primaryStage.setTitle("Order Details Page");
	            //Create a scene with the inflated view
	            Scene scene = new Scene(root);
	            scene.getStylesheets().add(getClass().getResource("/itmd510/fp/view/application.css").toExternalForm());
	            //Set the scene to the stage
	            primaryStage.setScene(scene);
	            //Get the controller instance from the loader
	            OrderDetailsController controller = loader.getController();
	            //Set the parent stage in the controller
	            controller.setDialogStage(primaryStage);
		        //Show the view
		        primaryStage.show();
		        controller.GetAllOrderDetails();
		        dialogStage.fireEvent(new WindowEvent(dialogStage,WindowEvent.WINDOW_CLOSE_REQUEST));
			}
			catch(Exception e)
			{
				System.out.println("Error occured while inflating view in CartItemsController: " + e);
			}
		}
	}
}
