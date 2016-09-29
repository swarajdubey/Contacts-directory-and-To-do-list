
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
public class SearchByName extends SQLiteDatabaseConnection{
    
    public void setparentObject(SQLiteDatabaseConnection parentobject)
    {
        pObj=null;
        pObj=parentobject; //object that contains database connection values
    }
    
    public ArrayList<ArrayList<String>> SearchContactByName(String username,String temp)
    {
        //clear data structures
        contactNames.clear();
        searchByNameContacts.clear();
        //allContacts.clear();
        try{
            //search for typed name in the database
            String searchBynameQUery="SELECT "+username+" FROM directory_names WHERE "+username+" LIKE '"+temp+"%'";
            System.out.println("Query is: "+searchBynameQUery+"\n");
            pObj.stmt = pObj.c.createStatement(); //prepare query
            pObj.rs = pObj.stmt.executeQuery( searchBynameQUery ); //executte query
            while(pObj.rs.next())//fetch rows
            {
                contactNames.add(pObj.rs.getString(username));
            }
            ArrayList<Integer> ids= new ArrayList<Integer>();
            for(int i=0;i<contactNames.size();i++)
            {
                try{
                    //select ID of the matches found
                    String IDQuery="SELECT ID FROM directory_names WHERE "+username+"='"+contactNames.get(i)+"'";
                    pObj.stmt = pObj.c.createStatement();//prepare query
                    pObj.rs = pObj.stmt.executeQuery( IDQuery ); //execute query
                    while(pObj.rs.next()) //fetch rows
                    {
                        ids.add(pObj.rs.getInt("ID"));
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
                    pObj.stmt = pObj.c.createStatement(); //prepare statement
                    pObj.rs = pObj.stmt.executeQuery( getcontactDataQUery ); //execute query
                    while(pObj.rs.next()) //fetch rows
                    {
                        partialContacts.add(pObj.rs.getString(username)); //stpre phone numbers
                    }
                    
                    //get email contacts
                    getcontactDataQUery="SELECT "+username+" FROM directory_emails WHERE ID="+ids.get(i);
                    pObj.stmt = pObj.c.createStatement(); //prepare statement
                    pObj.rs = pObj.stmt.executeQuery( getcontactDataQUery ); //execute query
                    while(pObj.rs.next())
                    {
                        partialContacts.add(pObj.rs.getString(username)); //store email contacts
                    }
                    searchByNameContacts.add(partialContacts);
                }catch(Exception e){
                    System.out.println("email , phone number exception exception\n");
                }
                
            }
            
        }catch(Exception e){
            System.out.println("Search by name excetpion founddd\n");
        }
        return searchByNameContacts;
    }
}
