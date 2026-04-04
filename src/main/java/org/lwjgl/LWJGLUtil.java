package org.lwjgl;

public class LWJGLUtil {

    public static final int PLATFORM_LINUX = 1;
    public static final int PLATFORM_MACOSX = 2;
    public static final int PLATFORM_WINDOWS = 3;

    public static int getPlatform() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) return PLATFORM_WINDOWS;
        if (os.contains("mac") || os.contains("darwin")) return PLATFORM_MACOSX;
        return PLATFORM_LINUX;
    }

    public static boolean getPrivilegedBoolean(String property) {
        try {
            return Boolean.parseBoolean(System.getProperty(property));
        } catch (Exception e) {
            return false;
        }
    }

    public static String getPrivilegedProperty(String property) {
        try {
            return System.getProperty(property);
        } catch (Exception e) {
            return null;
        }
    }

    public static void log(CharSequence msg) {
        System.out.println("[LWJGL] " + msg);
    }
}
