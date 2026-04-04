package org.lwjgl.opengl;

public class DisplayMode {
    private final int width;
    private final int height;
    private final int bpp;
    private final int frequency;

    public DisplayMode(int width, int height) {
        this(width, height, 32, 60);
    }

    public DisplayMode(int width, int height, int bpp, int frequency) {
        this.width = width;
        this.height = height;
        this.bpp = bpp;
        this.frequency = frequency;
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getBitsPerPixel() { return bpp; }
    public int getFrequency() { return frequency; }

    @Override
    public String toString() {
        return width + " x " + height + " x " + bpp + " @" + frequency + "Hz";
    }
}
