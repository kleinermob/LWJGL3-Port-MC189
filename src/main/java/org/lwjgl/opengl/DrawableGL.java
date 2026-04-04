package org.lwjgl.opengl;

import org.lwjgl.LWJGLException;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class DrawableGL implements Drawable {
    @Override
    public boolean isCurrent() throws LWJGLException {
        return glfwGetCurrentContext() == Display.getWindowHandle();
    }

    @Override
    public void makeCurrent() throws LWJGLException {
        glfwMakeContextCurrent(Display.getWindowHandle());
        GL.createCapabilities();
    }

    @Override
    public void releaseContext() throws LWJGLException {
        glfwMakeContextCurrent(NULL);
    }

    @Override
    public void destroy() {
    }
}
