package ab3;

import static org.lwjgl.opengl.GL30.*;

import lenz.opengl.AbstractOpenGLBase;
import lenz.opengl.ShaderProgram;

public class Aufgabe3undFolgende extends AbstractOpenGLBase {
	float[] dreiecksKoordinaten = new float[]{0.7f,0.9f,0,-0.6f,0.6f,0,-0.4f,-0.8f,0,};
	float[] farben = new float[]{1,0,0,0.9f,0,0.9f,1,0.5f,0};
	int degree = 0;
	Mat4 matrix = new Mat4();
	int loc = 0;

	private ShaderProgram shaderProgram;

	public static void main(String[] args) {
		new Aufgabe3undFolgende().start("CG Aufgabe 3", 700, 700);
	}

	@Override
	protected void init() {
		shaderProgram = new ShaderProgram("aufgabe3");
		glUseProgram(shaderProgram.getId());
		loc = glGetUniformLocation(shaderProgram.getId(),"matrix");
		// Koordinaten, VAO, VBO, ... hier anlegen und im Grafikspeicher ablegen
		int vaoId = glGenVertexArrays();
		glBindVertexArray(vaoId);
		createVBO(dreiecksKoordinaten,3,0);
		createVBO(farben,3,1);

		glEnable(GL_DEPTH_TEST); // z-Buffer aktivieren
		glEnable(GL_CULL_FACE); // backface culling aktivieren
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
		// Transformation durchführen (Matrix anpassen)
		matrix.rotateX(0.01f);
	}

	@Override
	protected void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		// Matrix an Shader übertragen
		glUniformMatrix4fv(loc,false,matrix.getValuesAsArray());
		// VAOs zeichnen
		glDrawArrays(GL_TRIANGLES, 0, 3);
	}

	@Override
	public void destroy() {
	}
}
