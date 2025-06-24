package images;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.*;//color
import java.io.File;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ImageProcessor
{
    //zad1
    public BufferedImage image;

    public void loadImage(String path)
    {
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            System.out.println("error loading image");
        }
    }

    public void saveImage(String path)
    {
        try {
            ImageIO.write(image, "png", new File(path));
        } catch (IOException e) {
            System.out.println("error saving image");
        }
    }

    //zad2
    public void changeOrigLight(int brightness)
    {
        //rozpoczęcie pomiaru czasu
        long startTime = System.nanoTime();

        int width = image.getWidth();
        int height = image.getHeight();
        for (int i = 0; i<width;i++)
        {
            for (int j = 0; j<height;j++)
            {
                Color c = new Color(image.getRGB(i,j));
                //int r = Math.max(0,Math.min(c.getRed() + brightness, 255));
                int r = Math.clamp(c.getRed() + brightness, 0, 255);
                int g = Math.clamp(c.getGreen() + brightness, 0, 255);
                int b = Math.clamp(c.getBlue() + brightness, 0, 255);
                image.setRGB(i,j,new Color(r,g,b).getRGB());
            }
        }
        //Zakończenie pomiaru czasu
        long endTime = System.nanoTime();
        // Konwersja na milisekundy
        long durationMs = (endTime - startTime) / 1_000_000;
        System.out.println("Zadanie 2 (jeden wątek): " + durationMs + " ms");
    }

    //zad3
    public void increaseBrightnessMultiThread(int brightness)
    {
        //rozpoczęcie pomiaru czasu
        long startTime = System.nanoTime();

        int cores = Runtime.getRuntime().availableProcessors();
        Thread[] threads = new Thread[cores];
        int width = image.getWidth();
        int height = image.getHeight();
        int part = height/cores;

        for(int n = 0; n<cores;n++)
        {
            int start = n * part;
            int end;

            if(n == cores - 1)
            {
                end = height;
            }else{
                end = start + part;
            }
            threads[n] = new Thread(() ->
            {
                for (int i = 0; i<width;i++)
                {
                    for (int j = start; j<end;j++)
                    {
                        Color c = new Color(image.getRGB(i,j));
                        //int r = Math.max(0,Math.min(c.getRed() + brightness, 255));
                        int r = Math.clamp(c.getRed() + brightness, 0, 255);
                        int g = Math.clamp(c.getGreen() + brightness, 0, 255);
                        int b = Math.clamp(c.getBlue() + brightness, 0, 255);
                        image.setRGB(i,j,new Color(r,g,b).getRGB());
                    }
                }
            });
            threads[n].start();
        }
        for (Thread thread: threads)
        {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println("error joining");
            }
        }
        //Zakończenie pomiaru czasu
        long endTime = System.nanoTime();
        // Konwersja na milisekundy
        long durationMs = (endTime - startTime) / 1_000_000; // Переводим в миллисекунды
        System.out.println("Zadanie 3 (wielowątkowe): " + durationMs + " ms (rdzeni: " + cores + ")");
    }

    //4
    public void increaseBrightnessThreadPool(int brightness) {
        //rozpoczęcie pomiaru czasu
        long startTime = System.nanoTime();

        int cores = Runtime.getRuntime().availableProcessors();

        ExecutorService executorService = Executors.newFixedThreadPool(cores);

        int height = image.getHeight();
        int width = image.getWidth();

        for (int y = 0; y < height; y++) {
            final int finalY = y;
            executorService.submit(() -> {

                for (int x = 0; x < width; x++) {

                    Color c = new Color(image.getRGB(x, finalY));

                    int r = Math.clamp(c.getRed() + brightness, 0, 255);
                    int g = Math.clamp(c.getGreen() + brightness, 0, 255);
                    int b = Math.clamp(c.getBlue() + brightness, 0, 255);

                    image.setRGB(x, finalY, new Color(r, g, b).getRGB());
                }
            });
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        long endTime = System.nanoTime();
        long durationMs = (endTime - startTime) / 1_000_000;
        System.out.println("Zadanie 4 ( pula wątków ): " + durationMs + " ms");
    }
}
