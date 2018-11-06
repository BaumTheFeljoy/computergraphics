#version 330

//globale Ausgabe unseres Programms (out)
//Typ ist vec3 für RGB (jeweils 0.0 bis 1.0)

out vec3 HintergrundFarbe;

void Rechteck(int x1, int x2, int y1, int y2){
    //HintergrundFarbe = vec3(0.4, 0.8, 0.9);
    vec2 Coordinate = gl_FragCoord.xy;
    if(Coordinate.x>x1 && Coordinate.x<x2 && Coordinate.y>y1 && Coordinate.y<y2){HintergrundFarbe = vec3(1.0,0.0,0.0);}
}

void Kreis(vec2 mitte, int radius){
     vec2 Coordinate = gl_FragCoord.xy;
//    HintergrundFarbe = vec3(0.4,0.4,0.4);
    float dist = distance(mitte,Coordinate);
    if(dist<radius){HintergrundFarbe = vec3(0.0,0.0,0.0);}
}

void GedrehtesRechteck(vec2 ul, vec2 or, float angle){
    mat2 mat = mat2(cos(-angle),sin(-angle),-sin(-angle),cos(-angle));
    vec2 coordinate = gl_FragCoord.xy;
    vec2 gedrehterPunkt = mat*coordinate;
    if(gedrehterPunkt.x>ul.x && gedrehterPunkt.x<or.x && gedrehterPunkt.y>ul.y && gedrehterPunkt.y<or.y){
    HintergrundFarbe = vec3(0.8,0.0,0.8);}
}

//void EZLine(float ywert){
//    vec2 coordinate = gl_FragCoord.xy;
//    if(coordinate.y < ywert+1 && coordinate.y > ywert-1){HintergrundFarbe = vec3(0.0,0.0,0.0);}
////    if(coordinate.y ywert){HintergrundFarbe = vec3(0.0,0.0,0.0);} //warum geht das nicht?
//}

//void Line(vec2 Punkt1, vec2 Punkt2){
//    vec2 li = Punkt2-Punkt1;
//    vec2 xVec = (1,0);
//
//}

void BildMalen(){
    Rechteck(700/4,700/4*3,700/4,700/4*3);
    Kreis(vec2(500,200),100);
    Kreis(vec2(200,500),100);
    Kreis(vec2(350,350),150);
    GedrehtesRechteck(vec2(200,200),vec2(400,400),0.2);
}


void main() {
    HintergrundFarbe = vec3(0.4, 0.8, 0.9);
//    BildMalen();
    BildMalen();
}
