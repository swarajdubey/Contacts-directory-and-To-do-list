
import java.sql.DriverManager;
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
public class EditPhoneContact extends SQLiteDatabaseConnection{
    
    public void setparentObject(SQLiteDatabaseConnection parentobject)
    {
        pObj=null;
        pObj=parentobject; //object that contains database connection values
    }
   
    //function to edit the phone number of the contact chosen
    public void editPhoneOfContact(String username, String uniqueEmail, String response) {
        
        //System.out.println("new class runs");
        try{
            int temp_id=0;
            String editmailQuery="SELECT ID FROM directory_emails WHERE "+username+"='"+uniqueEmail+"'";
            pObj.stmt = pObj.c.createStatement();// prepare statement
            pObj.rs = pObj.stmt.executeQuery( editmailQuery ); //execute query
            while(pObj.rs.next()) //fetch all rows
            {
                temp_id=pObj.rs.getInt("ID");
            }
            
            //edit phone contact query 
            String editNameOfQuery="UPDATE directory_phone_numbers SET "+username+"='"+response+"' WHERE ID="+temp_id;
            PreparedStatement ps = pObj.c.prepareStatement(editNameOfQuery);
            ps.execute();
            //pObj.stmt = pObj.c.createStatement(); //prepare statement
            //pObj.stmt.executeUpdate( editNameOfQuery ); //execute query
            //pObj.c.commit(); //to save the changes
            
        }catch(Exception e){
            System.out.println("edit phone contact query\n");
        }
    }
 
}
