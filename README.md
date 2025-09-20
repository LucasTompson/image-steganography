# Image Steganography - XMas 2024

Scripts for both encoding and decoding data with image steganography.

---

## How it works
WriteImage.java: This program takes a plaintext file (.txt) and an image file (.png or .jpg), and then hides the text within the image's pixel data. It outputs a new image file with the hidden message.

ReadImage.java: This program takes an image file containing a hidden message, extracts the LSB from each pixel, and reconstructs the original plaintext message.

YOU MUST ADD A TILDE (~) AT THE END OF THE PLAINTEXT FILE SO THAT THE PROGRAM STOPS READING IT.

## Usage
1. Compile the java files:
```bash
javac WriteImage.java
javac ReadImage.java
```

2. Encode using ```java WriteImage```, entering the filenames of the plaintext text file and the image to hide the plaintext in when prompted
3. Decode using ```java ReadImage``` entering the filename of the image with the hidden plaintext when prompted