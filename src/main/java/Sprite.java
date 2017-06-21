import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL;

import java.awt.*;
import java.nio.ByteBuffer;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;

public class Sprite {

    private Rectangle rect;
    private ByteBuffer raster;
    private int rgb;
    private int id;

    public Sprite(int rgb, Rectangle rect){
        this.rgb = rgb;
        this.rect = rect;
        this.raster = createRaster();
    }

    public int getRgb() {
        return rgb;
    }

    public ByteBuffer getRaster() {
        return raster;
    }

    public int getId() {return id; }

    public Rectangle getRect(){
        return rect;
    }

    private ByteBuffer createRaster() {
        ByteBuffer buffer = BufferUtils.createByteBuffer(rect.width * rect.height * 3);
        for (int y = 0; y < rect.height; y++) {
            for (int x = 0; x < rect.width; x++) {
                buffer.put((byte) ((rgb >> 16) & 0xFF));
                buffer.put((byte) ((rgb >> 8) & 0xFF));
                buffer.put((byte) (rgb & 0xFF));
            }
        }
        this.id = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, id);
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
        System.out.println(id);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, rect.width, rect.height, 0, GL_RGB, GL_UNSIGNED_BYTE, buffer);
        return buffer;
    }

}
