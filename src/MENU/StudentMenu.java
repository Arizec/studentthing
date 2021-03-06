package MENU;

import MENU.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import Driver.Driver;
import USERS.Student;


/**
 * Created by Martin on 2/09/2016.
 */
public class StudentMenu {
    Driver driverClass = new Driver();


    private String studentID;



    public StudentMenu(String studentID){
        this.studentID = studentID;






    }







    private void printHeader(){

        System.out.println("\n+--------------------------------+");
        System.out.println("|            Student               |");
        System.out.println("|              Menu                |");
        System.out.println("+----------------------------------+");

    }

    private void menuOptions(){
        System.out.println("\n1. View Results"); //done
        System.out.println("2. View current enrolments"); //done
        System.out.println("3. Apply to Graduate"); //done
    }

    private void performChoices(int choice, Student student){


        switch (choice){
            case 1:

                System.out.println("View Result");
                student.viewPastEnrolments();

                break;
            case 2:
                System.out.println("For Semester 2, 2016, you are currently enrolled in the following courses: ");
                student.viewCurrentEnrolments();

                break;

            case 3:
                System.out.println("Graduate");
                driverClass.applyToGraduate(studentID);
                break;
                
            default:
                System.out.println("4");
                break;

        }
    }

    private int getInput(){
        int choice = -1;
        Scanner input = new Scanner(System.in);

        while( choice <0 || choice > 5){	// catches exception if not a number between 0 and 12.
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




    public void runMenu(){

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
                    char type = studentProgram.charAt(0);
                    int credit = Integer.parseInt(studentTxt[4]);






                    //if ID in file matches ID of student logged in
                    //print their current details
                    if(studentID.equals(ID)){

                        Student studentObject = new Student(ID, studentName, studentProgram, DOB, credit, type);
                        studentObject.addCourses();



                            if(studentObject.enrolCourses()){
                                studentObject.enrolPastResults();

                            }


                            System.out.println(studentObject.getType());
                            printHeader();
                            menuOptions();
                            int choice = getInput();
                            performChoices(choice, studentObject);






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


}
