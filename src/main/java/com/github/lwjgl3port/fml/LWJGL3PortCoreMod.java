package com.github.lwjgl3port.fml;

import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

@IFMLLoadingPlugin.MCVersion("1.8.9")
@IFMLLoadingPlugin.Name("LWJGL3PortCore")
public class LWJGL3PortCoreMod implements IFMLLoadingPlugin {

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
            }
        } catch (Exception e) {
            System.err.println("[LWJGL3Port] Failed to patch LaunchClassLoader: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[0];
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
