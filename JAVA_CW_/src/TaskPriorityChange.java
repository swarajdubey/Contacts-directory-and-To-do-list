
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
public class TaskPriorityChange extends SQLiteDatabaseConnection{
    
    public void setparentObject(SQLiteDatabaseConnection parentobject)
    {
        pObj=null;
        pObj=parentobject; //object that contains database connection values
    }
    
    //function to change the priority
    public void changePriority(String username,int change_task_priority,String uniqueTask)
    {
        try{
            int temp_id=0;
            String changePriorityQuery="SELECT ID FROM user_tasks WHERE "+username+"='"+uniqueTask+"'";
            pObj.stmt = pObj.c.createStatement();//prepare query 
            pObj.rs = pObj.stmt.executeQuery( changePriorityQuery ); //execute query
            while(pObj.rs.next())//fetch rows
            {
                temp_id=pObj.rs.getInt("ID");
            }
            
            //priority change query
            changePriorityQuery="UPDATE user_task_priorities SET "+username+"="+change_task_priority+" WHERE ID is "+temp_id;
            System.out.println("priority query is: "+changePriorityQuery+"\n");
            
            PreparedStatement ps = pObj.c.prepareStatement(changePriorityQuery);
            ps.execute();

            
        }catch(Exception e){
            System.out.println("cannot change priority\n");
        }
        
        return;
    }
}
