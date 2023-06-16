package pad.mandelbrot;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class Mandelbrot extends JFrame implements Runnable {
    private static final int INTERACTIONS = 10000;
    private static final double ZOOM = 300;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private static final int numThreads = 8;
    private BufferedImage image;
    private int currentBlock = 0;
    private final Object lock = new Object();

    public Mandelbrot() {
        super("Fractal de Mandelbrot");
        setBounds(100, 100, WIDTH, HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);


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
        int block;
        while ((block = getNextBlock()) < numThreads) {
            int blockWidth = WIDTH / numThreads;
            int startX = block * blockWidth;
            int endX = startX + blockWidth;

            for (int x = startX; x < endX; x++) {
                for (int y = 0; y < HEIGHT; y++) {
                    double zx = x - WIDTH / 2; //Calcula a coordenada zx deslocando o valor de x em relação ao centro horizontal da imagem.
                    double zy = y - HEIGHT / 2; // Calcula a coordenada zy deslocando o valor de y em relação ao centro vertical da imagem.
                    zx /= ZOOM;
                    zy /= ZOOM; // Divide zx e zy pelo zoom pra ajustar a escala do fractal
                    double cX = zx;
                    double cY = zy;
                    double zxtmp;
                    int iter = INTERACTIONS;
                    while (zx * zx + zy * zy < 4 && iter > 0) {
                        zxtmp = zx * zx - zy * zy + cX;
                        zy = 2 * zx * zy + cY;
                        zx = zxtmp;
                        iter--;
                    }
                    image.setRGB(x, y, iter | (iter << 8));
                }
            }
        }
    }

    private int getNextBlock() {
        synchronized (lock) {
            return currentBlock++;
        }
    }
}
