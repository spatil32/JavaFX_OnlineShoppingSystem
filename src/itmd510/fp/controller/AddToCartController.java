package itmd510.fp.controller;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import itmd510.fp.dao.AddToCartDAO;
import itmd510.fp.dao.CartItemsDao;
import itmd510.fp.model.Customer;
import itmd510.fp.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class AddToCartController
{
	//This is the parent stage
	private Stage dialogStage;
	
	//This is the Text box element in the view for name of bank
	@FXML private Button allProducts;
	@FXML private TableView<Product> ProductsTable = new TableView<Product>();
	@FXML public TextField productId;
	@FXML private Button addToCart;
	@FXML private TextField deleteFromCart;
	@FXML private Button deleteBtn;
	@FXML private Label productLabel;
	@FXML private Label errorMsgLabel;
	private ObservableList data = FXCollections.observableArrayList();
	//Method to set the parent stage of the current view
	public void setDialogStage(Stage dialogStage) 
	{
		this.dialogStage = dialogStage;
	}
	
	public void GetAllProductsList(Customer loginCustomer)
	{
		try 
		{
			System.out.println("In AddtoCartController = GetAllProductsList()");
			new AddToCartDAO().seeProducts(loginCustomer,data, ProductsTable);
		} 
		catch (ClassNotFoundException | SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public void AddProductToCart()
	{
		boolean validate = true;
		String id = this.productId.getText();
		Pattern pattern = Pattern.compile("[0-9]+");
		Matcher matcher = pattern.matcher(id);
		if(!matcher.matches())
		{
			errorMsgLabel.setText("Id should only have digits.");
			validate = false;
		}
		else
			errorMsgLabel.setText("");
		
		if(validate)
		{
			int product_id = Integer.parseInt(id);
			System.out.println("In AddtoCartController = AddProductToCart()");
			Product newadded = new CartItemsDao().Add_Item_To_List(product_id);
			if(newadded.getProduct_Name().equals(""))
				productLabel.setText("Given Product doesn't exist.");
			else
			{
				productLabel.setText("");
				new CartItemsDao().Add_Item_To_Cart(newadded);
				productLabel.setText("Product Added To Cart.");
				Customer.productList.add(newadded);
			}
		}
	}
	
	public void SeeProductsCart()
	{
		try 
		{
			Stage primaryStage = new Stage();
			//Create a loader for the UI components
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/itmd510/fp/view/CartItems.fxml"));
			//Inflate the view using the loader
            AnchorPane root = (AnchorPane) loader.load();
            //Set window title
            primaryStage.setTitle("Cart Items Page");
            //Create a scene with the inflated view
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/itmd510/fp/view/application1.css").toExternalForm());
            //Set the scene to the stage
            primaryStage.setScene(scene);
            //Get the controller instance from the loader
            CartItemsController controller = loader.getController();
            //Set the parent stage in the controller
            controller.setDialogStage(primaryStage);
	        //Show the view
	        primaryStage.show();
	        controller.GetCartProducts();
	        
	        dialogStage.fireEvent(new WindowEvent(dialogStage,WindowEvent.WINDOW_CLOSE_REQUEST));
		}
		catch(Exception e)
		{
			System.out.println("Error occured while inflating view: " + e);
		}
	}
	
	public void DeleteProductFromCart()
	{
		boolean validate = true;
		String pid = this.deleteFromCart.getText();
		Pattern pattern = Pattern.compile("[0-9]+");
		Matcher matcher = pattern.matcher(pid);
		if(!matcher.matches())
		{
			errorMsgLabel.setText("Id should only have digits.");
			validate = false;
		}
		else
			errorMsgLabel.setText("");
		if(validate)
		{
			int id = Integer.parseInt(pid);
			new CartItemsDao().DeleteCartProduct(errorMsgLabel,id);
		}
	}
}