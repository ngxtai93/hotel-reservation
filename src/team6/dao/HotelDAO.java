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
				+ " WHERE h.location = ?;"
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
				+ " WHERE h.address = ? AND h.location = ?;"
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
}
