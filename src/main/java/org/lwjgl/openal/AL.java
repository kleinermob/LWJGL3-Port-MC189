package org.lwjgl.openal;

import org.lwjgl.LWJGLException;

public class AL {

    private static boolean created = false;

    public static void create() throws LWJGLException {
        created = true;
    }

    public static void destroy() {
        created = false;
    }

    public static boolean isCreated() {
        return created;
    }
}
