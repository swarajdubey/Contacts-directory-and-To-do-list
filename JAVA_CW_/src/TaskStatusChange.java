
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
public class TaskStatusChange extends SQLiteDatabaseConnection{
    
    public void setparentObject(SQLiteDatabaseConnection parentobject)
    {
        pObj=null;
        pObj=parentobject; //object that contains database connection values
    }
    
    //function to change the status of the task
    public void changeTaskStatus(String username,String response,String uniqueTask)
    {
        try{
            int temp_id=0;
            String changeTaskStatusQuery="SELECT ID FROM user_tasks WHERE "+username+"='"+uniqueTask+"'";
            pObj.stmt = pObj.c.createStatement();//prepare statement
            pObj.rs = pObj.stmt.executeQuery( changeTaskStatusQuery ); //execute query
            while(pObj.rs.next())
            {
                temp_id=pObj.rs.getInt("ID");
            }
            //change the status of the task query
            changeTaskStatusQuery="UPDATE user_task_status SET "+username+"='Done' WHERE ID is "+temp_id;
            PreparedStatement ps = pObj.c.prepareStatement(changeTaskStatusQuery);
            ps.execute();

            
        }catch(Exception e){
            System.out.println("Change task status exception\n");
        }
        return;
    }
}
