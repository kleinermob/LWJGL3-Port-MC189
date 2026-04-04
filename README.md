# LWJGL 3 Port for Minecraft 1.8.9

A Forge mod that replaces LWJGL 2 with LWJGL 3 via classpath shadowing. No bytecode hacks — just drop-in replacement classes backed by GLFW.

## Building

**Requirements:** Java 8 JDK (e.g. [Eclipse Adoptium](https://adoptium.net/))

```bash
# Set JAVA_HOME to your Java 8 installation
# Windows:
set JAVA_HOME=C:\path\to\jdk8
# Linux/macOS:
export JAVA_HOME=/path/to/jdk8

# First time only — sets up ForgeGradle workspace
./gradlew setupDecompWorkspace

# Build the mod JAR
./gradlew build
```

Output: `build/libs/LWJGL3Port-1.0.0.jar`

## Installation (PrismLauncher / MultiMC)

1. Create a Minecraft 1.8.9 instance with **Forge** installed
2. Copy `build/libs/LWJGL3Port-1.0.0.jar` into the instance's `mods/` folder
3. Copy `lwjgl3-1.8.9.json` into the instance's `patches/` folder
4. Launch the game

The JSON patch tells the launcher to provide LWJGL 3.3.3 libraries on the classpath. The mod JAR provides `org.lwjgl.opengl.Display`, `org.lwjgl.input.Keyboard`, `org.lwjgl.input.Mouse`, etc. — backed by GLFW internally.

## How It Works

Minecraft 1.8.9 calls LWJGL 2 APIs like `org.lwjgl.opengl.Display.create()`. Our mod ships classes in the **exact same packages** that get loaded before the original LWJGL 2 classes. These replacement classes internally use LWJGL 3 (GLFW) for window management, input, and OpenGL context.

**No Mixin redirects needed for LWJGL calls** — classpath priority handles everything. The only Mixin is a safety wrapper for `glGetString` in crash reports (with `require=0` so it never causes a fatal error).

### What's replaced

| LWJGL 2 Class | Backed By |
|---|---|
| `org.lwjgl.opengl.Display` | GLFW window + GL context |
| `org.lwjgl.opengl.DisplayMode` | Resolution descriptor |
| `org.lwjgl.opengl.PixelFormat` | GL pixel format hints |
| `org.lwjgl.input.Keyboard` | GLFW key/char callbacks + event queue |
| `org.lwjgl.input.Mouse` | GLFW cursor/button/scroll callbacks + event queue |
| `org.lwjgl.Sys` | GLFW timer + clipboard |
| `org.lwjgl.openal.AL` | Passthrough stub |

### What's NOT replaced

OpenGL calls (`GL11`, `GL15`, etc.) are **not** wrapped — LWJGL 3's OpenGL bindings are already API-compatible with LWJGL 2's static methods.

## License

MIT
