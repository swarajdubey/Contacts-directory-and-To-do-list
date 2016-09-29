/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author swaraj
 */
public class DuplicatePhoneNumber extends SQLiteDatabaseConnection{
    
    public void setparentObject(SQLiteDatabaseConnection parentobject)
    {
        pObj=null;
        pObj=parentobject; //object that contains database connection values
    }
    
    //function to check if a duplicate number exists
    public boolean checkDuplicatePhoneNumber(String username,String contact_number)
    {
        try{
            String checkDuplicatePhoneNumber="SELECT "+username+" FROM directory_phone_numbers WHERE "+username+" <> 'null'"; //select all phone numbers
            pObj.stmt = pObj.c.createStatement(); //prerpare statement
            pObj.rs = pObj.stmt.executeQuery( checkDuplicatePhoneNumber ); //execute query
            while(pObj.rs.next()) //fetch rows
            {
                if(contact_number.contentEquals(pObj.rs.getString(username))) //duplicate phone contact found here
                {
                    return false;//returning a false means a duplicate number has been found
                }
            }
        }catch(Exception e){
            System.out.println(" check duplicate number exception \n");
        }
        
        return true; //reurning a true means to duplicate
    }
}
