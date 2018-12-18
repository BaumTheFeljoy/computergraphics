#version 330
in vec3 farbe;
in vec3 pixelCoordimRaum;
in vec3 normalenAdjusted;
in vec2 uv;

out vec3 objectFarbe;

vec3 licht = vec3(-1,3,3);
uniform sampler2D smplr;
float ambient = 0.1;
float intensity = 1;


void main(){
    vec4 texel = texture(smplr,uv);

    vec3 PixelzuLicht = normalize(licht - pixelCoordimRaum);
    vec3 r =normalize(2*dot(PixelzuLicht,normalenAdjusted)*normalenAdjusted-PixelzuLicht);
    vec3 v = normalize(-pixelCoordimRaum);

    float diffuse = max(0,dot(PixelzuLicht,normalenAdjusted)*intensity);
    float specular = pow(max(0,intensity*dot(r,v)),100);
    float lightResult = ambient + diffuse + specular;
    //objectFarbe = lightResult*farbe;
    objectFarbe = lightResult*texel.rgb;
}