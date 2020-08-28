package com.kmit.sushma;
import java.util.*;

/**
 * 
 *
 */
class Student{
	String m,l,k;
    int i;
    Scanner s=new Scanner(System.in);
    void read() {
    	System.out.println( "Enter the name of the student" );
        l=s.nextLine();
        
       System.out.println("Enter college of the student");
        m=s.nextLine();
        System.out.println("Enter branch of the student");
        k=s.nextLine();
        System.out.println("Enter the roll number of the student");
        i=s.nextInt();
    }
    void display() {
    	System.out.println("The details are:");
    	System.out.println("name-"+ l +" rollno-"+ i +" college-"+ m+ " branch-"+k);
    }
}
public class App 
{
    public static void main( String[] args )
    {  
    	Student p=new Student();
        p.read();
        p.display();
       
    }
}
