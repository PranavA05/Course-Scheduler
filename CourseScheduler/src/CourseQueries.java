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
public class CourseQueries {
    private static Connection connection;
    private static PreparedStatement addCors;
    private static PreparedStatement getCourseCodes;
    private static ResultSet resultSet;
    
    
    
    public static void addCourse(CourseEntry course)
    {
        connection = DBConnection.getConnection(); //Connected to DB
        try
        {
            addCors = connection.prepareStatement("insert into app.courseentry (courseCode, description) values (?, ?)");
            addCors.setString(1, course.getCourseCode());
            addCors.setString(2, course.getDescription());
            addCors.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    public static ArrayList<String> getAllCourseCodes()
    {
        connection = DBConnection.getConnection(); //Connected to DB
        ArrayList<String> courseCodes = new ArrayList<String>();
        try
        {
            getCourseCodes = connection.prepareStatement("select courseCode from app.courseentry");
            resultSet = getCourseCodes.executeQuery();
            
            while(resultSet.next())
            {
                courseCodes.add(resultSet.getString(1));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return courseCodes;
        
    }

    
    
}

