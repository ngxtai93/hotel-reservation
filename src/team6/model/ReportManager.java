package team6.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import team6.entity.Hotel;
import team6.entity.Location;
import team6.entity.RoomType;
import team6.servlet.ServletReport;

public class ReportManager {
	private HotelManager hm = new HotelManager();
	
	public Map<Hotel, List<RoomType>> prepareHotelReportList() {
		Map<Hotel, List<RoomType>> mapHotelRoomType;
		List<Hotel> listHotel = hm.getAvailableHotel();
		List<RoomType> listRoomType = hm.getAvailableRoomType();
		mapHotelRoomType = new HashMap<>();
		for(Hotel h: listHotel) {
			List<RoomType> listRoomTypeHotel = new ArrayList<>();
			for(RoomType rt: listRoomType) {
				if(rt.getHotelBelong().getSeqNo().equals(h.getSeqNo())) {
					listRoomTypeHotel.add(rt);
				}
			}
			mapHotelRoomType.put(h, listRoomTypeHotel);
		}
		return mapHotelRoomType;
	}
	
	public Map<String, Integer> prepareHotelReportBarchart() {
		Map<String, Integer> mapLocationHotelCount;
		List<Hotel> listHotel = hm.getAvailableHotel();
		List<Location> listLocation = hm.getAvailableLocation();
		
		mapLocationHotelCount = new HashMap<>();
		for(Location l: listLocation) {
			int count = 0;
			for(Hotel h: listHotel) {
				if(h.getLocation().getSeqNo().equals(l.getSeqNo())) {
					count++;
				}
			}
			mapLocationHotelCount.put(l.toString(), Integer.valueOf(count));
		}
		return mapLocationHotelCount;
	}

	public Map<Hotel, List<RoomType>> prepareHotelReportDiscount() {
		Map<Hotel, List<RoomType>> mapHotelRoomType;
		List<Hotel> listHotel = hm.getAvailableHotel();
		List<RoomType> listRoomType = hm.getAvailableRoomType();
		mapHotelRoomType = new HashMap<>();
		for(Hotel h: listHotel) {
			List<RoomType> listRoomTypeHotel = new ArrayList<>();
			for(RoomType rt: listRoomType) {
				if(rt.getHotelBelong().getSeqNo().equals(h.getSeqNo()) && (rt.getDiscount() != null && rt.getDiscount() > 0.0)) {
					listRoomTypeHotel.add(rt);
				}
			}
			mapHotelRoomType.put(h, listRoomTypeHotel);
		}
		return mapHotelRoomType;
	}
}
