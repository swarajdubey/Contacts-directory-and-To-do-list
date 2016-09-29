
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author swaraj
 */
public class SQLiteDatabaseConnection {
    
    Connection c = null;
    Statement stmt = null;
    ResultSet rs=null;
    private boolean user_status;
    
    protected ArrayList<Integer> prioritynumbers=new ArrayList<Integer>();
    protected ArrayList<Integer> prioritynumbersID=new ArrayList<Integer>();
    protected ArrayList<Integer> completedtaskid=new ArrayList<Integer>();
    protected ArrayList<Integer> incompletetaskid=new ArrayList<Integer>();
    protected ArrayList<String> Taskscompleted=new ArrayList<String>();
    protected ArrayList<String> TasksIncomplete=new ArrayList<String>();
    
    protected ArrayList<ArrayList<String>> completeprioritytasks = new ArrayList<ArrayList<String>>();
    protected ArrayList<ArrayList<String>> completeprioritytasks2 = new ArrayList<ArrayList<String>>();
    protected ArrayList<ArrayList<String>> incompletetask = new ArrayList<ArrayList<String>>();
    protected ArrayList<ArrayList<String>> finalContacts = new ArrayList<ArrayList<String>>();
    protected ArrayList<ArrayList<String>> sortedfinalContacts = new ArrayList<ArrayList<String>>();
    protected ArrayList<ArrayList<String>> searchByNameContacts = new ArrayList<ArrayList<String>>();
    
    protected ArrayList<String> contactNames=new ArrayList<String>();
    private ArrayList<String> allTableNames=new ArrayList<String>(); //store names of all tables in the central database
    
    protected SQLiteDatabaseConnection pObj;
    
    
    public SQLiteDatabaseConnection()
    {
        try{
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:MyManager.db");
            c.setAutoCommit(false);
        }catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        //System.out.println("Opened database successfully");
    }
    public boolean checkLogin(String encodedPassword,String email)//check if user's email and encrypted password is correct
    {
        try{
            String loginQuery="SELECT pass FROM user_info WHERE email='"+email+"'";
            stmt = c.createStatement();
            rs = stmt.executeQuery( loginQuery );
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
            }
            else{
                System.out.println("Invalid login\n");
                user_status=false;
            }
        }catch(Exception e){
            System.out.println("login exception\n");
        }
        return user_status;
    }
    
    public String getPersoneName(String email) //returns user name for welcome message
    {
        String name="";
        try{
            String personnameQuery="SELECT first_name FROM user_info WHERE email='"+email+"'";
            stmt = c.createStatement();
            rs = stmt.executeQuery( personnameQuery );
            while(rs.next())
            {
                name=rs.getString("first_name");
            }
        }catch(Exception e){
            System.out.println("can't select name");
        }
        return name;
    }
    public boolean addNewUser(String firstname,String lastname,String username,String encodedpassword)
    {
        if(checkForDuplicateEmail(username)==true)
        {
            try{
                String insertNewUserQUery="INSERT INTO user_info (first_name,last_name,email,pass) VALUES ('"+firstname+"','"+lastname+"','"+username+"','"+encodedpassword+"')";
                System.out.println("INSERT Query is: "+insertNewUserQUery);
                stmt = c.createStatement();
                stmt.executeUpdate( insertNewUserQUery );
                System.out.println("QUery is ok \n");
                c.commit(); //to save the changes
                //return true;
            }catch(Exception e){
                System.out.println("INVALID NEW USER QUERY \n");
                
                return false;
            }
            
        }
        else //prompt the user to choose a new username
        {
            return false; //on this return statement, the user has to enter new username
        }
        return true;
    }
    private boolean checkForDuplicateEmail(String email)//this checks forr an existing username
    {
        try{

            String check_duplicate_email_query="SELECT email FROM user_info";
            stmt = c.createStatement();
            rs = stmt.executeQuery( check_duplicate_email_query );
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
    
    public void addNameAsColumn(String username) //column adding is working
    {
        allTableNames.clear();
        allTableNames.add("user_tasks");
        allTableNames.add("user_task_dates");
        allTableNames.add("user_task_time");
        allTableNames.add("user_task_status");
        allTableNames.add("directory_names");
        allTableNames.add("directory_phone_numbers");
        allTableNames.add("directory_emails");
        
        try{
            
            for(int i=0;i<allTableNames.size();i++)
            {
                String addNameAsColumnQuery="ALTER TABLE "+allTableNames.get(i)+" ADD COLUMN "+username+" varchar(100)";
                
                PreparedStatement ps = c.prepareStatement(addNameAsColumnQuery);
                ps.execute();
            
                //stmt = c.createStatement();
                //stmt.executeUpdate( addNameAsColumnQuery );
                //c.commit();
            }
            /*String addNameAsColumnQuery="ALTER TABLE user_tasks ADD COLUMN "+username+" varchar(100)";//tasks are in string format
            stmt = c.createStatement();
            stmt.executeUpdate( addNameAsColumnQuery );
            c.commit();*/
            
            String addNameAsColumnQuery="ALTER TABLE user_task_priorities ADD COLUMN "+username+" int";//priorities are in integer format
            
            PreparedStatement ps = c.prepareStatement(addNameAsColumnQuery);
            ps.execute();
            
            //stmt = c.createStatement();
            //stmt.executeUpdate( addNameAsColumnQuery );
            //c.commit();
            
            /*addNameAsColumnQuery="ALTER TABLE user_task_dates ADD COLUMN "+username+" varchar(100)";
            stmt = c.createStatement();
            stmt.executeUpdate( addNameAsColumnQuery );
            c.commit();
            
            addNameAsColumnQuery="ALTER TABLE user_task_time ADD COLUMN "+username+" varchar(100)";
            stmt = c.createStatement();
            stmt.executeUpdate( addNameAsColumnQuery );
            c.commit();
            
            addNameAsColumnQuery="ALTER TABLE user_task_status ADD COLUMN "+username+" varchar(100)";
            stmt = c.createStatement();
            stmt.executeUpdate( addNameAsColumnQuery );
            c.commit();
            
            
            
            //Update the contacts directory table as well
            addNameAsColumnQuery="ALTER TABLE directory_names ADD COLUMN "+username+" varchar(100)";
            stmt = c.createStatement();
            stmt.executeUpdate( addNameAsColumnQuery );
            c.commit();
            
            addNameAsColumnQuery="ALTER TABLE directory_phone_numbers ADD COLUMN "+username+" varchar(100)";
            stmt = c.createStatement();
            stmt.executeUpdate( addNameAsColumnQuery );
            c.commit();
            
            addNameAsColumnQuery="ALTER TABLE directory_emails ADD COLUMN "+username+" varchar(100)";
            stmt = c.createStatement();
            stmt.executeUpdate( addNameAsColumnQuery );
            c.commit();
            */
            
            
        }catch(Exception e){
            System.out.println("COLUMN ADD Query Exception\n");
        }
    }
    
    //adds items into the database
    public void insertUpdatesIntoDatabases(String task_name,String task_due_date,String task_due_time,int task_priority_chosen,String username)//this function adds the updates into the sqlite database
    {return;}
    
    //function the returns the tasks in descending priority order
    public ArrayList<ArrayList<String>> getPrioritiesOfUserTasks(String username)
    {return completeprioritytasks;}
    
    //functions that returns the tasks that have been completed
    public ArrayList<ArrayList<String>> getUserCompletedTasks(String username)
    {
        return completeprioritytasks2;
    }
    
    //function that returns incomplete tasks
    public ArrayList<ArrayList<String>> getUserInCompleteTasks(String username)
    {
        return incompletetask;
    }
    
    //function that checks if a task already exists 
    public boolean checkForDuplicateTask(String username, String task_name)
    {
        return true; //this means no duplicate has been found
    }
    
    //function that changes the task name of the contact selected
    public void changeTaskName(String username,String response,String uniqueTask){}
    
    //function that changes the status of the task to 'Done'
    public void changeTaskStatus(String username,String response,String uniqueTask){}
    
    //function that deletes tasks
    public void deleteTask(String username,String uniqueTask){}
    
    //function that changes due date
    public void changeDueDate(String username,String task_modify_due_date,String uniqueTask){}
    
    //function that changes priority
    public void changePriority(String username,int change_task_priority,String uniqueTask){}
    
    //function that changes due time
    public void changeDueTime(String username,String task_modify_due_time,String uniqueTask){}
    
    public void getUserTaskDates(){}
    
    
    //-------------------------CONTACTS DIRECTORY FUNCTIONS--------------------
    public void updateContacts(String username,String name_of_task,String contact_number,String email_contact){}
    
    public ArrayList<ArrayList<String>> sortContactsByNames(String username){return sortedfinalContacts;}
    
    public ArrayList<ArrayList<String>> getContacts(String username){return finalContacts;}
    
    public boolean checkdirectoryDuplicateEmail(String email,String username){return true;}
    
    public boolean checkDuplicatePhoneNumber(String username,String contact_number){return true;}
    
    public ArrayList<ArrayList<String>> SearchContactByName(String username,String temp){return searchByNameContacts;}
    
    //---------------function to delete the selected contact by the user----------------
    public void deleteSelectedContact(String email,String username){}
    
    //-------------------function to edit name of the contact-----------
    public void editNameOfContact(String username,String uniqueEmail,String response){}
    
    //------------------function to edit the email of the contact-----
    public void editEmailOfContact(String username,String uniqueEmail,String response){}
    
    //-----function to edit the phone number of the contact-----
    public void editPhoneOfContact(String username,String uniqueEmail,String response){}
    
    //-----funtion to set the parent object type
    public void setparentObject(SQLiteDatabaseConnection o){}
}
