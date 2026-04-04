package org.lwjgl.opengl;

import org.lwjgl.LWJGLException;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class SharedDrawable implements Drawable {

    private final long sharedWindow;

    public SharedDrawable(Drawable drawable) throws LWJGLException {
        // Create a hidden 1x1 window that shares the main window's GL context.
        // This allows the splash thread to have its own current context while
        // sharing textures/buffers with the main window.
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        sharedWindow = glfwCreateWindow(1, 1, "", NULL, Display.getWindowHandle());
        if (sharedWindow == NULL) {
            throw new LWJGLException("[LWJGL3Port] Failed to create shared GL context");
        }
    }

    @Override
    public boolean isCurrent() throws LWJGLException {
        return glfwGetCurrentContext() == sharedWindow;
    }

    @Override
    public void makeCurrent() throws LWJGLException {
        glfwMakeContextCurrent(sharedWindow);
        GL.createCapabilities();
    }

    @Override
    public void releaseContext() throws LWJGLException {
        glfwMakeContextCurrent(NULL);
    }

    @Override
    public void destroy() {
        if (sharedWindow != NULL) {
            glfwDestroyWindow(sharedWindow);
        }
    }
}
