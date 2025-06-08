package org.example;
import images.ImageProcessor;

public class Main {
    public static void main(String[] args) {
        ImageProcessor ip = new ImageProcessor();
        //zad 2 i 3
        ip.loadImage("obr.png");
        ip.changeOrigLight(40);
        ip.saveImage("zad_2_changeOrigLight.png");

        ip.loadImage("obr.png");
        ip.increaseBrightnessMultiThread(40);
        ip.saveImage("zad_3_increaseBrightnessMultiThread.png");

        //zad 4
        ip.loadImage("obr.png");
        ip.increaseBrightnessThreadPool(40);
        ip.saveImage("zad_4_increaseBrightnessThreadPool.png");
    }
}