/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author swaraj
 */
public class DuplicateTaskCheck extends SQLiteDatabaseConnection{
    
    public void setparentObject(SQLiteDatabaseConnection parentobject)
    {
        pObj=null;
        pObj=parentobject;
    }
    
    public boolean checkForDuplicateTask(String username, String task_name)
    {
        try{
            String checkForDuplicateTask="SELECT "+username+" FROM user_tasks WHERE "+username+" <> 'null'";
            pObj.stmt = pObj.c.createStatement();
            pObj.rs = pObj.stmt.executeQuery( checkForDuplicateTask );
            
            System.out.println("task name here is: "+task_name+" and user name is: "+username+"\n");
            while(pObj.rs.next())
            {
                System.out.println("tasks obtained are: "+pObj.rs.getString(username)+"\n");
                if(task_name.contentEquals(pObj.rs.getString(username)))//duplicate task found here
                {
                    System.out.println("duplicate found\n");
                    return false; 
                }
            }
        }catch(Exception e){
            System.out.println("");
        }
        return true; //this means no duplicate has been found
    }
}
