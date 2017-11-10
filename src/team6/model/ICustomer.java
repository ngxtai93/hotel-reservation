package team6.model;

import java.time.LocalDate;

public interface ICustomer {

	//this is called when a customer click confirm a book on website
	public ITranscation Book(RoomType rt, LocalDate checkinDate);
	
	public int GetCustomerID();
	
	public int GetCustmerCreditNumber();
	
	public void CheckIn(int RoomNumber);
	
	public void CheckOut(int RoomNumber);
	
	//remove this room from database
	public void RemoveFromDB();
	
	//generate a string query that can used for directly store this 
	//object to DB
	public String GetQueryOfStoreToDB(); 
}
