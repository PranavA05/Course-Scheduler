
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author prana
 */
public class StudentQueries {

    private static Connection connection;
    private static PreparedStatement addStud;
    private static PreparedStatement getStuds;
    private static PreparedStatement getStudID;
    private static PreparedStatement dropStudent;
    private static ResultSet resultSet;
    
    public static void addStudent(StudentEntry student)
    {
        connection = DBConnection.getConnection();
        try
        {
            addStud = connection.prepareStatement("insert into app.student (studentID, firstName, lastName) values (?, ?, ?)");
            addStud.setString(1, student.getStudentID());
            addStud.setString(2, student.getFirstName());
            addStud.setString(3, student.getLastName());
            addStud.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        
    }
    public static StudentEntry getStudent(String studentID) {
        connection = DBConnection.getConnection();
        StudentEntry student = null;
        try
        {
            addStud = connection.prepareStatement("select firstname, lastname from app.student where studentid = ? ");
            addStud.setString(1, studentID);
            resultSet = addStud.executeQuery();
                                    
            if(resultSet.next())
            {    
                student = new StudentEntry(studentID, resultSet.getString(1), resultSet.getString(2));               
            }
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }            
        
        return student;
        
    }  
    
    
    public static ArrayList<StudentEntry> getAllStudents()
    {
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> students = new ArrayList<StudentEntry>();
        try
        {
            getStuds = connection.prepareStatement("select studentID, firstName, lastName from app.student");
            resultSet = getStuds.executeQuery();
            
            while(resultSet.next())
            {
                students.add(new StudentEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3)));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return students;
        
    }
    public static String getStudentID(String lastName, String firstName) {
        connection = DBConnection.getConnection();
        String studentID = "";
        try
        {
            getStudID = connection.prepareStatement("select studentID from app.student where firstName = '" + firstName + "' and lastName = '" + lastName + "'");
            resultSet = getStudID.executeQuery();
            
            while(resultSet.next())
            {
                studentID = resultSet.getString("STUDENTID");
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return studentID;
    }
    public static void dropStudent(String studentID) {
        connection = DBConnection.getConnection();
        try
        {
            dropStudent = connection.prepareStatement("delete from app.student where studentid = ?");
            dropStudent.setString(1, studentID);
            dropStudent.executeUpdate();           
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    
}
