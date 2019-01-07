package org.lwjglb.game;

import org.lwjgl.system.MemoryUtil;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import org.lwjglb.engine.graph.ShaderProgram;
import org.lwjglb.engine.Utils;
import java.nio.FloatBuffer;
import org.lwjglb.engine.Window;

public class Renderer {

    private ShaderProgram shaderProgram;
    private int vboId;
    private int vaoId;

    public void init() throws Exception {
        // Create the shader program and load its resources
        shaderProgram = new ShaderProgram();
        shaderProgram.CreateVertexShader(Utils.Load("/resources/vertex.vs"));
        shaderProgram.CreateFragmentShader(Utils.Load("/resources/fragment.fs"));
        shaderProgram.Link();

        // A single triangle
        float[] vertices = new float[]{
                0.0f, 0.5f, 0.0f,
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f
        };

        // Float buffer
        FloatBuffer verticesBuffer = MemoryUtil.memAllocFloat(vertices.length);
        verticesBuffer.put(vertices).flip();

        int vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId);

        vboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);

        if (verticesBuffer != null){
            MemoryUtil.memFree(verticesBuffer);
        }
    }

    public void Clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void Render(Window window){
        Clear();

        if (window.IsResized()){
            glViewport(0, 0, window.GetWidth(), window.GetHeight());
            window.SetResized(false);
        }

        shaderProgram.Bind();

        // Bind the VAO
        glBindVertexArray(vaoId);
        glEnableVertexAttribArray(0);

        // Draw the vertices
        glDrawArrays(GL_TRIANGLES, 0, 3);

        // Restore state
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);

        shaderProgram.UnBind();
    }

    public void Cleanup(){
        if (shaderProgram != null){
            shaderProgram.Cleanup();
        }

        glDisableVertexAttribArray(0);

        // Delete the VBO
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glDeleteBuffers(vboId);

        // Delete the VAO
        glBindVertexArray(0);
        glDeleteVertexArrays(vaoId);
    }
}
