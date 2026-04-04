package org.lwjgl.util.vector;

import java.nio.FloatBuffer;

public class Vector4f extends Vector implements ReadableVector4f {

    private static final long serialVersionUID = 1L;

    public float x, y, z, w;

    public Vector4f() {}

    public Vector4f(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vector4f(ReadableVector4f src) {
        set(src);
    }

    public void set(ReadableVector4f src) {
        x = src.getX();
        y = src.getY();
        z = src.getZ();
        w = src.getW();
    }

    public void set(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    @Override
    public float getX() { return x; }

    @Override
    public float getY() { return y; }

    @Override
    public float getZ() { return z; }

    @Override
    public float getW() { return w; }

    public void setX(float x) { this.x = x; }
    public void setY(float y) { this.y = y; }
    public void setZ(float z) { this.z = z; }
    public void setW(float w) { this.w = w; }

    public float lengthSquared() {
        return x * x + y * y + z * z + w * w;
    }

    public Vector4f negate() {
        x = -x;
        y = -y;
        z = -z;
        w = -w;
        return this;
    }

    public static Vector4f add(Vector4f left, Vector4f right, Vector4f dest) {
        if (dest == null) dest = new Vector4f();
        dest.set(left.x + right.x, left.y + right.y, left.z + right.z, left.w + right.w);
        return dest;
    }

    public static Vector4f sub(Vector4f left, Vector4f right, Vector4f dest) {
        if (dest == null) dest = new Vector4f();
        dest.set(left.x - right.x, left.y - right.y, left.z - right.z, left.w - right.w);
        return dest;
    }

    public static float dot(Vector4f left, Vector4f right) {
        return left.x * right.x + left.y * right.y + left.z * right.z + left.w * right.w;
    }

    public Vector4f store(FloatBuffer buf) {
        buf.put(x);
        buf.put(y);
        buf.put(z);
        buf.put(w);
        return this;
    }

    public Vector4f load(FloatBuffer buf) {
        x = buf.get();
        y = buf.get();
        z = buf.get();
        w = buf.get();
        return this;
    }

    public Vector4f scale(float scale) {
        x *= scale;
        y *= scale;
        z *= scale;
        w *= scale;
        return this;
    }

    @Override
    public String toString() {
        return "Vector4f[" + x + ", " + y + ", " + z + ", " + w + "]";
    }
}
