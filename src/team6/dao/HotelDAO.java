package team6.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import team6.entity.BedType;
import team6.entity.Hotel;
import team6.entity.Location;
import team6.entity.RoomType;

public class HotelDAO {

	private Connection conn;
	
	public HotelDAO() {
		conn = MySQLDatabaseOperator.INSTANCE.getConnection();
	}
	
	/**
	 * Select all room type from the database
	 */
	public List<RoomType> selectAllRoomType() {
		List<RoomType> listRoomType = null;
		String sql = "SELECT * from csp584_project.room_type WHERE del_flag = 0";
		
		try(PreparedStatement ps = conn.prepareStatement(sql)) {
			ResultSet rs = ps.executeQuery();
			if(rs.isBeforeFirst()) {
				listRoomType = new ArrayList<>();
			}
			while(rs.next()) {
				RoomType roomType = buildRoomTypeObject(rs);
				listRoomType.add(roomType);
			}
			
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return listRoomType;
	}

	public List<Hotel> selectHotelByLocation(String city, String state) {
		String sql = "SELECT h.seq_no, h.location, l.city, l. state, l.zip, h.name, h.address, h.image_link, h.description"
				+ " FROM csp584_project.hotel h JOIN csp584_project.location l"
				+ " ON h.location = l.seq_no"
				+ " WHERE l.city = ? AND l.state = ? AND h.del_flag = 0;"
		;
		List<Hotel> listHotel = null;
		
		try(PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, city);
			ps.setString(2, state);
			ResultSet rs = ps.executeQuery();
			if(rs.isBeforeFirst()) {
				listHotel = new ArrayList<>();
				while(rs.next()) {
					Hotel hotel = buildHotelObject(rs);
					listHotel.add(hotel);
				}
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return listHotel;
	}

	public List<Hotel> selectHotelByLocation(int locationId) {
		String sql = "SELECT h.seq_no, h.location, l.city, l. state, l.zip, h.name, h.address, h.image_link, h.description"
				+ " FROM csp584_project.hotel h JOIN csp584_project.location l"
				+ " ON h.location = l.seq_no"
				+ " WHERE h.location = ? AND h.del_flag = 0;"
		;
		List<Hotel> listHotel = null;
		
		try(PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, locationId);
			ResultSet rs = ps.executeQuery();
			if(rs.isBeforeFirst()) {
				listHotel = new ArrayList<>();
				while(rs.next()) {
					Hotel hotel = buildHotelObject(rs);
					listHotel.add(hotel);
				}
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return listHotel;
	}

	/**
	 * Select hotel by address and given location
	 */
	public Hotel selectHotel(String address, Location location) {
		String sql = "SELECT h.seq_no, h.location, l.city, l. state, l.zip, h.name, h.address, h.image_link, h.description"
				+ " FROM csp584_project.hotel h JOIN csp584_project.location l"
				+ " ON h.location = l.seq_no"
				+ " WHERE h.address = ? AND h.location = ? AND h.del_flag = 0;"
		;
		Hotel hotel = null;
		
		try(PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, address);
			ps.setInt(2, location.getSeqNo().intValue());
			ResultSet rs = ps.executeQuery();
			if(rs.isBeforeFirst()) {
				rs.next();
				hotel = buildHotelObject(rs);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return hotel;
	}
	
	public void insertHotel(Hotel hotel) {
		Location location = hotel.getLocation();
		String city = location.getCity();
		String state = location.getState();
		String zip = location.getZip();
		
		Location locationDb = selectLocation(city, state, zip);
		if(locationDb == null) {
			insertLocation(city, state, zip);
			locationDb = selectLocation(city, state, zip);
		}
		hotel.setLocation(locationDb);
		
		String sql = "INSERT INTO `csp584_project`.`hotel` (`location`, `name`, `address`, `description`) VALUES (?, ?, ?, ?);";
		try(PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, locationDb.getSeqNo().intValue());
			ps.setString(2, hotel.getName());
			ps.setString(3, hotel.getAddress());
			ps.setString(4, hotel.getDescription());
			ps.execute();
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		// get the last inserted id and set to the Hotel object
		int lastInsertId = -1;
		sql = "SELECT LAST_INSERT_ID();";
		try(PreparedStatement ps = conn.prepareStatement(sql)) {
			ResultSet rs = ps.executeQuery();
			rs.next();
			lastInsertId = rs.getInt(1);
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		hotel.setSeqNo(Integer.valueOf(lastInsertId));
		
	}

	public void updateListImage(int seqNo, List<String> listImage) {
		// listImage -> image_link
		StringBuilder imageLink = new StringBuilder();
		for(String s: listImage) {
			imageLink.append(s).append(",");
		}
		
		String sql = "UPDATE csp584_project.hotel SET image_link = ? WHERE seq_no = ?;";
		try(PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, imageLink.toString());
			ps.setInt(2, seqNo);
			ps.execute();
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public void updateHotel(Hotel hotel) {
		Location location = hotel.getLocation();
		String city = location.getCity();
		String state = location.getState();
		String zip = location.getZip();
		
		Location locationDb = selectLocation(city, state, zip);
		if(locationDb == null) {
			insertLocation(city, state, zip);
			locationDb = selectLocation(city, state, zip);
		}
		
		String sql = "UPDATE csp584_project.hotel SET location = ?, name = ?, address = ?, description = ? WHERE seq_no = ?;";
		try(PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, locationDb.getSeqNo().intValue());
			ps.setString(2, hotel.getName());
			ps.setString(3, hotel.getAddress());
			ps.setString(4, hotel.getDescription());
			ps.setInt(5, hotel.getSeqNo().intValue());
			ps.execute();
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public void deleteHotel(int seqNo) {
		String sql = "UPDATE csp584_project.hotel SET del_flag = 1 WHERE seq_no = ?;";
		try(PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, seqNo);
			ps.execute();
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	private void insertLocation(String city, String state, String zip) {
		String sql = "INSERT INTO `csp584_project`.`location` (`city`, `state`, `zip`) VALUES (?, ?, ?);";
		try(PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, city);
			ps.setString(2, state);
			ps.setString(3, zip);
			ps.execute();
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Get Location by given info
	 */
	public Location selectLocation(String city, String state, String zip) {
		String sql = "SELECT * from csp584_project.location WHERE city = ? AND state = ? AND zip = ?";
		Location result = null;
		
		try(PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, city);
			ps.setString(2, state);
			ps.setString(3, zip);
			ResultSet rs = ps.executeQuery();
			if(rs.isBeforeFirst()) {
				rs.next();
				result = buildLocationObject(rs);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return result;
	}

	public List<Location> selectAllLocation() {
		List<Location> listLocation = null;
		String sql = "SELECT * from csp584_project.location";
		
		try(PreparedStatement ps = conn.prepareStatement(sql)) {
			ResultSet rs = ps.executeQuery();
			if(rs.isBeforeFirst()) {
				listLocation = new ArrayList<>();
			}
			while(rs.next()) {
				Location location = buildLocationObject(rs);
				listLocation.add(location);
			}
			
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return listLocation;
	}

	public List<RoomType> selectRoomTypeByHotel(int hotelId) {
		String sql = "SELECT * from csp584_project.room_type WHERE hotel = ? AND del_flag = 0";
		List<RoomType> result = null;
		try(PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, hotelId);
			ResultSet rs = ps.executeQuery();
			if(rs.isBeforeFirst()) {
				result = new ArrayList<>();
			}
			while(rs.next()) {
				RoomType rt = buildRoomTypeObject(rs);
				result.add(rt);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return result;

	}

	public RoomType selectRoomType(int hotelId, String roomName) {
		String sql = "SELECT * from csp584_project.room_type WHERE hotel = ? AND name = ? AND del_flag = 0";
		RoomType rt = null;
		try(PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, hotelId);
			ps.setString(2, roomName);
			ResultSet rs = ps.executeQuery();
			if(rs.isBeforeFirst()) {
				rs.next();
				rt = buildRoomTypeObject(rs);
			}
			
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return rt;
	}

	public void insertRoomType(int hotelId, RoomType paramObject) {
		String sql = "INSERT INTO `csp584_project`.`room_type`"
					+ "(hotel, name, bed_type, bed_amount, people_no, view, is_wifi, is_tv, price, discount, room_list)"
					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		try(PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, hotelId);
			ps.setString(2, paramObject.getName());
			ps.setString(3, paramObject.getBedType().toString().toLowerCase());
			ps.setInt(4, paramObject.getBedAmount().intValue());
			ps.setInt(5, paramObject.getPeopleNo().intValue());
			ps.setString(6, paramObject.getView());
			ps.setBoolean(7, paramObject.getIsWifi().booleanValue());
			ps.setBoolean(8, paramObject.getIsTV().booleanValue());
			ps.setDouble(9, paramObject.getPrice().doubleValue());
			ps.setDouble(10, paramObject.getDiscount().doubleValue());
			
			StringBuilder sb = new StringBuilder();
			for(Integer i: paramObject.getRoomList()) {
				sb.append(i.toString()).append(",");
			}
			ps.setString(11, sb.toString());
			ps.execute();
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public void updateRoomType(RoomType paramObject) {
		String sql = "UPDATE csp584_project.room_type"
					+ " SET name = ?, bed_type = ?, bed_amount = ?, people_no = ?, view = ?"
					+ ", is_wifi = ?, is_tv = ?, price = ?, discount = ?, room_list = ?"
					+ " WHERE seq_no = ?;";
		try(PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, paramObject.getName());
			ps.setString(2, paramObject.getBedType().toString().toLowerCase());
			ps.setInt(3, paramObject.getBedAmount().intValue());
			ps.setInt(4, paramObject.getPeopleNo().intValue());
			ps.setString(5, paramObject.getView());
			ps.setBoolean(6, paramObject.getIsWifi().booleanValue());
			ps.setBoolean(7, paramObject.getIsTV().booleanValue());
			ps.setDouble(8, paramObject.getPrice().doubleValue());
			ps.setDouble(9, paramObject.getDiscount().doubleValue());
			
			StringBuilder sb = new StringBuilder();
			for(Integer i: paramObject.getRoomList()) {
				sb.append(i.toString()).append(",");
			}
			ps.setString(10, sb.toString());
			ps.setInt(11, paramObject.getSeqNo().intValue());
			ps.execute();
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Select all room list by hotel ID 
	 */
	public List<Integer> selectRoomListByHotel(int hotelId) {
		List<Integer> result = null;
		String sql = "SELECT room_list from csp584_project.room_type WHERE hotel = ? AND del_flag = 0";
		try(PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, hotelId);
			ResultSet rs = ps.executeQuery();
			
			if(rs.isBeforeFirst()) {
				result = new ArrayList<>();
			}
			while(rs.next()) {
				List<Integer> listInDb =	Arrays.asList(
												rs.getString("room_list").split(",")
											)
											.stream()
											.map(Integer::parseInt)
											.collect(Collectors.toList())
				;
				result.addAll(listInDb);	// union of result and listInDb
			}
			
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return result;
	}

	public void insertRoom(int hotelId, int roomNum, int roomTypeId, double price, double discount) {
		String sql = "INSERT INTO `csp584_project`.`room` (`hotel`, `room_number`, `room_type`, `price`, `discount`)"
				+ " VALUES (?, ?, ?, ?, ?);";
		try(PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, hotelId);
			ps.setInt(2, roomNum);
			ps.setInt(3, roomTypeId);
			ps.setDouble(4, price);
			ps.setDouble(5, discount);
			ps.execute();
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}



	public void deleteRoomType(int seqNo) {
		String sql = "UPDATE csp584_project.room_type SET del_flag = 1 WHERE seq_no = ?;";
		try(PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, seqNo);
			ps.execute();
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
	}

	/**
	 * Populate hotel given seq_no
	 */
	private void populateHotel(Hotel hotel) {
		String sql = "SELECT * from csp584_project.hotel WHERE seq_no = ?";
		try(PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, hotel.getSeqNo());
			ResultSet rs = ps.executeQuery();
			rs.next();
			
			hotel.setLocation(new Location());
			hotel.getLocation().setSeqNo(Integer.valueOf(rs.getString("location")));
			hotel.setName(rs.getString("name"));
			hotel.setAddress(rs.getString("address"));
			hotel.setListImage(parseImageLink(rs.getString("image_link")));
			hotel.setDescription(rs.getString("description"));
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		populateLocation(hotel.getLocation());
	}

	/**
	 * Populate location given seq_no
	 */
	private void populateLocation(Location location) {
		String sql = "SELECT * from csp584_project.location WHERE seq_no = ?";
		try(PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, location.getSeqNo());
			ResultSet rs = ps.executeQuery();
			rs.next();
			
			location.setCity(rs.getString("city"));
			location.setState(rs.getString("state"));
			location.setZip(rs.getString("zip"));
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
	}

	/**
	 * Build RoomType object from SQL 
	 */
	private RoomType buildRoomTypeObject(ResultSet rs) {
		RoomType roomType = new RoomType();
		
		try {
			roomType.setSeqNo(Integer.valueOf(rs.getInt("seq_no")));
			
			Hotel hotel = new Hotel();
			hotel.setSeqNo(Integer.valueOf(rs.getInt("hotel")));
			roomType.setHotelBelong(hotel);
			
			roomType.setName(rs.getString("name"));
			roomType.setBedType(BedType.valueOf(rs.getString("bed_type").toUpperCase()));
			roomType.setBedAmount(Integer.valueOf(rs.getInt("bed_amount")));
			roomType.setPeopleNo(Integer.valueOf(rs.getInt("people_no")));
			roomType.setView(rs.getString("view"));
			roomType.setIsWifi(rs.getBoolean("is_wifi"));
			roomType.setIsTV(rs.getBoolean("is_tv"));
			roomType.setPrice(rs.getDouble("price"));
			roomType.setDiscount(rs.getDouble("discount"));
			roomType.setRoomList(parseRoomList(rs.getString("room_list")));
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		populateHotel(roomType.getHotelBelong());
		return roomType;
	}

	private Location buildLocationObject(ResultSet rs) {
		Location location = new Location();
		try {
			location.setSeqNo(Integer.valueOf(rs.getInt("seq_no")));
			location.setCity(rs.getString("city"));
			location.setState(rs.getString("state"));
			location.setZip(rs.getString("zip"));
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return location;
	}

	/**
	 * Build Hotel object from SQL
	 */
	private Hotel buildHotelObject(ResultSet rs) {
		Hotel hotel = new Hotel();
		try {
			hotel.setSeqNo(Integer.valueOf(rs.getInt("seq_no")));
			hotel.setName(rs.getString("name"));
			hotel.setAddress(rs.getString("address"));
			hotel.setListImage(parseImageLink(rs.getString("image_link")));
			
			Location location = new Location();
			location.setSeqNo(Integer.valueOf(rs.getInt("location")));
			location.setCity(rs.getString("city"));
			location.setState(rs.getString("state"));
			location.setZip(rs.getString("zip"));
			hotel.setLocation(location);
			
			hotel.setDescription(rs.getString("description"));
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return hotel;
	}

	/**
	 * Build Hotel object from SQL
	 */
//	private Room buildRoomObject(ResultSet rs) {
//		Room room = new Room();
//		
//		try {
//			room.setSeqNo(Integer.valueOf(rs.getInt("seq_no")));
//			
//			room.setHotel(new Hotel());
//			room.getHotel().setSeqNo(Integer.valueOf(rs.getInt("hotel")));
//			
//			room.setRoomNumber(Integer.valueOf(rs.getInt("room_number")));
//			
//			room.setRoomType(new RoomType());
//			room.getRoomType().setSeqNo(Integer.valueOf(rs.getInt("room_type")));
//			
//			room.setPrice(Double.valueOf(rs.getDouble("price")));
//			
//			room.setDiscount(Double.valueOf(rs.getDouble("discount")));
//		}
//		catch(SQLException e) {
//			e.printStackTrace();
//			System.out.println(e.getMessage());
//		}
//		return room;
//	}

	/**
	 * Parse room list entry with format: <room,room,>
	 */
	private List<Integer> parseRoomList(String input) {
		List<Integer> result = null;
		if(input != null) {
			result = new ArrayList<>();
			String[] inputSplit = input.split(",");
			for(String str: inputSplit) {
				result.add(Integer.valueOf(str));
			}
		}
		return result;
	}

	/**
	 * Parse image file list with format: <image,image,>
	 */
	private List<String> parseImageLink(String imageLink) {
		List<String> result = null;
		if(imageLink != null) {
			result = Arrays.asList(imageLink.split(","));
		}
		return result;
	}
}
