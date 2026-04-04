package org.lwjgl.opengl;

public class GLContext {

    private static ContextCapabilities capabilities;

    public static ContextCapabilities getCapabilities() {
        if (capabilities == null) {
            capabilities = new ContextCapabilities();
        }
        return capabilities;
    }
}
