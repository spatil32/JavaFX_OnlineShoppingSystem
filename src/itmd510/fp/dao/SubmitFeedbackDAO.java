package itmd510.fp.dao;

import itmd510.fp.model.GiveFeedback;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SubmitFeedbackDAO 
{
	public void FeedbackFormDetails(int custId, String description, int rating)
	{
		String sql = "INSERT INTO a20367073_Feedback(CustomerId,Description,Rating) VALUES(?,?,?)";
		PreparedStatement statement;
		try
		{
			statement = DatabaseConnection.prepareStatement(sql);
			statement.setInt(1, custId);
			statement.setString(2, description);
			statement.setInt(3, rating);			
			statement.execute();
			System.out.println("Feedback Given Successfully.");
		}
		catch (ClassNotFoundException | SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public GiveFeedback GetFeedback(int custId)
	{
		GiveFeedback f = new GiveFeedback();
		String sql = "Select * from a20367073_feedback where CustomerId=?";
		PreparedStatement stmt;
		try 
		{
			stmt = DatabaseConnection.prepareStatement(sql);
			stmt.setInt(1, custId);
			ResultSet rst = stmt.executeQuery();
			if(rst.next())
			{
				f.setCustomerId(rst.getInt("CustomerId"));
				f.setDescription(rst.getString("Description"));
				f.setRating(rst.getInt("Rating"));
			}
			else
			{
				f.setDescription("Empty. Feedback Not Found for this user.");
				f.setRating(0);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return f;
	}
}