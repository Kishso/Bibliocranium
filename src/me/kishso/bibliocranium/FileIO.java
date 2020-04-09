package me.kishso.bibliocranium;

import java.io.*;
import java.util.Scanner;

public class FileIO {

    public static void loadHeadDataBase(){
        try{
            Scanner in = new Scanner(new File("headDatabase.txt"));
            while(in.hasNextLine()){
                CustomSkull.knownHeads.add(in.nextLine());
            }
        }catch(FileNotFoundException e){
            System.out.println("File does not exist.");
        }
    }

    public static void saveHeadDataBase(){
        try{
            FileWriter fileWriter = new FileWriter("headDatabase.txt",true);
            PrintWriter out = new PrintWriter(fileWriter);
            for(String s : CustomSkull.knownHeads){
                out.println(s);
            }
            out.close();
            fileWriter.close();
        }catch(IOException e){
            System.out.print("IOException Occured");
        }
    }

    public static void main(String[] args){
        CustomSkull.knownHeads.add("Test");
        CustomSkull.knownHeads.add("Test2");
        CustomSkull.knownHeads.add("Test3");
        FileIO.saveHeadDataBase();

        FileIO.loadHeadDataBase();
        for(String s : CustomSkull.knownHeads){
            System.out.println(s);
        }
    }

}
