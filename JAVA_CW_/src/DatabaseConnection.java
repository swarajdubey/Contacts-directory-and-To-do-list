
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author swaraj
 */
public class DatabaseConnection {
    private Connection con;
    private ResultSet rs;
    private PreparedStatement statement;
    private int loginAttempts=0;
    private boolean user_status;
    
    public DatabaseConnection(){
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("attempting to connect nowwwwww \n");
            con=DriverManager.getConnection("jdbc:mysql://110.4.46.195:3306/test01","user01","111111");
        }catch(Exception e){
            System.out.println("Can't connect to the database");
        }
    }
    
    
    public boolean checkLogin(String encodedPassword,String email)
    {
        try{
            String loginQuery="SELECT pass FROM user_info WHERE email='"+email+"'";
            statement=con.prepareCall(loginQuery);
            
            rs=statement.executeQuery(loginQuery);
            String pass="";
            while(rs.next())
            {
                pass=rs.getString("pass");
            }
            System.out.println("ecnoded pass is: "+encodedPassword+" and email: "+email);
            if(encodedPassword.contentEquals(pass))
            {
                System.out.println("can login now \n");
                user_status=true;
                //return user_status;
            }
            else{
                System.out.println("Invalid login\n");
                loginAttempts++;
                if(loginAttempts==5)
                {
                    System.out.println("Alert!\n");
                    //update the user via phone here
                    loginAttempts=0;
                }
                user_status=false;
                //return user_status;
            }
        }catch(Exception e){
            System.out.println("INCORRECT LOGIN QUERY \n");
        }
        //System.out.println("can reach in here ");
        return user_status;
    }
    
    public void UpdateUserInformation(String first_name,String last_name,String email,String pass)
    {
        try{

            String check_duplicate_email_query="SELECT email FROM user_info";
            statement=con.prepareCall(check_duplicate_email_query);
            rs=statement.executeQuery(check_duplicate_email_query);
            while(rs.next())
            {
                if(email.contentEquals(rs.getString("email")))
                {
                    System.out.println("This email already exists choose another one");
                    return;
                }
            }
            
            String updateUserInfoQuery="INSERT INTO user_info(first_name,last_name,email,pass) VALUES ('"+first_name+"','"+last_name+"','"+email+"','"+pass+"')";
            statement=con.prepareStatement(updateUserInfoQuery);
            statement.executeUpdate(updateUserInfoQuery);
            
        }catch(Exception e){
            System.out.println("INCORRECT UPDATE QUERY \n");
        }
    }
    
    public String getPersoneName(String email) //returns user name for welcome message
    {
        String name="";
        try{
            String personnameQuery="SELECT first_name FROM user_info WHERE email='"+email+"'";
            statement=con.prepareCall(personnameQuery);
            rs=statement.executeQuery(personnameQuery);
            while(rs.next())
            {
                name=rs.getNString("first_name");
            }
        }catch(Exception e){
            System.out.println("can't select name");
        }
        
        return name;
    }
    
    public boolean addNewUser(String firstname,String lastname,String username,String encodedpassword)
    {
        //add new user
        if(checkForDuplicateEmail(username)==true)
        {
            try{
                String insertNewUserQUery="INSERT INTO user_info(first_name,last_name,email,pass) VALUES ('"+firstname+"','"+lastname+"','"+username+"','"+encodedpassword+"')";
            statement=con.prepareStatement(insertNewUserQUery);
            statement.executeUpdate(insertNewUserQUery);
            return true;
            }catch(Exception e){
                System.out.println("INVALID NEW USER QUERY \n");
            }
            
        }
        else //prompt the user to choose a new username
        {
            return false; //on this return statement, the user has to enter new username
        }
        return true;
    }
    
    //this function adds the name as a column
    public void addNameAsColumn(String username)
    {
        try{
            String addNameAsColumnQuery="ALTER TABLE user_tasks ADD COLUMN "+username+" varchar (100)";
            statement=con.prepareCall(addNameAsColumnQuery);
            statement.executeUpdate(addNameAsColumnQuery);
            
            addNameAsColumnQuery="ALTER TABLE user_task_priorities ADD COLUMN "+username+" int";
            statement=con.prepareCall(addNameAsColumnQuery);
            statement.executeUpdate(addNameAsColumnQuery);
            
            //update the user name as column in the priorities table as well
            
        }catch(Exception e){
            System.out.println("ALTER TABLE Query\n");
        }
    }
    
    public void insertTaskData()//updates the entire to do list table
    {
        
    }
    //this function can only be called from this class
    private boolean checkForDuplicateEmail(String email)//this checks forr an existing username
    {
        try{

            String check_duplicate_email_query="SELECT email FROM user_info";
            statement=con.prepareCall(check_duplicate_email_query);
            rs=statement.executeQuery(check_duplicate_email_query);
            while(rs.next())
            {
                if(email.contentEquals(rs.getString("email")))
                {
                    System.out.println("This email already exists choose another one");
                    return false; //means the username already exists
                }
            }
            return true;
        }catch(Exception e){
            System.out.println("INCORRECT Duplicate email QUERY \n");
        }
        return true; //this means the user exists
    }
    
    public void closeconnection() throws SQLException
    {
        con.close();
    }
}
