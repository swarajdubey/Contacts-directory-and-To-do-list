
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
public class TaskDueTimeChange extends SQLiteDatabaseConnection{
    
    public void setparentObject(SQLiteDatabaseConnection parentobject)
    {
        pObj=null;
        pObj=parentobject; //object that contains database connection values
    }
    
    public void changeDueTime(String username,String task_modify_due_time,String uniqueTask)
    {
        try{
            int temp_id=0;
            String changeDueTime="SELECT ID FROM user_tasks WHERE "+username+"='"+uniqueTask+"'";
            pObj.stmt = pObj.c.createStatement();//prepare statement
            pObj.rs = pObj.stmt.executeQuery( changeDueTime );//execute query
            while(pObj.rs.next())//fetch rows
            {
                temp_id=pObj.rs.getInt("ID");
                //System.out.println("id of task to be changed: "+temp_id+"\n");
            }
            
            //update the new due time
            changeDueTime="";
            changeDueTime="UPDATE user_task_time SET "+username+"='"+task_modify_due_time+"' WHERE ID is "+temp_id;
            
            PreparedStatement ps = pObj.c.prepareStatement(changeDueTime);
            ps.execute();
            
            
        }catch(Exception e){
            System.out.println("Exception changing time \n");
        }
    }
    
}
