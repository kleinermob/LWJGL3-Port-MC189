package org.lwjgl.opengl;

import org.lwjgl.LWJGLException;

public interface Drawable {
    boolean isCurrent() throws LWJGLException;
    void makeCurrent() throws LWJGLException;
    void releaseContext() throws LWJGLException;
    void destroy();
}
