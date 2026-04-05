package com.github.lwjgl3port;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.*;

import java.util.HashMap;
import java.util.Map;

/**
 * ASM transformer that renames LWJGL 2 GL method names to LWJGL 3 equivalents.
 * LWJGL 2 dropped the OpenGL 'v' suffix for buffer-accepting methods;
 * LWJGL 3 restored it. This transformer patches Minecraft's bytecode.
 */
public class GLMethodTransformer implements IClassTransformer {

    private static final String GL11 = "org/lwjgl/opengl/GL11";

    /**
     * Map of (methodName + descriptor) -> renamedMethodName for GL11.
     * Only buffer-accepting overloads need renaming; scalar versions keep their names.
     */
    private static final Map<String, String> GL11_RENAMES = new HashMap<>();

    static {
        // glGetXxx(int, Buffer) -> glGetXxxv
        GL11_RENAMES.put("glGetFloat|(ILjava/nio/FloatBuffer;)V", "glGetFloatv");
        GL11_RENAMES.put("glGetInteger|(ILjava/nio/IntBuffer;)V", "glGetIntegerv");
        GL11_RENAMES.put("glGetDouble|(ILjava/nio/DoubleBuffer;)V", "glGetDoublev");
        GL11_RENAMES.put("glGetBoolean|(ILjava/nio/ByteBuffer;)V", "glGetBooleanv");

        // glLight(int, int, FloatBuffer) -> glLightfv
        GL11_RENAMES.put("glLight|(IILjava/nio/FloatBuffer;)V", "glLightfv");
        // glLightModel(int, FloatBuffer) -> glLightModelfv
        GL11_RENAMES.put("glLightModel|(ILjava/nio/FloatBuffer;)V", "glLightModelfv");
        // glMaterial(int, int, FloatBuffer) -> glMaterialfv
        GL11_RENAMES.put("glMaterial|(IILjava/nio/FloatBuffer;)V", "glMaterialfv");

        // glFog(int, FloatBuffer/IntBuffer) -> glFogfv/glFogiv
        GL11_RENAMES.put("glFog|(ILjava/nio/FloatBuffer;)V", "glFogfv");
        GL11_RENAMES.put("glFog|(ILjava/nio/IntBuffer;)V", "glFogiv");

        // glTexEnv(int, int, FloatBuffer/IntBuffer) -> glTexEnvfv/glTexEnviv
        GL11_RENAMES.put("glTexEnv|(IILjava/nio/FloatBuffer;)V", "glTexEnvfv");
        GL11_RENAMES.put("glTexEnv|(IILjava/nio/IntBuffer;)V", "glTexEnviv");

        // glTexGen(int, int, FloatBuffer/DoubleBuffer) -> glTexGenfv/glTexGendv
        GL11_RENAMES.put("glTexGen|(IILjava/nio/FloatBuffer;)V", "glTexGenfv");
        GL11_RENAMES.put("glTexGen|(IILjava/nio/DoubleBuffer;)V", "glTexGendv");

        // glMultMatrix(FloatBuffer) -> glMultMatrixf
        GL11_RENAMES.put("glMultMatrix|(Ljava/nio/FloatBuffer;)V", "glMultMatrixf");
        GL11_RENAMES.put("glMultMatrix|(Ljava/nio/DoubleBuffer;)V", "glMultMatrixd");

        // glLoadMatrix(FloatBuffer) -> glLoadMatrixf
        GL11_RENAMES.put("glLoadMatrix|(Ljava/nio/FloatBuffer;)V", "glLoadMatrixf");
        GL11_RENAMES.put("glLoadMatrix|(Ljava/nio/DoubleBuffer;)V", "glLoadMatrixd");

        // glTexImage1D / glTexImage2D with buffer variants
        // LWJGL 2: glTexImage2D(int,int,int,int,int,int,int,int,ByteBuffer)
        // LWJGL 3: same name — no rename needed

        // glPixelStore(int, int) -> same in LWJGL 3, no rename
        // glReadPixels — same name in LWJGL 3

        // glGetTexImage with buffer — LWJGL 3 keeps same name
    }

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (basicClass == null) return null;

        // Skip classes that can't reference LWJGL, and our own package to avoid ClassCircularityError
        if (name.startsWith("java.") || name.startsWith("sun.")
                || name.startsWith("org.objectweb.") || name.startsWith("org.apache.")
                || name.startsWith("com.google.") || name.startsWith("io.netty.")
                || name.startsWith("com.github.lwjgl3port.")
                || name.startsWith("net.minecraft.launchwrapper.")
                || name.startsWith("org.lwjgl.")) {
            return basicClass;
        }

        ClassReader cr = new ClassReader(basicClass);
        ClassWriter cw = new ClassWriter(cr, 0);
        ClassVisitor cv = new ClassVisitor(Opcodes.ASM5, cw) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String desc,
                                             String signature, String[] exceptions) {
                MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
                return new MethodVisitor(Opcodes.ASM5, mv) {
                    @Override
                    public void visitMethodInsn(int opcode, String owner, String mName,
                                                String mDesc, boolean itf) {
                        if (opcode == Opcodes.INVOKESTATIC && owner.equals(GL11)) {
                            String key = mName + "|" + mDesc;
                            String renamed = GL11_RENAMES.get(key);
                            if (renamed != null) {
                                super.visitMethodInsn(opcode, owner, renamed, mDesc, itf);
                                return;
                            }
                        }
                        super.visitMethodInsn(opcode, owner, mName, mDesc, itf);
                    }
                };
            }
        };
        cr.accept(cv, 0);
        return cw.toByteArray();
    }
}
