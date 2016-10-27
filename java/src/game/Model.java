package game;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

public class Model {
	private int drawCount;
	private int vertexId;
	private int textureId;
	private int indexId;
	
	public Model(float[] vertices, float[] texCoords, int[] indexes) {
		drawCount = indexes.length;
		
		vertexId = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vertexId);
		glBufferData(GL_ARRAY_BUFFER, createBuffer(vertices), GL_STATIC_DRAW);
		
		textureId = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, textureId);
		glBufferData(GL_ARRAY_BUFFER, createBuffer(texCoords), GL_STATIC_DRAW);
		
		indexId = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexId);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, createBuffer(indexes), GL_STATIC_DRAW);
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	public void render() {
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		
		glBindBuffer(GL_ARRAY_BUFFER, vertexId);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		
		glBindBuffer(GL_ARRAY_BUFFER, textureId);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexId);
		glDrawElements(GL_TRIANGLES, drawCount, GL_UNSIGNED_INT, 0);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
	}
	
	private FloatBuffer createBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		
		return buffer;
	}
	
	private IntBuffer createBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		
		return buffer;
	}
}
