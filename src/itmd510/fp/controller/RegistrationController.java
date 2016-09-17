package itmd510.fp.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import itmd510.fp.dao.CustomerDAO;
import itmd510.fp.model.Customer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class RegistrationController 
{
	//This is the parent stage
	private Stage dialogStage;
	//This is the Text box element in the view for name of bank
	@FXML private TextField input_first_name;
	//This is the Text box element in the view for address of bank
	@FXML private TextField input_last_name;		
	//This is the Text box element in the view for name of bank
	@FXML private TextField input_age;
	//This is the Text box element in the view for address of bank
	@FXML private RadioButton radioMale;
	@FXML private RadioButton radioFemale;
	//This is the Text box element in the view for name of bank
	@FXML private TextField input_address;
	//This is the Text box element in the view for address of bank
	@FXML private TextField input_email;
	//This is the Text box element in the view for name of bank
	@FXML private TextField input_phone;
	//This is the Text box element in the view for address of bank
	@FXML private TextField input_username;
	//This is the Text box element in the view for name of bank
	@FXML private TextField input_password;
	@FXML private Label validFname;
	@FXML private Label validLname;
	@FXML private Label validAge;
	@FXML private Label validAddress;
	@FXML private Label validEmail;
	@FXML private Label validPhone;
	@FXML private Label validUsername;
	@FXML private Label validPassword;
	
	//Method to set the parent stage of the current view
	public void setDialogStage(Stage dialogStage) 
	{
		this.dialogStage = dialogStage;
	}
	
	public void NewRegistration()
	{
		boolean validated = true;
		final ToggleGroup group = new ToggleGroup();
		radioMale.setToggleGroup(group);
		radioMale.setSelected(true);
		radioFemale.setToggleGroup(group);
		radioFemale.setUserData("Female");
		radioMale.setUserData("Male");
		String fname = input_first_name.getText();
		String lname = input_last_name.getText();
		String age = input_age.getText();
		String gender = group.getSelectedToggle().getUserData().toString();
		String address = input_address.getText();
		String email = input_email.getText();
		String phone = input_phone.getText();
		String username = input_username.getText();
		String password = input_password.getText();

		//validations
		if(fname.equals(""))
		{
			validFname.setText("Blank Username");
			validated = false;
		}
		else
			validFname.setText("");		
		
		if(lname.equals(""))
		{
			validLname.setText("Blank Username");
			validated = false;
		}
		else
			validLname.setText("");
		
		if(age.equals(""))
		{
			validAge.setText("Age cannot be blank");
			validated = false;
		}
		else
			validAge.setText("");
		
		for(int i=0; i<age.length(); i++)
		{
			if(Character.isLetter(age.charAt(i)))
			{
				validAge.setText("Age cannot have letters.");
				validated = false;
			}
			else
				validAge.setText("");
		}

		if(address.equals(""))
		{
			validAddress.setText("Address cannot be blank");
			validated = false;
		}
		else
			validAddress.setText("");

		if(email.equals(""))
		{
			validEmail.setText("Email cannot be blank");
			validated = false;
		}
		else
			validEmail.setText("");

		String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
		Pattern emailPattern = Pattern.compile(emailRegex);
		Matcher matcher = emailPattern.matcher(email);
		if(!matcher.matches())
		{
			validEmail.setText("Email not in proper format");
			validated = false;
		}
		else
			validEmail.setText("");

		
		if(phone.equals(""))
		{
			validPhone.setText("Phone cannot be blank");
			validated = false;
		}
		else
			validPhone.setText("");
		
		for(int i=0; i<phone.length(); i++)
		{
			if(Character.isLetter(phone.charAt(i)))
			{
				validPhone.setText("Phone cannot have letters.");
				validated = false;
			}
			else
				validPhone.setText("");
		}

		if(username.equals(""))
		{
			validUsername.setText("Username cannot be blank");
			validated = false;
		}
		else
			validUsername.setText("");
		
		if(password.equals(""))
		{
			validPassword.setText("Password cannot be blank");
			validated = false;
		}
		else
			validPassword.setText("");
		
		if(validated)
		{
			Customer regCustomer = new Customer();
			regCustomer.setFirst_Name(fname);
			regCustomer.setLast_Name(lname);
			regCustomer.setAge(Integer.parseInt(age));
			regCustomer.setGender(gender);
			regCustomer.setAddress(address);
			regCustomer.setEmail(email);
			regCustomer.setPhoneNumber(phone);
			regCustomer.setUsername(username);
			regCustomer.setPassword(password);
			CustomerDAO d = new CustomerDAO();
			d.NewRegistration(regCustomer);
			
			dialogStage.fireEvent(new WindowEvent(dialogStage,WindowEvent.WINDOW_CLOSE_REQUEST));
		}
	}
}