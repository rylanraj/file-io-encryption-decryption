import java.util.ArrayList;
import java.util.Random;

public class FileEncrypter {
    
    FileRead read = new FileRead();
    FileWrite write = new FileWrite();

    // Casts a character into its ascii table integer number
    public int characterCast(char letter)
    {
        return (int)letter;
    }

    // Converts an int into whatever it's character is in the ascii table
    public char convertBackToChar(int number)
    {
        return (char)number;
    }

    // Changed back around if the int the char is cast to goes above 126 or below 32 (Tier 1 problem)
    public String encodeString(String message, int shift, boolean print) {

        String encodedString = "";
        
        for(int i = 0; i < message.length(); i++) // Loop as many times as there are letters
        {
            char tempChar = message.charAt(i); // Snag each character from the string one at a time
            // Now, do the converting
            int letterVal = characterCast(tempChar);
            // Check if the value falls in the right ranges
            int newLetterVal = letterValueChecker(letterVal + shift);

            char newChar = convertBackToChar(newLetterVal);
            encodedString += newChar;
        }
        if (print){
            System.out.println(encodedString);
        }
        return encodedString;
    }

    // This method is used to make sure that when encrypted a character stays within the ranges of 32-126
    public int letterValueChecker(int letterVal){
        // If the character's ascii table value exceeds 126
        if (letterVal > 126){
            // The recursive value checker will make sure that it loops back to the bottom of the table
            letterVal = recursiveValueCheckerTopRange(letterVal);
        }
        // If the character's ascii table value is less than 32 to begin with
        else if(letterVal < 32){
            // The recursive value checker will make sure that it loops back to the top of the table
            letterVal = recursiveValueCheckerBottomRange(letterVal);
        }
        return letterVal;
    }

    // Check if the letter value is below the ascii table, if it is bring it to the top of the table
    public int recursiveValueCheckerBottomRange(int letterVal){
        // If the letter value is less than 32
        if (letterVal < 32){
            // Add 95 since 31 + 95 = 126
            letterVal += 95;
            /* A value can fall below the table greatly so recursively checking insures that it will keep on
             increasing until it falls into the range */
            letterVal = recursiveValueCheckerBottomRange(letterVal);
        }
        return letterVal;
    }

    // Check if the letter value is above the ascii table, if it is bring it back to the beginning
    public int recursiveValueCheckerTopRange(int letterVal){
        // If the value is above 126
        if (letterVal > 126){
            // Decrease it by 95 as 127 - 95 = 32
            letterVal -= 95;
            /* A value can exceed the table greatly so recursively checking insures that it will keep on
             decreasing until it falls into the range */
            letterVal = recursiveValueCheckerTopRange(letterVal);
        }
        return letterVal;
    }

    // Encode a list of strings (Tier 1 problem)
    public ArrayList<String> encodeData(ArrayList<String> dataToEncode, int shift){
        // To avoid modifying the original, I'll create a new empty list
        ArrayList<String> encodedData = new ArrayList<>();
        // For every string in the data
        for (String s : dataToEncode){
            // Encode it with the parameters wanted
            encodedData.add(encodeString(s, shift, false));
        }
        // Return the encoded list
        return encodedData;
    }

    // Data sent to this method will be encoded and written to a custom file (Tier 1)
    public void encodeToFile(String dataToEncode, String fileName, int shift){
        // Encode data with parameters wanted
        String encodedData = encodeString(dataToEncode, shift, false);
        // Create an instance of file write
        FileWrite w = new FileWrite();
        // Create a file with our encoded data
        w.createFileFromContent(encodedData, fileName);

    }
    // Same method but with list
    public void encodeToFile(ArrayList<String> dataToEncode, String fileName, int shift){
        ArrayList<String> encodedData = encodeData(dataToEncode, shift);
        FileWrite w = new FileWrite();
        w.createFileFromContent(encodedData, fileName);
    }

    // Takes a file and creates a new encrypted file using a random shift
    public void randomizedEncrypter(String fileName, String encryptedFileName){
        // Generate the random shift
        int randomShift = new Random().nextInt();
        // Create a file reader and writer
        FileRead reader = new FileRead();
        FileWrite writer = new FileWrite();
        // Get the data from said file
        ArrayList<String> data = reader.retrieveDataListFromFile(fileName);
        // Encrypt it
        ArrayList<String> encryptedData = encodeData(data, randomShift);
        // Write it to a new file named to whatever user likes
        writer.createFileFromContent(encryptedData, encryptedFileName);
    }

    // Layered encryption (using the example given)
    // Shifts every letter by 3, every other character by 2, and reverses the string
    public String layeredEncryption(String message, boolean print){

        // Declare the encrypted version of the string
        String encodedString;

        //Shift every letter by 3
        encodedString = encodeString(message, 3, false);

        // Shift every other character by 2
        encodedString = customShifter(encodedString);

        // Reverse the string
        encodedString = reverseString(encodedString);

        if (print){
            System.out.println(encodedString);
        }
        return encodedString;
    }

    // Shifts every other letter
    public String customShifter(String message){
        String encodedString = "";

        for(int i = 0; i < message.length(); i++) // Loop as many times as there are letters
        {
            char tempChar = message.charAt(i); // Snag each character from the string one at a time
            // Now, do the converting
            int letterVal = characterCast(tempChar);
            // Shift other character by 2
            if(i % 2 == 0){
                int newLetterVal = letterValueChecker(letterVal + 2);
                char newChar = convertBackToChar(newLetterVal);
                encodedString += newChar;
            }
            else{
                char newChar = convertBackToChar(letterVal);
                encodedString += newChar;
            }

        }

        return encodedString;
    }

    // Reverses a given string
    public String reverseString(String str) {
        StringBuilder reversed = new StringBuilder();
        for (int i = str.length() - 1; i >= 0; i--) {
            reversed.append(str.charAt(i));
        }
        return reversed.toString();
    }

    
}
