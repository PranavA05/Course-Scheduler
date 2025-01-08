
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
public class MultiTableQueries {
    private static Connection connection;    
    private static PreparedStatement getAllClassDescriptions;
    private static PreparedStatement getScheduledStudentsByClass;
    private static PreparedStatement getWaitlistedStudentsByClass;
    private static ResultSet resultSet;
    
    public static ArrayList<ClassDescription> getAllClassDescriptions(String semester) {

        connection = DBConnection.getConnection();
        ArrayList<ClassDescription> classDescriptions = new ArrayList<>();
        try
        {
            getAllClassDescriptions = connection.prepareStatement("select app.class.courseCode, description, seats from app.class, app.courseentry where semester = (?) and app.class.courseCode = app.courseentry.courseCode order by app.class.courseCode");
            getAllClassDescriptions.setString(1, semester);
            resultSet = getAllClassDescriptions.executeQuery();
            
            while(resultSet.next())
            {                         
                classDescriptions.add(new ClassDescription(resultSet.getString(1), resultSet.getString(2), resultSet.getInt(3)));
            }

        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        return classDescriptions;                                                                                     
                
    }   
    
    public static ArrayList<StudentEntry> getScheduledStudentsByClass(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> studentEntries = new ArrayList<>();
        try
        {
            getScheduledStudentsByClass = connection.prepareStatement("select app.student.studentid, firstname, lastname from app.student join app.schedule on app.student.studentid = app.schedule.studentid where semester = ? and status = ? and coursecode = ?");
            getScheduledStudentsByClass.setString(1, semester);
            getScheduledStudentsByClass.setString(2, "scheduled");
            getScheduledStudentsByClass.setString(3, courseCode);
            resultSet = getScheduledStudentsByClass.executeQuery();
            
            while(resultSet.next())
            {                   
                studentEntries.add(new StudentEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3)));
            }

        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        return studentEntries;                                                                                     
                
    }    
    
    public static ArrayList<StudentEntry> getWaitlistedStudentsByClass(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> studentEntries = new ArrayList<>();
        try
        {
            getWaitlistedStudentsByClass = connection.prepareStatement("select app.student.studentid, firstname, lastname from app.student join app.schedule on app.student.studentid = app.schedule.studentid where semester = ? and status = ? and coursecode = ?");
            getWaitlistedStudentsByClass.setString(1, semester);
            getWaitlistedStudentsByClass.setString(2, "waitlisted");
            getWaitlistedStudentsByClass.setString(3, courseCode);
            resultSet = getWaitlistedStudentsByClass.executeQuery();
            
            while(resultSet.next())
            {                   
                studentEntries.add(new StudentEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3)));
            }

        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        return studentEntries;                                                                                     
                
    }        
}