import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class ReadImage {
    public static void main(String[] args) {
        try {

            // Get userinput for filepath
            System.out.print("Enter name of file to be read -> ");
            Scanner scanner = new Scanner(System.in);
            String messageFile = scanner.nextLine();

            // Find the file at the given path
            File readFile = new File(messageFile);
            // Access the image 
            BufferedImage image = ImageIO.read(readFile);
            
            // Get the width and height of the image
            int width = image.getWidth();
            int height = image.getHeight();
            
            // Prepare the output string builder object which will have characters appended to it
            StringBuilder output = new StringBuilder();

            // Declare a 2-element array which will be used to store the pixel which is to be read
            int[] pixel = new int[2];

            // Each pixel contributes 1 bit so an 8-bit ASCII value must be built up over 8 iterations
            // Use a stringbuilder for this and then convert to integer
            StringBuilder ascii = new StringBuilder();

            // Iterate through every pixel
            for (int let = 0; let < width*height; let++) {

                // Calculate the x (pixel[0]) and y (pixel[1]) coordinates of the next pixel to be read
                pixel[0] = (let % width);
                pixel[1] = (let / width); // pixel[1] will store the integer part

                // Get the pixel data (RGB) 
                // Perform bit-wise AND with 1 (in binary: ...00001) to get the LSB
                int bit = image.getRGB(pixel[0],pixel[1]) & 1; 

                // Add this to the ascii stringbuilder
                ascii.append(String.valueOf(bit));

                // When thge ASCII strinbuilder reaches length 8 then this means that it represents an ASCII character
                if (ascii.length() == 8) {
                    
                    // Get this character using type casting to character and converting the binary string to base-10 int
                    char character = (char) Integer.parseInt(ascii.toString(),2);
                    
                    // ~ is the break character. Stop the loop if this is found
                    if (character == '~') {
                        break;
                    }

                    // Add the character to the overall output string, wipe the ASCII stringbuilder
                    output.append(character);
                    ascii.setLength(0);
                }
            }
            
            // When the program has finished iterating through the image pixels, output the decoded message
            System.out.println(output.toString());

        } catch (Exception e) {
            System.out.println("Could not read file");
        }

    }
}