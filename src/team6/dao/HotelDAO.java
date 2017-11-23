package team6.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import team6.entity.BedType;
import team6.entity.Hotel;
import team6.entity.Location;
import team6.entity.Room;
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
		String sql = "SELECT * from csp584_project.room_type";
		
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

	public List<Hotel> selectHotelByLocation(int locationId) {
		String sql = "SELECT h.seq_no, h.location, l.city, l. state, l.zip, h.name, h.address"
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
		String sql = "SELECT h.seq_no, h.location, l.city, l. state, l.zip, h.name, h.address"
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
		
		String sql = "INSERT INTO `csp584_project`.`hotel` (`location`, `name`, `address`) VALUES (?, ?, ?);";
		try(PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, locationDb.getSeqNo().intValue());
			ps.setString(2, hotel.getName());
			ps.setString(3, hotel.getAddress());
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
		
		String sql = "UPDATE csp584_project.hotel SET location = ?, name = ?, address = ? WHERE seq_no = ?;";
		try(PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, locationDb.getSeqNo().intValue());
			ps.setString(2, hotel.getName());
			ps.setString(3, hotel.getAddress());
			ps.setInt(4, hotel.getSeqNo().intValue());
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

	public void insertRoom(int hotelId, int roomNum, int roomTypeId) {
		String sql = "INSERT INTO `csp584_project`.`room` (`hotel`, `room_number`, `room_type`) VALUES (?, ?, ?);";
		try(PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, hotelId);
			ps.setInt(2, roomNum);
			ps.setInt(3, roomTypeId);
			ps.execute();
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public Room selectRoom(int hotelId, int roomNum) {
		String sql = "SELECT * from csp584_project.room WHERE hotel = ? AND room_number = ? AND del_flag = 0";
		Room room = null;
		
		try(PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, hotelId);
			ps.setInt(2, roomNum);
			ResultSet rs = ps.executeQuery();
			if(rs.isBeforeFirst()) {
				rs.next();
				room = buildRoomObject(rs);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		if(room != null) {
			populateHotel(room.getHotel());
			populateRoomType(room.getRoomType());
		}
		
		return room;
	}

	public List<Room> selectRoomByHotel(int hotelId) {
		String sql = "SELECT * from csp584_project.room WHERE hotel = ? AND del_flag = 0";
		List<Room> listRoom = null;
		
		try(PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, hotelId);
			ResultSet rs = ps.executeQuery();
			if(rs.isBeforeFirst()) {
				listRoom = new ArrayList<>();
				while(rs.next()) {
					Room room = buildRoomObject(rs);
					listRoom.add(room);
				}
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		if(listRoom != null) {
			for(Room room: listRoom) {
				populateHotel(room.getHotel());
				populateRoomType(room.getRoomType());
			}
		}
		
		return listRoom;
	}

	public void updateRoom(int roomSeqNo, int roomNum, int roomTypeId) {
		String sql = "UPDATE csp584_project.room SET room_number = ?, room_type = ? WHERE seq_no = ?;";
		try(PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, roomNum);
			ps.setInt(2, roomTypeId);
			ps.setInt(3, roomSeqNo);
			ps.execute();
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public void deleteRoom(int seqNo) {
		String sql = "UPDATE csp584_project.room SET del_flag = 1 WHERE seq_no = ?;";
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
	 * Populate room type given seq_no
	 */
	private void populateRoomType(RoomType roomType) {
		String sql = "SELECT * from csp584_project.room_type WHERE seq_no = ?";
		try(PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, roomType.getSeqNo());
			ResultSet rs = ps.executeQuery();
			rs.next();
			
			roomType.setName(rs.getString("name"));
			roomType.setBedMap(parseBedColumn(rs.getString("bed")));
			roomType.setPeopleNo(Integer.valueOf(rs.getInt("people_no")));
			roomType.setView(rs.getString("view"));
			roomType.setIsWifi(rs.getBoolean("is_wifi"));
			roomType.setIsTV(rs.getBoolean("is_tv"));
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
	 * Parse bed entry with format: <bedtype,amount;>
	 */
	private Map<BedType, Integer> parseBedColumn(String input) {
		String[] inputSplitEntry = input.split(";");
		Map<BedType, Integer> result = new HashMap<>();
		
		for(String entry: inputSplitEntry) {
			String[] entrySplit = entry.split(",");
			String bedTypeStr = entrySplit[0];
			Integer amount = Integer.valueOf(entrySplit[1]);
			
			bedTypeStr = bedTypeStr.replaceAll("_", "").toUpperCase();
			BedType bt = null;
			for(BedType bed: BedType.values()) {
				if(bedTypeStr.equals(bed.toString())) {
					bt = bed;
					break;
				}
			}
			result.put(bt, amount);
		}
		
		return result;
	}

	/**
	 * Build RoomType object from SQL 
	 */
	private RoomType buildRoomTypeObject(ResultSet rs) {
		RoomType roomType = new RoomType();
		
		try {
			roomType.setSeqNo(Integer.valueOf(rs.getInt("seq_no")));
			roomType.setName(rs.getString("name"));
			roomType.setBedMap(parseBedColumn(rs.getString("bed")));
			roomType.setPeopleNo(Integer.valueOf(rs.getInt("people_no")));
			roomType.setView(rs.getString("view"));
			roomType.setIsWifi(rs.getBoolean("is_wifi"));
			roomType.setIsTV(rs.getBoolean("is_tv"));
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
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
			
			Location location = new Location();
			location.setSeqNo(Integer.valueOf(rs.getInt("location")));
			location.setCity(rs.getString("city"));
			location.setState(rs.getString("state"));
			location.setZip(rs.getString("zip"));
			hotel.setLocation(location);
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
	private Room buildRoomObject(ResultSet rs) {
		Room room = new Room();
		
		try {
			room.setSeqNo(Integer.valueOf(rs.getInt("seq_no")));
			room.setHotel(new Hotel());
			room.getHotel().setSeqNo(Integer.valueOf(rs.getInt("hotel")));
			room.setRoomNumber(Integer.valueOf(rs.getInt("room_number")));
			room.setRoomType(new RoomType());
			room.getRoomType().setSeqNo(Integer.valueOf(rs.getInt("room_type")));
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return room;
	}
}
