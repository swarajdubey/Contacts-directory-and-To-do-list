
import java.util.ArrayList;
import java.util.Collections;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author swaraj
 */
public class SortNames extends SQLiteDatabaseConnection{
    
    public void setparentObject(SQLiteDatabaseConnection parentobject)
    {
        pObj=null;
        pObj=parentobject;
    }
    
    public ArrayList<ArrayList<String>> sortContactsByNames(String username)
    {
        contactNames.clear();
        //allContacts.clear();
        sortedfinalContacts.clear();
        //allContacts.add("fewew");
        try{
            //allContacts.add("fewew");
            String sortNamesQuery="SELECT "+username+" FROM directory_names WHERE "+username+" <> 'null'";
            pObj.stmt = pObj.c.createStatement();
            pObj.rs = pObj.stmt.executeQuery( sortNamesQuery );
            while(pObj.rs.next())
            {
                contactNames.add(pObj.rs.getString(username));
            }
            
            //sort the names here 
            Collections.sort(contactNames); //sort the name here
            //--------NAMES are being sorted properly (no problem)
            
            
            ArrayList<Integer> ids= new ArrayList<Integer>();
            for(int i=0;i<contactNames.size();i++)
            {
                try{
                    String IDQuery="SELECT ID FROM directory_names WHERE "+username+"='"+contactNames.get(i)+"'";
                    pObj.stmt = pObj.c.createStatement();
                    pObj.rs = pObj.stmt.executeQuery( IDQuery );
                    while(pObj.rs.next())
                    {
                        ids.add(pObj.rs.getInt("ID"));
                    }
                }catch(Exception e){
                    System.out.println("contacts directory id select exception \n");
                }
            }
            //all id's should be sorted at this point
            
            
            for(int i=0;i<contactNames.size();i++)
            {
                ArrayList<String> partialContacts= new ArrayList<String>();
                try{
                    partialContacts.add(contactNames.get(i));
                    String getcontactDataQUery="SELECT "+username+" FROM directory_phone_numbers WHERE ID="+ids.get(i);
                    pObj.stmt = pObj.c.createStatement();
                    pObj.rs = pObj.stmt.executeQuery( getcontactDataQUery );
                    while(pObj.rs.next())
                    {
                        partialContacts.add(pObj.rs.getString(username));
                    }
                    
                    getcontactDataQUery="SELECT "+username+" FROM directory_emails WHERE ID="+ids.get(i);
                    pObj.stmt = pObj.c.createStatement();
                    pObj.rs = pObj.stmt.executeQuery( getcontactDataQUery );
                    while(pObj.rs.next())
                    {
                        partialContacts.add(pObj.rs.getString(username));
                    }
                    sortedfinalContacts.add(partialContacts);
                }catch(Exception e){
                    System.out.println("email , phone number exception exception\n");
                }
                
            }
            
        }catch(Exception e){
            System.out.println("Sort names exception\n");
        }
        

        return sortedfinalContacts;
    }
    
}
