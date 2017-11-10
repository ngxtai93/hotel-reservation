package team6.model;

import java.time.LocalDate;

public interface ITranscation {

	public LocalDate GetBookedDate();
	
	public LocalDate GetCheckInDate();
	
	public LocalDate GetCheckOutDate();
	
	public int GetCustomerID();
	
	public int GetBookedRoomNumber();
	
	public int SetTranscationNumber(int transcationID);
	
	//remove this room from database
	public void RemoveFromDB();
	
	//generate a string query that can used for directly store this 
	//object to DB
	public String GetQueryOfStoreToDB(); 
	
}
