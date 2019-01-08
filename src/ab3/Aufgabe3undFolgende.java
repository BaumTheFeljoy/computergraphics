package ab3;

import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.opengl.GL30.*;

import Input.KeyboardHandler;
import lenz.opengl.AbstractOpenGLBase;
import lenz.opengl.ShaderProgram;
import lenz.opengl.Texture;
import org.lwjgl.glfw.GLFWKeyCallback;

public class Aufgabe3undFolgende extends AbstractOpenGLBase {
	private GLFWKeyCallback keyCallback;

	float val = (float) (Math.sqrt(3) / 4);
	float[] tetraeder = new float[] {
				//front
				0.5f, val, 0,
				-0.5f, val, 0,
				0, -val, 0.5f,

				//left
				-0.5f, val, 0,
				0, -val, -0.5f,
				0, -val, 0.5f,

				//right
				0.5f, val, 0,
				0, -val, 0.5f,
				0, -val, -0.5f,

				//back
				-0.5f, val, 0,
				0.5f, val, 0,
				0, -val, -0.5f
	};
	
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

	float[] uvKoord = {
			0,0,0,0.5f,0.5f,0, 0.5f,0,0,0.5f,0.5f,0.5f, //gut 1.
			0,0,0,0.25f,0.25f,0, 0,0.25f,0.25f,0.25f,0.25f,0, //gut 2.
			0,0.75f,0.75f,0,0,0, 0,0.75f,0.75f,0.75f,0.75f,0,//gut 3.
			0.25f,0.25f,0.5f,0.25f,0.5f,0.5f, 0.25f,0.25f,0.5f,0.5f,0.25f,0.5f,// gut 4.
			0.75f,0.75f,0.75f,1,1,0.75f, 1,0.75f,0.75f,1,1,1, //gut 5.
			0,0,0,1,1,0, 0,1,1,1,1,0, //gut 6.
	};

	float[] normalen = generateNormals(tetraeder,12);
	//float[] farben = new float[]{1,0,0,0.9f,0,0.9f,1,0.5f,0};
	Mat4 matrix = new Mat4().translate(0,0,-4);

	Mat4 matrix2 = new Mat4().translate(1,1,-4);

	int loc = 0;

	private ShaderProgram shaderProgram;

	public static void main(String[] args) {
		new Aufgabe3undFolgende().start("WASD = move; QE = zoom; Arrow Keys = rotate", 900, 900);
	}

	@Override
	protected void init() {
		glfwSetKeyCallback(super.getWindow(), keyCallback = new KeyboardHandler());

		shaderProgram = new ShaderProgram("aufgabe3");
		glUseProgram(shaderProgram.getId());
		loc = glGetUniformLocation(shaderProgram.getId(),"matrix"); //Warum steht das hier?

		// Koordinaten, VAO, VBO, ... hier anlegen und im Grafikspeicher ablegen
		int vaoId = glGenVertexArrays();
		glBindVertexArray(vaoId);
		createVBO(tetraeder,3,0);
		createVBO(normalen,3,1);
		createVBO(uvKoord,2,2);


		glEnable(GL_DEPTH_TEST); // z-Buffer aktivieren
		glEnable(GL_CULL_FACE); // backface culling aktivieren

		int uniformProjectionMatrixID = glGetUniformLocation(shaderProgram.getId(), "projectionMatrix");
		glUniformMatrix4fv(uniformProjectionMatrixID, false, new Mat4(2f, 100F).getValuesAsArray());

		Texture texture = new Texture("tex.jpg",8,true);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST_MIPMAP_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST_MIPMAP_NEAREST);
		glBindTexture(GL_TEXTURE_2D, texture.getId());

	}


	private void createVBO(float[] werte, int size, int id) {
		int vboId = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboId);
		glBufferData(GL_ARRAY_BUFFER,werte, GL_STATIC_DRAW);
		glEnableVertexAttribArray(id);
		glVertexAttribPointer(id, size, GL_FLOAT,false, 0, 0);
	}

	@Override
	/**
	 * Move the cube with awsd, Rotate the cube with arrow keys
	 */
	public void update() {
		if(KeyboardHandler.isKeyDown(65)) { //A
			matrix.translate(-0.01f, 0, 0);
		}
		if(KeyboardHandler.isKeyDown(68)) { //D
			matrix.translate(0.01f, 0, 0);
		}
		if(KeyboardHandler.isKeyDown(87)) { //W
			matrix.translate(0, 0.01f,0);
		}
		if(KeyboardHandler.isKeyDown(83)) { //S
			matrix.translate(0, -0.01f, 0);
		}
		if(KeyboardHandler.isKeyDown(263)) {//Left Arrow Key
			float[] loc = matrix.getMatLoc();
			matrix.translate(-loc[0], -loc[1], -loc[2]).rotateY(0.01f).translate(loc[0], loc[1], loc[2]);
		}
		if(KeyboardHandler.isKeyDown(262)) { //Right Arrow Key
			float[] loc = matrix.getMatLoc();
			matrix.translate(-loc[0], -loc[1], -loc[2]).rotateY(-0.01f).translate(loc[0], loc[1], loc[2]);
		}
		if(KeyboardHandler.isKeyDown(265)) {//Up Arrow Key
			float[] loc = matrix.getMatLoc();
			matrix.translate(-loc[0], -loc[1], -loc[2]).rotateX(-0.01f).translate(loc[0], loc[1], loc[2]);
		}
		if(KeyboardHandler.isKeyDown(264)) {//Down Arrow Key
			float[] loc = matrix.getMatLoc();
			matrix.translate(-loc[0], -loc[1], -loc[2]).rotateX(0.01f).translate(loc[0], loc[1], loc[2]);
		}
		if (KeyboardHandler.isKeyDown(81)) { //Q
			matrix.translate(0, 0, 0.01f);
		}
		if (KeyboardHandler.isKeyDown(69)) { //E
			matrix.translate(0, 0, -0.01f);
		}
	}

	@Override
	protected void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		// Matrix an Shader übertragen
		glUniformMatrix4fv(loc,false,matrix.getValuesAsArray());
		// VAOs zeichnen
		glDrawArrays(GL_TRIANGLES, 0, 12);
	}

	@Override
	public void destroy() {
	}


	private float[] generateNormals(float[] objectKoords, int amountPoints){
		Vector[] vectors = new Vector[amountPoints];
		int counter = 0;
		for (int i = 0; i < objectKoords.length; i+=3) {
			Vector p = new Vector(objectKoords[i],objectKoords[i+1],objectKoords[i+2]);
			vectors[counter] = p;
			counter++;
		}

		float[] norms = new float[amountPoints*3];
		int normscounter = -1;
		for (int i = 0; i < vectors.length; i+=3) {
			Vector a = vectors[i];
			Vector b = vectors[i+1];
			Vector c = vectors[i+2];

			Vector normA = a.getVectorTo(b).crossWith(a.getVectorTo(c));
			for (int j = 0; j < 3; j++) {
				norms[++normscounter]=normA.x;
				norms[++normscounter]=normA.y;
				norms[++normscounter]=normA.z;
			}
		}
		return norms;
	}


}
