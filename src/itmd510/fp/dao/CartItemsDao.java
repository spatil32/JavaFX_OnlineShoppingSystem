package itmd510.fp.dao;

import itmd510.fp.model.Product;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;


public class CartItemsDao 
{
	public Product Add_Item_To_List(int product_id)
	{
		Product addedProduct = new Product();
		String sql = "Select * from a20367073_products where ProductId=?";
		PreparedStatement stmt;
		try 
		{
			stmt = DatabaseConnection.prepareStatement(sql);
			stmt.setInt(1, product_id);
			ResultSet rst = stmt.executeQuery();
			if(rst.next())
			{
				int pid = rst.getInt("ProductId");
				String pname = rst.getString("ProductName");
				Date pdate = rst.getDate("MfgDate");
				char pcat = rst.getString("Category").charAt(0);
				int pprice = rst.getInt("Price");
				int pdiscount = rst.getInt("Discount");
				int ptqty = rst.getInt("TotalQty");
				int paqty = rst.getInt("AvailableQty");
				addedProduct.setProduct_Id(pid);
				addedProduct.setProduct_Name(pname);
				addedProduct.setManufacture_Date(pdate);
				addedProduct.setCategory(pcat);
				addedProduct.setPrice(pprice);
				addedProduct.setDiscount(pdiscount);
				addedProduct.setTotal_quantity(ptqty);
				addedProduct.setAvailable_quantity(paqty);
			}
			else
			{
				return addedProduct;
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return addedProduct;
	}
	
	public void Add_Item_To_Cart(Product newadded)
	{
		try
		{
			String insert = "Insert into a20367073_basket(CustomerId,ProductId,ShoppingDate,NumberOfItems,PicePerUnit) values(?,?,?,?,?)";
			PreparedStatement insertStmt = DatabaseConnection.prepareStatement(insert);
			insertStmt.setInt(1, CustomerDAO.CustomerIdentity);
			insertStmt.setInt(2, newadded.getProduct_Id());
			insertStmt.setDate(3, Date.valueOf(LocalDate.now()));
			insertStmt.setInt(4, 1);
			insertStmt.setInt(5, newadded.getPrice());
			insertStmt.execute();
			System.out.println("Product added to cart..");
		}
		catch(ClassNotFoundException | SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public void SeeAllElementsInBasket(ObservableList data, TableView<Product> cartProducts)
	{
		try
		{
			String select = "Select * from a20367073_basket where CustomerId=?";
			PreparedStatement stmt = DatabaseConnection.prepareStatement(select);
			stmt.setInt(1, CustomerDAO.CustomerIdentity);
			ResultSet rst = stmt.executeQuery();
			
			for(int i=0 ; i<rst.getMetaData().getColumnCount(); i++){
	            //We are using non property style for making dynamic table
	            final int j = i;                
	            TableColumn col = new TableColumn(rst.getMetaData().getColumnName(i+1));
	            col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
	                public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                                                              
	                    return new SimpleStringProperty(param.getValue().get(j).toString());                        
	                }                    
	            });
			cartProducts.getColumns().addAll(col);
			System.out.println("Column ["+i+"] ");
			}
			
			while(rst.next())
			{
				ObservableList row = FXCollections.observableArrayList();
				
				 for (int i = 1; i <= rst.getMetaData().getColumnCount(); i++) 
				 {
	                 row.add(rst.getString(i));
	                 System.out.println(row);
	             }
				 
				 data.add(row);
			}
			 cartProducts.setItems(data);	
		}
		catch(ClassNotFoundException | SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	
	public void DeleteCartProduct(Label msg, int id)
	{
		ObservableList data = FXCollections.observableArrayList();
		try
		{
			String delete = "Delete from a20367073_basket where ProductId=?";
			PreparedStatement deleteStmt = DatabaseConnection.prepareStatement(delete);
			deleteStmt.setInt(1, id);
			deleteStmt.execute();
			msg.setText("Product Deleted From Cart.");
			System.out.println("Product deleted from cart..");
		}
		catch(ClassNotFoundException | SQLException e)
		{
			e.printStackTrace();
		}
	}
}
