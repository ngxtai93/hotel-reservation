package team6.entity;

import java.time.LocalDate;

import team6.model.ITranscation;

public interface ICustomer {

	//this is called when a customer click confirm a book on website
	public ITranscation Book(BedType rt, LocalDate checkinDate);
	
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
