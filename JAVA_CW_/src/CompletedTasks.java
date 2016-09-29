
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
public class CompletedTasks extends SQLiteDatabaseConnection{
    
    private ArrayList<String> tableNames= new ArrayList<String>();
    
    public void setparentObject(SQLiteDatabaseConnection parentobject)
    {
        pObj=null;
        pObj=parentobject; //object that contains database connection values
    }
    
    //function that returns the completed tasks 
    public ArrayList<ArrayList<String>> getUserCompletedTasks(String username)
    {
        
        //-----------clear all the arraylists to prevent unneccesary appending of data if this function is called again---------
        Taskscompleted.clear();
        completedtaskid.clear();
        incompletetaskid.clear();
        completeprioritytasks2.clear();
        incompletetask.clear();
        TasksIncomplete.clear();
        tableNames.clear();
        
        //add table names here
        tableNames.add("user_tasks");
        tableNames.add("user_task_priorities");
        tableNames.add("user_task_dates");
        tableNames.add("user_task_time");
        tableNames.add("user_task_status");
        
        try{
            String getUserCompletedTasksQuery="SELECT ID FROM user_task_status WHERE "+username+"='Done'"; //gets priority values from the user_task_status table
            stmt = c.createStatement();
            rs = stmt.executeQuery( getUserCompletedTasksQuery );
            while(rs.next()) //keep fetching the rows
            {
                completedtaskid.add(rs.getInt("ID")); //get the ID's
            }

            for(int i=0;i<completedtaskid.size();i++) //start getting tasks that are done
            {
                ArrayList<String> partialprioritytasks=new ArrayList<String>();
                for(int j=0;j<tableNames.size();j++)
                {
                    getUserCompletedTasksQuery="SELECT "+username+" FROM "+tableNames.get(j)+" WHERE ID="+completedtaskid.get(i);
                    stmt = c.createStatement(); //prepare the sql query
                    rs = stmt.executeQuery( getUserCompletedTasksQuery ); //execute query
                    while(rs.next()) //fetch rows
                    {
                        partialprioritytasks.add(rs.getString(username));
                    }
                }
                
                completeprioritytasks2.add(partialprioritytasks);
            }
 
        }catch(Exception e){
            System.out.println("Completed tasks exception\n");
        }
        
        
        return completeprioritytasks2;
    }
}
