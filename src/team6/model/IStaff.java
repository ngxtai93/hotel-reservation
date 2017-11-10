package team6.model;

public interface IStaff {

	//Just HardCode? Or
	
	public String GetStaffType();
	
	public int SetStaffID();
	//default Setters and Getters
	
	
	//remove this room from database
	public void RemoveFromDB();
	
	//generate a string query that can used for directly store this 
	//object to DB
	public String GetQueryOfStoreToDB(); 
	
}
