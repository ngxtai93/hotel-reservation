package team6.entity;

public interface IRoom {
	
	public int GetRoomNumber();
	public void SetRoomNumber(int number);
	
	public BedType GetRoomType();
	public void SetRoomType(BedType rt);
	
	public void Book();
	public void Occupy();
	
	public boolean IsBooked();
	public boolean IsOccupied();
	
	//remove this room from database
	public void RemoveFromDB();
	
	//generate a string query that can used for directly store this 
	//object to DB
	public String GetQueryOfStoreToDB(); 
	

}
