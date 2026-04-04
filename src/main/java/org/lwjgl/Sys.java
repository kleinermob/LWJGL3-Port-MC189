package org.lwjgl;

import org.lwjgl.glfw.GLFW;

public class Sys {

    private static final long TICKS_PER_SECOND = 1000L;

    public static String getVersion() {
        return "3.3.3-compat";
    }

    public static long getTime() {
        return (long) (GLFW.glfwGetTime() * TICKS_PER_SECOND);
    }

    public static long getTimerResolution() {
        return TICKS_PER_SECOND;
    }

    public static void alert(String title, String message) {
        System.err.println("[LWJGL3Port] Alert: " + title + " - " + message);
    }

    public static boolean openURL(String url) {
        try {
            Runtime rt = Runtime.getRuntime();
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                rt.exec("rundll32 url.dll,FileProtocolHandler " + url);
            } else if (os.contains("mac")) {
                rt.exec("open " + url);
            } else {
                rt.exec("xdg-open " + url);
            }
            return true;
        } catch (Exception e) {
            System.err.println("[LWJGL3Port] Failed to open URL: " + url);
            return false;
        }
    }

    public static String getClipboard() {
        try {
            long window = org.lwjgl.opengl.Display.getWindowHandle();
            if (window != 0) {
                String clip = GLFW.glfwGetClipboardString(window);
                return clip != null ? clip : "";
            }
        } catch (Exception e) {
            // Fall through
        }
        return "";
    }
}
