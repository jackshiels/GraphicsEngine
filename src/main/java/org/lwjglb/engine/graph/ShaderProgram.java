package org.lwjglb.engine.graph;

import static org.lwjgl.opengl.GL20.*;

public class ShaderProgram {

    // Members
    private int programId;
    private int vertexShaderId;
    private int fragmentShaderId;

    // Constructor
    public ShaderProgram() throws Exception
    {
        programId = glCreateProgram();
        if (programId == 0){
            throw new Exception("Could not create shader");
        }
    }

    // Methods
    public void CreateVertexShader(String shaderCode) throws Exception{
        vertexShaderId = CreateShader(shaderCode, GL_VERTEX_SHADER);
    }

    public void CreateFragmentShader(String shaderCode) throws Exception{
        fragmentShaderId = CreateShader(shaderCode, GL_FRAGMENT_SHADER);
    }

    // Build the shader
    protected int CreateShader(String shaderCode, int shaderType) throws Exception {

        int shaderId = glCreateShader(shaderType);

        if (shaderId == 0){
            throw new Exception("Error creating shader of type: " + shaderType);
        }

        // Handle shader code compilation
        glShaderSource(shaderId, shaderCode);
        glCompileShader(shaderId);

        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0){
            throw new Exception("Error compiling shader: " + glGetShaderInfoLog(shaderId, 1024));
        }

        glAttachShader(programId, shaderId);

        return shaderId;
    }

    // Link the program with checks
    public void Link() throws Exception{

        glLinkProgram(programId);

        if (glGetProgrami(programId, GL_LINK_STATUS) == 0){
            throw new Exception("Error linking shader code: " + glGetProgramInfoLog(programId, 1024));
        }

        // Shader checks
        if (vertexShaderId != 0){
            glDetachShader(programId, vertexShaderId);
        }

        if (fragmentShaderId != 0){
            glDetachShader(programId, fragmentShaderId);
        }

        glValidateProgram(programId);

        if (glGetProgrami(programId, GL_VALIDATE_STATUS) == 0){
            System.err.println(("Warning validating shader code: " + glGetProgramInfoLog(programId, 1024)));
        }
    }

    // Utilities
    public void Bind(){
        glUseProgram(programId);
    }

    public void UnBind(){
        glUseProgram(0);
    }

    public void Cleanup(){
        UnBind();
        if (programId == 0){
            glDeleteProgram(programId);
        }
    }
}
