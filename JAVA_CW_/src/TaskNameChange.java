
import java.sql.PreparedStatement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author swaraj
 */
public class TaskNameChange extends SQLiteDatabaseConnection{
    
    public void setparentObject(SQLiteDatabaseConnection parentobject)
    {
        pObj=null;
        pObj=parentobject; //object that contains database connection values
    }
    
    public void changeTaskName(String username,String response,String uniqueTask)
    {
        try{
            int temp_id=0;
            String selectIdOfTask="SELECT ID FROM user_tasks WHERE "+username+"='"+uniqueTask+"'";
            pObj.stmt = pObj.c.createStatement();//prepare statement 
            pObj.rs = pObj.stmt.executeQuery( selectIdOfTask ); //execute query
            while(pObj.rs.next()) //fetch rows
            {
                temp_id=pObj.rs.getInt("ID"); //this gets tht ID of the task whose name needs to be changed
                System.out.println("temp_id is: "+temp_id+"\n");
            }
                
            //changing the task name query
            String changeTaskNameQuery="UPDATE user_tasks SET "+username+"='"+response+"' WHERE ID is "+temp_id;
            System.out.println("query is: "+changeTaskNameQuery+"\n");
            
            PreparedStatement ps = pObj.c.prepareStatement(changeTaskNameQuery);
            ps.execute();
            
            
        }catch(Exception e){
            System.out.println("Cannot change task name \n");
        }
    }
    
}
