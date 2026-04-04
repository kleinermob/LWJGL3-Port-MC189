package org.lwjgl.opengl;

import org.lwjgl.LWJGLException;

public class OpenGLException extends LWJGLException {
    public OpenGLException() { super(); }
    public OpenGLException(String message) { super(message); }
    public OpenGLException(int gl_error_code) { super("OpenGL Error: " + gl_error_code); }
}
