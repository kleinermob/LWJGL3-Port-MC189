package org.lwjgl.opengl;

public class PixelFormat {
    private int bpp = 0;
    private int alpha = 8;
    private int depth = 24;
    private int stencil = 8;
    private int samples = 0;

    public PixelFormat() {}

    public PixelFormat(int bpp, int alpha, int depth, int stencil, int samples) {
        this.bpp = bpp;
        this.alpha = alpha;
        this.depth = depth;
        this.stencil = stencil;
        this.samples = samples;
    }

    public PixelFormat withDepthBits(int bits) { this.depth = bits; return this; }
    public PixelFormat withStencilBits(int bits) { this.stencil = bits; return this; }
    public PixelFormat withSamples(int samples) { this.samples = samples; return this; }
    public PixelFormat withBitsPerPixel(int bpp) { this.bpp = bpp; return this; }
    public PixelFormat withAlphaBits(int alpha) { this.alpha = alpha; return this; }

    public int getBitsPerPixel() { return bpp; }
    public int getAlphaBits() { return alpha; }
    public int getDepthBits() { return depth; }
    public int getStencilBits() { return stencil; }
    public int getSamples() { return samples; }
}
