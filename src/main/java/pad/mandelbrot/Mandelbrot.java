package pad.mandelbrot;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;


public class Mandelbrot extends JFrame implements Runnable {
    private static final int MAX_ITER = 15000;
    private static final double ZOOM = 300;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private BufferedImage image;
    private int[] colors = new int[MAX_ITER];
    private int currentRow = 0;

    public Mandelbrot() {
        super("Fractal de Mandelbrot");
        setBounds(100, 100, WIDTH, HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < MAX_ITER; i++) {
            colors[i] = Color.HSBtoRGB(i / 256f, 1, i / (i + 8f));
        }

        int numThreads = 8;
        // System.out.println(numThreads);
        Thread[] threads = new Thread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Thread(this);
            threads[i].start();
        }

        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void paint(Graphics g) {
        g.drawImage(image, 0, 0, this);
    }

    public void run() {
        int row;
        while ((row = getNextRow()) < HEIGHT) {
            for (int x = 0; x < WIDTH; x++) {
                double zx = x - 400;
                double zy = row - 400;
                zx /= ZOOM;
                zy /= ZOOM;
                double cX = zx;
                double cY = zy;
                double zxtmp;
                int iter = MAX_ITER;
                while (zx * zx + zy * zy < 4 && iter > 0) {
                    zxtmp = zx * zx - zy * zy + cX;
                    zy = 2 * zx * zy + cY;
                    zx = zxtmp;
                    iter--;
                }
                image.setRGB(x, row, iter | (iter << 8));
            }
        }
    }

    private int getNextRow() {
        return currentRow++;
    }


}
