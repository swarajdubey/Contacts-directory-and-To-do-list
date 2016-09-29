
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
public class ContactsUpdate extends SQLiteDatabaseConnection{
    
    private ArrayList<String> tableNames = new ArrayList<String>();
    private ArrayList<String> info = new ArrayList<String>();
    
    public void setparentObject(SQLiteDatabaseConnection parentobject)
    {
        pObj=null;
        pObj=parentobject; //store the sql database connection object
    }
    
    public void updateContacts(String username,String name_of_task,String contact_number,String email_contact)
    {
        //clear the arraylist to prevent data from being appended
        tableNames.clear();
        info.clear();
        
        //add table names
        tableNames.add("directory_names");
        tableNames.add("directory_phone_numbers");
        tableNames.add("directory_emails");

        info.add(name_of_task);
        info.add(contact_number);
        info.add(email_contact);
        
        
        try{
            
            for(int i=0;i<tableNames.size();i++)//iterate through table names 
            {
                //insert updates into the database
                String updateContactsQuery="INSERT INTO "+tableNames.get(i)+"("+username+") VALUES('"+info.get(i)+"')";
                
                PreparedStatement ps = pObj.c.prepareStatement(updateContactsQuery);
                ps.execute();
                
                //pObj.stmt = pObj.c.createStatement(); //prepare sql statement
                //pObj.stmt.executeUpdate( updateContactsQuery ); //execute query
                //pObj.c.commit(); //to save the changes
            }
            //System.out.println("contacts updated \n");
        }catch(Exception e){
            System.out.println("Contacts update query exception\n");
        }
        
        //clear the data structures!!
        tableNames.clear();
        info.clear();
        
        return;
    }
    
}
