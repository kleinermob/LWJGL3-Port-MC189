package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.system.MemoryUtil;

/**
 * Shadow of LWJGL 2's GL20 — bridges legacy method signatures to LWJGL 3's GL20C.
 */
public class GL20 {

    public static final int GL_SHADING_LANGUAGE_VERSION = 0x8B8C;
    public static final int GL_CURRENT_PROGRAM = 0x8B8D;
    public static final int GL_SHADER_TYPE = 0x8B4F;
    public static final int GL_DELETE_STATUS = 0x8B80;
    public static final int GL_COMPILE_STATUS = 0x8B81;
    public static final int GL_LINK_STATUS = 0x8B82;
    public static final int GL_VALIDATE_STATUS = 0x8B83;
    public static final int GL_INFO_LOG_LENGTH = 0x8B84;
    public static final int GL_ATTACHED_SHADERS = 0x8B85;
    public static final int GL_ACTIVE_UNIFORMS = 0x8B86;
    public static final int GL_ACTIVE_UNIFORM_MAX_LENGTH = 0x8B87;
    public static final int GL_ACTIVE_ATTRIBUTES = 0x8B89;
    public static final int GL_ACTIVE_ATTRIBUTE_MAX_LENGTH = 0x8B8A;
    public static final int GL_SHADER_SOURCE_LENGTH = 0x8B88;
    public static final int GL_VERTEX_SHADER = 0x8B31;
    public static final int GL_FRAGMENT_SHADER = 0x8B30;
    public static final int GL_FLOAT_VEC2 = 0x8B50;
    public static final int GL_FLOAT_VEC3 = 0x8B51;
    public static final int GL_FLOAT_VEC4 = 0x8B52;
    public static final int GL_INT_VEC2 = 0x8B53;
    public static final int GL_INT_VEC3 = 0x8B54;
    public static final int GL_INT_VEC4 = 0x8B55;
    public static final int GL_BOOL = 0x8B56;
    public static final int GL_BOOL_VEC2 = 0x8B57;
    public static final int GL_BOOL_VEC3 = 0x8B58;
    public static final int GL_BOOL_VEC4 = 0x8B59;
    public static final int GL_FLOAT_MAT2 = 0x8B5A;
    public static final int GL_FLOAT_MAT3 = 0x8B5B;
    public static final int GL_FLOAT_MAT4 = 0x8B5C;
    public static final int GL_SAMPLER_1D = 0x8B5D;
    public static final int GL_SAMPLER_2D = 0x8B5E;
    public static final int GL_SAMPLER_3D = 0x8B5F;
    public static final int GL_SAMPLER_CUBE = 0x8B60;
    public static final int GL_SAMPLER_1D_SHADOW = 0x8B61;
    public static final int GL_SAMPLER_2D_SHADOW = 0x8B62;
    public static final int GL_MAX_VERTEX_ATTRIBS = 0x8869;
    public static final int GL_MAX_TEXTURE_IMAGE_UNITS = 0x8872;
    public static final int GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS = 0x8B4D;
    public static final int GL_MAX_VERTEX_TEXTURE_IMAGE_UNITS = 0x8B4C;
    public static final int GL_MAX_VERTEX_UNIFORM_COMPONENTS = 0x8B4A;
    public static final int GL_MAX_FRAGMENT_UNIFORM_COMPONENTS = 0x8B49;
    public static final int GL_MAX_VARYING_FLOATS = 0x8B4B;
    public static final int GL_VERTEX_ATTRIB_ARRAY_ENABLED = 0x8622;
    public static final int GL_VERTEX_ATTRIB_ARRAY_SIZE = 0x8623;
    public static final int GL_VERTEX_ATTRIB_ARRAY_STRIDE = 0x8624;
    public static final int GL_VERTEX_ATTRIB_ARRAY_TYPE = 0x8625;
    public static final int GL_VERTEX_ATTRIB_ARRAY_NORMALIZED = 0x886A;
    public static final int GL_VERTEX_ATTRIB_ARRAY_POINTER = 0x8645;
    public static final int GL_VERTEX_PROGRAM_POINT_SIZE = 0x8642;
    public static final int GL_DRAW_BUFFER0 = 0x8825;
    public static final int GL_DRAW_BUFFER1 = 0x8826;
    public static final int GL_DRAW_BUFFER2 = 0x8827;
    public static final int GL_DRAW_BUFFER3 = 0x8828;
    public static final int GL_DRAW_BUFFER4 = 0x8829;
    public static final int GL_DRAW_BUFFER5 = 0x882A;
    public static final int GL_DRAW_BUFFER6 = 0x882B;
    public static final int GL_DRAW_BUFFER7 = 0x882C;
    public static final int GL_DRAW_BUFFER8 = 0x882D;
    public static final int GL_DRAW_BUFFER9 = 0x882E;
    public static final int GL_DRAW_BUFFER10 = 0x882F;
    public static final int GL_DRAW_BUFFER11 = 0x8830;
    public static final int GL_DRAW_BUFFER12 = 0x8831;
    public static final int GL_DRAW_BUFFER13 = 0x8832;
    public static final int GL_DRAW_BUFFER14 = 0x8833;
    public static final int GL_DRAW_BUFFER15 = 0x8834;
    public static final int GL_MAX_DRAW_BUFFERS = 0x8824;
    public static final int GL_BLEND_EQUATION_ALPHA = 0x883D;
    public static final int GL_POINT_SPRITE = 0x8861;
    public static final int GL_COORD_REPLACE = 0x8862;
    public static final int GL_STENCIL_BACK_FUNC = 0x8800;
    public static final int GL_STENCIL_BACK_FAIL = 0x8801;
    public static final int GL_STENCIL_BACK_PASS_DEPTH_FAIL = 0x8802;
    public static final int GL_STENCIL_BACK_PASS_DEPTH_PASS = 0x8803;
    public static final int GL_STENCIL_BACK_REF = 0x8CA3;
    public static final int GL_STENCIL_BACK_VALUE_MASK = 0x8CA4;
    public static final int GL_STENCIL_BACK_WRITEMASK = 0x8CA5;
    public static final int GL_LOWER_LEFT = 0x8CA1;
    public static final int GL_UPPER_LEFT = 0x8CA2;

    // ---- Shader source (LWJGL 2 signature: ByteBuffer) ----
    public static void glShaderSource(int shader, ByteBuffer string) {
        byte[] bytes = new byte[string.remaining()];
        string.get(bytes);
        string.position(string.position() - bytes.length);
        GL20C.glShaderSource(shader, new String(bytes));
    }

    public static void glShaderSource(int shader, CharSequence source) {
        GL20C.glShaderSource(shader, source);
    }

    public static void glShaderSource(int shader, CharSequence[] sources) {
        StringBuilder sb = new StringBuilder();
        for (CharSequence s : sources) sb.append(s);
        GL20C.glShaderSource(shader, sb.toString());
    }

    // ---- Shader lifecycle ----
    public static int glCreateShader(int type) {
        return GL20C.glCreateShader(type);
    }

    public static void glCompileShader(int shader) {
        GL20C.glCompileShader(shader);
    }

    public static void glDeleteShader(int shader) {
        GL20C.glDeleteShader(shader);
    }

    public static boolean glIsShader(int shader) {
        return GL20C.glIsShader(shader);
    }

    // ---- Program lifecycle ----
    public static int glCreateProgram() {
        return GL20C.glCreateProgram();
    }

    public static void glAttachShader(int program, int shader) {
        GL20C.glAttachShader(program, shader);
    }

    public static void glDetachShader(int program, int shader) {
        GL20C.glDetachShader(program, shader);
    }

    public static void glLinkProgram(int program) {
        GL20C.glLinkProgram(program);
    }

    public static void glUseProgram(int program) {
        GL20C.glUseProgram(program);
    }

    public static void glValidateProgram(int program) {
        GL20C.glValidateProgram(program);
    }

    public static void glDeleteProgram(int program) {
        GL20C.glDeleteProgram(program);
    }

    public static boolean glIsProgram(int program) {
        return GL20C.glIsProgram(program);
    }

    // ---- Query shader / program ----
    // LWJGL 3 names
    public static void glGetShaderiv(int shader, int pname, IntBuffer params) {
        GL20C.glGetShaderiv(shader, pname, params);
    }

    public static int glGetShaderi(int shader, int pname) {
        return GL20C.glGetShaderi(shader, pname);
    }

    public static void glGetProgramiv(int program, int pname, IntBuffer params) {
        GL20C.glGetProgramiv(program, pname, params);
    }

    public static int glGetProgrami(int program, int pname) {
        return GL20C.glGetProgrami(program, pname);
    }

    // LWJGL 2 names (stripped postfix)
    public static void glGetShader(int shader, int pname, IntBuffer params) {
        GL20C.glGetShaderiv(shader, pname, params);
    }

    public static int glGetShader(int shader, int pname) {
        return GL20C.glGetShaderi(shader, pname);
    }

    public static void glGetProgram(int program, int pname, IntBuffer params) {
        GL20C.glGetProgramiv(program, pname, params);
    }

    public static int glGetProgram(int program, int pname) {
        return GL20C.glGetProgrami(program, pname);
    }

    public static String glGetShaderInfoLog(int shader, int maxLength) {
        return GL20C.glGetShaderInfoLog(shader, maxLength);
    }

    public static void glGetShaderInfoLog(int shader, IntBuffer length, ByteBuffer infoLog) {
        GL20C.glGetShaderInfoLog(shader, length, infoLog);
    }

    public static String glGetProgramInfoLog(int program, int maxLength) {
        return GL20C.glGetProgramInfoLog(program, maxLength);
    }

    public static void glGetProgramInfoLog(int program, IntBuffer length, ByteBuffer infoLog) {
        GL20C.glGetProgramInfoLog(program, length, infoLog);
    }

    // ---- Shader source query ----
    public static String glGetShaderSource(int shader, int maxLength) {
        return GL20C.glGetShaderSource(shader, maxLength);
    }

    public static void glGetShaderSource(int shader, IntBuffer length, ByteBuffer source) {
        GL20C.glGetShaderSource(shader, length, source);
    }

    // ---- Uniforms ----
    public static int glGetUniformLocation(int program, CharSequence name) {
        return GL20C.glGetUniformLocation(program, name);
    }

    public static int glGetUniformLocation(int program, ByteBuffer name) {
        return GL20C.glGetUniformLocation(program, MemoryUtil.memASCII(name));
    }

    public static void glUniform1f(int location, float v0) {
        GL20C.glUniform1f(location, v0);
    }

    public static void glUniform2f(int location, float v0, float v1) {
        GL20C.glUniform2f(location, v0, v1);
    }

    public static void glUniform3f(int location, float v0, float v1, float v2) {
        GL20C.glUniform3f(location, v0, v1, v2);
    }

    public static void glUniform4f(int location, float v0, float v1, float v2, float v3) {
        GL20C.glUniform4f(location, v0, v1, v2, v3);
    }

    public static void glUniform1i(int location, int v0) {
        GL20C.glUniform1i(location, v0);
    }

    public static void glUniform2i(int location, int v0, int v1) {
        GL20C.glUniform2i(location, v0, v1);
    }

    public static void glUniform3i(int location, int v0, int v1, int v2) {
        GL20C.glUniform3i(location, v0, v1, v2);
    }

    public static void glUniform4i(int location, int v0, int v1, int v2, int v3) {
        GL20C.glUniform4i(location, v0, v1, v2, v3);
    }

    public static void glUniform1fv(int location, FloatBuffer values) {
        GL20C.glUniform1fv(location, values);
    }

    public static void glUniform2fv(int location, FloatBuffer values) {
        GL20C.glUniform2fv(location, values);
    }

    public static void glUniform3fv(int location, FloatBuffer values) {
        GL20C.glUniform3fv(location, values);
    }

    public static void glUniform4fv(int location, FloatBuffer values) {
        GL20C.glUniform4fv(location, values);
    }

    public static void glUniform1iv(int location, IntBuffer values) {
        GL20C.glUniform1iv(location, values);
    }

    public static void glUniform2iv(int location, IntBuffer values) {
        GL20C.glUniform2iv(location, values);
    }

    public static void glUniform3iv(int location, IntBuffer values) {
        GL20C.glUniform3iv(location, values);
    }

    public static void glUniform4iv(int location, IntBuffer values) {
        GL20C.glUniform4iv(location, values);
    }

    public static void glUniformMatrix2fv(int location, boolean transpose, FloatBuffer matrices) {
        GL20C.glUniformMatrix2fv(location, transpose, matrices);
    }

    public static void glUniformMatrix3fv(int location, boolean transpose, FloatBuffer matrices) {
        GL20C.glUniformMatrix3fv(location, transpose, matrices);
    }

    public static void glUniformMatrix4fv(int location, boolean transpose, FloatBuffer matrices) {
        GL20C.glUniformMatrix4fv(location, transpose, matrices);
    }

    public static void glGetUniformfv(int program, int location, FloatBuffer params) {
        GL20C.glGetUniformfv(program, location, params);
    }

    public static void glGetUniformiv(int program, int location, IntBuffer params) {
        GL20C.glGetUniformiv(program, location, params);
    }

    // LWJGL 2 names (stripped postfix)
    public static void glGetUniform(int program, int location, FloatBuffer params) {
        GL20C.glGetUniformfv(program, location, params);
    }

    public static void glGetUniform(int program, int location, IntBuffer params) {
        GL20C.glGetUniformiv(program, location, params);
    }

    // ---- Attributes ----
    public static int glGetAttribLocation(int program, CharSequence name) {
        return GL20C.glGetAttribLocation(program, name);
    }

    public static int glGetAttribLocation(int program, ByteBuffer name) {
        return GL20C.glGetAttribLocation(program, MemoryUtil.memASCII(name));
    }

    public static void glBindAttribLocation(int program, int index, CharSequence name) {
        GL20C.glBindAttribLocation(program, index, name);
    }

    public static void glBindAttribLocation(int program, int index, ByteBuffer name) {
        GL20C.glBindAttribLocation(program, index, MemoryUtil.memASCII(name));
    }

    public static void glEnableVertexAttribArray(int index) {
        GL20C.glEnableVertexAttribArray(index);
    }

    public static void glDisableVertexAttribArray(int index) {
        GL20C.glDisableVertexAttribArray(index);
    }

    public static void glVertexAttribPointer(int index, int size, int type, boolean normalized, int stride, long pointer) {
        GL20C.glVertexAttribPointer(index, size, type, normalized, stride, pointer);
    }

    public static void glVertexAttribPointer(int index, int size, int type, boolean normalized, int stride, ByteBuffer pointer) {
        GL20C.glVertexAttribPointer(index, size, type, normalized, stride, pointer);
    }

    public static void glVertexAttrib1f(int index, float v0) {
        GL20C.glVertexAttrib1f(index, v0);
    }

    public static void glVertexAttrib2f(int index, float v0, float v1) {
        GL20C.glVertexAttrib2f(index, v0, v1);
    }

    public static void glVertexAttrib3f(int index, float v0, float v1, float v2) {
        GL20C.glVertexAttrib3f(index, v0, v1, v2);
    }

    public static void glVertexAttrib4f(int index, float v0, float v1, float v2, float v3) {
        GL20C.glVertexAttrib4f(index, v0, v1, v2, v3);
    }

    // ---- Active uniform / attrib query ----
    public static void glGetActiveUniform(int program, int index, IntBuffer length, IntBuffer size, IntBuffer type, ByteBuffer name) {
        GL20C.glGetActiveUniform(program, index, length, size, type, name);
    }

    public static void glGetActiveAttrib(int program, int index, IntBuffer length, IntBuffer size, IntBuffer type, ByteBuffer name) {
        GL20C.glGetActiveAttrib(program, index, length, size, type, name);
    }

    // ---- Draw buffers ----
    public static void glDrawBuffers(IntBuffer buffers) {
        GL20C.glDrawBuffers(buffers);
    }

    public static void glDrawBuffers(int buffer) {
        GL20C.glDrawBuffers(buffer);
    }

    // ---- Stencil ----
    public static void glStencilFuncSeparate(int face, int func, int ref, int mask) {
        GL20C.glStencilFuncSeparate(face, func, ref, mask);
    }

    public static void glStencilOpSeparate(int face, int sfail, int dpfail, int dppass) {
        GL20C.glStencilOpSeparate(face, sfail, dpfail, dppass);
    }

    public static void glStencilMaskSeparate(int face, int mask) {
        GL20C.glStencilMaskSeparate(face, mask);
    }

    // ---- Blend ----
    public static void glBlendEquationSeparate(int modeRGB, int modeAlpha) {
        GL20C.glBlendEquationSeparate(modeRGB, modeAlpha);
    }
}
