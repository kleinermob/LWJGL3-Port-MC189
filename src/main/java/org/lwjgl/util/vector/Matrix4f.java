package org.lwjgl.util.vector;

import java.nio.FloatBuffer;

public class Matrix4f extends Matrix {

    private static final long serialVersionUID = 1L;

    public float m00, m01, m02, m03;
    public float m10, m11, m12, m13;
    public float m20, m21, m22, m23;
    public float m30, m31, m32, m33;

    public Matrix4f() {
        setIdentity();
    }

    public Matrix4f(Matrix4f src) {
        load(src);
    }

    public Matrix setIdentity() {
        return setIdentity(this);
    }

    public static Matrix4f setIdentity(Matrix4f m) {
        m.m00 = 1.0f; m.m01 = 0.0f; m.m02 = 0.0f; m.m03 = 0.0f;
        m.m10 = 0.0f; m.m11 = 1.0f; m.m12 = 0.0f; m.m13 = 0.0f;
        m.m20 = 0.0f; m.m21 = 0.0f; m.m22 = 1.0f; m.m23 = 0.0f;
        m.m30 = 0.0f; m.m31 = 0.0f; m.m32 = 0.0f; m.m33 = 1.0f;
        return m;
    }

    public Matrix setZero() {
        m00 = 0.0f; m01 = 0.0f; m02 = 0.0f; m03 = 0.0f;
        m10 = 0.0f; m11 = 0.0f; m12 = 0.0f; m13 = 0.0f;
        m20 = 0.0f; m21 = 0.0f; m22 = 0.0f; m23 = 0.0f;
        m30 = 0.0f; m31 = 0.0f; m32 = 0.0f; m33 = 0.0f;
        return this;
    }

    public Matrix4f load(Matrix4f src) {
        m00 = src.m00; m01 = src.m01; m02 = src.m02; m03 = src.m03;
        m10 = src.m10; m11 = src.m11; m12 = src.m12; m13 = src.m13;
        m20 = src.m20; m21 = src.m21; m22 = src.m22; m23 = src.m23;
        m30 = src.m30; m31 = src.m31; m32 = src.m32; m33 = src.m33;
        return this;
    }

    public Matrix load(FloatBuffer buf) {
        m00 = buf.get(); m01 = buf.get(); m02 = buf.get(); m03 = buf.get();
        m10 = buf.get(); m11 = buf.get(); m12 = buf.get(); m13 = buf.get();
        m20 = buf.get(); m21 = buf.get(); m22 = buf.get(); m23 = buf.get();
        m30 = buf.get(); m31 = buf.get(); m32 = buf.get(); m33 = buf.get();
        return this;
    }

    public Matrix loadTranspose(FloatBuffer buf) {
        m00 = buf.get(); m10 = buf.get(); m20 = buf.get(); m30 = buf.get();
        m01 = buf.get(); m11 = buf.get(); m21 = buf.get(); m31 = buf.get();
        m02 = buf.get(); m12 = buf.get(); m22 = buf.get(); m32 = buf.get();
        m03 = buf.get(); m13 = buf.get(); m23 = buf.get(); m33 = buf.get();
        return this;
    }

    public Matrix store(FloatBuffer buf) {
        buf.put(m00); buf.put(m01); buf.put(m02); buf.put(m03);
        buf.put(m10); buf.put(m11); buf.put(m12); buf.put(m13);
        buf.put(m20); buf.put(m21); buf.put(m22); buf.put(m23);
        buf.put(m30); buf.put(m31); buf.put(m32); buf.put(m33);
        return this;
    }

    public Matrix storeTranspose(FloatBuffer buf) {
        buf.put(m00); buf.put(m10); buf.put(m20); buf.put(m30);
        buf.put(m01); buf.put(m11); buf.put(m21); buf.put(m31);
        buf.put(m02); buf.put(m12); buf.put(m22); buf.put(m32);
        buf.put(m03); buf.put(m13); buf.put(m23); buf.put(m33);
        return this;
    }

    public static Matrix4f add(Matrix4f left, Matrix4f right, Matrix4f dest) {
        if (dest == null) dest = new Matrix4f();
        dest.m00 = left.m00 + right.m00; dest.m01 = left.m01 + right.m01; dest.m02 = left.m02 + right.m02; dest.m03 = left.m03 + right.m03;
        dest.m10 = left.m10 + right.m10; dest.m11 = left.m11 + right.m11; dest.m12 = left.m12 + right.m12; dest.m13 = left.m13 + right.m13;
        dest.m20 = left.m20 + right.m20; dest.m21 = left.m21 + right.m21; dest.m22 = left.m22 + right.m22; dest.m23 = left.m23 + right.m23;
        dest.m30 = left.m30 + right.m30; dest.m31 = left.m31 + right.m31; dest.m32 = left.m32 + right.m32; dest.m33 = left.m33 + right.m33;
        return dest;
    }

    public static Matrix4f sub(Matrix4f left, Matrix4f right, Matrix4f dest) {
        if (dest == null) dest = new Matrix4f();
        dest.m00 = left.m00 - right.m00; dest.m01 = left.m01 - right.m01; dest.m02 = left.m02 - right.m02; dest.m03 = left.m03 - right.m03;
        dest.m10 = left.m10 - right.m10; dest.m11 = left.m11 - right.m11; dest.m12 = left.m12 - right.m12; dest.m13 = left.m13 - right.m13;
        dest.m20 = left.m20 - right.m20; dest.m21 = left.m21 - right.m21; dest.m22 = left.m22 - right.m22; dest.m23 = left.m23 - right.m23;
        dest.m30 = left.m30 - right.m30; dest.m31 = left.m31 - right.m31; dest.m32 = left.m32 - right.m32; dest.m33 = left.m33 - right.m33;
        return dest;
    }

    public static Matrix4f mul(Matrix4f left, Matrix4f right, Matrix4f dest) {
        if (dest == null) dest = new Matrix4f();
        float nm00 = left.m00 * right.m00 + left.m10 * right.m01 + left.m20 * right.m02 + left.m30 * right.m03;
        float nm01 = left.m01 * right.m00 + left.m11 * right.m01 + left.m21 * right.m02 + left.m31 * right.m03;
        float nm02 = left.m02 * right.m00 + left.m12 * right.m01 + left.m22 * right.m02 + left.m32 * right.m03;
        float nm03 = left.m03 * right.m00 + left.m13 * right.m01 + left.m23 * right.m02 + left.m33 * right.m03;
        float nm10 = left.m00 * right.m10 + left.m10 * right.m11 + left.m20 * right.m12 + left.m30 * right.m13;
        float nm11 = left.m01 * right.m10 + left.m11 * right.m11 + left.m21 * right.m12 + left.m31 * right.m13;
        float nm12 = left.m02 * right.m10 + left.m12 * right.m11 + left.m22 * right.m12 + left.m32 * right.m13;
        float nm13 = left.m03 * right.m10 + left.m13 * right.m11 + left.m23 * right.m12 + left.m33 * right.m13;
        float nm20 = left.m00 * right.m20 + left.m10 * right.m21 + left.m20 * right.m22 + left.m30 * right.m23;
        float nm21 = left.m01 * right.m20 + left.m11 * right.m21 + left.m21 * right.m22 + left.m31 * right.m23;
        float nm22 = left.m02 * right.m20 + left.m12 * right.m21 + left.m22 * right.m22 + left.m32 * right.m23;
        float nm23 = left.m03 * right.m20 + left.m13 * right.m21 + left.m23 * right.m22 + left.m33 * right.m23;
        float nm30 = left.m00 * right.m30 + left.m10 * right.m31 + left.m20 * right.m32 + left.m30 * right.m33;
        float nm31 = left.m01 * right.m30 + left.m11 * right.m31 + left.m21 * right.m32 + left.m31 * right.m33;
        float nm32 = left.m02 * right.m30 + left.m12 * right.m31 + left.m22 * right.m32 + left.m32 * right.m33;
        float nm33 = left.m03 * right.m30 + left.m13 * right.m31 + left.m23 * right.m32 + left.m33 * right.m33;
        dest.m00 = nm00; dest.m01 = nm01; dest.m02 = nm02; dest.m03 = nm03;
        dest.m10 = nm10; dest.m11 = nm11; dest.m12 = nm12; dest.m13 = nm13;
        dest.m20 = nm20; dest.m21 = nm21; dest.m22 = nm22; dest.m23 = nm23;
        dest.m30 = nm30; dest.m31 = nm31; dest.m32 = nm32; dest.m33 = nm33;
        return dest;
    }

    public static Vector4f transform(Matrix4f left, Vector4f right, Vector4f dest) {
        if (dest == null) dest = new Vector4f();
        float x = left.m00 * right.x + left.m10 * right.y + left.m20 * right.z + left.m30 * right.w;
        float y = left.m01 * right.x + left.m11 * right.y + left.m21 * right.z + left.m31 * right.w;
        float z = left.m02 * right.x + left.m12 * right.y + left.m22 * right.z + left.m32 * right.w;
        float w = left.m03 * right.x + left.m13 * right.y + left.m23 * right.z + left.m33 * right.w;
        dest.set(x, y, z, w);
        return dest;
    }

    public Matrix transpose() {
        return transpose(this);
    }

    public Matrix4f transpose(Matrix4f dest) {
        if (dest == null) dest = new Matrix4f();
        float nm00 = m00, nm01 = m10, nm02 = m20, nm03 = m30;
        float nm10 = m01, nm11 = m11, nm12 = m21, nm13 = m31;
        float nm20 = m02, nm21 = m12, nm22 = m22, nm23 = m32;
        float nm30 = m03, nm31 = m13, nm32 = m23, nm33 = m33;
        dest.m00 = nm00; dest.m01 = nm01; dest.m02 = nm02; dest.m03 = nm03;
        dest.m10 = nm10; dest.m11 = nm11; dest.m12 = nm12; dest.m13 = nm13;
        dest.m20 = nm20; dest.m21 = nm21; dest.m22 = nm22; dest.m23 = nm23;
        dest.m30 = nm30; dest.m31 = nm31; dest.m32 = nm32; dest.m33 = nm33;
        return dest;
    }

    public float determinant() {
        float f = m00 * ((m11 * m22 * m33 + m12 * m23 * m31 + m13 * m21 * m32) - m13 * m22 * m31 - m11 * m23 * m32 - m12 * m21 * m33);
        f -= m01 * ((m10 * m22 * m33 + m12 * m23 * m30 + m13 * m20 * m32) - m13 * m22 * m30 - m10 * m23 * m32 - m12 * m20 * m33);
        f += m02 * ((m10 * m21 * m33 + m11 * m23 * m30 + m13 * m20 * m31) - m13 * m21 * m30 - m10 * m23 * m31 - m11 * m20 * m33);
        f -= m03 * ((m10 * m21 * m32 + m11 * m22 * m30 + m12 * m20 * m31) - m12 * m21 * m30 - m10 * m22 * m31 - m11 * m20 * m32);
        return f;
    }

    public Matrix invert() {
        return invert(this, null);
    }

    public static Matrix4f invert(Matrix4f src, Matrix4f dest) {
        float determinant = src.determinant();
        if (determinant != 0) {
            if (dest == null) dest = new Matrix4f();
            float determinant_inv = 1f / determinant;
            float t00 = determinant3x3(src.m11, src.m12, src.m13, src.m21, src.m22, src.m23, src.m31, src.m32, src.m33);
            float t01 = -determinant3x3(src.m10, src.m12, src.m13, src.m20, src.m22, src.m23, src.m30, src.m32, src.m33);
            float t02 = determinant3x3(src.m10, src.m11, src.m13, src.m20, src.m21, src.m23, src.m30, src.m31, src.m33);
            float t03 = -determinant3x3(src.m10, src.m11, src.m12, src.m20, src.m21, src.m22, src.m30, src.m31, src.m32);
            float t10 = -determinant3x3(src.m01, src.m02, src.m03, src.m21, src.m22, src.m23, src.m31, src.m32, src.m33);
            float t11 = determinant3x3(src.m00, src.m02, src.m03, src.m20, src.m22, src.m23, src.m30, src.m32, src.m33);
            float t12 = -determinant3x3(src.m00, src.m01, src.m03, src.m20, src.m21, src.m23, src.m30, src.m31, src.m33);
            float t13 = determinant3x3(src.m00, src.m01, src.m02, src.m20, src.m21, src.m22, src.m30, src.m31, src.m32);
            float t20 = determinant3x3(src.m01, src.m02, src.m03, src.m11, src.m12, src.m13, src.m31, src.m32, src.m33);
            float t21 = -determinant3x3(src.m00, src.m02, src.m03, src.m10, src.m12, src.m13, src.m30, src.m32, src.m33);
            float t22 = determinant3x3(src.m00, src.m01, src.m03, src.m10, src.m11, src.m13, src.m30, src.m31, src.m33);
            float t23 = -determinant3x3(src.m00, src.m01, src.m02, src.m10, src.m11, src.m12, src.m30, src.m31, src.m32);
            float t30 = -determinant3x3(src.m01, src.m02, src.m03, src.m11, src.m12, src.m13, src.m21, src.m22, src.m23);
            float t31 = determinant3x3(src.m00, src.m02, src.m03, src.m10, src.m12, src.m13, src.m20, src.m22, src.m23);
            float t32 = -determinant3x3(src.m00, src.m01, src.m03, src.m10, src.m11, src.m13, src.m20, src.m21, src.m23);
            float t33 = determinant3x3(src.m00, src.m01, src.m02, src.m10, src.m11, src.m12, src.m20, src.m21, src.m22);
            dest.m00 = t00 * determinant_inv; dest.m11 = t11 * determinant_inv; dest.m22 = t22 * determinant_inv; dest.m33 = t33 * determinant_inv;
            dest.m01 = t10 * determinant_inv; dest.m10 = t01 * determinant_inv; dest.m20 = t02 * determinant_inv; dest.m02 = t20 * determinant_inv;
            dest.m12 = t21 * determinant_inv; dest.m21 = t12 * determinant_inv; dest.m03 = t30 * determinant_inv; dest.m30 = t03 * determinant_inv;
            dest.m13 = t31 * determinant_inv; dest.m31 = t13 * determinant_inv; dest.m23 = t32 * determinant_inv; dest.m32 = t23 * determinant_inv;
            return dest;
        } else {
            return null;
        }
    }

    private static float determinant3x3(float t00, float t01, float t02, float t10, float t11, float t12, float t20, float t21, float t22) {
        return t00 * (t11 * t22 - t12 * t21) + t01 * (t12 * t20 - t10 * t22) + t02 * (t10 * t21 - t11 * t20);
    }

    public Matrix negate() {
        return negate(this);
    }

    public Matrix4f negate(Matrix4f dest) {
        if (dest == null) dest = new Matrix4f();
        dest.m00 = -m00; dest.m01 = -m01; dest.m02 = -m02; dest.m03 = -m03;
        dest.m10 = -m10; dest.m11 = -m11; dest.m12 = -m12; dest.m13 = -m13;
        dest.m20 = -m20; dest.m21 = -m21; dest.m22 = -m22; dest.m23 = -m23;
        dest.m30 = -m30; dest.m31 = -m31; dest.m32 = -m32; dest.m33 = -m33;
        return dest;
    }

    public Matrix4f scale(Vector3f vec) {
        m00 *= vec.x; m01 *= vec.x; m02 *= vec.x; m03 *= vec.x;
        m10 *= vec.y; m11 *= vec.y; m12 *= vec.y; m13 *= vec.y;
        m20 *= vec.z; m21 *= vec.z; m22 *= vec.z; m23 *= vec.z;
        return this;
    }

    public static Matrix4f scale(Vector3f vec, Matrix4f src, Matrix4f dest) {
        if (dest == null) dest = new Matrix4f();
        dest.m00 = src.m00 * vec.x; dest.m01 = src.m01 * vec.x; dest.m02 = src.m02 * vec.x; dest.m03 = src.m03 * vec.x;
        dest.m10 = src.m10 * vec.y; dest.m11 = src.m11 * vec.y; dest.m12 = src.m12 * vec.y; dest.m13 = src.m13 * vec.y;
        dest.m20 = src.m20 * vec.z; dest.m21 = src.m21 * vec.z; dest.m22 = src.m22 * vec.z; dest.m23 = src.m23 * vec.z;
        dest.m30 = src.m30; dest.m31 = src.m31; dest.m32 = src.m32; dest.m33 = src.m33;
        return dest;
    }

    public static Matrix4f rotate(float angle, Vector3f axis, Matrix4f src, Matrix4f dest) {
        if (dest == null) dest = new Matrix4f();
        float c = (float) Math.cos(angle);
        float s = (float) Math.sin(angle);
        float oneminusc = 1.0f - c;
        float xy = axis.x * axis.y;
        float yz = axis.y * axis.z;
        float xz = axis.x * axis.z;
        float xs = axis.x * s;
        float ys = axis.y * s;
        float zs = axis.z * s;
        float f00 = axis.x * axis.x * oneminusc + c;
        float f01 = xy * oneminusc + zs;
        float f02 = xz * oneminusc - ys;
        float f10 = xy * oneminusc - zs;
        float f11 = axis.y * axis.y * oneminusc + c;
        float f12 = yz * oneminusc + xs;
        float f20 = xz * oneminusc + ys;
        float f21 = yz * oneminusc - xs;
        float f22 = axis.z * axis.z * oneminusc + c;
        float t00 = src.m00 * f00 + src.m10 * f01 + src.m20 * f02;
        float t01 = src.m01 * f00 + src.m11 * f01 + src.m21 * f02;
        float t02 = src.m02 * f00 + src.m12 * f01 + src.m22 * f02;
        float t03 = src.m03 * f00 + src.m13 * f01 + src.m23 * f02;
        float t10 = src.m00 * f10 + src.m10 * f11 + src.m20 * f12;
        float t11 = src.m01 * f10 + src.m11 * f11 + src.m21 * f12;
        float t12 = src.m02 * f10 + src.m12 * f11 + src.m22 * f12;
        float t13 = src.m03 * f10 + src.m13 * f11 + src.m23 * f12;
        dest.m20 = src.m00 * f20 + src.m10 * f21 + src.m20 * f22;
        dest.m21 = src.m01 * f20 + src.m11 * f21 + src.m21 * f22;
        dest.m22 = src.m02 * f20 + src.m12 * f21 + src.m22 * f22;
        dest.m23 = src.m03 * f20 + src.m13 * f21 + src.m23 * f22;
        dest.m00 = t00; dest.m01 = t01; dest.m02 = t02; dest.m03 = t03;
        dest.m10 = t10; dest.m11 = t11; dest.m12 = t12; dest.m13 = t13;
        dest.m30 = src.m30; dest.m31 = src.m31; dest.m32 = src.m32; dest.m33 = src.m33;
        return dest;
    }

    public Matrix4f rotate(float angle, Vector3f axis) {
        return rotate(angle, axis, this, this);
    }

    public static Matrix4f translate(Vector3f vec, Matrix4f src, Matrix4f dest) {
        if (dest == null) dest = new Matrix4f();
        if (dest != src) dest.load(src);
        dest.m30 += src.m00 * vec.x + src.m10 * vec.y + src.m20 * vec.z;
        dest.m31 += src.m01 * vec.x + src.m11 * vec.y + src.m21 * vec.z;
        dest.m32 += src.m02 * vec.x + src.m12 * vec.y + src.m22 * vec.z;
        dest.m33 += src.m03 * vec.x + src.m13 * vec.y + src.m23 * vec.z;
        return dest;
    }

    public Matrix4f translate(Vector3f vec) {
        return translate(vec, this, this);
    }

    public Matrix4f translate(Vector2f vec) {
        return translate(new Vector3f(vec.x, vec.y, 0), this, this);
    }

    @Override
    public String toString() {
        return "Matrix4f:\n" +
            m00 + " " + m10 + " " + m20 + " " + m30 + "\n" +
            m01 + " " + m11 + " " + m21 + " " + m31 + "\n" +
            m02 + " " + m12 + " " + m22 + " " + m32 + "\n" +
            m03 + " " + m13 + " " + m23 + " " + m33;
    }
}
