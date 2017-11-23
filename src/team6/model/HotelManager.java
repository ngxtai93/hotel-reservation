package team6.model;

import java.util.ArrayList;
import java.util.List;

import team6.dao.HotelDAO;
import team6.entity.Hotel;
import team6.entity.Location;
import team6.entity.Room;
import team6.entity.RoomType;

public class HotelManager {

	private HotelDAO hotelDao = new HotelDAO();
	/**
	 * Get list of available room type from DB 
	 */
	public List<RoomType> getListRoomType() {
		return hotelDao.selectAllRoomType();
	}

	/**
	 * Return list of room by given hotel 
	 */
	public List<Room> getListRoom(int hotelId) {
		return hotelDao.selectRoomByHotel(hotelId);
	}

	/**
	 * Input validation. Return a list of error
	 */
	public List<String> validateInput(String name, String address, String city, String state, String zip) {
		List<String> listError = new ArrayList<>();
		
		// check duplicate address
		Location location = hotelDao.selectLocation(city, state, zip);
		if(location != null) {	// location already registered
			Hotel hotel = hotelDao.selectHotel(address, location);
			if(hotel != null) {
				listError.add("Address already has a hotel.");
			}
		}
		
		return (listError.size() == 0 ? null : listError);
	}

	public void addHotel(String name, String address, String city, String state, String zip) {
		Hotel hotel = new Hotel(name, address, city, state, zip);
		hotelDao.insertHotel(hotel);
	}

	public List<Location> getAvailableLocation() {
		return hotelDao.selectAllLocation();
	}

	/**
	 * Get all available hotel in an Location 
	 */
	public List<Hotel> getAvailableHotel(int locationId) {
		return hotelDao.selectHotelByLocation(locationId);
	}

	public void addRoom(int hotelId, int roomNum, int roomTypeId) {
		hotelDao.insertRoom(hotelId, roomNum, roomTypeId);
	}

	public boolean isRoomExist(int hotelId, int roomNum) {
		Room room = hotelDao.selectRoom(hotelId, roomNum);
		return (room != null ? true : false);
	}

	public void updateRoom(int roomId, int roomNum, int roomTypeId) {
		hotelDao.updateRoom(roomId, roomNum, roomTypeId);
	}

	public void deleteRoom(int roomId) {
		hotelDao.deleteRoom(roomId);
	}

}
