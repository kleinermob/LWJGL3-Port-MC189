package org.lwjgl.opengl;

import org.lwjgl.opengl.GL11;

public class Util {
    public static void checkGLError() throws OpenGLException {
        int err = GL11.glGetError();
        if (err != GL11.GL_NO_ERROR) {
            throw new OpenGLException(err);
        }
    }
}
