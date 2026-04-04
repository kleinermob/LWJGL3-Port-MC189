package org.lwjgl.opengl;

import org.lwjgl.LWJGLException;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import java.awt.Canvas;
import java.nio.ByteBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Display {

    private static final long window;
    private static final DisplayMode initialMode;

    private static String title = "Minecraft 1.8.9";
    private static int width = 854;
    private static int height = 480;
    private static boolean resizable = true;
    private static boolean vsync = false;
    private static boolean created = false;
    private static boolean closeRequested = false;
    private static boolean wasResized = false;

    // Create GLFW window + GL context in static initializer so GL is ready
    // before ANY code can call GL functions (fixes crash in Forge splash screen)
    static {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit())
            throw new RuntimeException("[LWJGL3Port] Failed to initialize GLFW");

        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        if (vidMode != null) {
            initialMode = new DisplayMode(vidMode.width(), vidMode.height(), 32, vidMode.refreshRate());
        } else {
            initialMode = new DisplayMode(1920, 1080);
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 2);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1);

        window = glfwCreateWindow(width, height, title, NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("[LWJGL3Port] Failed to create GLFW window");

        glfwMakeContextCurrent(window);
        GL.createCapabilities();

        System.out.println("[LWJGL3Port] GLFW window + GL context created in static init.");
    }

    // --- Window handle access ---

    public static long getWindowHandle() {
        return window;
    }

    // --- Create / Destroy ---

    public static void create() throws LWJGLException {
        create(new PixelFormat());
    }

    public static void create(PixelFormat pixelFormat) throws LWJGLException {
        create(pixelFormat, null);
    }

    public static void create(PixelFormat pixelFormat, ContextAttribs attribs) throws LWJGLException {
        if (created) return;

        // Window + GL context already exist from static init — just configure and show
        glfwSetWindowSize(window, width, height);
        glfwSetWindowPos(window,
            (initialMode.getWidth() - width) / 2,
            (initialMode.getHeight() - height) / 2
        );

        // Window resize callback
        glfwSetWindowSizeCallback(window, (win, w, h) -> {
            width = w;
            height = h;
            wasResized = true;
        });

        // Close callback
        glfwSetWindowCloseCallback(window, (win) -> {
            closeRequested = true;
        });

        glfwSwapInterval(vsync ? 1 : 0);
        glfwShowWindow(window);
        created = true;

        System.out.println("[LWJGL3Port] Display.create() — window visible.");
    }

    public static void destroy() {
        if (!created) return;

        org.lwjgl.input.Keyboard.destroy();
        org.lwjgl.input.Mouse.destroy();

        if (window != NULL) {
            org.lwjgl.glfw.Callbacks.glfwFreeCallbacks(window);
            glfwDestroyWindow(window);
        }
        glfwTerminate();
        created = false;
    }

    public static boolean isCreated() {
        return created;
    }

    // --- Update ---

    public static void update() {
        if (!created) return;
        glfwSwapBuffers(window);
        glfwPollEvents();
    }

    // --- Frame sync ---

    private static long variableYieldTime = 0;
    private static long lastSyncTime = 0;

    public static void sync(int fps) {
        if (fps <= 0) return;
        long sleepTime = 1000000000L / fps;
        long yieldTime = Math.min(sleepTime, variableYieldTime + sleepTime % (1000000000L / fps));
        long overSleep = 0;
        try {
            while (true) {
                long t = System.nanoTime() - lastSyncTime;
                if (t < sleepTime - yieldTime) {
                    Thread.sleep(1);
                } else if (t < sleepTime) {
                    Thread.yield();
                } else {
                    overSleep = t - sleepTime;
                    break;
                }
            }
        } catch (InterruptedException e) {
            // Ignore
        } finally {
            lastSyncTime = System.nanoTime() - Math.min(overSleep, sleepTime);
            if (overSleep > variableYieldTime) {
                variableYieldTime = Math.min(variableYieldTime + 200000, sleepTime);
            } else if (overSleep < variableYieldTime - 200000) {
                variableYieldTime = Math.max(variableYieldTime - 2000, 0);
            }
        }
    }

    // --- Display mode ---

    public static DisplayMode getDisplayMode() {
        return new DisplayMode(width, height);
    }

    public static void setDisplayMode(DisplayMode mode) throws LWJGLException {
        width = mode.getWidth();
        height = mode.getHeight();
        if (created) {
            glfwSetWindowSize(window, width, height);
            glfwSetWindowPos(window,
                (initialMode.getWidth() - width) / 2,
                (initialMode.getHeight() - height) / 2
            );
        }
    }

    public static DisplayMode getDesktopDisplayMode() {
        return initialMode;
    }

    public static DisplayMode[] getAvailableDisplayModes() throws LWJGLException {
        org.lwjgl.glfw.GLFWVidMode.Buffer modes = glfwGetVideoModes(glfwGetPrimaryMonitor());
        if (modes == null) {
            return new DisplayMode[] { new DisplayMode(854, 480) };
        }
        DisplayMode[] result = new DisplayMode[modes.capacity()];
        for (int i = 0; i < modes.capacity(); i++) {
            modes.position(i);
            result[i] = new DisplayMode(modes.width(), modes.height(), 32, modes.refreshRate());
        }
        return result;
    }

    // --- Title ---

    public static void setTitle(String newTitle) {
        title = newTitle;
        if (created && window != NULL) {
            glfwSetWindowTitle(window, title);
        }
    }

    public static String getTitle() {
        return title;
    }

    // --- Resizable ---

    public static void setResizable(boolean r) {
        resizable = r;
        if (created && window != NULL) {
            glfwSetWindowAttrib(window, GLFW_RESIZABLE, r ? GLFW_TRUE : GLFW_FALSE);
        }
    }

    public static boolean isResizable() {
        return resizable;
    }

    // --- Size ---

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    public static boolean wasResized() {
        boolean r = wasResized;
        wasResized = false;
        return r;
    }

    // --- State queries ---

    public static boolean isCloseRequested() {
        if (window != NULL && glfwWindowShouldClose(window)) {
            closeRequested = true;
        }
        return closeRequested;
    }

    public static boolean isActive() {
        return created && window != NULL && glfwGetWindowAttrib(window, GLFW_FOCUSED) == GLFW_TRUE;
    }

    public static boolean isVisible() {
        return created && window != NULL && glfwGetWindowAttrib(window, GLFW_VISIBLE) == GLFW_TRUE;
    }

    // --- VSync ---

    public static void setVSyncEnabled(boolean enabled) {
        vsync = enabled;
        if (created) {
            glfwSwapInterval(enabled ? 1 : 0);
        }
    }

    // --- Fullscreen ---

    public static boolean isFullscreen() {
        return false; // Windowed mode only for now
    }

    public static void setFullscreen(boolean fullscreen) throws LWJGLException {
        // Not implemented — windowed mode is standard for 1.8.9 modded
    }

    // --- Location ---

    public static void setLocation(int x, int y) {
        if (created && window != NULL) {
            glfwSetWindowPos(window, x, y);
        }
    }

    // --- Icon ---

    public static int setIcon(ByteBuffer[] icons) {
        // Icon setting not implemented (cosmetic only)
        return 0;
    }

    // --- Parent (AWT embedding, not used in normal gameplay) ---

    public static void setParent(Canvas parent) throws LWJGLException {
        // AWT embedding not supported in GLFW — no-op
    }

    // --- Initial background (Forge splash screen) ---

    public static void setInitialBackground(float r, float g, float b) {
        // No-op — GLFW handles clear color in the render loop
    }

    // --- Adapter/Version info ---

    public static String getAdapter() {
        return "N/A";
    }

    public static String getVersion() {
        return "N/A";
    }

    // --- Process messages ---

    public static void processMessages() {
        glfwPollEvents();
    }

    // --- Drawable ---

    public static Drawable getDrawable() {
        return new DrawableGL();
    }
}
