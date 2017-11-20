package team6.entity;

import java.util.Map;

public class RoomType {
	private Integer seqNo;
	private String name;
	private Map<BedType, Integer> bedMap;
	private Integer peopleNo;
	private String view;
	private Boolean isWifi;
	private Boolean isTV;
	
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
	public Map<BedType, Integer> getBedMap() {
		return bedMap;
	}
	public void setBedMap(Map<BedType, Integer> bedMap) {
		this.bedMap = bedMap;
	}
	public Integer getPeopleNo() {
		return peopleNo;
	}
	public void setPeopleNo(Integer peopleNo) {
		this.peopleNo = peopleNo;
	}
	public String getView() {
		return view;
	}
	public void setView(String view) {
		this.view = view;
	}
	public Boolean getIsWifi() {
		return isWifi;
	}
	public void setIsWifi(Boolean isWifi) {
		this.isWifi = isWifi;
	}
	public Boolean getIsTV() {
		return isTV;
	}
	public void setIsTV(Boolean isTV) {
		this.isTV = isTV;
	}
}
