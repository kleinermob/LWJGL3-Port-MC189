# LWJGL 3 Port for Minecraft 1.8.9

A not very useful Forge mod that demonstrates how to bridge LWJGL 2 to LWJGL 3, letting Minecraft 1.8.9 run on LWJGL 3 instead of LWJGL 2. It acts as a **compatibility shim** — Minecraft's bytecode is unchanged, but all LWJGL 2 API calls are transparently translated to LWJGL 3 at runtime.

## Why?

- **LWJGL 2 is abandoned** — no updates, broken on modern Java (17+), issues on newer macOS and Wayland
- **LWJGL 3 is actively maintained** — better performance, modern platform support, required by newer launchers
- **PrismLauncher / MultiMC already ship LWJGL 3** — this mod makes 1.8.9 compatible without needing old LWJGL 2 JARs

## How It Works

The mod uses three techniques to bridge LWJGL 2 → LWJGL 3:

### 1. Shadow Classes
Drop-in replacements for LWJGL 2 classes that were removed or restructured in LWJGL 3. They implement the LWJGL 2 API but delegate to LWJGL 3 internally:

| LWJGL 2 Class | LWJGL 3 Backend |
|---|---|
| `org.lwjgl.opengl.Display` | GLFW window + context management |
| `org.lwjgl.opengl.DisplayMode` | Resolution/refresh-rate descriptor |
| `org.lwjgl.opengl.GL20` | `GL20C` (bridges `ByteBuffer` → `CharSequence` signatures) |
| `org.lwjgl.opengl.GLContext` | `GL.createCapabilities()` |
| `org.lwjgl.opengl.ContextCapabilities` | `GLCapabilities` wrapper |
| `org.lwjgl.input.Keyboard` | GLFW key/char callbacks + event queue |
| `org.lwjgl.input.Mouse` | GLFW cursor/button/scroll callbacks + event queue |
| `org.lwjgl.util.vector.*` | Reimplemented (Vector2f, Vector3f, Vector4f, Matrix4f, etc.) |
| `org.lwjgl.util.glu.GLU` | Reimplemented `gluPerspective`, `gluErrorString`, `gluProject`, `gluUnProject` |

### 2. ASM Bytecode Transformers
For cases where LWJGL 3 has the same class but methods were renamed:

- **`GLMethodTransformer`** — renames GL11 buffer methods: `glGetFloat` → `glGetFloatv`, `glLight` → `glLightfv`, `glMultMatrix` → `glMultMatrixf`, etc.
- **`OpenALTransformer`** — redirects paulscode's `AL.create()`, `AL10.alSourcePlay(IntBuffer)`, and other bulk audio methods to our `OpenALHelper` bridge

### 3. CoreMod ClassLoader Patching
`LWJGL3PortCoreMod` manipulates Forge's `LaunchClassLoader` so our shadow classes get loaded instead of LWJGL 3's conflicting versions (e.g., our `GL20` instead of LWJGL 3's `GL20`).

## Building

**Requirements:** Java 8 JDK (e.g., [Eclipse Adoptium JDK 8](https://adoptium.net/))

```bash
# Set JAVA_HOME to your Java 8 installation
# Windows PowerShell:
$env:JAVA_HOME = "C:\path\to\jdk8"
# Linux/macOS:
export JAVA_HOME=/path/to/jdk8

# First time only — sets up ForgeGradle workspace
./gradlew setupDecompWorkspace

# Build the mod JAR
./gradlew build
```

Output: `build/libs/LWJGL3Port-1.0.0.jar`

## Installation (PrismLauncher / MultiMC)

### Step 1: Create the instance
1. Create a **Minecraft 1.8.9** instance
2. Install **Forge** for 1.8.9 (version 11.15.1.2318)

### Step 2: Add the LWJGL 3 library patch
1. Copy `lwjgl3-1.8.9.json` and **rename it to `org.lwjgl.json`**
2. Place it in your instance's `patches/` folder, **overwriting** the existing `org.lwjgl.json`
   - PrismLauncher: `~/.local/share/PrismLauncher/instances/<name>/patches/`
   - Windows: `%APPDATA%/PrismLauncher/instances/<name>/patches/`
3. This tells the launcher to provide LWJGL 3.3.3 libraries on the classpath instead of LWJGL 2

### Step 3: Install the mod
1. Copy `build/libs/LWJGL3Port-1.0.0.jar` into the instance's `minecraft/mods/` folder

### Step 4: Launch
Launch the game normally. You should see `[LWJGL3Port]` messages in the log confirming the shim is active.

## What's NOT Replaced

Standard OpenGL calls (`GL11`, `GL13`, `GL14`, `GL15`, `GL30`, etc.) are **not** wrapped — LWJGL 3's OpenGL bindings are already API-compatible with LWJGL 2's static methods. Only methods where names changed (buffer-accepting variants that gained a `v`/`f`/`d` suffix) are patched via ASM.

## Compatibility

- **Minecraft**: 1.8.9
- **Forge**: 11.15.1.2318
- **LWJGL 3**: 3.3.3
- **Java**: 8+ (built with Java 8)
- **Platform**: Windows (Linux/macOS would need platform-specific natives in `lwjgl3-1.8.9.json`)

## Important Notes

**Mod compatibility:** This should work with any Minecraft 1.8.9 Forge mod because it's only a wrapper/shim — it doesn't modify Minecraft's bytecode, just translates LWJGL calls at runtime. Mods that use LWJGL 2 APIs (paulscode audio, shader mods, etc.) will automatically benefit, unless something specific is not implemented.

**Native LWJGL 3 port basis:** The code in this project serves as a reference implementation for actually porting Minecraft 1.8.9 to native LWJGL 3. About 60-70% of the integration work (Display, Mouse, Keyboard, OpenAL init, GL20 bridging, GLU) would be reused almost verbatim — you'd just apply these changes directly to Minecraft's source instead of wrapping them in a compatibility layer.

## License

MIT
