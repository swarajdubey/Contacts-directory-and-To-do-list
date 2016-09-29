
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author swaraj
 */
public class PriorityTasks extends SQLiteDatabaseConnection{
    
    public void setparentObject(SQLiteDatabaseConnection parentobject)
    {
        pObj=null;
        pObj=parentobject; //object that contains database connection values
    }
    
    public ArrayList<ArrayList<String>> getPrioritiesOfUserTasks(String username)
    {
        //clear the arraylists before inserting and this will lead to incorrect data
        //ArrayList<Integer> prioritynumbers=new ArrayList<Integer>();
        prioritynumbers.clear();
        //ArrayList<ArrayList<String>> completeprioritytasks = new ArrayList<ArrayList<String>>();
        completeprioritytasks.clear();
        //ArrayList<Integer> prioritynumbersID=new ArrayList<Integer>();
        prioritynumbersID.clear();
        
        try{
           //select priority values from the database
            String pirorityQuery="SELECT "+username+" FROM user_task_priorities WHERE "+username+" <> 'null' ORDER BY ID DESC";
            pObj.stmt = pObj.c.createStatement(); //prepare statement
            pObj.rs = pObj.stmt.executeQuery( pirorityQuery ); //execute the query
            while(pObj.rs.next()) //fetch the rows from the database
            {
                if(pObj.rs.getInt(username)>0)
                {
                    prioritynumbers.add(pObj.rs.getInt(username)); //add the priority values
                }
            }
        }catch(Exception e){
            System.out.println("priorities query exception \n");
        }
        
        
        //sort the arraylist here
        
        //-------Remove duplicate priority values--------------
        HashSet hs = new HashSet();
        hs.addAll(prioritynumbers);
        prioritynumbers.clear();
        prioritynumbers.addAll(hs);
        
        Comparator comparator = Collections.reverseOrder();
        Collections.sort(prioritynumbers,comparator); //sorts the arraylist in descending order

        
        for(int i=0;i<prioritynumbers.size();i++)
        {
            //System.out.println("priority numbers are: "+prioritynumbers.get(i)+"\n");
        }
        
        //-----------search for the respective ID's of the priority tasks----------------
        for(int i=0;i<prioritynumbers.size();i++)
        {
            try{
                String getPriorityIDQuery="SELECT ID FROM user_task_priorities WHERE "+username+"="+prioritynumbers.get(i);
                pObj.stmt = pObj.c.createStatement();
                pObj.rs = pObj.stmt.executeQuery( getPriorityIDQuery );
                while(pObj.rs.next())
                {
                    prioritynumbersID.add(pObj.rs.getInt("ID"));
                }
            }catch(Exception e){
                System.out.println("Priority numbers id selection exception \n");
            }
        }
        
        
        
        for(int i=0;i<prioritynumbersID.size();i++)
        {
            //System.out.println("priority numbers ID  are: "+prioritynumbersID.get(i)+"\n");
        }
        //----------SELECT tasks by decreasing priority values(i.e from highest to lowest priority)
        
        for(int i=0;i<prioritynumbersID.size();i++)
        {
            try{
                ArrayList<String> partialprioritytasks=new ArrayList<String>();
                String taskInfoQuery="SELECT "+username+" FROM user_tasks WHERE ID="+prioritynumbersID.get(i); //selects the task name
                pObj.stmt = pObj.c.createStatement();
                pObj.rs = pObj.stmt.executeQuery( taskInfoQuery );
                while(pObj.rs.next())
                {
                    partialprioritytasks.add(pObj.rs.getString(username));
                }
                
                taskInfoQuery="SELECT "+username+" FROM user_task_priorities WHERE ID="+prioritynumbersID.get(i);
                pObj.stmt = pObj.c.createStatement();
                pObj.rs = pObj.stmt.executeQuery( taskInfoQuery );
                while(pObj.rs.next())
                {
                    partialprioritytasks.add(pObj.rs.getString(username)+"");
                }
                
                taskInfoQuery="SELECT "+username+" FROM user_task_dates WHERE ID="+prioritynumbersID.get(i);
                pObj.stmt = pObj.c.createStatement();
                pObj.rs = pObj.stmt.executeQuery( taskInfoQuery );
                while(pObj.rs.next())
                {
                    partialprioritytasks.add(pObj.rs.getString(username));
                }
                
                taskInfoQuery="SELECT "+username+" FROM user_task_time WHERE ID="+prioritynumbersID.get(i);
                pObj.stmt = pObj.c.createStatement();
                pObj.rs = pObj.stmt.executeQuery( taskInfoQuery );
                while(pObj.rs.next())
                {
                    partialprioritytasks.add(pObj.rs.getString(username));
                }
                
                taskInfoQuery="SELECT "+username+" FROM user_task_status WHERE ID="+prioritynumbersID.get(i);
                pObj.stmt = pObj.c.createStatement();
                pObj.rs = pObj.stmt.executeQuery( taskInfoQuery );
                while(pObj.rs.next())
                {
                    partialprioritytasks.add(pObj.rs.getString(username));
                }

                completeprioritytasks.add(partialprioritytasks);

            }catch(Exception e){
                System.out.println("Error receiving priority tasks\n");
            }
        }
        
        return completeprioritytasks;
    }
    
}
