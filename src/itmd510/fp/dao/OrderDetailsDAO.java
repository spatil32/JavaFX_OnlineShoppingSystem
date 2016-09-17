package itmd510.fp.dao;

import itmd510.fp.model.Basket;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;

public class OrderDetailsDAO 
{
	public Basket GetBillAmount(int id)
	{
		Basket b = new Basket();
		b.setCustomerId(CustomerDAO.CustomerIdentity);
		int totalBill = 0;
		String sql = "Select * from a20367073_basket where CustomerId=?";
		PreparedStatement st;
		try
		{
			st = DatabaseConnection.prepareStatement(sql);
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();
			while(rs.next())
			{
				totalBill = totalBill + rs.getInt("PicePerUnit");
			}
			b.setTotalBillAmount(totalBill);
			Date shoppingDate = Date.valueOf(LocalDate.now());
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, 7);
			shoppingDate = new Date(cal.getTime().getTime());
			b.setDeliveryDate(shoppingDate);
		} 
		catch (ClassNotFoundException | SQLException e) 
		{
			e.printStackTrace();
		}
		return b;
	}
	
	public void AddOrderDetailsInfo(Basket b)
	{
		String sql = "INSERT INTO a20367073_Orders(CustomerId,TotalBillAmount,DeliveryDate) VALUES(?,?,?)";
		PreparedStatement statement;
		try
		{
			statement = DatabaseConnection.prepareStatement(sql);
			statement.setInt(1, b.getCustomerId());
			statement.setInt(2, b.getTotalBillAmount());
			statement.setDate(3, b.getDeliveryDate());
			statement.execute();
			System.out.println("Order placed successfully");
		}
		catch (ClassNotFoundException | SQLException e)
		{
			e.printStackTrace();
		}
	}
}