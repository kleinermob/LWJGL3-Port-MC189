package org.lwjgl.util.vector;

import java.nio.FloatBuffer;

public class Vector3f extends Vector implements ReadableVector3f, WritableVector3f {

    private static final long serialVersionUID = 1L;

    public float x, y, z;

    public Vector3f() {}

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3f(ReadableVector3f src) {
        set(src);
    }

    public void set(ReadableVector3f src) {
        x = src.getX();
        y = src.getY();
        z = src.getZ();
    }

    @Override
    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public float getX() { return x; }

    @Override
    public float getY() { return y; }

    @Override
    public float getZ() { return z; }

    @Override
    public void setX(float x) { this.x = x; }

    @Override
    public void setY(float y) { this.y = y; }

    @Override
    public void setZ(float z) { this.z = z; }

    public float lengthSquared() {
        return x * x + y * y + z * z;
    }

    public Vector3f negate() {
        x = -x;
        y = -y;
        z = -z;
        return this;
    }

    public static Vector3f add(Vector3f left, Vector3f right, Vector3f dest) {
        if (dest == null) dest = new Vector3f();
        dest.set(left.x + right.x, left.y + right.y, left.z + right.z);
        return dest;
    }

    public static Vector3f sub(Vector3f left, Vector3f right, Vector3f dest) {
        if (dest == null) dest = new Vector3f();
        dest.set(left.x - right.x, left.y - right.y, left.z - right.z);
        return dest;
    }

    public static Vector3f cross(Vector3f left, Vector3f right, Vector3f dest) {
        if (dest == null) dest = new Vector3f();
        dest.set(
            left.y * right.z - left.z * right.y,
            left.z * right.x - left.x * right.z,
            left.x * right.y - left.y * right.x
        );
        return dest;
    }

    public static float dot(Vector3f left, Vector3f right) {
        return left.x * right.x + left.y * right.y + left.z * right.z;
    }

    public static float angle(Vector3f a, Vector3f b) {
        float dls = dot(a, b) / (a.length() * b.length());
        if (dls < -1f) dls = -1f;
        else if (dls > 1.0f) dls = 1.0f;
        return (float) Math.acos(dls);
    }

    public Vector3f store(FloatBuffer buf) {
        buf.put(x);
        buf.put(y);
        buf.put(z);
        return this;
    }

    public Vector3f load(FloatBuffer buf) {
        x = buf.get();
        y = buf.get();
        z = buf.get();
        return this;
    }

    public Vector3f scale(float scale) {
        x *= scale;
        y *= scale;
        z *= scale;
        return this;
    }

    @Override
    public String toString() {
        return "Vector3f[" + x + ", " + y + ", " + z + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Vector3f other = (Vector3f) obj;
        return Float.compare(x, other.x) == 0 && Float.compare(y, other.y) == 0 && Float.compare(z, other.z) == 0;
    }

    @Override
    public int hashCode() {
        int result = Float.floatToIntBits(x);
        result = 31 * result + Float.floatToIntBits(y);
        result = 31 * result + Float.floatToIntBits(z);
        return result;
    }
}
