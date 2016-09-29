
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
public class EditNameContact extends SQLiteDatabaseConnection{
    
    public void setparentObject(SQLiteDatabaseConnection parentobject)
    {
        pObj=null;
        pObj=parentobject; //object that contains database connection values
    }
    
    //function to edit the name of the contact selected
    public void editNameOfContact(String username,String uniqueEmail,String response)
    {
        try{
            int temp_id=0;
            String getIDOfNameContactQuery="SELECT ID FROM directory_emails WHERE "+username+"='"+uniqueEmail+"'";
            pObj.stmt = pObj.c.createStatement(); //prepare statement
            pObj.rs = pObj.stmt.executeQuery( getIDOfNameContactQuery ); //execute query
            while(pObj.rs.next()) //fetch rows
            {
                temp_id=pObj.rs.getInt("ID");
            }
            
            String editNameOfQuery="UPDATE directory_names SET "+username+"='"+response+"' WHERE ID="+temp_id;
            PreparedStatement ps = pObj.c.prepareStatement(editNameOfQuery);
            ps.execute();
            
            //pObj.stmt = pObj.c.createStatement();
            //pObj.stmt.executeUpdate( editNameOfQuery );
            //pObj.c.commit(); //to save the changes
            
        }catch(Exception e){
            System.out.println("editing name exception");
        }
    }
}
