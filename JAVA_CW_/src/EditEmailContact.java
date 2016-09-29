
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
public class EditEmailContact extends SQLiteDatabaseConnection {
    
    public void setparentObject(SQLiteDatabaseConnection parentobject)
    {
        pObj=null;
        pObj=parentobject; //object that contains database connection values
    }
    
    public void editEmailOfContact(String username,String uniqueEmail,String response)
    {
        try{
            int temp_id=0;
            String editmailQuery="SELECT ID FROM directory_emails WHERE "+username+"='"+uniqueEmail+"'"; //fetch ID of the email of the contact to be edited
            pObj.stmt = pObj.c.createStatement(); //prerpare statement
            pObj.rs = pObj.stmt.executeQuery( editmailQuery ); //execute query
            while(pObj.rs.next()) //fetch rows
            {
                temp_id=pObj.rs.getInt("ID"); //store the ID if the email is found
            }
            
            //edit email contact
            String editNameOfQuery="UPDATE directory_emails SET "+username+"='"+response+"' WHERE ID="+temp_id;
            
            PreparedStatement ps = pObj.c.prepareStatement(editNameOfQuery);
            ps.execute();
            
            //pObj.stmt = pObj.c.createStatement(); //prepare statement
            //pObj.stmt.executeUpdate( editNameOfQuery ); //execute query
            //pObj.c.commit(); //to save the changes
            
        }catch(Exception e){
            System.out.println("edit email contact query");
        }
    }
    
}
