import java.util.*;

public class Candidate {
	//Variables
	private String origBranch;
	private String Name;
	private String RollNo;
	private int preferenceNow;
	private ArrayList<String> preferenceList;
	private double CPI;
	private String category;
	private boolean selected;
	private boolean waitList;
	public int temppref;
	private boolean eligible;

	//GetSet Functions
	public String getCategory(){
		return this.category;
	}
	public void setCategory(String cate){
		category = cate;
	}

	public boolean isselected(){
		return this.selected;
	}
	public void setselected(boolean status){
		selected = status;
	}
	
	public boolean isWaitList(){
		return this.waitList;
	}
	public void setWaitList(boolean status){
		waitList = status;
	}
	
	public boolean iseligible(){
		return this.eligible;
	}
	public void seteligibility(boolean status){
		eligible = status;
	}
	
	public String getOrigBranch(){
		return this.origBranch;
	}
	public void setOrigBranch(String branch){
		origBranch = branch;
	}

	public String getName(){
		return this.Name;
	}
	public void setName(String name){
		Name = name;
	}

	public String getRollNo(){
		return this.RollNo;
	}
	public void setRollNo(String name){
		RollNo = name;
	}

	public int getpreferenceNow(){
		return this.preferenceNow;
	}
	public void setpreferenceNow(int prefNow){
		preferenceNow = prefNow;
	}

	public ArrayList<String> getpreferenceList(){
		return this.preferenceList;
	}
	public void setpreferenceList(ArrayList<String> preferncelist){
		preferenceList = preferncelist;
	}

	public double getCPI(){
		return this.CPI;
	}
	public void setCPI(double cgpa){
		CPI = cgpa;
	}

	//Constructor
	public Candidate() {
		preferenceNow = 0;
		preferenceList = new ArrayList<String>();
		CPI = 0.0;
		category = "";
		origBranch = "";
		Name = "";
		RollNo = "";
		selected = false;
		waitList = false;
		eligible = true;
		temppref = 0;
	}

}
