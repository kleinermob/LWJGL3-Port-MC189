package org.lwjgl.util.vector;

import java.nio.FloatBuffer;

public class Vector2f extends Vector implements ReadableVector2f, WritableVector2f {

    private static final long serialVersionUID = 1L;

    public float x, y;

    public Vector2f() {}

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2f(ReadableVector2f src) {
        set(src);
    }

    public void set(ReadableVector2f src) {
        x = src.getX();
        y = src.getY();
    }

    @Override
    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public float getX() { return x; }

    @Override
    public float getY() { return y; }

    @Override
    public void setX(float x) { this.x = x; }

    @Override
    public void setY(float y) { this.y = y; }

    public float lengthSquared() {
        return x * x + y * y;
    }

    public Vector2f negate() {
        x = -x;
        y = -y;
        return this;
    }

    public static Vector2f add(Vector2f left, Vector2f right, Vector2f dest) {
        if (dest == null) dest = new Vector2f();
        dest.set(left.x + right.x, left.y + right.y);
        return dest;
    }

    public static Vector2f sub(Vector2f left, Vector2f right, Vector2f dest) {
        if (dest == null) dest = new Vector2f();
        dest.set(left.x - right.x, left.y - right.y);
        return dest;
    }

    public static float dot(Vector2f left, Vector2f right) {
        return left.x * right.x + left.y * right.y;
    }

    public Vector2f store(FloatBuffer buf) {
        buf.put(x);
        buf.put(y);
        return this;
    }

    public Vector2f load(FloatBuffer buf) {
        x = buf.get();
        y = buf.get();
        return this;
    }

    public Vector2f scale(float scale) {
        x *= scale;
        y *= scale;
        return this;
    }

    @Override
    public String toString() {
        return "Vector2f[" + x + ", " + y + "]";
    }
}
