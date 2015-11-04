import java.io.*;
import java.util.*;

public class Algorithm {
	//Variables
	private ArrayList<Candidate> CandidateList; // ArrayList for the Candidates applied
	private Map<String, Branch>  branchMap; // Map to map the branch name with the Branch Details
	PrintWriter 				 writer;  // Writer to write into allotment.csv
	PrintWriter 				 writer1; //Writer to write into output_stats.csv
	
	//Constructor
	public Algorithm() throws FileNotFoundException, UnsupportedEncodingException {
		//Initialization
		writer        = new PrintWriter("./output/allotment.csv", "UTF-8");
		writer1       = new PrintWriter("./output/output_stats.csv", "UTF-8");
		branchMap     = new HashMap<String,Branch>();
		CandidateList = new ArrayList<Candidate>();
	}
	
	//Functions
	//Sort the Candidates according to CPI
	public void sortList(){
		Collections.sort(CandidateList,new Comparator<Candidate>(){
			@Override
			public int compare(Candidate candidate1, Candidate candidate2){
				if(candidate1.getCPI()<candidate2.getCPI())
					return 1;
				else
					return -1;
			}
		});
	}

	//Print Each candidate details into allotment
	public void printCandidate(Candidate candi) throws FileNotFoundException, UnsupportedEncodingException {
		if(candi.iseligible()) {
			if(candi.isselected()) {
				//Candidate is allowed to change branch
				String s = candi.getpreferenceList().get(candi.getpreferenceNow());
				writer.println(candi.getRollNo() + "," + candi.getName()+
						","+candi.getOrigBranch()+","+s);
			}
			else {
				//Candidate is eligible but no preferred branch can be alloted
				writer.println(candi.getRollNo() + "," + candi.getName()+"," +
					candi.getOrigBranch()+","+"Branch Unchanged");
			}
		}
		else {
			//Candidate is Ineligible 
			writer.println(candi.getRollNo() + "," + candi.getName()+"," +
					candi.getOrigBranch()+","+"Ineligible");
		}
	}
	
	//Print all Branch Statistics
	public void printBranchStatus() throws FileNotFoundException, UnsupportedEncodingException {
		// Print Headers 
		writer1.println("Program,Cutoff,Santioned Strength,Original Strength,Final Strength");
		for(Map.Entry<String, Branch> entry: branchMap.entrySet()) {
			// Print all entries in the Map
			Branch e = entry.getValue();
			
			if(e.getCutoff() == -1) {
				// No one changed their branch to this branch
				writer1.println(e.getbranchName()+",NA,"+ e.getsancStrength()+
						","+ e.getinitStrength() +","+e.getcurrStrength());
			}
			else {
				// People have changed their branch to this branch
				writer1.println(e.getbranchName()+","+e.getCutoff()+","
						+e.getsancStrength()+","+ e.getinitStrength() +","+e.getcurrStrength());
			}
		}
	}
	
	//Read Preference Lists from CSV
	// Pattern: RollNo,Name,Original Branch,CPI,Category,Preference List
	public void readData(String csvFile) throws IOException {
		// Initialization
		BufferedReader br = null;
		String line = ""; 
		String delimiter = ","; // Delimiter is ","
		br = new BufferedReader(new FileReader(csvFile));
		//Read from csvFile
		while ((line = br.readLine()) != null) {	
		    // use comma as separator
			String[] input = line.split(delimiter);
			//Setting values to the candidate
			Candidate canditemp = new Candidate();
			canditemp.setRollNo(input[0]);
			canditemp.setName(input[1]);
			if(branchMap.containsKey(input[2])) {
				canditemp.setOrigBranch(input[2]);
				canditemp.setCPI(Double.parseDouble(input[3]));
				canditemp.setCategory(input[4]);
				ArrayList<String> prefList = new ArrayList<String>();
				// Get Preference List
				for(int j =5; j <input.length; j++ ){
					// Check if the preference entered is Empty 
					if(input[j]!="" || !input[j].equalsIgnoreCase("null")){
//						Check if the branch entered is a valid branch
						if(branchMap.containsKey(input[j])) {
							prefList.add(input[j]);
						}
						else{
//							Ignore that entry if the branch entered is not valid
							System.out.println("Branch not found");
						}
					}	
				}
				canditemp.setpreferenceList(prefList);
				CandidateList.add(canditemp);
			}
			else{
//				Ignore the candidate if the original Branch is not found
				System.out.println("Candidate's Original Branch not found. Removing candidate");
			}
		}
		br.close();
	}
	
	//Read Programme Information from CSV
	// Pattern: 
	public void readProgrammes(String csvFile,double alpha,double beta) throws NumberFormatException, IOException{
		//Initialization
		BufferedReader br = null;
		String line = "";
		String delimiter = ",";
		br = new BufferedReader(new FileReader(csvFile));
		// Read input from the csvFile
		while ((line = br.readLine()) != null) {	
		    // use comma as separator
			String[] input = line.split(delimiter);
			// Set the Branch details
			Branch branchtemp = new Branch();
			if(input[0]!=""){
				// Check if the Branch Name is not Empty
				branchtemp.setbranchName(input[0]);
			}
			branchtemp.setsancStrength((int) Double.parseDouble(input[1]));
			branchtemp.setcurrStrength((int) Double.parseDouble(input[2]));
			branchtemp.setinitStrength(branchtemp.getcurrStrength());
			branchtemp.setmaxStrength(beta);
			branchtemp.setminStrength(alpha);
			branchtemp.tempStrength = branchtemp.getcurrStrength();
			branchMap.put(input[0], branchtemp);
		}
		br.close();
	}
	
	//Initial Filter for eligibility
	public void filter() throws FileNotFoundException, UnsupportedEncodingException {
		Candidate e;
		// Iterate over the Candidates
		for(int i = 0; i < CandidateList.size(); i++) {
			e = CandidateList.get(i);
			// Check if the Candidate is of Category GE or OBC
			if(e.getCategory().equalsIgnoreCase("GE") || e.getCategory().equalsIgnoreCase("OBC")) {
				// Check if the CPI is less than 8.00
				if(e.getCPI()<8.00) {
					// Set the Candidate Ineligible and print it to csv file 
					e.seteligibility(false);
					CandidateList.set(i,e);
					printCandidate(CandidateList.get(i));
				}
			}
			else if(e.getCategory().equalsIgnoreCase("ST") || e.getCategory().equalsIgnoreCase("PwD")
			|| e.getCategory().equalsIgnoreCase("SC")) {
//				If he is of the other categories set CPI filter at 7.00
				if(e.getCPI()<7.00) {
					// Set the Candidate Ineligible
					e.seteligibility(false);
					CandidateList.set(i,e);
					printCandidate(CandidateList.get(i));			
				}
			}
			else {
				// Check if Valid Category is entered
				System.out.println("Something's not right. Category not found");
				e.seteligibility(false);
				CandidateList.set(i,e);
			}
		}
		// Remove the Candidate from the List after printing it to the csvFile
		int i = 0;
		while(i < CandidateList.size()) {
			if(!CandidateList.get(i).iseligible()) {
				// Remove the Candidate from the List
				CandidateList.remove(i);
			}
			else {
				i++;
			}
		}
	}
	
	//Transfer function only for initialization stage
	/*
	 *  Handles the Branch Change calls from the Initialization Function. viz. for the first time
	 *  Updates the student and the branch Details 
	 *  Prints and Deletes the Candidates who have got their first preference
	 */
	public int transfer(int i, int j) throws FileNotFoundException, UnsupportedEncodingException {
		// Initialization
		Candidate e = CandidateList.get(i);
		Branch origb = branchMap.get(e.getOrigBranch());
		Branch transb = branchMap.get((e.getpreferenceList().get(j)));
		e.setselected(true); // Make the Candidate Selected for Branch Change  
		CandidateList.set(i,e);
		// Update Branch Details
		transb.setcurrStrength(transb.getcurrStrength()+1); //Increase the strength of the branch changed to by 1 
		transb.setCutoff(e.getCPI());// Set the temporary cutoff of the Branch to be the CPI of the Last person selected
		origb.setcurrStrength(origb.getcurrStrength()-1);
		// Insert origb into Branch Map
		branchMap.put(origb.getbranchName(), origb);
		// Check if the Branch transferred to is to be Closed due to upper strength limits
		if(!transb.checkmaxStrength()) {
			transb.setClosed(true);
		}
		branchMap.put(transb.getbranchName(), transb);
		//Delete Element if alloted first preferences
		if(e.getpreferenceNow()==0) {
			// Print the Candidate who got his First preference
			printCandidate(CandidateList.get(i));
			// Delete the Candidate from the List
			CandidateList.remove(i);
			i--;
			return i;
		}
		else {
			return i;
		}
	}
	
	//Transfer Function for Recursion
	public int transferRec(int i, int j) throws FileNotFoundException, UnsupportedEncodingException {
		Candidate e = CandidateList.get(i);
		Branch origb = branchMap.get(e.getOrigBranch());
		
		Branch transb = branchMap.get((e.getpreferenceList().get(j)));
		e.setselected(true);
		CandidateList.set(i,e);
		//Update Branch Details
		transb.setcurrStrength(transb.getcurrStrength()+1);
		Branch prefb = branchMap.get((e.getpreferenceList().get(e.getpreferenceNow())));
		prefb.setcurrStrength(prefb.getcurrStrength()-1);
		branchMap.put(prefb.getbranchName(), prefb);
		transb.setCutoff(e.getCPI());
		//Insert origb
		branchMap.put(origb.getbranchName(), origb);
		if(!transb.checkmaxStrength()) {
			transb.setClosed(true);
			System.out.println("Branch Closed");
		}
		branchMap.put(transb.getbranchName(), transb);
		//Delete Element if alloted first preferences
		if(e.getpreferenceNow()==0) {
			System.out.println("First Preference");
			printCandidate(CandidateList.get(i));
			if(CandidateList.get(i).isselected())
			{
				System.out.println("Selected");
			}
			CandidateList.remove(i);
			i--;
			return i;
		}
		else {
			return i;
		}
	}
	
	//Initialization Function
	//Make changes for closedVacancyinclusion
	public int initialization() throws FileNotFoundException, UnsupportedEncodingException {
		int change = 0;
		int i = 0;
		Candidate e;
		Branch origb;
		Branch prefb;
		while(i < CandidateList.size()) {
			e = CandidateList.get(i);
			origb = branchMap.get(e.getOrigBranch());
			if (e.getCPI()>=9.00) {
				while(e.getpreferenceList().size()> e.getpreferenceNow()) {
					e = CandidateList.get(i);
					prefb= branchMap.get(e.getpreferenceList().get(e.getpreferenceNow()));
					if(prefb.isClosed()) {
						if(prefb.getCutoff()==e.getCPI()) {
							//transfer
//							System.out.println("Transfer Through Init Closed");
							i = transfer(i,e.getpreferenceNow());
							change++;
							//Special i-- if preferenceNow =0
							break;
						}
					}
					else
					{
//						System.out.println("Transfer Through Init Open");
						i = transfer(i,e.getpreferenceNow());
						change++;
						break;
					}
					e.setpreferenceNow(e.getpreferenceNow()+1);
					CandidateList.set(i, e);
				}
			}
			else{
				if(origb.checkminStrength()) {
					while(e.getpreferenceList().size()> e.getpreferenceNow()) {
						e = CandidateList.get(i);
						prefb= branchMap.get(e.getpreferenceList().get(e.getpreferenceNow()));
						if(prefb.isClosed()) {
							if(prefb.getCutoff()==e.getCPI()) {
								//transfer
//								System.out.println("Transfer Through Init Closed");
								i = transfer(i,e.getpreferenceNow());
								change++;
								//Special i-- if preferenceNow =0
								break;
							}
							else {
								//Person above him closed the branch due to Limitations
								if(prefb.getCutoff_ifVacancy()==e.getCPI()) {
									prefb.setTriggerOn(true);
									prefb.setClosedVacancy(true);
									prefb.setCutoff_ifVacancy(e.getCPI());
									e.setWaitList(true);
									branchMap.put(prefb.getbranchName(), prefb);
								}
							}
						}
						else {
//							System.out.println("Transfer Through Init Open");
							i = transfer(i,e.getpreferenceNow());
							change++;
							break;
						}
						//Update Preference Now
						e.setpreferenceNow(e.getpreferenceNow()+1);
						CandidateList.set(i, e);
					}
				}
				else {
					// From Different branch to same branch same CPI
					for(int k =0; k <CandidateList.get(i).getpreferenceList().size();k++) {
						prefb = branchMap.get(e.getpreferenceList().get(k));
						prefb.setClosed(true);
						//Insert prefb to BranchMap
						branchMap.put(prefb.getbranchName(), prefb);				
					}
				}
			}
			i++;
		}
		return change;
	}
	
	//Add a Function to include all the vacancy guys after the list iteration and update the branch Vacancy details
	public int updateVacancy(boolean calledfromloop) throws FileNotFoundException, UnsupportedEncodingException{
		int change=0;
		Candidate e;
		Branch transb;
		for(int i = 0; i < CandidateList.size(); i++){
			e = CandidateList.get(i);
			for(int j = 0; j < e.getpreferenceList().size(); j++){
//				System.out.println(e.getpreferenceList().get(j));
//				System.out.println("1");
				//To be changed
				transb = branchMap.get(e.getpreferenceList().get(j));
//				System.out.println("Fire Away");
				if(transb.isTriggerOn()){
					if(e.getCPI()==transb.getCutoff_ifVacancy()){
						//transfer
						if(calledfromloop){
							e.setpreferenceNow(j);
							CandidateList.set(i, e);
							transferRec(i,j);
							change++;
							break;
						}
						else{
							e.setpreferenceNow(j);
							CandidateList.set(i, e);
							transferRec(i,j);
							change++;
							break;
						}		
					}
				}
			}
		}
		return change;
	}
	
	//Recursive Function
	//! Make changes for closedVacancyinclusion
	public int recursion() throws FileNotFoundException, UnsupportedEncodingException {
		int change = 0;
		int i = 0;
		int k = 0;
		Candidate e;
		Branch origb;
		Branch prefb;
		while(i < CandidateList.size()) {
			e = CandidateList.get(i);
			origb = branchMap.get(e.getOrigBranch());
			//Split for CPI >= 9 and <8
			if (e.getCPI()>=9.00) {
				for(k =0; k < e.getpreferenceNow(); k++) {
					e = CandidateList.get(i);
					prefb= branchMap.get(e.getpreferenceList().get(k));
					if(prefb.isClosed()) {
						if(prefb.getCutoff()==e.getCPI()) {
							//transfer
							i = transferRec(i,k);
							change++;
							e.setpreferenceNow(k);
							//Special i-- if preferenceNow =0
							break;
						}
					}
					else
					{
//						System.out.println("Transfer Through Recursion Open");
						i = transferRec(i,k);
						change++;
						e.setpreferenceNow(k);
						break;
					}
				}
				CandidateList.set(i,e);
			}
			else{
				//Check if the branch allows
				if(origb.checkminStrength()) {
					CandidateList.set(i,e);
					//Preference will always increase
					for(k =0; k < e.getpreferenceNow(); k++) {
						e = CandidateList.get(i);
						prefb= branchMap.get(e.getpreferenceList().get(k));
						if(prefb.isClosed()) {
							//Person above him is the last person to get in with same CPI or the 
							if(prefb.getCutoff()==e.getCPI()) {
								//transfer
								i = transferRec(i,k);
								change++;
								break;
							}
							else {
								//Person above him closed the branch due to Limitations
								if(prefb.getCutoff_ifVacancy()==e.getCPI()) {
									prefb.setTriggerOn(true);
									prefb.setClosedVacancy(true);
									prefb.setCutoff_ifVacancy(e.getCPI());
									e.setWaitList(true);
									branchMap.put(prefb.getbranchName(), prefb);
								}
							}
						}
						else {
							i = transferRec(i,k);
							change++;
							break;
						}
					}
				}
				else {
					// From Different branch to same branch same CPI
					int l;
					for(l =0; l <CandidateList.get(i).getpreferenceList().size();l++) {
					prefb = branchMap.get(e.getpreferenceList().get(l));
					if(prefb.isClosed()) {
							// Branch is Closed because a person above him can't transfer himself 
							// and he is in the same category.
							if(prefb.getCutoff_ifVacancy()==e.getCPI()){
								e.setWaitList(true);
								prefb.setClosedVacancy(true);
								prefb.setCutoff_ifVacancy(e.getCPI());;
								//Insert e
								CandidateList.add(i, e);
							}
							else {
								prefb.setClosed(true);
							}
						}
						else {
							//The branch is not closed 
							prefb.setClosedVacancy(true);
							prefb.setCutoff_ifVacancy(e.getCPI());
							prefb.setClosed(true);
							e.setWaitList(true);
							//Insert prefb
							branchMap.put(prefb.getbranchName(), prefb);
						}
					//Insert prefb to BranchMap
					branchMap.put(prefb.getbranchName(), prefb);
					}
				}
			}
			i++;
		}
		return change;
	}
	
	//Update branch List
	public void updateBranchList() {
		Branch e;
		for(Map.Entry<String, Branch> entry: branchMap.entrySet()) {
			e = entry.getValue();
			e.setClosedVacancy(false);
			e.setCutoff_ifVacancy(-1);
			if (e.checkmaxStrength()) {
				e.setClosed(false);
			}
			else {
				e.setClosed(true);
			}
			branchMap.put(e.getbranchName(),e);
		}
	}
	
	//Update Candidate List
	public void updateCandidateList() {
		Candidate e;
		for(int i = 0; i < CandidateList.size();i++) {
			e = CandidateList.get(i);
			e.setWaitList(false);
			if(e.getpreferenceNow()<e.getpreferenceList().size()){
				e.setselected(true);
			}
			else{
				e.setselected(false);
			}
			CandidateList.set(i, e);
		}
	}
	
	//Algorithm: For Case without Loops
	/*
	 *  0. Filter the CPIs based on eligibility
	 *  1. Sort according to CPI
	 *  2. Iterate now through the list individually initially and assign the branches 
	 *		according to preference order without any exception
	 *  3. Assign the branches according to preference  
	 *  4. For people below CPI 9 we check original branch strength
	 *  5. We have implemented the following corner cases
	 *  	Optimality if seat vacant and the seat to be given to a person with same CPI
	 *  	Optimality if seat vacant and the seat to be given to a person who should be allowed but blocked due to one person with same CPI is blocked
	 *  
	 */
	//Implementation
	public void implement() throws FileNotFoundException, UnsupportedEncodingException {
		filter();
		sortList();
		int change = initialization();
		change +=updateVacancy(false);
		while(!CandidateList.isEmpty() && change!=0) {
			change = recursion();
			change+=updateVacancy(true);
			updateBranchList();
			updateCandidateList();
			
		}
//		System.out.println("Init done out Loop");
		for(int i = 0; i < CandidateList.size(); i++) {
			printCandidate(CandidateList.get(i));
		}
	}
	
	//Close the writing files
	public void closeall(){
		writer.close();
		writer1.close();
	}
}
