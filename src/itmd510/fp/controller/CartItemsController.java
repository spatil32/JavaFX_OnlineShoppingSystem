package itmd510.fp.controller;

import java.io.IOException;
import java.util.List;

import itmd510.fp.dao.CartItemsDao;
import itmd510.fp.dao.ProductDAO;
import itmd510.fp.model.Customer;
import itmd510.fp.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class CartItemsController 
{
	//This is the parent stage
	private Stage dialogStage;
	@FXML private TableView cartProducts = new TableView<Product>();
	@FXML private Button placeOrder;
	@FXML private ComboBox<String> CategoryBox = new ComboBox<String>();
	@FXML private Button detailsButton;
	@FXML private Label errorLabel;
	private ObservableList data = FXCollections.observableArrayList();
	//Method to set the parent stage of the current view
	public void setDialogStage(Stage dialogStage) 
	{
		this.dialogStage = dialogStage;
	}
	
	public void GetCartProducts()
	{
		List<Product> allItemsinCart = Customer.productList;
		System.out.println("In CartItemsController cart size= " + allItemsinCart.size());
		System.out.println("ho ithe " + allItemsinCart.size());
		new CartItemsDao().SeeAllElementsInBasket(data, cartProducts);
		CategoryBox.getItems().addAll("Kitchen","Electronics","Books");
	}	
	
	public void GetOrderDetails() throws IOException
	{
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
	
	public void ShowDetails()
	{
		boolean validate = true;
		String category = this.CategoryBox.getValue();
		if(category == "" || category.equals(""))
		{
			this.errorLabel.setText("Please select Category.");
			validate = false;
		}
		
		if(validate)
		{
			new ProductDAO().ShowProductDetails(category,errorLabel);
		}
	}
}