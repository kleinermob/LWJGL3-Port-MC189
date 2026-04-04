package org.lwjgl.opengl;

public class ContextAttribs {
    public ContextAttribs() {}
    public ContextAttribs(int major, int minor) {}
    public ContextAttribs withForwardCompatible(boolean forwardCompatible) { return this; }
    public ContextAttribs withProfileCore(boolean profileCore) { return this; }
}
