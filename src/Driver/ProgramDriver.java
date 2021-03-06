package Driver;

import PROGRAMS.ProgramCourses;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Martin on 6/09/2016.
 */
public class ProgramDriver {


    ArrayList<ProgramCourses> programList =  new ArrayList<ProgramCourses>();


    private boolean active;


    /*
     * Append program information to external text file
     */
    public void initializeProgramANDCourses(){
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader("programs.txt"));
            try {
                String x;
                while ( (x = br.readLine()) != null ) {
                    // printing out each line in the file

                    String programTxt[] = x.split(":", 6);
                    String programCode = programTxt[0];
                    String programName = programTxt[1];
                    String version = programTxt[2];
                    int creditsRequired = Integer.parseInt(programTxt[3]);
                    String programType = programTxt[4];

                    if(programTxt[5].equals("Active")){
                        active = true;

                    }else{
                        active = false;

                    }

                    ProgramCourses program = new ProgramCourses(programCode, programName, version, creditsRequired,
                            programType, active);
                    programList.add(program);



                }
                addCoursesToProgram();


            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
            e.printStackTrace();
        }


    }

    /*
     * Clears the program buffer, if user wants to create another program
     */
    public void refreshProgramCourses(){
        for(int i = 0; i < programList.size(); i++){
            programList.remove(i);

        }
        programList.clear();
        initializeProgramANDCourses();
    }


    /*
     * Print associated core courses and specialisation courses of related
     * programs
     */
    public void printPrograms(){
        for(int i = 0; i< programList.size(); i++){
            String programCode = programList.get(i).getProgramCode();
            String programName = programList.get(i).getProgramName();
            String programType = programList.get(i).getProgramType();
            System.out.println("=============================");
            System.out.println("    Program Name: "+ programType +" of "+ programName);
            System.out.println("    Program Code: " + programCode);
            System.out.println("============================= ");
            System.out.println("    Core Courses");
            programList.get(i).printCourseDetails();
            System.out.println("    Specialization Courses");
            programList.get(i).printSpecialCourseDetails();
        }
        System.out.println("=============================");
    }

    /*
     * Assign courses to certain programs based on program faculty and
     * course type
     */
    public void addCoursesToProgram(){
        for(int i = 0; i< programList.size(); i++){
            programList.get(i).addCourses();
        }
    }


    public String defineProgram(String programCode) {


        for(int i = 0; i< programList.size(); i++){
            if(programCode.equals(programList.get(i).getProgramCode())){


                //menu for define program
                System.out.println("==== What would you like to Change ====");
                System.out.println("        1. Program Name ");
                System.out.println("        2. Program Code");
                System.out.println("   3. Program Credits Required");
                System.out.println("======================================= ");

                int choice = getInput(4);

                Scanner define = new Scanner(System.in);

                switch (choice){
                    case 1: //change program name
                        System.out.println("Enter Program Name");


                        programList.get(i).setProgramName(define.nextLine());


                        System.out.println("=============================");
                        System.out.println("    Program Name: "+ programList.get(i).getProgramType() +" of "+
                                programList.get(i).getProgramName());
                        System.out.println("    Program Code: " + programList.get(i).getProgramCode());
                        System.out.println("============================= ");

                        return "Program name changed";
                    case 2: //change program code
                        System.out.println("Enter Program Code");


                        programList.get(i).setProgramCode(define.nextLine());

                        System.out.println("=============================");
                        System.out.println("    Program Name: "+  programList.get(i).getProgramType() +" of "+
                                programList.get(i).getProgramName());
                        System.out.println("    Program Code: " + programList.get(i).getProgramCode());
                        System.out.println("============================= ");

                        return "Program code changed";

                    case 3: //change credits required to complete a program
                        System.out.println("Enter Program Credits");

                        programList.get(i).setProgramCredit(define.nextInt());


                        System.out.println("=============================");
                        System.out.println("    Program Name: "+ programList.get(i).getProgramType() +" of "+
                                programList.get(i).getProgramName());
                        System.out.println("    Program Code: " +  programList.get(i).getProgramCode());
                        System.out.println("============================= ");


                        return "Program Credits Changed";

                    default:
                        break;

                }
                break;
            }


        }


        return "Program Code Not Found";

    }

    //validates input
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




}
