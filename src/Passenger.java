
public class Passenger {
	
	private String name;
	private String dlNum;
	private int boardingGroupId;
	
	public Passenger(String name, String dlNum, int boardingGroupId) {
		this.name = name;
		this.dlNum = dlNum;
		this.boardingGroupId = boardingGroupId;
		
	}
	
	public String getName() {return name;}
	public String getDLNum() {return dlNum;}
	public int getBoardingGroupId() {return boardingGroupId;}
	
	public void setBoardingGroupId(int boardingGroupId) {this.boardingGroupId = boardingGroupId;}
	
	public String toString() {
		return "name: " + name + " dlNum: " + dlNum + " bgId: " + boardingGroupId;
	}

}
