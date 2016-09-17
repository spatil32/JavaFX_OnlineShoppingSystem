package itmd510.fp.dao;

import itmd510.fp.model.BookProduct;
import itmd510.fp.model.ElectronicsProduct;
import itmd510.fp.model.KitchenProduct;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;

import javafx.scene.control.Label;

public class ProductDAO
{
	public void UpdateProductPrice(Label errorMsg, int id, int price)
	{
		try
		{
			String update = "UPDATE a20367073_products set Price=? where ProductId=?";
			PreparedStatement pst = DatabaseConnection.prepareStatement(update);
			pst.setInt(1, price);
			pst.setInt(2, id);
			pst.executeUpdate();
			errorMsg.setText("Product Price Updated.");
			System.out.println("Product updated successfully.");
		}
		catch(ClassNotFoundException | SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public void ShowProductDetails(String category,Label errorLabel)
	{
		String sql = "Select * from a20367073_products where Category=?";
		PreparedStatement st;
		try
		{
			st = DatabaseConnection.prepareStatement(sql);
			st.setString(1, String.valueOf(category.charAt(0)));
			ResultSet rs = st.executeQuery();
			if(rs.next())
			{
				int id = rs.getInt("ProductId");
				char cat = rs.getString("Category").charAt(0);
				int discount = rs.getInt("Discount");
				int available = rs.getInt("AvailableQty");
				if(cat == 'K')
				{
					errorLabel.setText("We have products of brand "+  new KitchenProduct(id,"Philips").getBrand() + ", at Discount of "+ discount
							+" percent and total "+ available + " in stock.");
				}
				else if(cat == 'B')
				{
					errorLabel.setText("We have books of author "+  new BookProduct(id, "Paulo Coelho").getAuthor() + ", at Discount of "+ discount
							+" percent and total "+ available + " in stock.");
				}				
				else if(cat == 'E')
				{
					errorLabel.setText("We have philips products of power "+  new ElectronicsProduct(id, 50).getWattPower() + " Watt, at Discount of "+ discount
							+" percent and total "+ available + " in stock.");
				}
				else
				{
					errorLabel.setText("There are no offers for this kind of products.");
				}
			}
		} 
		catch (ClassNotFoundException | SQLException e) 
		{
			e.printStackTrace();
		}
	}
}