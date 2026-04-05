package com.github.lwjgl3port.fml;

import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@IFMLLoadingPlugin.MCVersion("1.8.9")
@IFMLLoadingPlugin.Name("LWJGL3PortCore")
public class LWJGL3PortCoreMod implements IFMLLoadingPlugin {

    // Shadow classes that conflict with LWJGL 3 and must be pre-defined.
    // NOTE: AL is NOT here — we use an ASM transformer instead (see OpenALTransformer)
    private static final String[] SHADOW_CLASSES = {
        "org.lwjgl.opengl.GL20"
    };

    public LWJGL3PortCoreMod() {
        System.out.println("[LWJGL3Port] Removing org.lwjgl. from LaunchClassLoader exclusions...");
        try {
            ClassLoader cl = getClass().getClassLoader();
            if (cl instanceof LaunchClassLoader) {
                LaunchClassLoader lcl = (LaunchClassLoader) cl;

                // Remove org.lwjgl. from classLoaderExceptions so our mod's
                // org.lwjgl.* classes get loaded from the mod JAR
                Field exceptionsField = LaunchClassLoader.class.getDeclaredField("classLoaderExceptions");
                exceptionsField.setAccessible(true);
                @SuppressWarnings("unchecked")
                Set<String> exceptions = (Set<String>) exceptionsField.get(lcl);
                boolean removed = exceptions.remove("org.lwjgl.");
                System.out.println("[LWJGL3Port] Removed org.lwjgl. exclusion: " + removed);

                // ADD org.lwjgl. to transformerExceptions so LCL loads the classes
                // from its URLs (mod JAR + LWJGL 3 JARs) but does NOT run ASM transformers
                // which would corrupt native method bindings
                Field transformerField = LaunchClassLoader.class.getDeclaredField("transformerExceptions");
                transformerField.setAccessible(true);
                @SuppressWarnings("unchecked")
                Set<String> transformerExceptions = (Set<String>) transformerField.get(lcl);
                transformerExceptions.add("org.lwjgl.");
                System.out.println("[LWJGL3Port] ClassLoader exclusions patched successfully.");

                // Pre-define shadow classes that conflict with LWJGL 3.
                // Since LWJGL 3 JARs are on the URLClassPath before our mod JAR,
                // classes like GL20 and AL would resolve to LWJGL 3's versions.
                // By defining them early and injecting into cachedClasses, our
                // shadow versions take priority.
                preDefineShadowClasses(lcl);
            }
        } catch (Exception e) {
            System.err.println("[LWJGL3Port] Failed to patch LaunchClassLoader: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void preDefineShadowClasses(LaunchClassLoader lcl) {
        try {
            // Locate our mod JAR via the CodeSource of this class
            URL modJarUrl = LWJGL3PortCoreMod.class.getProtectionDomain().getCodeSource().getLocation();
            java.io.File modJarFile = new java.io.File(modJarUrl.toURI());
            JarFile jar = new JarFile(modJarFile);

            // Access LaunchClassLoader's cachedClasses map
            Field cachedClassesField = LaunchClassLoader.class.getDeclaredField("cachedClasses");
            cachedClassesField.setAccessible(true);
            @SuppressWarnings("unchecked")
            Map<String, Class<?>> cachedClasses = (Map<String, Class<?>>) cachedClassesField.get(lcl);

            // Get the protected defineClass method
            Method defineClassMethod = ClassLoader.class.getDeclaredMethod(
                "defineClass", String.class, byte[].class, int.class, int.class);
            defineClassMethod.setAccessible(true);

            for (String className : SHADOW_CLASSES) {
                String entryName = className.replace('.', '/') + ".class";
                JarEntry entry = jar.getJarEntry(entryName);
                if (entry != null) {
                    byte[] bytes = readAllBytes(jar.getInputStream(entry));
                    Class<?> clazz = (Class<?>) defineClassMethod.invoke(
                        lcl, className, bytes, 0, bytes.length);
                    cachedClasses.put(className, clazz);
                    System.out.println("[LWJGL3Port] Pre-defined shadow class: " + className);
                } else {
                    System.err.println("[LWJGL3Port] Shadow class not found in JAR: " + entryName);
                }
            }
            jar.close();
        } catch (Exception e) {
            System.err.println("[LWJGL3Port] Failed to pre-define shadow classes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static byte[] readAllBytes(InputStream is) throws java.io.IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[4096];
        int len;
        while ((len = is.read(buf)) != -1) {
            bos.write(buf, 0, len);
        }
        is.close();
        return bos.toByteArray();
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[]{
            "com.github.lwjgl3port.OpenALTransformer",
            "com.github.lwjgl3port.GLMethodTransformer"
        };
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
