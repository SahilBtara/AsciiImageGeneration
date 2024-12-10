import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintWriter;
import javax.imageio.ImageIO;

public class App {
    public static void main(String[] args) {
        try {
            File file = new File("resources/input/john-torcasio-hbEIBvMGJEE-unsplash.jpg");
            BufferedImage img = ImageIO.read(file);
            int originalHeight = img.getHeight();
            int originalWidth = img.getWidth();

            // Fixed dimensions for ASCII art
            int fixedWidth = 100;  // Desired width of the ASCII output
            int fixedHeight = 100; // Desired height of the ASCII output

            // Scaling factors
            double scaleX = (double) originalWidth / fixedWidth;
            double scaleY = (double) originalHeight / fixedHeight;

            String asciiCharacters = "@#S%?*+;:,.";
            char[][] asciiImage = new char[fixedHeight][fixedWidth];

            for (int j = 0; j < fixedHeight; j++) {
                for (int i = 0; i < fixedWidth; i++) {
                    // Map fixed dimensions to original dimensions
                    int pixelX = (int) (i * scaleX);
                    int pixelY = (int) (j * scaleY);

                    int pixel = img.getRGB(pixelX, pixelY);
                    int blue = pixel & 0xFF;
                    int green = (pixel >> 8) & 0xFF;
                    int red = (pixel >> 16) & 0xFF;

                    int grayscale = (int) (0.299 * red + 0.587 * green + 0.114 * blue);
                    int index = (grayscale * (asciiCharacters.length() - 1)) / 255;

                    asciiImage[j][i] = asciiCharacters.charAt(index);
                }
            }

            // Save ASCII art to a file
            try (PrintWriter writer = new PrintWriter(new File("resources/output/ascii_art.txt"))) {
                for (int j = 0; j < fixedHeight; j++) {
                    for (int i = 0; i < fixedWidth; i++) {
                        writer.print(asciiImage[j][i]);
                    }
                    writer.println();
                }
            }

            System.out.println("Fixed-size ASCII art generated and saved to ascii_art.txt");

        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
