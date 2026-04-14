import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class WriteImage {
    public static void main(String[] args) {
    
        try {

            // Prompt the user to enter the name of the file containing the plaintext
            System.out.print("Enter name of file containing plaintext -> ");
            Scanner scanner = new Scanner(System.in);
            String filePath = scanner.nextLine();
            
            // Create a stringbuilder to use when reading the plaintext file
            StringBuilder inputBuild = new StringBuilder();

            // Try to open the file
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;
                // Iterate through each line in the plaintext file until line = null, i.e no more lines.
                while ((line = br.readLine()) != null) {
                    inputBuild.append(line);
                    inputBuild.append('\n');
                }
            } catch (FileNotFoundException e) { // Catch FileNotFoundException
                System.out.println("File does not exist");
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            // Convert the stringbuilder to normal String variable type
            String input = inputBuild.toString();

            // Convert string to continuous ASCII binary code...

            // Create a stringbuilder to add the ASCII codes to 
            StringBuilder binaryString = new StringBuilder();

            // Loop through each character of the string
            for (int i = 0; i < input.length(); i++) {
                // Get ASCII value of the character
                int asciiValue = input.charAt(i);
                if (asciiValue == '~') break;
                // Convert the ASCII value to binary, and ensure it's 8 bits long
                String binary = String.format("%8s", Integer.toBinaryString(asciiValue)).replace(' ', '0');
                
                // Append the binary representation to the StringBuilder
                binaryString.append(binary);
            }
            
            // Convert the stringbuilder to normal String variable type 
            String binary = binaryString.toString();
            
            // Load the image
            System.out.print("Enter the name of the image to hide the plaintext in -> ");
            String inputFilePath = scanner.nextLine();
            File inputFile = new File(inputFilePath);
            BufferedImage image1 = ImageIO.read(inputFile);
            BufferedImage image = new BufferedImage(image1.getWidth(), image1.getHeight(), BufferedImage.TYPE_INT_RGB);
            image.setData(image1.getData());
            
            int width = image.getWidth();

            // Save to a new file (PNG to avoid JPEG compression issues)
            File outputFile = new File("stego-image.png");

            // Modify the image using the binary data
            int[] change = new int[2];
            for (int let = 0; let < binary.length(); let++) {
                change[0] = (let % width);
                change[1] = (let / width);
                int pixel = image.getRGB(change[0], change[1]);
                int binaryBit = Character.getNumericValue(binary.charAt(let));
                if ((pixel & 1) == binaryBit) {
                    image.setRGB(change[0], change[1], pixel);
                } else {
                    pixel ^= 1;
                    image.setRGB(change[0], change[1], pixel);
                }
            }

            // Write the modified image to the output file
            ImageIO.write(image, "PNG", outputFile);  // Use PNG to avoid compression artifacts

            System.out.println("Message written successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
