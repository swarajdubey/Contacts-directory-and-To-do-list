
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
public class RetrieveContacts extends SQLiteDatabaseConnection{
    
    public void setparentObject(SQLiteDatabaseConnection parentobject)
    {
        pObj=null;
        pObj=parentobject; //object that contains database connection values
    }
    
    public ArrayList<ArrayList<String>> getContacts(String username)
    {
        //clear all the araylists before operations
        contactNames.clear();
        //allContacts.clear();
        finalContacts.clear();
        
        try{
            String sortNamesQuery="SELECT "+username+" FROM directory_names WHERE "+username+" <> 'null'";
            pObj.stmt = pObj.c.createStatement(); //prepare statement
            pObj.rs = pObj.stmt.executeQuery( sortNamesQuery ); //execute query
            while(pObj.rs.next())//read all rows
            {
                contactNames.add(pObj.rs.getString(username)); //add contact names
            }
            
            ArrayList<Integer> ids= new ArrayList<Integer>();
            for(int i=0;i<contactNames.size();i++)
            {
                try{
                    String IDQuery="SELECT ID FROM directory_names WHERE "+username+"='"+contactNames.get(i)+"'";
                    pObj.stmt = pObj.c.createStatement(); //prepare statement
                    pObj.rs = pObj.stmt.executeQuery( IDQuery );//execute query 
                    while(pObj.rs.next())//fetch rows
                    {
                        ids.add(pObj.rs.getInt("ID")); //get id of contact selected
                    }
                }catch(Exception e){
                    System.out.println("contacts directory id select exception \n");
                }
            }
            
            
            for(int i=0;i<contactNames.size();i++)
            {
                ArrayList<String> partialContacts= new ArrayList<String>();
                try{
                    partialContacts.add(contactNames.get(i));
                    
                    String getcontactDataQUery="SELECT "+username+" FROM directory_phone_numbers WHERE ID="+ids.get(i);
                    pObj.stmt = pObj.c.createStatement(); //prepare query
                    pObj.rs = pObj.stmt.executeQuery( getcontactDataQUery ); //execute query
                    while(pObj.rs.next())
                    {
                        partialContacts.add(pObj.rs.getString(username));
                    }
                    
                    getcontactDataQUery="SELECT "+username+" FROM directory_emails WHERE ID="+ids.get(i);
                    pObj.stmt = pObj.c.createStatement(); //prepare query
                    pObj.rs = pObj.stmt.executeQuery( getcontactDataQUery ); //execute query
                    while(pObj.rs.next()) //fetch rows
                    {
                        partialContacts.add(pObj.rs.getString(username));
                    }
                    //System.out.println(temp_contacts_string+"\n");
                    finalContacts.add(partialContacts);
                }catch(Exception e){
                    System.out.println("email , phone number exception exception\n");
                }
                
            }
            
        }catch(Exception e){
            
        }
        return finalContacts; //return the contacts
    }
}
