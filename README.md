# Image Steganography

Hide secret messages inside image files using the Least Significant Bit (LSB) technique.

## How it works

LSB steganography works by replacing the least significant bit of each pixel's color value with one bit of the message. A single-bit change per pixel is imperceptible to the human eye, so the modified image looks identical to the original.

- **WriteImage.java** — reads a plaintext `.txt` file and embeds it into the pixel data of a `.png` or `.jpg` image, outputting a new `stego-image.png` with the hidden message
- **ReadImage.java** — takes a stego image, extracts the LSB from each pixel in sequence, and reconstructs the original plaintext

## Requirements

- Java 8 or later
- No external dependencies — uses only the standard `javax.imageio` and `java.awt` libraries

## Usage

**1. Compile**
```bash
javac WriteImage.java ReadImage.java
```

**2. Encode a message**

Add a tilde `~` to the end of your plaintext file — this acts as the termination character so the decoder knows where the message ends.

```
Hello, this is a secret message.~
```

Then run:
```bash
java WriteImage
```
You will be prompted for the plaintext file path and the carrier image path. The encoded image is saved as `stego-image.png`.

**3. Decode a message**
```bash
java ReadImage
```
You will be prompted for the path to the stego image. The hidden message is printed to stdout.

## Limitations

- **Capacity** — each pixel stores 1 bit, so a message of N characters requires at least `N * 8` pixels. A 100×100 image (10,000 pixels) can hold up to 1,250 characters.
- **Lossless images only** — the carrier image must be lossless (PNG). JPEG compression destroys the LSBs, corrupting the hidden data. The encoder always outputs PNG regardless of the input format.
- **ASCII only** — the current implementation encodes standard 8-bit ASCII characters.
- **No encryption** — this is steganography, not cryptography. The message is hidden but not encrypted. Anyone who knows to look for LSB-encoded data can read it.