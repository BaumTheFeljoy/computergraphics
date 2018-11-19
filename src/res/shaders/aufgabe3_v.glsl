#version 330
#define M_PI 3.1415926535897932384626433832795
layout(location=0) in vec3 vertex;
/*
layout(location=1) in vec3 farbenAusJava;
*/

//int gradzahl = 90;
out vec3 farbe;

uniform mat4 matrix;
uniform mat4 projectionMatrix;

/*void main() {
//hier kann Transformation erfolgen
vec4 eckenRotiert = matrix*eckenAusJava;
farbe = farbenAusJava;
gl_Position = eckenRotiert;
//warum nicht als out wie im F.Shader?!
}*/
void main() {
/*
    //black
    if (vertex.x == -0.5 && vertex.y == -0.5 && vertex.z == -0.5) {
        farbe = vec3(0.0, 0.0, 0.0);
    //red
    } else if (vertex.x == -0.5 && vertex.y == 0.5 && vertex.z == -0.5) {
        farbe = vec3(1.0, 0.0, 0.0);
    //green
    } else if (vertex.x == -0.5 && vertex.y == -0.5 && vertex.z == 0.5) {
        farbe = vec3(0.0, 1.0, 0.0);
    //blue
    } else if (vertex.x == 0.5 && vertex.y == -0.5 && vertex.z == -0.5) {
        farbe = vec3(0.0, 0.0, 1.0);
    //magenta
    } else if (vertex.x == 0.5 && vertex.y == 0.5 && vertex.z == -0.5) {
        farbe = vec3(1.0, 0.0, 1.0);
    //yellow
    } else if (vertex.x == -0.5 && vertex.y == 0.5 && vertex.z == 0.5) {
        farbe = vec3(1.0, 1.0, 0.0);
    //cyan
    } else if (vertex.x == 0.5 && vertex.y == -0.5 && vertex.z == 0.5) {
        farbe = vec3(0.0, 1.0, 1.0);
    //white
    } else if (vertex.x == 0.5 && vertex.y == 0.5 && vertex.z == 0.5) {
        farbe = vec3(1.0, 1.0, 1.0);
    } else {
        farbe = vec3(0.5, 0.5, 0.5);
    }
*/
 if (gl_VertexID < 6) {
        farbe = vec3(1.0, 0.0, 0.0);
    } else if (gl_VertexID < 12) {
        farbe  = vec3(0.0, 1.0, 0.0);
    } else if (gl_VertexID < 18) {
        farbe  = vec3(0.0, 0.0, 1.0);
    } else if (gl_VertexID < 24) {
        farbe  = vec3(1.0, 1.0, 0.0);
    } else if (gl_VertexID < 30) {
        farbe  = vec3(1.0, 0.0, 1.0);
    } else {
        farbe  = vec3(0.0, 1.0, 1.0);
    }
    gl_Position = matrix* vec4(vertex,1.0);
}