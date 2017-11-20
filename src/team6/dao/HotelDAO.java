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

	/**
	 * Select hotel by address and given location
	 */
	public Hotel selectHotel(String address, Integer locationSeqNo) {
		String sql = "SELECT * from csp584_project.hotel WHERE address = ? AND location = ?";
		Hotel hotel = null;
		
		try(PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, address);
			ps.setInt(2, locationSeqNo.intValue());
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
		String city = hotel.getCity();
		String state = hotel.getState();
		String zip = hotel.getZip();
		
		Integer locationSeqNo = selectLocationSeqNo(city, state, zip);
		if(locationSeqNo == null) {
			insertLocation(city, state, zip);
			locationSeqNo = selectLocationSeqNo(city, state, zip);
		}
		
		String sql = "INSERT INTO `csp584_project`.`hotel` (`location`, `name`, `address`) VALUES (?, ?, ?);";
		try(PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, locationSeqNo.intValue());
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
	 * Get seq_no by given info
	 */
	public Integer selectLocationSeqNo(String city, String state, String zip) {
		String sql = "SELECT seq_no from csp584_project.location WHERE city = ? AND state = ? AND zip = ?";
		Integer result = null;
		
		try(PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, city);
			ps.setString(2, state);
			ps.setString(3, zip);
			ResultSet rs = ps.executeQuery();
			if(rs.isBeforeFirst()) {
				rs.next();
				result = Integer.valueOf(rs.getInt("seq_no"));
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return result;
	}

	/**
	 * Build RoomType object from SQL 
	 */
	private RoomType buildRoomTypeObject(ResultSet rs) {
		RoomType roomType = new RoomType();
		
		try {
			roomType.setSeqNo(rs.getInt("seq_no"));
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

	/**
	 * Build Hotel object from SQL
	 */
	private Hotel buildHotelObject(ResultSet rs) {
		Hotel hotel = new Hotel();
		try {
			hotel.setSeqNo(rs.getInt("seq_no"));
			hotel.setName(rs.getString("name"));
			hotel.setAddress(rs.getString("address"));
			
			ResultSet locationResult = getLocationResultSet(rs.getInt("location"));
			hotel.setCity(locationResult.getString("city"));
			hotel.setState(locationResult.getString("state"));
			hotel.setZip(locationResult.getString("zip"));
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return hotel;
	}

	/**
	 * Get result set of location from given seq_no. Return null if not found.
	 */
	private ResultSet getLocationResultSet(int seqNo) {
		String sql = "SELECT * from csp584_project.location WHERE seq_no = ?";
		
		try(PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, seqNo);
			ResultSet rs = ps.executeQuery();
			if(rs.isBeforeFirst()) {
				rs.next();
				return rs;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
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
}
