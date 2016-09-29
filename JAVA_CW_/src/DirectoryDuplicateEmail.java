/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author swaraj
 */
public class DirectoryDuplicateEmail extends SQLiteDatabaseConnection{
    
    public void setparentObject(SQLiteDatabaseConnection parentobject)
    {
        pObj=null;
        pObj=parentobject; //object that contains database connection values
    }
    
    //function to check if email entered is pre existing
    public boolean checkdirectoryDuplicateEmail(String email,String username)
    {
        try{
            String checkDuplicateQuery="SELECT "+username+" FROM directory_emails WHERE "+username+" <> 'null'"; //select all emails
            pObj.stmt = pObj.c.createStatement(); //prepare statement
            pObj.rs = pObj.stmt.executeQuery( checkDuplicateQuery ); //execute query
            while(pObj.rs.next()) //fetch all rows
            {
                if(email.contentEquals(pObj.rs.getString(username))) //duplicate contact email found
                {
                    //System.out.println("duplicated foun hereeeeeeeeeeeeeeeeeeeeeeeeeeeee\n");
                    return false; //returning a false means a duplicate has been found
                }
            }
        }catch(Exception e){
            System.out.println("duplicate contact email exception \n");
        }
        return true; //program will reach here on if duplicate has not been found
    }
}
