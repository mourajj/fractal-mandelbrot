import pad.mandelbrot.Mandelbrot;

public class Main {

    public static void main(String[] args) {
        long startTime = System.nanoTime();

        Mandelbrot mandelbrot = new Mandelbrot();
        mandelbrot.setVisible(true);

        long endTime = System.nanoTime();

        long timeElapsed = endTime - startTime;

        System.out.println("Execution time in milliseconds: " + timeElapsed / 1000000);
    }
}
