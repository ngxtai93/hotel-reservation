package team6.model;

import java.util.ArrayList;
import java.util.List;

import team6.dao.HotelDAO;
import team6.entity.Hotel;
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
	 * Input validation. Return a list of error
	 */
	public List<String> validateInput(String name, String address, String city, String state, String zip) {
		List<String> listError = new ArrayList<>();
		
		// check duplicate address
		Integer locationSeqNo = hotelDao.selectLocationSeqNo(city, state, zip);
		if(locationSeqNo != null) {	// location already registered
			Hotel hotel = hotelDao.selectHotel(address, locationSeqNo);
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

}
