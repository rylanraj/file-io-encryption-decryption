
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;


public class FileDecrypter {

    // See if a String contains a real word by comparing to the all words text file
    public boolean containsRealWord(String phrase)
    {
        FileRead reader = new FileRead();
        
        String[] phraseWords = phrase.split(" "); // Breaks my phrase down into individual words
        ArrayList<String> allWords = reader.retrieveDataListFromFile("AllWords.txt");
        
        boolean foundRealWord = false;
        
        for(String myWord: phraseWords)
        {
            for(String dictWord: allWords)
            {
                if(dictWord.toLowerCase().equals(myWord.toLowerCase()))
                {
                    foundRealWord = true;
                    break;
                }
            }
        }
        
        return foundRealWord;
    }
    // Tier 2 problem, decoding strings
    public String decodeString(String message, int decodeAmount, boolean print){
        String decryptedMessage = "";

        for(int i = 0; i < message.length(); i++) // Loop as many times as there are letters
        {
            char tempChar = message.charAt(i); // Snag each character from the string one at a time

            // Use the file encrypter to turn it into it's int value
            FileEncrypter encrypter = new FileEncrypter();
            int letterVal = encrypter.characterCast(tempChar);
            // Subtract the decode amount from the letter value
            int newLetterVal = letterVal - decodeAmount;
            // Check if the value falls into the right ranges with value checker in the encrypter
            int checkedLetterVal = encrypter.letterValueChecker(newLetterVal);
            // Convert the int back to a character
            char newChar = encrypter.convertBackToChar(checkedLetterVal);
            // Keep on adding it from the previous character until the string is completely decoded
            decryptedMessage += newChar;
        }
        if (print){
            System.out.println(decryptedMessage);
        }
        return decryptedMessage;
    }

    // Tier 2 Problem, decoding list of strings
    public ArrayList<String> decodeListsOfStrings(ArrayList<String> encodedStrings, int decodeAmount){
        ArrayList<String> decodedStrings = new ArrayList<>();
        for (String s : encodedStrings){
            decodedStrings.add(decodeString(s, decodeAmount, false));
        }
        return decodedStrings;
    }

    // Tier 2 Problem, decoding a file and writing it a new one
    public void decodeFromFile(String fileName, int decodeAmount, String decodedFileName){
        FileRead reader = new FileRead();
        FileWrite writer = new FileWrite();
        // Get the contents of the requested file
        ArrayList<String> fileContents = reader.retrieveDataListFromFile(fileName);
        // Decode the contents
        ArrayList<String> decodedFileContents = decodeListsOfStrings(fileContents, decodeAmount);
        // Create the new file
        writer.createFileFromContent(decodedFileContents, decodedFileName);
    }
    // Brute force decryption (Tier 3)
    public void bruteForceDecryption(String encryptedFileName, int encryptionRangeBottom, int encryptionRangeTop){
        // Create a scanner
        Scanner scanner = new Scanner(System.in);

        // Create a file reader
        FileRead reader = new FileRead();

        // Get the data of the encrypted file
        ArrayList<String> data = reader.retrieveDataListFromFile(encryptedFileName);

        // Get the data of all words
        ArrayList<String> allWordsData = reader.retrieveDataListFromFile("AllWords.txt");

        // Decrypt through the ranges decided by the user
        for (int i = encryptionRangeBottom; i < encryptionRangeTop; i++){
            boolean decryptionFound = false;
            // For every string in the data
            for (String s : data){
                System.out.println(decodeString(s, i, false));
                // If the string contains a real word at the decode amount we are using
                if (containsRealWord(decodeString(s, i, false))){
                    // Ask the user if their message contains said string
                    // If it does then break the loop, create the decrypted file
                    // If it doesn't move on to the next real word found whilst decoding
                    System.out.println("Does your encrypted message start with: " + decodeString(s, i, false));
                    System.out.println("1. Yes");
                    System.out.println("2. No");
                    int input = scanner.nextInt();
                    if (input == 1){
                        System.out.println("The decryption amount used was " + i);
                        System.out.println("Ending algorithm");
                        decryptionFound = true;
                        break;
                    }
                }
            }
            if (decryptionFound){
                break;
            }
        }

    }
    // Layered decryption, decrypts layered encryptions
    public String layeredDecryption(String messageToDecode, boolean print){
        String decodedMessage = messageToDecode;
        FileEncrypter encrypter = new FileEncrypter();
        // Reverse
        decodedMessage = encrypter.reverseString(decodedMessage);
        // Every letter by 3
        decodedMessage = decodeString(decodedMessage, 3, false);
        // Every other by 2
        decodedMessage = customDecoder(decodedMessage);

        if (print){
            System.out.println(decodedMessage);
        }
        return decodedMessage;
    }
    public String customDecoder(String message){
        String decodedString = "";
        // Use the encrypter to do this
        FileEncrypter encrypter = new FileEncrypter();
        for(int i = 0; i < message.length(); i++){ // Loop as many times as there are letters
            char tempChar = message.charAt(i); // Snag each character from the string one at a time
            // Now, do the converting
            int letterVal = encrypter.characterCast(tempChar);
            // Shift other character by -2
            if(i % 2 == 0){
                int newLetterVal = encrypter.letterValueChecker(letterVal - 2);
                char newChar = encrypter.convertBackToChar(newLetterVal);
                decodedString += newChar;
            }
            else{
                char newChar = encrypter.convertBackToChar(letterVal);
                decodedString += newChar;
            }
        }

        return decodedString;
    }
    
}
