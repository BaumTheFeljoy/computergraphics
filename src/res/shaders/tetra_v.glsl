#version 330
#define M_PI 3.1415926535897932384626433832795
layout(location=0) in vec3 vertex;
layout(location=1) in vec3 normalen;
layout(location=2) in vec2 uvKoord;
//layout(location=3) in float colors;
/*
layout(location=1) in vec3 farbenAusJava;
*/

//int gradzahl = 90;
out vec3 farbe;
out vec3 pixelCoordimRaum;
out vec3 normalenAdjusted;
out vec2 uv;

uniform mat4 matrix2;
uniform mat4 projectionMatrix;

void main() {

    farbe = vec3(vertex.x,vertex.y,vertex.z);

    uv=uvKoord;

    mat4 temp = transpose(inverse(matrix2));
    normalenAdjusted = normalize(mat3(temp)*normalen);
    //normalenAdjusted = normalize(mat3(matrix)*normalen);
    pixelCoordimRaum = vec3(matrix2*vec4(vertex,1.0));

    gl_Position = projectionMatrix*matrix2* vec4(vertex,1.0);
}
