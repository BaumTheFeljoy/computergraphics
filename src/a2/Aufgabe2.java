package a2;

import static org.lwjgl.opengl.GL30.*;

import lenz.opengl.AbstractOpenGLBase;
import lenz.opengl.ShaderProgram;

public class Aufgabe2 extends AbstractOpenGLBase {
	float[] dreiecksKoordinaten = new float[]{-0.4f,-0.8f,-0.6f,0.6f,0.7f,0.9f};
	float[] farben = new float[]{1,0,0,0.9f,0,0.9f,1,0.5f,0};
	int degree = 0;


	public static void main(String[] args) {
		new Aufgabe2().start("CG Aufgabe 2", 700, 700);
	}

	@Override
	protected void init() {
		// folgende Zeile läd automatisch "aufgabe2_v.glsl" (vertex) und "aufgabe2_f.glsl" (fragment)
		ShaderProgram shaderProgram = new ShaderProgram("aufgabe2");
		glUseProgram(shaderProgram.getId());
		// Koordinaten, VAO, VBO, ... hier anlegen und im Grafikspeicher ablegen
		int vaoId = glGenVertexArrays();
		glBindVertexArray(vaoId);
		createVBO(dreiecksKoordinaten,2,0);
		createVBO(farben,3,1);
	}

	private void createVBO(float[] werte, int size, int id) {
		int vboId = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboId);
		glBufferData(GL_ARRAY_BUFFER,werte, GL_STATIC_DRAW);
		glVertexAttribPointer(id, size, GL_FLOAT,false, 0, 0);
		glEnableVertexAttribArray(id);
	}

	@Override
	public void update() {
		degree++;
		int vboId = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboId);
		glBufferData(GL_ARRAY_BUFFER,degree , GL_STATIC_DRAW);
		glVertexAttribPointer(2, 1, GL_FLOAT,false, 0, 0);
		glEnableVertexAttribArray(2);

	}

	@Override
	protected void render() {
		glClear(GL_COLOR_BUFFER_BIT); // Zeichenfläche leeren
		//foliencode einfügen
		//glBindVertexArray(vaoId); notwendig wozu?

		// hier vorher erzeugte VAOs zeichnen
		glDrawArrays(GL_TRIANGLES, 0, 3);
	}

	@Override
	public void destroy() {
	}
}
