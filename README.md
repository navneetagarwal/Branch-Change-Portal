# Branch-Change-Portal

Assignment 11 :

Group No: 02

|Team Members:|		CSE ID|		Roll No.|	Percentage Contribution|
|-------------|         ------|         --------|       -----------------------|
|Harshal Mahajan|	harshal.m|	140050003|	100%|
|Tanmay Parekh|		tanmayb|        140100011|	100%|
|Navneet Agarwal|	navneet|	140100090|	100%|


Honor Code: 

	    I  pledge on our own honour that I have not consulted or given any assistance to any group for this particular assignment.
	    We have not copied or looked 

	    I  pledge on our own honour that I have not consulted or given any assistance to any group for this particular assignment.
	    We have not copied or looked 

	    I  pledge on our own honour that I have not consulted or given any assistance to any group for this particular assignment.
	    We have not copied or looked 

Citations - 

1. Django.project

2. Djangogirlstutorial

3. Stack Overflow

4. python tutorials


How to use -

NOTE - We have stuck to python rather than python3.

INSTRUCTIONS TO RUN SERVER:

Follow the following instructions in order 

1. Install 'pip'.

2. Create a virtual environment - Install virtualenv. Create a virtual environment by 'virtualenv myvenv'.

3. Activate virtual environment - Use 'source myvenv/bin/activate' to activate the environment. Use 'deactivate' to deactivate it.

4. Change directory to project folder.

5. Install django - Use 'pip install django==1.8'

6. Make migrations - (You should be in outer mysite folder now. i.e. when you use 'ls' ) Used if any changes are made. 'python manage.py makemigrations'

7. Migrate - 'python manage.py migrate'

8. Runserver - 'python manage.py runserver'.

Now click on the link provided on the terminal. It will open the webapp page.


INSTRUCTIONS FOR USING WEBAPP PAGE:

1. Sign up - Open the main page. It asks for username and password. Click on the plus sign below it. It opens a new page. Enter in the required fields and click on the 'Submit' button. Thus, you have registered yourself as a user.
2. Login - To access any page(except signup) you need to login. Enter the username and password entered and click the 'login' button.
3. Fill form - If haven't filled the form before, click the 'plus' button. Else you can click on the 'pencil aka edit' button to edit the form.


INSTRUCTIONS FOR ADMIN:

- Our class is named 'Detail'. (Might be confusing)

- All input files will be stored in the 'input' folder:
	input_programmes.csv (As mentioned in imported file)
	input_options.csv (As mentioned in imported file)
	input.csv (This will just contain the personal details of students. i.e. Roll No, Name, Present Branch, CPI, Category in the respective order. This is point 2 in import documents (as in the piazza post). This is verify whether the student enters his/her details correctly)

- All output files will be stored in the 'output' folder:
	allotment.csv (As mentioned in imported file)
	output_stats.csv (As mentioned in imported file)

1. One important instruction - Never delete a detail with roll no 150000000. That should be the default detail and should be present in the list of details. If it is not present, then add detail. Put author name = default, roll no = 150000000, Name = new user, CPI = 0.0, Category = GE and save the detail. (Important for validating details)
2. Admin actions -
	a. Delete selected details - Available by default. Select one (or multiple) details and delete them from the database.
	(CAUTION - Never delete the detail with roll no 150000000.)
	b. Download csv - Export (download) the database as a csv. Select the details you want to export and select this option. Those details will overwrite the previously present details in input_options.csv present in the input folder. 
	(CAUTION - a. If you have stored the data to be imported as in point 3 (Piazza post), it will be OVERWRITTEN.
		   b. If some preference has been kept empty, it would store 'null' in that field.)
	c. Import data - Imports the input_options.csv file, clears the present database and fills it with the data present in csv file. To do so, first replace the input_options.csv present in the input folder by the file you want to import. Open the admin page, select any detail and click this option.
	d. Apply algorithm - To apply algorithm, select all the details, use 'Download csv' action. Select any one detail and click on this action button. It will create the required files in the output folder.
3. Remember to log out after completing all your work. If you don't log out, you would stay logged in the webapp page.

 ALGGORRITHM IS EXPLAINED IN THE ALGORITHM.JAVA FILE
 CHALLENGE ALGORITHM COULD NOT BE IMPLEMENTED AND THUS WE ARE SUBMITTING JUST THE ALGORITHM AND WHERE TO IMPLEMENT IN CHALLENGE_ALGORITHM.TXT
