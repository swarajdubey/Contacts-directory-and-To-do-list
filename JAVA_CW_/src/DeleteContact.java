
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
public class DeleteContact extends SQLiteDatabaseConnection{
 
    
    private ArrayList<String> tableNames = new ArrayList<String>();
    
    
    public void setparentObject(SQLiteDatabaseConnection parentobject)
    {
        pObj=null;
        pObj=parentobject; //object that contains database connection values
    }
    
    public void deleteSelectedContact(String email,String username)
    {
        //clear the arraylist to prevent data from being appended
        tableNames.clear();
        
        //names of tables in the central database
        tableNames.add("directory_names");
        tableNames.add("directory_phone_numbers");
        tableNames.add("directory_emails");
        
        try{
            int temp_id=0;
            String getIDOfDeletedContactQuery="SELECT ID FROM directory_emails WHERE "+username+"='"+email+"'"; //get the id 
            pObj.stmt = pObj.c.createStatement(); //prepare statement before execution 
            pObj.rs = pObj.stmt.executeQuery( getIDOfDeletedContactQuery ); //execute query
            while(pObj.rs.next())
            {
               temp_id=pObj.rs.getInt("ID"); //gets the id of the contact to be deleted
            }
            
            for(int i=0;i<tableNames.size();i++)
            {
                String deleteContactQuery="DELETE FROM "+tableNames.get(i)+" WHERE ID="+temp_id; //delete the name of the contact
                
                PreparedStatement ps = pObj.c.prepareStatement(deleteContactQuery);
                ps.execute();
                
                //pObj.stmt = pObj.c.createStatement();
                //pObj.stmt.executeUpdate( deleteContactQuery );
                //pObj.c.commit(); //to save the changes
            }

        }catch(Exception e){
            System.out.println("Error deleting the email contact\n");
        }
        
        tableNames.clear(); //clear the arraylist to free up the memory
        
        return; //terminate the function here
    }
}
