
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
public class TaskDueDateChange extends SQLiteDatabaseConnection{
    
    public void setparentObject(SQLiteDatabaseConnection parentobject)
    {
        pObj=null;
        pObj=parentobject; //object that contains database connection values
    }
    
    public void changeDueDate(String username,String task_modify_due_date,String uniqueTask)
    {
        try{
            int temp_id=0;
            String changeDueDateQuery="SELECT ID FROM user_tasks WHERE "+username+"='"+uniqueTask+"'";
            pObj.stmt = pObj.c.createStatement(); //prepare query 
            pObj.rs = pObj.stmt.executeQuery( changeDueDateQuery ); //execute query
            while(pObj.rs.next())//fetch rows
            {
                temp_id=pObj.rs.getInt("ID");
            }
            
            //update the due date
            changeDueDateQuery="";
            changeDueDateQuery="UPDATE user_task_dates SET "+username+"='"+task_modify_due_date+"' WHERE ID is "+temp_id;
            
            PreparedStatement ps = pObj.c.prepareStatement(changeDueDateQuery);
            ps.execute();
            
            
        }catch(Exception e){
            System.out.println("Change due date exception\n");
        }
    }
}
