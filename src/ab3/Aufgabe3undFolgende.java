package ab3;

import static org.lwjgl.opengl.GL30.*;

import lenz.opengl.AbstractOpenGLBase;
import lenz.opengl.ShaderProgram;

public class Aufgabe3undFolgende extends AbstractOpenGLBase {
	int rotCounter;
	//float[] dreiecksKoordinaten = new float[]{0.7f,0.9f,0,-0.6f,0.6f,0,-0.4f,-0.8f,0,};
	float[] wuerfel = new float[]{
			//vorne
			-0.5F, -0.5F, 0.5F,
			0.5F, -0.5F, 0.5F,
			-0.5F, 0.5F, 0.5F,

			-0.5F, 0.5F, 0.5F,
			0.5F, -0.5F, 0.5F,
			0.5F, 0.5F, 0.5F,

			//hinten
			-0.5F, -0.5F, -0.5F,
			-0.5F, 0.5F, -0.5F,
			0.5F, -0.5F, -0.5F,

			-0.5F, 0.5F, -0.5F,
			0.5F, 0.5F, -0.5F,
			0.5F, -0.5F, -0.5F,

			//unten
			-0.5F, -0.5F, 0.5F,
			0.5F, -0.5F, -0.5F,
			0.5F, -0.5F, 0.5F,

			-0.5F, -0.5F, 0.5F,
			-0.5F, -0.5F, -0.5F,
			0.5F, -0.5F, -0.5F,

			//oben
			-0.5F, 0.5F, 0.5F,
			0.5F, 0.5F, 0.5F,
			0.5F, 0.5F, -0.5F,

			-0.5F, 0.5F, 0.5F,
			0.5F, 0.5F, -0.5F,
			-0.5F, 0.5F, -0.5F,

			//links
			-0.5F, 0.5F, 0.5F,
			-0.5F, 0.5F, -0.5F,
			-0.5F, -0.5F, 0.5F,

			-0.5F, -0.5F, 0.5F,
			-0.5F, 0.5F, -0.5F,
			-0.5F, -0.5F, -0.5F,

			//rechts
			0.5F, 0.5F, 0.5F,
			0.5F, -0.5F, 0.5F,
			0.5F, 0.5F, -0.5F,

			0.5F, -0.5F, 0.5F,
			0.5F, -0.5F, -0.5F,
			0.5F, 0.5F, -0.5F
	};
	float[] normalen = {
			0,0,1,0,0,1,0,0,1,0,0,1,0,0,1,0,0,1,//vorne
			0,0,-1,0,0,-1,0,0,-1,0,0,-1,0,0,-1,0,0,-1,//hinten
			0,-1,0,0,-1,0,0,-1,0,0,-1,0,0,-1,0,0,-1,0,//unten
			0,1,0,0,1,0,0,1,0,0,1,0,0,1,0,0,1,0,//oben
			-1,0,0,-1,0,0,-1,0,0,-1,0,0,-1,0,0,-1,0,0,//links
			1,0,0,1,0,0,1,0,0,1,0,0,1,0,0,1,0,0 //rechts
	};
	float[] farben = new float[]{1,0,0,0.9f,0,0.9f,1,0.5f,0};
	Mat4 matrix = new Mat4().translate(0,0,-2);
	int loc = 0;

	private ShaderProgram shaderProgram;

	public static void main(String[] args) {
		new Aufgabe3undFolgende().start("CG Aufgabe 3", 1300, 1300);
	}

	@Override
	protected void init() {
		shaderProgram = new ShaderProgram("aufgabe3");
		glUseProgram(shaderProgram.getId());
		loc = glGetUniformLocation(shaderProgram.getId(),"matrix");
		// Koordinaten, VAO, VBO, ... hier anlegen und im Grafikspeicher ablegen
		int vaoId = glGenVertexArrays();
		glBindVertexArray(vaoId);
		/*createVBO(dreiecksKoordinaten,3,0);*/
		createVBO(wuerfel,3,0);
		//createVBO(farben,3,1);
		createVBO(normalen,3,1);

		glEnable(GL_DEPTH_TEST); // z-Buffer aktivieren
		glEnable(GL_CULL_FACE); // backface culling aktivieren

		int uniformProjectionMatrixID = glGetUniformLocation(shaderProgram.getId(), "projectionMatrix");
		glUniformMatrix4fv(uniformProjectionMatrixID, false, new Mat4(0.5F, 100F).getValuesAsArray());
	}

	private void createVBO(float[] werte, int size, int id) {
		int vboId = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboId);
		glBufferData(GL_ARRAY_BUFFER,werte, GL_STATIC_DRAW);
		glEnableVertexAttribArray(id);
		glVertexAttribPointer(id, size, GL_FLOAT,false, 0, 0);
	}

	@Override
	public void update() {
		// Transformation durchführen (Matrix anpassen)
		if(rotCounter<90){ matrix.translate(0,0,2).rotateX(0.01f).translate(0,0,-2);}
		else{ matrix.translate(0,0,2).rotateY(0.01f).translate(0,0,-2);}
		rotCounter = (rotCounter+1)%180;
		/*matrix.translate(0,0,2).rotateY(0.006f).translate(0,0,-2);*/
	}

	@Override
	protected void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		// Matrix an Shader übertragen
		glUniformMatrix4fv(loc,false,matrix.getValuesAsArray());
		// VAOs zeichnen
		glDrawArrays(GL_TRIANGLES, 0, 36);
	}

	@Override
	public void destroy() {
	}
}
