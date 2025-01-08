
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
public class ScheduleQueries {
    private static Connection connection;    
    private static PreparedStatement addScheduleEntry;
    private static PreparedStatement getScheduleByStudent; 
    private static PreparedStatement getScheduledStudentCount; 
    private static PreparedStatement getWaitlistedStudentsByClass;
    private static PreparedStatement dropStudentScheduleByCourse;
    private static PreparedStatement dropScheduleByCourse;
    private static PreparedStatement updateScheduleEntry;
    private static ResultSet resultSet;    
 

    public static void addScheduleEntry(ScheduleEntry entry) {
        connection = DBConnection.getConnection();
        try
        {
            addScheduleEntry = connection.prepareStatement("insert into app.schedule (semester, coursecode, studentid, status, timestamp) values (?, ?, ?, ?, ?)");
            addScheduleEntry.setString(1, entry.getSemester());
            addScheduleEntry.setString(2, entry.getCourseCode());
            addScheduleEntry.setString(3, entry.getStudentID());
            addScheduleEntry.setString(4, entry.getStatus());
            addScheduleEntry.setTimestamp(5, entry.getTimestamp());
            addScheduleEntry.executeUpdate();           
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }            
        
    }
    
    public static ArrayList<ScheduleEntry> getScheduleByStudent(String semester, String studentID) {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> schedules = new ArrayList<>();
        try
        {
            getScheduleByStudent = connection.prepareStatement("select * from app.schedule where semester = (?) and studentid = (?)");
            getScheduleByStudent.setString(1, semester);
            getScheduleByStudent.setString(2, studentID);
            resultSet = getScheduleByStudent.executeQuery();
            
            while(resultSet.next())
            {                         
                schedules.add(new ScheduleEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getTimestamp(5)));                
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        return schedules;                
        
    }
    
    public static int getScheduledStudentCount(String currentSemester,String courseCode) {
        connection = DBConnection.getConnection();
        int studentCount = 0;
        try
        {
            getScheduledStudentCount = connection.prepareStatement("select count(*) from app.schedule where semester = (?) and coursecode = (?)");
            getScheduledStudentCount.setString(1, currentSemester);
            getScheduledStudentCount.setString(2, courseCode);
            resultSet = getScheduledStudentCount.executeQuery();
            
            if (resultSet.next()) {
                studentCount = resultSet.getInt(1);
            }

        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        return studentCount;  
        
    }
    
    public static ArrayList<ScheduleEntry> getWaitlistedStudentsByClass(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> schedules = new ArrayList<>();
        try
        {
            getWaitlistedStudentsByClass = connection.prepareStatement("select * from app.schedule where semester = (?) and coursecode = (?) and status = (?)");
            getWaitlistedStudentsByClass.setString(1, semester);
            getWaitlistedStudentsByClass.setString(2, courseCode);
            getWaitlistedStudentsByClass.setString(3, "waitlisted");
            resultSet = getWaitlistedStudentsByClass.executeQuery();
            
            while (resultSet.next()) {
                schedules.add(new ScheduleEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getTimestamp(5)));                
            }

        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        return schedules;  
        
    }
    
    public static void dropStudentScheduleByCourse(String semester, String studentID, String courseCode) {
        connection = DBConnection.getConnection();
        try
        {
            dropStudentScheduleByCourse = connection.prepareStatement("delete from app.schedule where semester = ? and studentid = ? and coursecode = ?");
            dropStudentScheduleByCourse.setString(1, semester);
            dropStudentScheduleByCourse.setString(2, studentID);
            dropStudentScheduleByCourse.setString(3, courseCode);
            dropStudentScheduleByCourse.executeUpdate();           
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }            
        
    }    
    
    public static void dropScheduleByCourse(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        try
        {
            dropScheduleByCourse = connection.prepareStatement("delete from app.schedule where semester = ? and coursecode = ?");
            dropScheduleByCourse.setString(1, semester);
            dropScheduleByCourse.setString(2, courseCode);
            dropScheduleByCourse.executeUpdate();           
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }            
        
    }     
    
    public static void updateScheduleEntry(ScheduleEntry entry) {
        connection = DBConnection.getConnection();
        try
        {
            dropScheduleByCourse = connection.prepareStatement("update app.schedule set status = ? where timestamp = ? and semester = ? and coursecode = ? and studentid = ?");
            dropScheduleByCourse.setString(1, "scheduled");
            dropScheduleByCourse.setTimestamp(2, entry.getTimestamp());
            dropScheduleByCourse.setString(3, entry.getSemester());
            dropScheduleByCourse.setString(4, entry.getCourseCode());
            dropScheduleByCourse.setString(5, entry.getStudentID());
            dropScheduleByCourse.executeUpdate();           
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }              
    }
}     
