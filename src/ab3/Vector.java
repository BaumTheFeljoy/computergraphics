package ab3;

class Vector {
    float x;
    float y;
    float z;

    Vector(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    Vector getVectorTo(Vector vector){
        float x = vector.x-this.x;
        float y = vector.y-this.y;
        float z = vector.z-this.z;

        return new Vector(x,y,z);
    }

    Vector crossWith(Vector vector){
        float x = this.y* vector.z-this.z* vector.y;
        float y = this.z* vector.x-this.x* vector.z;
        float z = this.x* vector.y-this.y* vector.x;
        return new Vector(x,y,z);
    }
}