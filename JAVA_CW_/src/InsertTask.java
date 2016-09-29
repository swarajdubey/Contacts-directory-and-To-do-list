
import java.sql.PreparedStatement;
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
public class InsertTask extends SQLiteDatabaseConnection{
    
    ArrayList<String> tableNames = new ArrayList<String>();
    ArrayList<String> info = new ArrayList<String>();
    
    public void setparentObject(SQLiteDatabaseConnection parentobject)
    {
        pObj=null;
        pObj=parentobject; //object that contains database connection values
    }
    
    public void insertUpdatesIntoDatabases(String task_name,String task_due_date,String task_due_time,int task_priority_chosen,String username)//this function adds the updates into the sqlite database
    {
        
        //clear the arraylists right at the beginning
        tableNames.clear();
        info.clear();
        
        //add table names into the arraylist
        tableNames.add("user_tasks");
        tableNames.add("user_task_dates");
        tableNames.add("user_task_time");
        tableNames.add("user_task_status");
        
        info.add(task_name);
        info.add(task_due_date);
        info.add(task_due_time);
        info.add("not done");
        
        try{
            
            for(int i=0;i<tableNames.size();i++)
            {
                //insert task information into the database
                String insert_user_task_Query="INSERT INTO "+tableNames.get(i)+"("+username+") VALUES('"+info.get(i)+"')";
                
                PreparedStatement ps = pObj.c.prepareStatement(insert_user_task_Query);
                ps.execute();
            
            }
            
            //a separate query for inserting the task status as 'not done'
            String insert_user_task_Query="INSERT INTO user_task_priorities("+username+") VALUES("+task_priority_chosen+")";
            
            PreparedStatement ps = pObj.c.prepareStatement(insert_user_task_Query);
            ps.execute();
            
           
        }catch(Exception e){
            System.out.println("inserting tasks exception with the folowwing data \n");
            System.out.println("user name: "+username+"\n");
            System.out.println("task name: "+task_name+"\n");
            System.out.println("task due date: "+task_due_date+"\n");
            System.out.println("task due time: "+task_due_time+"\n");
            System.out.println("task priority: "+task_priority_chosen+"\n");
           
            
            //System.out.println(username+" "+task_name+" "+task_due_date+" "+task_due_time+" "+task_priority_chosen+" \n");
        }
        
        tableNames.clear();
        info.clear();
        
        return;
    }
}
