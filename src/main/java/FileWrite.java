
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


public class FileWrite {
    
    /**
     * Write data to fileName file either overriding or appending
     * @param fileName      The name of the file to write to
     * @param data          The data to be written
     * @param appendData    True = append, False = overwrite
     */
    public void writeContentsToFile(String fileName, String data, boolean appendData)
    {
         try {
            PrintWriter writer = new PrintWriter(new FileWriter(fileName, appendData));
            
            writer.println(data);
            
            writer.close();
            
        } catch(IOException e)
        {
            System.out.println("" + e);
        }           
    }
    
    /**
     * Write each string in list to file, each token on a new line
     * @param fileName      File name
     * @param contents      Array list contents
     * @param appendData    False = override data, True = append data
     */
    public void writeContentsToFile(String fileName, ArrayList<String> contents, boolean appendData)
    {
         try {
            PrintWriter writer = new PrintWriter(new FileWriter(fileName, appendData));
            
            for(String data: contents)
            {
                writer.println(data);
            }
            
            writer.close();
            
        } catch(IOException e)
        {
            System.out.println("" + e);
        }          
    }
    
    
    /**
     * Writes the numbers 1 to 100 to the specified file
     * @param fileName
     * @param appendData 
     */
    public void writeNumbers(String fileName, boolean appendData)
    {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(fileName, appendData));

            int num = 1; // variable to hold current number to print
            
            // Print all complete rows to file
            for(int i = 0; i < 10; i++)
            {
                for(int j = 0; j < 10; j++) // Loop through content inside a row
                {
                    writer.print(num + "\t"); // Print each number with an indent in between
                    num++;
                }
                writer.println(); // Create next line
            }

            writer.close(); // Close writer to end session

            } catch(IOException e)
            {
                System.out.println(e);
            }
                
    }
    
    /**
     * Writes the numbers 1 to 100 separated by your custom delimiter
     * @param fileName
     * @param appendData
     * @param delimiter 
     */
    public void writeNumbersCustom(String fileName, boolean appendData, String delimiter)
    {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(fileName, appendData));

            int num = 1; // variable to hold current number to print
            
            // Print all complete rows to file
            for(int i = 0; i < 10; i++)
            {
                for(int j = 0; j < 10; j++)
                {
                    writer.print(num + "" + delimiter); // Print each number with custom delimiter in between
                    num++;
                }
                writer.println(); // Create next line
            }

            writer.close(); // Close writer to end session

            } catch(IOException e)
            {
                System.out.println(e);
            }        
    }
    
    
    /**
     * Writes the requested range of numbers to a file w custom organization
     * @param fileName      Name of file to be written to
     * @param maxNum        Highest number to count to
     * @param numsPerRow    Max number of entries to appear on each line of the file
     * @param appendData    True adds to end of file; false overwrites file
     */
    public void writeNumbersAdvanced(String fileName, int maxNum, int numsPerRow, boolean appendData)
    {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(fileName, appendData));

            int numRows = maxNum / numsPerRow; // Determine number of rows fitting custom values needed
            int extraNums = maxNum % numsPerRow; // Determine any leftover from above division for final print step
            int num = 1; // Set up variable to track number printing

            String placeholder =  "            ";// Whitespace used to organize output alignment; make bigger if numbers expect to get really big
            
            // Print all complete rows to file
            for(int i = 0; i < numRows; i++)
            {
                for(int j = 0; j < numsPerRow; j++)
                {
                    String output = "" + num; // Cast num to a string
                    writer.print(output += placeholder.substring(0, placeholder.length() - output.length())); // Fit num into the placeholder content to ensure alignment
                    num++;
                }
                writer.println();
            }
            // Finally, print final (shorter) row, if there is one
            for(int k = 0; k < extraNums; k++)
            {
                    String output = "" + num; // Cast num to a string
                    writer.print(output += placeholder.substring(0, placeholder.length() - output.length())); // Fit num into the placeholder content to ensure alignment
                    num++;            
            }
             
            writer.close(); // Close writer to end session
            
        } catch(IOException e)
        {
            System.out.println(e);
        } 
    }
    
    /**
     * Accept 2 files and merge their contents into a new file
     * @param fileOneName
     * @param fileTwoName
     * @param mergedFileName 
     */
    public void mergeFiles(String fileOneName, String fileTwoName, String mergedFileName)
    {
        // Create file reader
        FileRead fr = new FileRead();
        
        // Snag data from both files
        ArrayList<String> fileOneData = fr.retrieveDataListFromFile(fileOneName);
        ArrayList<String> fileTwoData = fr.retrieveDataListFromFile(fileTwoName);
        
        // Merge files into one
        fileOneData.addAll(fileTwoData);
        
        // Write combined data to file
        FileWrite fw = new FileWrite();
        fw.writeContentsToFile(mergedFileName, fileOneData, false);
    }
    
    // Your TODO
    
    
    // Create a method that can join 3 different files together
    public void joinThreeFiles(String fileOneName, String fileTwoName, String fileThreeName, String mergedFileName){
        mergeFiles(fileOneName, fileTwoName, mergedFileName);
        // This will override the first merged file since they have the same name
        mergeFiles(mergedFileName, fileThreeName, mergedFileName);
    }
    
    
    // Create a method that creates a new file with the contents of the ‘AllWords.txt’ file sorted by word length rather than alphabetically
    public void createNewAllWordsSortedByLength(String newFileName){
        try {
            // We need to track the longest word to know how many times to iterate the loop
            int longestWord = 0;
            // Create file reader
            FileRead fr = new FileRead();
            // Get the data of all words as an arraylist
            ArrayList<String> allWordsData = fr.retrieveDataListFromFile("AllWords.txt");
            // For every word in all words
            for (String s : allWordsData){
                // See if a string is the longest word
                if (s.length() > longestWord){
                    // If it is, make the longestWord variable equal to it
                    longestWord = s.length();
                }
            }
            // Create a printWriter
            PrintWriter write = new PrintWriter(new FileWriter(newFileName, false));
            // Loop through all word lengths
            for (int i = 1; i <= longestWord; i++){
                // Loop through every word
                for (String s : allWordsData){
                    // If the word is equal to what the current length is
                    if (s.length() == i){
                        // Write it to the file
                        write.println(s);

                    }
                }
                // If it's the last iteration in the loop, close the file writer
            if (longestWord == i){
                write.close();
            }
            }
        } catch(IOException e)
        {
            System.out.println(e);
        }

    }
    
    
    // Create a method that writes all sent data (either a String or a List) to a file with a common format decided by you
    public void createFileFromContent(ArrayList<String> data, String txtFileName){
        // Create a file writer
        FileWrite fw = new FileWrite();
        // Write the data to the file
        fw.writeContentsToFile(txtFileName + ".txt", data, false);
    }
    public void createFileFromContent(String data, String txtFileName){
        // Create a file writer
        FileWrite fw = new FileWrite();
        // Write the data to the file
        fw.writeContentsToFile(txtFileName + ".txt", data, false);
    }
    
}
