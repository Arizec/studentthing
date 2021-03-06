package Driver;

import USERS.Student;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringJoiner;

import PROGRAMS.ProgramCourses;


public class Driver {
	
    private String studentID;
    private String fullName;
    private String programCode;
    private String dob;

    
	ArrayList<ProgramCourses> programList =  new ArrayList<ProgramCourses>();
	ArrayList<Student> studentList =  new ArrayList<Student>();


    public void appendStudent(){
        BufferedReader br;
        try {
            //read external text file containing student info
            br = new BufferedReader(new FileReader("studentList.txt"));
            try {
                String x;

                //read all lines in file
                while ( (x = br.readLine()) != null ) {


                    String studentTxt[] = x.split(":", 5);
                    String ID = studentTxt[0];
                    String studentName = studentTxt[1];
                    String studentProgram = studentTxt[2];
                    String DOB = studentTxt[3];
                    int credit = Integer.parseInt(studentTxt[4]);

                    Student student = new Student(ID, studentName, studentProgram, DOB, credit, studentProgram.charAt(0));
                    studentList.add(student);





                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
            e.printStackTrace();
        }


    }

    public void viewResultsOfStudent(){
        for(int i = 0; i<studentList.size(); i++){
            System.out.println(studentList.get(i).toString());

        }

    }


/*
 * Graduate a student
 */

    public void graduateStudent(){
        BufferedReader br;
        try {
            //read external text file containing student info
            br = new BufferedReader(new FileReader("graduate.txt"));
            try {
                String x;

                //read all lines in file
                while ( (x = br.readLine()) != null ) {
                    System.out.println(x);
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
            e.printStackTrace();
        }

    }



    /*
     * To see if a student is has enough credits to graduate
     */
    public void applyToGraduate(String existingID){
    	BufferedReader br;
        try {
        	//read external text file containing student info
            br = new BufferedReader(new FileReader("studentList.txt"));

            try {
                String x;
                
                //read all lines in file
                while ( (x = br.readLine()) != null ) {
                
                	
                    String studentTxt[] = x.split(":", 5);
                    String ID = studentTxt[0];
                    String studentProgram = studentTxt[2];
                    String credits = studentTxt[4];
                    
                    int creditsCompleted = 0; //initialise credits
                    
                  //print credits if studentID in student list file
                  //matches ID of student logged in
                    if(existingID.equals(ID)){
                    	if(studentProgram.charAt(0) == 'B'){
                    		creditsCompleted = 288; //Bachelors: 3 year program
                    	}
                    	else if(studentProgram.charAt(0) == 'D'){
                    		creditsCompleted = 96; //Diploma: 1 year program
                    	}
                    	
                    	else if(studentProgram.charAt(0) == 'H'){
                    		creditsCompleted = 96; //Honours: 1 year program
                    	}
                    	
                    	else if(studentProgram.charAt(0) == 'M'){
                    		creditsCompleted = 192; //Masters: 2 year program
                    	}
                    	
                    	//else just set to 3 years as placeholder
                    	else {
                    		creditsCompleted = 288;
                    	}
                 
                    	//Credit points not met. Student not able to graduate
                    	if(Integer.parseInt(credits) != creditsCompleted){
                            System.out.println("\nCredits completed: "+ credits);
                            System.out.println("Credits needed to graduate: "+ creditsCompleted);
                    		System.out.println("Sorry, you are not able to graduate");
                    		break;
                    	}
                    	
                    	//Credit points met. Student is able to graduate
                    	else
                            System.out.println("\nCredits completed: "+ credits);
                            System.out.println("Credits needed to graduate: "+ creditsCompleted);
                    		System.out.println("Congratulations, you are able to graduate!");

                            String filename= "graduate.txt";
                            FileWriter fw = new FileWriter(filename,true);

                            fw.write("\r");
                            fw.write(existingID);//appends the string to the file
                            fw.close();



                        }
                    }
                	
                
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                System.out.println(e);
                e.printStackTrace();
            }
                    
    }

    
    /*
     * function to create student login account, i.e their
     * username and password
     */
    public String createStudentLogin(){

        try
        {

            String filename= "loginDetails.txt";
            FileWriter fw = new FileWriter(filename,true); //the true will append the new data
            Scanner reader = new Scanner(System.in);

            //student login id and password is created
            System.out.println("Create Student login details: ");
            System.out.println("Create Student ID");
            String studentID = reader.nextLine();
            System.out.println("Create Student Password");
            String password = reader.nextLine();

            String logindetails = studentID + ":" + password;
            fw.write("\r");
            fw.write(logindetails);//appends the string to the file
            fw.close();
            
            return studentID;

        }
        catch(IOException ioe)
        {
            System.err.println("IOException: " + ioe.getMessage());
        }

        return "Error";

    }

    /*
     * Used to verify student profile creation
     */
    public String returnID(String existingID){
    	return existingID;
    }


    /*
     * Create student profile/information
     */
    public void createStudent(String existingID){
        Scanner reader = new Scanner(System.in);
        //student profile is create. Name+ DOB + program

        System.out.println("\n Now create student profile");
        while(true){			// forces you to write the correct student ID before you can continue.
            System.out.println("Re-enter Student ID");
            String studentID = reader.nextLine();
            if(existingID.equals(studentID)){
                break;
            }
        }

        //student profile and info is created
        System.out.println("Enter student name: ");
        String studentName = reader.nextLine();
        System.out.println("Enter DOB: ");
        String DOB = reader.nextLine();
        System.out.println("Enter Program Code");
        String programCode = reader.nextLine();


        Student student = new Student(existingID, studentName, programCode, DOB, 0, programCode.charAt(0));
        writeToFile(student);


    }

    /*
     * Student bulk enrollments are created.
     * Only difference with create student is that you can enter the amount of credits a student has completed.
     * Useful for transfer students (either external or internal)
     */
    public void createEnrollment(String existingID){
        Scanner reader = new Scanner(System.in);
        //student profile is create. Name+ DOB + program

        System.out.println("\n Now create student profile");
        while(true){			// forces you to write the correct student ID before you can continue.
            System.out.println("Re-enter Student ID");
            String studentID = reader.nextLine();
            if(existingID.equals(studentID)){
                break;
            }
        }

        //student details are created
        System.out.println("Enter student name: ");
        String studentName = reader.nextLine();
        System.out.println("Enter DOB: ");
        String DOB = reader.nextLine();
        System.out.println("Enter Program Code");
        String programCode = reader.nextLine();

        System.out.println("Enter credits completed");
        int credit = reader.nextInt();

        Student student = new Student(existingID, studentName, programCode, DOB, credit, programCode.charAt(0));
        writeToFile(student); //and is writted to file


    }

    /*
     * Creates a Program which includes, program code, ver no, credits to complete
     */
    public boolean createProgram(){
        try{

            String filename= "programs.txt";

            FileWriter fw = new FileWriter(filename,true); //the true will append the new data
            Scanner reader = new Scanner(System.in);
            Scanner reader2 = new Scanner(System.in);

            //program details are created
            System.out.println("Creating a new program");
            String facultyChoice = schoolFaculty();
            System.out.println("Program code [numbers only]");
            String programCode = facultyChoice + reader.nextLine();

            System.out.println("Program Name");
            String programName = reader.nextLine();

            System.out.println("Version number");
            String versionNo = reader.nextLine();

            System.out.println("Credits to complete program");
            String creditsNeeded = reader.nextLine();

            String programType = programType();

            String programStatus = status();

            String program = programCode + ":" + programName + ":" + versionNo + ":" + creditsNeeded + ":" +
                    programType +  ":" + programStatus;



            //4 specialisation courses are added to a program
            System.out.println("Enter 4 Specialization Courses for this program");
            while(true){

                String special1 = reader2.nextLine();
                if(checkforCourses(special1)){
                    while(true){

                        String special2 = reader2.nextLine();
                        if(checkforCourses(special2)){

                            while(true){
                                String special3 = reader2.nextLine();
                                if(checkforCourses(special3)){

                                    while(true){
                                        String special4 = reader2.nextLine();
                                        if(checkforCourses(special4)){
                                            fw.write("\r\n");
                                            fw.write(program);//appends the string to the file
                                            fw.close();
                                            printSpecial(programCode, special1, special2, special3, special4);
                                            return true;
                                        }
                                        System.out.println("Invalid Course Code");
                                    }
                                }
                                System.out.println("Invalid Course Code");
                            }
                        }
                        System.out.println("Invalid Course Code");
                    }
                }
                System.out.println("Invalid Course Code");
            }






        }
        catch(IOException ioe)
        {
            System.err.println("IOException: " + ioe.getMessage());
        }

        return false;

    }

    /*
     * Append student information to an external text file
     */
    private void writeToFile(Student student){

        try
        {

            String filename= "studentList.txt";
            FileWriter fw = new FileWriter(filename,true); //the true will append the new data
            fw.write("\r\n");
            fw.write(student.toString());//appends the string to the file
            fw.close();
        }
        catch(IOException ioe)
        {
            System.err.println("IOException: " + ioe.getMessage());
        }


    }


    /*
     * Returns program type (i.e Bachelors ect)
     */
    private String programType(){
        while(true) {
            System.out.println("\nProgram Type:");
            System.out.println("\n1. Bachelor");
            System.out.println("2. Honours");
            System.out.println("3. Master");
            System.out.println("4. Diploma");
            int choice = getInput(4);

            switch(choice){
                case 1:
                    return "Bachelor";
                case 2:
                    return "Honours";
                case 3:
                    return "Master";

                case 4:
                    return "Diploma";

            }


        }
    }

    /*
     * Returns Faculty of Program.
     * Every program will start with Facaulty number (i.e SS/IT/SE/SB)
     */
    private String schoolFaculty(){
        while(true) {
            System.out.println("\nEnter Faculty of Program:");
            System.out.println("\n1. School of Science");
            System.out.println("2. School of Business");
            System.out.println("3. School of Information Technology");
            System.out.println("4. School of Engineering");
            int choice = getInput(4);

            switch(choice){
                default:
                    return "BIT";


            }


        }

    }

    /*
     * Choose whether program will be active or inactive
     */
    private String status(){
        while(true) {
            System.out.println("\nEnter Program status:");
            System.out.println("\n1. Active");
            System.out.println("2. Inactive");
            int choice = getInput(2);

            switch(choice){
                case 1:
                    return "Active";
                case 2:
                    return "Inactive";

            }


        }
    }

    /*
     * Tests whether choice entered at menu selection is valid
     */
    private int getInput(int numberofChoices){
        int choice = -1;
        Scanner input = new Scanner(System.in);

        while( choice <0 || choice >= numberofChoices){			// catches exception if not a number between 0 and 12.
            try {
                System.out.print("\nEnter Your Choice: ");
                choice = Integer.parseInt(input.nextLine());
            }
            catch(NumberFormatException e){
                System.out.println("Invalid Selection. Please Try Again");
            }
        }
        return choice;
    }


    /*
     * Finds the course in text file to see if it exists
     */
    private boolean checkforCourses(String coursecode){
        BufferedReader br;
        try {
            //read external text file containing student info
            br = new BufferedReader(new FileReader("courses.txt"));
            try {
                String x;

                //read all lines in file
                while ( (x = br.readLine()) != null ) {


                    String studentTxt[] = x.split(":", 4);
                    if(coursecode.equals(studentTxt[0])){
                        return true;

                    }

                }
                return false;


            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
            e.printStackTrace();
        }

        return false;


    }

    /*
     * prints specialisation courses associated to a program
     */
    private void printSpecial(String programCode, String special1, String special2, String special3, String special4){
        try{
            String specializationtxt = programCode + ":" + special1 + ":" + special2 + ":" + special3 + ":" + special4;
            String filename2= "specialCourse.txt";
            FileWriter fw2 = new FileWriter(filename2,true); //the true will append the new data
            fw2.write("\r\n");
            fw2.write(specializationtxt);
            fw2.close();

        } catch(IOException ioe) {

            System.err.println("IOException: " + ioe.getMessage());
        }

    }







}
