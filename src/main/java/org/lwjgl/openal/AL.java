package org.lwjgl.openal;

import org.lwjgl.LWJGLException;

/**
 * Compile-time stub for org.lwjgl.openal.AL.
 * At runtime, LWJGL 3's real AL class is loaded instead (this is NOT pre-defined).
 * The createCapabilities stub exists only so OpenALHelper compiles against it.
 */
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

    // Compile-time stub — at runtime, LWJGL 3's real AL.createCapabilities() is used
    public static ALCapabilities createCapabilities(ALCCapabilities alcCaps) {
        throw new UnsupportedOperationException("Stub — should never be called at runtime");
    }
}
