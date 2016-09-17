package itmd510.fp.dao;

import itmd510.fp.model.Customer;
import itmd510.fp.model.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public class AddToCartDAO 
{
	public void seeProducts(Customer loginCustomer,ObservableList data, TableView<Product> ProductsTable) throws ClassNotFoundException, SQLException
	{
		String sql = "Select * from a20367073_products";
		PreparedStatement stmt = DatabaseConnection.prepareStatement(sql);
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
		ProductsTable.getColumns().addAll(col);
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
		 ProductsTable.setItems(data);		
	}
	
	public void DeleteBasketitems(int id)
	{
		try
		{
			String sql = "Delete from a20367073_Basket where CustomerId=?";
			PreparedStatement pst = DatabaseConnection.prepareStatement(sql);
			pst.setInt(1, id);
			pst.executeUpdate();
			System.out.println("Product deleted.");
		}
		catch(SQLException | ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
}
