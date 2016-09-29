
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author swaraj
 */
public class IncompleteTasks extends SQLiteDatabaseConnection{
    
    private ArrayList<String> tableNames= new ArrayList<String>();
    
    public void setparentObject(SQLiteDatabaseConnection parentobject)
    {
        pObj=null;
        pObj=parentobject; //object that contains database connection values
    }
    
    public ArrayList<ArrayList<String>> getUserInCompleteTasks(String username)
    {
        //clear all the arraylist to prevent data from being appended everytime this function is called 
        Taskscompleted.clear();
        completedtaskid.clear();
        incompletetaskid.clear();
        completeprioritytasks2.clear();
        incompletetask.clear();
        TasksIncomplete.clear();
        
        //add table names here
        tableNames.add("user_tasks");
        tableNames.add("user_task_priorities");
        tableNames.add("user_task_dates");
        tableNames.add("user_task_time");
        tableNames.add("user_task_status");
        
        try{
            //query to get ID of tasks not complete
            String getUserInCompleteTasksQuery="SELECT ID FROM user_task_status WHERE "+username+"='not done'"; //gets priority values from the user_task_status table
            stmt = c.createStatement(); //prepare statement
            rs = stmt.executeQuery( getUserInCompleteTasksQuery ); //execute query
            while(rs.next())//fetch all rows
            {
                incompletetaskid.add(rs.getInt("ID")); //store the ID of incomplete commands
            }
            
            for(int i=0;i<incompletetaskid.size();i++)
            {
                
                ArrayList<String> partialprioritytasks=new ArrayList<String>();
                for(int j=0;j<tableNames.size();j++)
                {
                    //fetch data from table where task is incomplete
                    getUserInCompleteTasksQuery="SELECT "+username+" FROM "+tableNames.get(j)+" WHERE ID="+incompletetaskid.get(i);
                    stmt = c.createStatement(); //prepare statement
                    rs = stmt.executeQuery( getUserInCompleteTasksQuery ); //execute query
                    while(rs.next()) //fetch the rows
                    {
                        partialprioritytasks.add(rs.getString(username));
                        TasksIncomplete.add(rs.getString(username)); //add all incomplete tasks
                    }
                }
                
                
                incompletetask.add(partialprioritytasks); //add all incomplete tasks
            }
            
        }catch(Exception e){
            System.out.println(" incomplete tasks exception \n");
        }
        
        return incompletetask;//return all incomplete tasks
    }
}
