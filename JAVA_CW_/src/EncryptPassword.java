
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
public class EncryptPassword {
    
    private int keyword=24315;
    private ArrayList<Integer> numbers=new ArrayList<Integer>();
    private String encrypted_passcode,userpassword;
    private int max;
    private ArrayList<ArrayList<String>> matrix=new ArrayList<ArrayList<String>>();
    
    
    public EncryptPassword(String password)
    {
        //initialize the variables here
        encrypted_passcode="";
        userpassword=password;
    }
    
    public void findMaxNumber()
    {
        String stringnum=Integer.toString(keyword);
        for(int i=0;i<stringnum.length();i++)
        {
            int y=Integer.parseInt(""+stringnum.charAt(i));
            numbers.add(y);
        }
        Collections.sort(numbers);
        max=Collections.max(numbers);
    }
    
    public String encodePassword()
    {
        int k=0;
        for(int i=0;i<userpassword.length();i++)
        {
            ArrayList<String> rowMatrix=new ArrayList<String>();
            for(int j=i;j<i+max;j++)
            {
                if(j<userpassword.length()){rowMatrix.add(""+userpassword.charAt(j));}
                else{rowMatrix.add("#");}
                
                k=j;
            }
            matrix.add(rowMatrix);
            i=k;
        }
      
        for(int i=0;i<matrix.size();i++)
        {
            System.out.println(""+matrix.get(i));
        }
        
        for(int i=0;i<numbers.size();i++)
        {
            for(int j=0;j<matrix.size();j++)
            {
                ArrayList<String> rowMatrix=new ArrayList<String>();
                rowMatrix=matrix.get(j);
                encrypted_passcode=encrypted_passcode+rowMatrix.get(i)+"";
            }
        }
        System.out.println("encoded pass is: "+encrypted_passcode);
        return encrypted_passcode;
    }
}
