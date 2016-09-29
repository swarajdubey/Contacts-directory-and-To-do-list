
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
public class DeleteTask extends SQLiteDatabaseConnection{
    
    private ArrayList<String> taskTables = new ArrayList<String>();
    
    public void setparentObject(SQLiteDatabaseConnection parentobject)
    {
        pObj=null;
        pObj=parentobject; //object that contains database connection values
    }
 
    public void deleteTask(String username,String uniqueTask)
    {
        //add the table names into the arraylist
        taskTables.clear();
        taskTables.add("user_tasks");
        taskTables.add("user_task_priorities");
        taskTables.add("user_task_dates");
        taskTables.add("user_task_time");
        taskTables.add("user_task_status");
        
        try{
            int temp_id=0;
            String deleteTaskQuery="SELECT ID FROM user_tasks WHERE "+username+"='"+uniqueTask+"'";
            
            //System.out.println("username is: "+username+" and task is: "+uniqueTask+"\n");
            pObj.stmt = pObj.c.createStatement(); //prepare statement
            pObj.rs = pObj.stmt.executeQuery( deleteTaskQuery ); //execute query
            while(pObj.rs.next()) //fetch rows
            {
                temp_id=pObj.rs.getInt("ID"); //store the ID's
                System.out.println("id to be deleted is: "+temp_id+"\n");
            }
            
            for(int i=0;i<taskTables.size();i++)
            {
                //delete record from each table
                deleteTaskQuery="DELETE FROM "+taskTables.get(i)+" WHERE ID is "+temp_id;
                System.out.println("query is: "+deleteTaskQuery);
                
                PreparedStatement ps = pObj.c.prepareStatement(deleteTaskQuery);
                ps.execute();
            
                //pObj.stmt = pObj.c.createStatement(); //prepare statement
                //pObj.stmt.executeUpdate( deleteTaskQuery );//execute query
                //pObj.c.commit(); //to save the changes
            }
            
        }catch(Exception e){
            System.out.println("Exception while deleting task\n");
        }
        
        taskTables.clear(); //clear the data structure!
        return;
    }
    
}
