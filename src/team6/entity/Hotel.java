package team6.entity;

import java.util.List;

public class Hotel {
	private Integer seqNo;
	private String name;
	private String address;
	private Location location;
	private List<String> listImage;
	
	
	public Hotel() {
		
	}
	
	public Hotel(String name, String address, String city, String state, String zip) {
		this.name = name;
		this.address = address;
		Location location = new Location();
		location.setCity(city);
		location.setState(state);
		location.setZip(zip);
		this.location = location;
	}
	
	public Integer getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public List<String> getListImage() {
		return listImage;
	}
	public void setListImage(List<String> listImage) {
		this.listImage = listImage;
	}
}
