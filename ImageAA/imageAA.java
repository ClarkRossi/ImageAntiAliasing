import java.io.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import javax.imageio.*;

public class imageAA {
    /*
     * This template for CSCI 365 does the following:
     * - Read a PNG from a file
     * - Place the color components in a 2-D array
     * - Re-copy the array into an imagebuffer
     * - Refresh the ImageBuffer
     */

    static int HEIGHT = 1536;
    static int WIDTH = 2048;

    static int R[][] = new int[WIDTH / 2][HEIGHT / 2];
    static int G[][] = new int[WIDTH / 2][HEIGHT / 2];
    static int B[][] = new int[WIDTH / 2][HEIGHT / 2];

    //RGB arrays for original image
    static int LR[][] = new int[WIDTH][HEIGHT];
    static int LG[][] = new int[WIDTH][HEIGHT];
    static int LB[][] = new int[WIDTH][HEIGHT];

    static BufferedImage buffer;
    static MyCanvas canvas;


    public static void main(String args[]) {
        Scanner input = new Scanner(System.in);
        String keyin;
        int x, y;

        /*
         * Necessary AWT/Swing steps.
         */
        JFrame frame = new JFrame();
        canvas = new MyCanvas();
        frame.add(canvas, "Center");

        /*
         * Boilerplate. Just do this. I don't even
         * remember what it all does any more, but
         * it's required.
         */
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Change if you like.
        frame.setTitle("CSCI 365");

        /*
         * Y > HEIGHT because of "Window Decorations"
         * (Java's terminology, not mine.)
         */
        frame.setSize(WIDTH / 2, (HEIGHT / 2) + 15);
        frame.setVisible(true);

        while (true) {
            printmenu();
            keyin = input.next();
            switch (keyin) {
                /*
                 * Anything your program "does" goes in
                 * here. Right now, read and display
                 */
                case "r":
                case "R":
                    System.out.printf("Name: ");
                    readimage(input.next());
                    break;
                case "s":
                case "S":
                    straightcut();
                    break;
                case "a":
                case "A":
                    ssaa();
                    break;
                case "d":
                case "D":
                    displayimage();
                    break;
                case "x":
                case "X":
                    System.exit(0);
            }
        }
    }
    public static void printmenu() {
        System.out.printf("Menu:\n");
        System.out.printf("r:\tread image\n");
        System.out.printf("s:\tapply straight cut AA\n");
        System.out.printf("a:\tapply SSAA\n");
        System.out.printf("d:\tdisplay image in memory\n");
        System.out.printf("x:\texit\n");
        System.out.printf("Enter an option: ");
    }

    public static void readimage(String name) {
        int x, y;
        Color c;
        try {
            buffer = ImageIO.read(new File(name));
            for (x = 0; x < WIDTH; x++) {
                for (y = 0; y < HEIGHT; y++) {
                    c = new Color(buffer.getRGB(x, y));

                    //Sets RGB values at pixel location
                    LR[x][y] = c.getRed();
                    LG[x][y] = c.getGreen();
                    LB[x][y] = c.getBlue();
                }
            }
        } catch (IOException e) {
            System.out.println("HALP ME.");
            e.printStackTrace();
        }
    }

    //Function to apply straight cut AA to image
    public static void straightcut() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                //Passes only odd x and y values
                if ((x % 2 != 0) && (y % 2 != 0)) {
                    R[(int) Math.ceil(x / 2)][(int) Math.ceil(y / 2)] = LR[x][y];
                    G[(int) Math.ceil(x / 2)][(int) Math.ceil(y / 2)] = LG[x][y];
                    B[(int) Math.ceil(x / 2)][(int) Math.ceil(y / 2)] = LB[x][y];
                }
            }
        }
    }

    //Function to apply SSAA to image
    public static void ssaa() {
        //Top right pixels
        int trR[][] = new int[WIDTH / 2][HEIGHT / 2];
        int trG[][] = new int[WIDTH / 2][HEIGHT / 2];
        int trB[][] = new int[WIDTH / 2][HEIGHT / 2];

        //Top left pixels
        int tlR[][] = new int[WIDTH / 2][HEIGHT / 2];
        int tlG[][] = new int[WIDTH / 2][HEIGHT / 2];
        int tlB[][] = new int[WIDTH / 2][HEIGHT / 2];

        //Bottom right pixels
        int brR[][] = new int[WIDTH / 2][HEIGHT / 2];
        int brG[][] = new int[WIDTH / 2][HEIGHT / 2];
        int brB[][] = new int[WIDTH / 2][HEIGHT / 2];

        //Bottom left pixels
        int blR[][] = new int[WIDTH / 2][HEIGHT / 2];
        int blG[][] = new int[WIDTH / 2][HEIGHT / 2];
        int blB[][] = new int[WIDTH / 2][HEIGHT / 2];

        //Assigns pixels to individual RGB arrays
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                //Passes values to top left pixel RGB array
                if ((x % 2 == 0) && (y % 2 == 0)) {

                }
                //Passes values to top right pixel RGB array
                if ((x % 2 != 0) && (y % 2 == 0)) {

                }
                //Passes values to bottom left pixel RGB array
                if ((x % 2 == 0) && (y % 2 != 0)) {

                }
                //Passes values to bottom right pixel RGB array
                if ((x % 2 != 0) && (y % 2 != 0)) {
                    brR[(int) Math.ceil(x / 2)][(int) Math.ceil(y / 2)] = LR[x][y];
                    brG[(int) Math.ceil(x / 2)][(int) Math.ceil(y / 2)] = LG[x][y];
                    brB[(int) Math.ceil(x / 2)][(int) Math.ceil(y / 2)] = LB[x][y];
                }
            }
        }

        //Loops through all four RGB arrays and averages them into one RGB array
    }

    public static void displayimage() {
        int x, y;
        for (x = 0; x < WIDTH / 2; x++)
            for (y = 0; y < HEIGHT / 2; y++) {
                buffer.setRGB(x, y,
                    ((R[x][y] << 16 |
                        G[x][y] << 8 |
                        B[x][y])));
            }
        canvas.repaint();
    }


    /*
     * Inner classes. Only exist for graphics, as far as I know
     */
    public static class MyCanvas extends JPanel {
        public MyCanvas() {
            super(true);
        }

        public void paint(Graphics g) {
            g.drawImage(buffer, 0, 0, Color.red, null);
        }
    }
}