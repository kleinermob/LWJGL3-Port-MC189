package com.github.lwjgl3port;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.*;

/**
 * ASM transformer that rewrites LWJGL 2 AL.create()/destroy()/isCreated() calls
 * in paulscode's LibraryLWJGLOpenAL to use our OpenALHelper bridge instead.
 */
public class OpenALTransformer implements IClassTransformer {

    private static final String AL_OWNER = "org/lwjgl/openal/AL";
    private static final String AL10_OWNER = "org/lwjgl/openal/AL10";
    private static final String HELPER_OWNER = "com/github/lwjgl3port/OpenALHelper";

    // LWJGL 2 AL10 methods with IntBuffer param: (Ljava/nio/IntBuffer;)V
    private static final String INTBUF_V = "(Ljava/nio/IntBuffer;)V";
    // LWJGL 2 AL10 methods with (int, IntBuffer): (ILjava/nio/IntBuffer;)V
    private static final String INT_INTBUF_V = "(ILjava/nio/IntBuffer;)V";
    // LWJGL 2 AL10.alListener(int, FloatBuffer): (ILjava/nio/FloatBuffer;)V
    private static final String INT_FLOATBUF_V = "(ILjava/nio/FloatBuffer;)V";
    // LWJGL 2 AL10.alSource(int, int, FloatBuffer): (IILjava/nio/FloatBuffer;)V
    private static final String INT_INT_FLOATBUF_V = "(IILjava/nio/FloatBuffer;)V";
    // LWJGL 2 AL10.alBufferData(int, int, IntBuffer, int): (IILjava/nio/IntBuffer;I)V
    private static final String BUFFERDATA_INTBUF = "(IILjava/nio/IntBuffer;I)V";

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (basicClass == null) return null;

        // Only transform paulscode sound classes
        if (!name.startsWith("paulscode.sound.")) return basicClass;

        ClassReader cr = new ClassReader(basicClass);
        ClassWriter cw = new ClassWriter(cr, 0);
        ClassVisitor cv = new ClassVisitor(Opcodes.ASM5, cw) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
                return new MethodVisitor(Opcodes.ASM5, mv) {
                    @Override
                    public void visitMethodInsn(int opcode, String owner, String mName, String mDesc, boolean itf) {
                        if (opcode == Opcodes.INVOKESTATIC) {
                            // --- AL class redirects ---
                            if (owner.equals(AL_OWNER)) {
                                if (mName.equals("create") && mDesc.equals("()V")) {
                                    super.visitMethodInsn(Opcodes.INVOKESTATIC, HELPER_OWNER, "create", "()V", false);
                                    return;
                                } else if (mName.equals("destroy") && mDesc.equals("()V")) {
                                    super.visitMethodInsn(Opcodes.INVOKESTATIC, HELPER_OWNER, "destroy", "()V", false);
                                    return;
                                } else if (mName.equals("isCreated") && mDesc.equals("()Z")) {
                                    super.visitMethodInsn(Opcodes.INVOKESTATIC, HELPER_OWNER, "isCreated", "()Z", false);
                                    return;
                                }
                            }
                            // --- AL10 bulk IntBuffer/FloatBuffer redirects ---
                            if (owner.equals(AL10_OWNER)) {
                                // alSourceStop/Play/Rewind/Pause(IntBuffer)
                                if (mDesc.equals(INTBUF_V)) {
                                    if (mName.equals("alSourceStop") || mName.equals("alSourcePlay")
                                            || mName.equals("alSourceRewind") || mName.equals("alSourcePause")
                                            || mName.equals("alGenSources") || mName.equals("alDeleteSources")
                                            || mName.equals("alGenBuffers") || mName.equals("alDeleteBuffers")) {
                                        super.visitMethodInsn(Opcodes.INVOKESTATIC, HELPER_OWNER, mName, mDesc, false);
                                        return;
                                    }
                                }
                                // alSourceQueueBuffers/alSourceUnqueueBuffers(int, IntBuffer)
                                if (mDesc.equals(INT_INTBUF_V)) {
                                    if (mName.equals("alSourceQueueBuffers") || mName.equals("alSourceUnqueueBuffers")) {
                                        super.visitMethodInsn(Opcodes.INVOKESTATIC, HELPER_OWNER, mName, mDesc, false);
                                        return;
                                    }
                                }
                                // alListener(int, FloatBuffer)
                                if (mName.equals("alListener") && mDesc.equals(INT_FLOATBUF_V)) {
                                    super.visitMethodInsn(Opcodes.INVOKESTATIC, HELPER_OWNER, "alListener", mDesc, false);
                                    return;
                                }
                                // alSource(int, int, FloatBuffer)
                                if (mName.equals("alSource") && mDesc.equals(INT_INT_FLOATBUF_V)) {
                                    super.visitMethodInsn(Opcodes.INVOKESTATIC, HELPER_OWNER, "alSource", mDesc, false);
                                    return;
                                }
                                // alBufferData(int, int, IntBuffer, int)
                                if (mName.equals("alBufferData") && mDesc.equals(BUFFERDATA_INTBUF)) {
                                    super.visitMethodInsn(Opcodes.INVOKESTATIC, HELPER_OWNER, "alBufferData", mDesc, false);
                                    return;
                                }
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
