#version 330
#define M_PI 3.1415926535897932384626433832795
layout(location=0) in vec2 eckenAusJava;
layout(location=1) in vec3 farbenAusJava;
layout(location=2) in int gradzahl;



//int gradzahl = 90;
float angle = (M_PI*2)/360*gradzahl;
out vec3 farbe;

mat2 mat = mat2(cos(angle),sin(angle),-sin(angle),cos(angle));

void main() {
//hier kann Transformation erfolgen
gl_Position = vec4(mat*eckenAusJava, 0.0, 1.0);
farbe = farbenAusJava;
//warum nicht als out wie im F.Shader?!
}