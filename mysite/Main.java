import java.io.IOException;

public class Main {
	
	public Main() {
	}

	public static void main(String[] args) throws IOException {
		
		String csvFile_data = "./input/input_options.csv";
		String csvFile_programmes = "./input/input_programmes.csv";
		double alpha = 0.75;
		double beta = 1.1;
		Algorithm algo = new Algorithm();
		
		algo.readProgrammes(csvFile_programmes,alpha,beta);
		algo.readData(csvFile_data);
		algo.implement();
		algo.printBranchStatus();
		algo.closeall();
	}
}
