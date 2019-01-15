package ab3;

import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.opengl.GL30.*;

import Input.KeyboardHandler;
import lenz.opengl.AbstractOpenGLBase;
import lenz.opengl.ShaderProgram;
import lenz.opengl.Texture;
import org.lwjgl.glfw.GLFWKeyCallback;

public class Aufgabe3undFolgende extends AbstractOpenGLBase {
	private ShaderProgram shaderProgram;
	private ShaderProgram shaderProgram2;
	private GLFWKeyCallback keyCallback;
	private int vaoIdCube;
	private int vaoIdTetr;
	private int texIdCube;
	private int texIdTetr;

    /*float[] colors = generateTex(uvKoord);*/


	//float[] farben = new float[]{1,0,0,0.9f,0,0.9f,1,0.5f,0};

	Mat4 matrix = new Mat4().translate(-1,-1,-4);

	Mat4 matrix2 = new Mat4().translate(1,1,-4);

	public static void main(String[] args) {
		new Aufgabe3undFolgende().start("WASD = move; QE = zoom; Arrow Keys = rotate", 900, 900);
	}

	private void generateCube(){
		int amountPoints = 36;

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

		float[] normalen = generateNormals(wuerfel,amountPoints);

		glUseProgram(shaderProgram.getId());

		// Koordinaten, VAO, VBO, ... hier anlegen und im Grafikspeicher ablegen
		vaoIdCube = glGenVertexArrays();
		glBindVertexArray(vaoIdCube);
		createVBO(wuerfel,3,0);
		createVBO(normalen,3,1);
		createVBO(uvKoord,2,2);
		//createVBO(colors,1,3);

		Texture texture = new Texture("tex.jpg",8,true);
		texIdCube = texture.getId();

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST_MIPMAP_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST_MIPMAP_NEAREST);
		glBindTexture(GL_TEXTURE_2D, texIdCube);
	}

	private void generateTetraeder(){
		int amountPoints = 12;
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

		float[] uvKoord2 = {
				0,0,0,1,1,0, 1,0,0,1,1,1, //gut 1. Zeile
				0,0,0,1,1,0, 0,1,1,1,1,0, //gut 2. Zeile
				0,1,1,0,0,0, 0,1,1,1,1,0, //gut 3. Zeile
				0,0,1,0,1,1, 0,0,1,1,0,1,// gut 4. Zeile
				0,0,0,1,1,0, 1,0,0,1,1,1, //gut 5. Zeile
				0,0,0,1,1,0, 0,1,1,1,1,0, //gut 6. Zeile
		};

		float[] normalen = generateNormals(tetraeder,amountPoints);

		glUseProgram(shaderProgram2.getId());

		vaoIdTetr = glGenVertexArrays();
		glBindVertexArray(vaoIdTetr);

		createVBO(tetraeder,3,0);
		createVBO(normalen,3,1);
		createVBO(tetraeder,2,2);

		Texture texture = new Texture("flag16.jpg",8,true);
		texIdTetr = texture.getId();

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST_MIPMAP_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST_MIPMAP_NEAREST);
		glBindTexture(GL_TEXTURE_2D, texIdTetr);
	}

	@Override
	protected void init() {
		shaderProgram = new ShaderProgram("aufgabe3");
		shaderProgram2 = new ShaderProgram("tetra");
		glfwSetKeyCallback(super.getWindow(), keyCallback = new KeyboardHandler());

		generateCube();
		generateTetraeder();

		glEnable(GL_DEPTH_TEST); // z-Buffer aktivieren
		glEnable(GL_CULL_FACE); // backface culling aktivieren

		int uniformProjectionMatrixID = glGetUniformLocation(shaderProgram.getId(), "projectionMatrix");
		glUniformMatrix4fv(uniformProjectionMatrixID, false, new Mat4(2f, 10F).getValuesAsArray());
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
			matrix.translate(-0.001f, 0, 0);
		}
		if(KeyboardHandler.isKeyDown(68)) { //D
			matrix.translate(0.001f, 0, 0);
		}
		if(KeyboardHandler.isKeyDown(87)) { //W
			matrix.translate(0, 0.001f,0);
		}
		if(KeyboardHandler.isKeyDown(83)) { //S
			matrix.translate(0, -0.001f, 0);
		}
		if(KeyboardHandler.isKeyDown(263)) {//Left Arrow Key
			float[] loc = matrix.getMatLoc();
			matrix.translate(-loc[0], -loc[1], -loc[2]).rotateY(0.001f).translate(loc[0], loc[1], loc[2]);
		}
		if(KeyboardHandler.isKeyDown(262)) { //Right Arrow Key
			float[] loc = matrix.getMatLoc();
			matrix.translate(-loc[0], -loc[1], -loc[2]).rotateY(-0.001f).translate(loc[0], loc[1], loc[2]);
		}
		if(KeyboardHandler.isKeyDown(265)) {//Up Arrow Key
			float[] loc = matrix.getMatLoc();
			matrix.translate(-loc[0], -loc[1], -loc[2]).rotateX(-0.001f).translate(loc[0], loc[1], loc[2]);
		}
		if(KeyboardHandler.isKeyDown(264)) {//Down Arrow Key
			float[] loc = matrix.getMatLoc();
			matrix.translate(-loc[0], -loc[1], -loc[2]).rotateX(0.001f).translate(loc[0], loc[1], loc[2]);
		}
		if (KeyboardHandler.isKeyDown(81)) { //Q
			matrix.translate(0, 0, 0.001f);
		}
		if (KeyboardHandler.isKeyDown(69)) { //E
			matrix.translate(0, 0, -0.001f);
		}

		float[] loc = matrix2.getMatLoc();
		matrix2.translate(-loc[0], -loc[1], -loc[2]).rotateY(0.001f).translate(loc[0], loc[1], loc[2]);
	}

	@Override
	protected void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		// Matrix an Shader übertragen
		int loc = glGetUniformLocation(shaderProgram.getId(), "matrix");
		glUniformMatrix4fv(loc,false,matrix.getValuesAsArray());
		glBindVertexArray(vaoIdCube);
		glBindTexture(GL_TEXTURE_2D, texIdCube);
		// VAOs zeichnen
		glDrawArrays(GL_TRIANGLES, 0, 36);

		// Matrix an Shader übertragen
		int loc2 = glGetUniformLocation(shaderProgram2.getId(), "matrix2");
		glUniformMatrix4fv(loc2, false,matrix2.getValuesAsArray());
		glBindVertexArray(vaoIdTetr);
		glBindTexture(GL_TEXTURE_2D, texIdTetr);
		// VAOs zeichnen
		glDrawArrays(GL_TRIANGLES,0,12);
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
