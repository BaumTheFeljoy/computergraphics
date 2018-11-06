package ab3;

//Alle Operationen ändern das Matrixobjekt selbst und geben das eigene Matrixobjekt zurück
//Dadurch kann man Aufrufe verketten, z.B.
//Matrix4 m = new Matrix4().scale(5).translate(0,1,0).rotateX(0.5f);
public class Mat4 {
    private float[] m;

    public Mat4() {
        //mit der Identitätsmatrix initialisieren
        m = new float[]{
                1f, 0, 0, 0,
                0, 1f, 0, 0,
                0, 0, 1f, 0,
                0, 0, 0, 1f};
    }

    public Mat4(float[] f){
        m=f;
    }

    public Mat4(Mat4 copy) {
        //neues Objekt mit den Werten von "copy" initialisieren
        m = copy.getValuesAsArray().clone();
    }

    public Mat4(float near, float far) {
        // TODO erzeugt Projektionsmatrix mit Abstand zur nahen Ebene "near" und Abstand zur fernen Ebene "far", ggf. weitere Parameter hinzufügen
    }

    public Mat4 multiply(Mat4 other) {
        // TODO hier Matrizenmultiplikation "this = other * this" einfügen
        float[] o = other.getValuesAsArray();
        float[] r = new float[16];

        r[0] = o[0]*m[0]+o[4]*m[1]+o[8]*m[2]+o[12]*m[3];
        r[4] = o[0]*m[4]+o[4]*m[5]+o[8]*m[6]+o[12]*m[7];
        r[8] = o[0]*m[8]+o[4]*m[9]+o[8]*m[10]+o[12]*m[11];
        r[12] = o[0]*m[12]+o[4]*m[13]+o[8]*m[14]+o[12]*m[15];

        r[1] = o[1]*m[0]+o[5]*m[1]+o[9]*m[2]+o[13]*m[3];
        r[5] = o[1]*m[4]+o[5]*m[5]+o[9]*m[6]+o[13]*m[7];
        r[9] = o[1]*m[8]+o[5]*m[9]+o[9]*m[10]+o[13]*m[11];
        r[13] = o[1]*m[12]+o[5]*m[13]+o[9]*m[14]+o[13]*m[15];

        r[2] = o[2]*m[0]+o[6]*m[1]+o[10]*m[2]+o[14]*m[3];
        r[6] = o[2]*m[4]+o[6]*m[5]+o[10]*m[6]+o[14]*m[7];
        r[10] = o[2]*m[8]+o[6]*m[9]+o[10]*m[10]+o[14]*m[11];
        r[14] = o[2]*m[12]+o[6]*m[13]+o[10]*m[14]+o[14]*m[15];

        r[3] = o[3]*m[0]+o[7]*m[1]+o[11]*m[2]+o[15]*m[3];
        r[7] = o[3]*m[4]+o[7]*m[5]+o[11]*m[6]+o[15]*m[7];
        r[11] = o[3]*m[8]+o[7]*m[9]+o[11]*m[10]+o[15]*m[11];
        r[15] = o[3]*m[12]+o[7]*m[13]+o[11]*m[14]+o[15]*m[15];

        m=r;
        return this;
    }

    public Mat4 translate(float x, float y, float z) {
        // TODO Verschiebung um x,y,z zu this hinzufügen
        multiply(new Mat4(new float[]{
                1,0,0,0,
                0,1,0,0,
                0,0,1,0,
                x,y,z,1}));
        return this;
    }

    public Mat4 scale(float uniformFactor) {
        //gleichmäßige Skalierung um Faktor "uniformFactor" zu this hinzufügen
        return scale(uniformFactor,uniformFactor,uniformFactor);
    }

    public Mat4 scale(float sx, float sy, float sz) {
        //ungleichförmige Skalierung zu this hinzufügen
        multiply(new Mat4(new float[]{
                sx,0,0,0,
                0,sy,0,0,
                0,0,sz,0,
                0,0,0, 1}));
        return this;
    }

    public Mat4 rotateX(float angle) {
        // TODO Rotation um X-Achse zu this hinzufügen
        float cos = (float)Math.cos(angle);
        float sin = (float)Math.sin(angle);
        multiply(new Mat4(new float[]{
                1,0,0,0,
                0,cos,sin,0,
                0,-sin,cos,0,
                0,0,0,1}));
        return this;
    }

    public Mat4 rotateY(float angle) {
        // TODO Rotation um Y-Achse zu this hinzufügen
        float cos = (float)Math.cos(angle);
        float sin = (float)Math.sin(angle);
        multiply(new Mat4(new float[]{
                cos,0,sin,0,
                0,1,0,0,
                -sin,0,cos,0,
                0,0,0,1}));
        return this;
    }

    public Mat4 rotateZ(float angle) {
        // TODO Rotation um Z-Achse zu this hinzufügen
        float cos = (float)Math.cos(angle);
        float sin = (float)Math.sin(angle);
        multiply(new Mat4(new float[]{
                cos,sin,0,0,
                -sin,cos,0,0,
                0,0,1,0,
                0,0,0,1}));
        return this;
    }

    public float[] getValuesAsArray() {
        // TODO hier Werte in einem Float-Array mit 16 Elementen (spaltenweise gefällt) herausgeben
        return m;
    }
}