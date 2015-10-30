
public class Branch {
	
	private int currStrength;
	private int sancStrength;
	private int initStrength;
	private int maxStrength;
	public int tempStrength;
	private int minStrength;
	private String Name;
	private boolean closed;
	private boolean trigger;
	private boolean closedVacancy;
	private double cutoffVacancy;
	private double cutoff;


//Get Set Functions
	public int getcurrStrength(){
		return this.currStrength;
	}
	public void setcurrStrength(int strength){
		currStrength = strength;
	}

	public int getinitStrength(){
		return this.initStrength;
	}
	public void setinitStrength(int strength){
		initStrength = strength;
	}

	public boolean isClosed(){
		return this.closed;
	}
	public void setClosed(boolean close){
		closed = close;
	}
	
	public boolean isClosedVacancy(){
		return this.closedVacancy;
	}
	public void setClosedVacancy(boolean close){
		closedVacancy = close;
	}
	
	public boolean isTriggerOn(){
		return this.trigger;
	}
	public void setTriggerOn(boolean close){
		trigger = close;
	}
	
	public int getsancStrength(){
		return this.sancStrength;
	}
	public void setsancStrength(int strength){
		sancStrength = strength;
	}

	public String getbranchName(){
		return this.Name;
	}
	public void setbranchName(String strength){
		Name = strength;
	}
	
	public int getmaxStrength(){
		return this.maxStrength;
	}
	public void setmaxStrength(double beta){
		maxStrength = (int) Math.round(beta*currStrength);
	}

	public int getminStrength(){
		return this.minStrength;
	}
	public void setminStrength(double alpha){
		minStrength = (int) Math.round(alpha * currStrength);
	}
	
	public double getCutoff(){
		return this.cutoff;
	}
	public void setCutoff(double cgpa){
		cutoff = cgpa;
	}
	
	public double getCutoff_ifVacancy(){
		if(closedVacancy)
		{
			return this.cutoffVacancy;
		}
		else
		{
			return this.cutoff;
		}
	}
	public void setCutoff_ifVacancy(double cgpa){
		cutoffVacancy = cgpa;
	}
	
	//Constructor
	public Branch() {
		currStrength=0;
		Name = "";
		sancStrength = 0;
		minStrength = 0;
		maxStrength = 0;
		closed = false;
		closedVacancy = false;
		trigger = false;
		cutoffVacancy = -1;
		initStrength = 0;
		cutoff = -1;
	}
	
	//Functions
	//Check if the branch strength matches min max condiions
	public boolean checkminStrength()
	{
		if(currStrength <= minStrength)
		{
			return false;
		}
		return true;
	}
	public boolean checkmaxStrength()
	{
		if(currStrength >= maxStrength)
		{
			return false;
		}
		return true;
	}
	
}
