package com.github.lwjgl3port;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Bridges LWJGL 2's AL.create()/destroy()/isCreated() to LWJGL 3's OpenAL init.
 * Called by paulscode via our ASM transformer rewriting AL.create() invocations.
 */
public class OpenALHelper {

    private static boolean created = false;
    private static long device = 0;
    private static long context = 0;

    public static void create() throws Exception {
        if (created) return;
        try {
            device = org.lwjgl.openal.ALC10.alcOpenDevice((ByteBuffer) null);
            if (device == 0L) {
                throw new Exception("[LWJGL3Port] Failed to open default OpenAL device");
            }

            org.lwjgl.openal.ALCCapabilities alcCaps = org.lwjgl.openal.ALC.createCapabilities(device);

            context = org.lwjgl.openal.ALC10.alcCreateContext(device, (IntBuffer) null);
            if (context == 0L) {
                throw new Exception("[LWJGL3Port] Failed to create OpenAL context");
            }

            org.lwjgl.openal.ALC10.alcMakeContextCurrent(context);
            org.lwjgl.openal.AL.createCapabilities(alcCaps);

            created = true;
            System.out.println("[LWJGL3Port] OpenAL initialized successfully via LWJGL 3.");
        } catch (Exception e) {
            System.err.println("[LWJGL3Port] OpenAL initialization failed: " + e.getMessage());
            throw e;
        }
    }

    public static void destroy() {
        if (!created) return;
        org.lwjgl.openal.ALC10.alcMakeContextCurrent(0L);
        if (context != 0L) {
            org.lwjgl.openal.ALC10.alcDestroyContext(context);
            context = 0L;
        }
        if (device != 0L) {
            org.lwjgl.openal.ALC10.alcCloseDevice(device);
            device = 0L;
        }
        created = false;
        System.out.println("[LWJGL3Port] OpenAL destroyed.");
    }

    public static boolean isCreated() {
        return created;
    }

    // =====================================================================
    // Bridge methods for LWJGL 2 AL10 signatures missing in LWJGL 3.
    // LWJGL 2 had bulk IntBuffer/FloatBuffer variants; LWJGL 3 removed them.
    // =====================================================================

    /** AL10.alListener(int, FloatBuffer) → alListenerfv(int, float[]) */
    public static void alListener(int pname, FloatBuffer data) {
        float[] arr = new float[data.remaining()];
        int pos = data.position();
        data.get(arr);
        data.position(pos);
        org.lwjgl.openal.AL10.alListenerfv(pname, arr);
    }

    /** AL10.alSource(int, int, FloatBuffer) → alSourcefv(int, int, float[]) */
    public static void alSource(int source, int param, FloatBuffer data) {
        float[] arr = new float[data.remaining()];
        int pos = data.position();
        data.get(arr);
        data.position(pos);
        org.lwjgl.openal.AL10.alSourcefv(source, param, arr);
    }

    /** AL10.alSourceStop(IntBuffer) → iterate alSourceStop(int) */
    public static void alSourceStop(IntBuffer sources) {
        int pos = sources.position();
        while (sources.hasRemaining()) {
            org.lwjgl.openal.AL10.alSourceStop(sources.get());
        }
        sources.position(pos);
    }

    /** AL10.alSourcePlay(IntBuffer) → iterate alSourcePlay(int) */
    public static void alSourcePlay(IntBuffer sources) {
        int pos = sources.position();
        while (sources.hasRemaining()) {
            org.lwjgl.openal.AL10.alSourcePlay(sources.get());
        }
        sources.position(pos);
    }

    /** AL10.alSourceRewind(IntBuffer) → iterate alSourceRewind(int) */
    public static void alSourceRewind(IntBuffer sources) {
        int pos = sources.position();
        while (sources.hasRemaining()) {
            org.lwjgl.openal.AL10.alSourceRewind(sources.get());
        }
        sources.position(pos);
    }

    /** AL10.alSourcePause(IntBuffer) → iterate alSourcePause(int) */
    public static void alSourcePause(IntBuffer sources) {
        int pos = sources.position();
        while (sources.hasRemaining()) {
            org.lwjgl.openal.AL10.alSourcePause(sources.get());
        }
        sources.position(pos);
    }

    /** AL10.alGenSources(IntBuffer) → fill with alGenSources() */
    public static void alGenSources(IntBuffer sources) {
        int count = sources.remaining();
        for (int i = 0; i < count; i++) {
            sources.put(org.lwjgl.openal.AL10.alGenSources());
        }
        sources.flip();
    }

    /** AL10.alDeleteSources(IntBuffer) → iterate alDeleteSources(int) */
    public static void alDeleteSources(IntBuffer sources) {
        int pos = sources.position();
        while (sources.hasRemaining()) {
            org.lwjgl.openal.AL10.alDeleteSources(sources.get());
        }
        sources.position(pos);
    }

    /** AL10.alGenBuffers(IntBuffer) → fill with alGenBuffers() */
    public static void alGenBuffers(IntBuffer buffers) {
        int count = buffers.remaining();
        for (int i = 0; i < count; i++) {
            buffers.put(org.lwjgl.openal.AL10.alGenBuffers());
        }
        buffers.flip();
    }

    /** AL10.alDeleteBuffers(IntBuffer) → iterate alDeleteBuffers(int) */
    public static void alDeleteBuffers(IntBuffer buffers) {
        int pos = buffers.position();
        while (buffers.hasRemaining()) {
            org.lwjgl.openal.AL10.alDeleteBuffers(buffers.get());
        }
        buffers.position(pos);
    }

    /** AL10.alSourceQueueBuffers(int, IntBuffer) — bridge to LWJGL 3 */
    public static void alSourceQueueBuffers(int source, IntBuffer buffers) {
        int pos = buffers.position();
        while (buffers.hasRemaining()) {
            org.lwjgl.openal.AL10.alSourceQueueBuffers(source, buffers.get());
        }
        buffers.position(pos);
    }

    /** AL10.alSourceUnqueueBuffers(int, IntBuffer) — bridge to LWJGL 3 */
    public static void alSourceUnqueueBuffers(int source, IntBuffer buffers) {
        int count = buffers.remaining();
        for (int i = 0; i < count; i++) {
            buffers.put(org.lwjgl.openal.AL10.alSourceUnqueueBuffers(source));
        }
        buffers.flip();
    }

    /** AL10.alBufferData(int, int, IntBuffer, int) → convert to short[] via ByteBuffer */
    public static void alBufferData(int buffer, int format, IntBuffer data, int freq) {
        java.nio.ByteBuffer byteBuffer = java.nio.ByteBuffer.allocateDirect(data.remaining() * 4);
        byteBuffer.order(java.nio.ByteOrder.nativeOrder());
        byteBuffer.asIntBuffer().put(data.duplicate());
        byteBuffer.limit(data.remaining() * 4);
        org.lwjgl.openal.AL10.alBufferData(buffer, format, byteBuffer, freq);
    }
}
