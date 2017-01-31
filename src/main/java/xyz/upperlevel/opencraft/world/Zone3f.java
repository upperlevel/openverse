package xyz.upperlevel.opencraft.world;

import lombok.Getter;
import org.joml.Vector3f;

public class Zone3f {

    @Getter
    public final float p1x, p1y, p1z;

    @Getter
    public final float p2x, p2y, p2z;
    
    @Getter
    public final float minX, maxX;
    
    @Getter
    public final float minY, maxY;
    
    @Getter
    public final float minZ, maxZ;
    
    @Getter
    public final float width, height, length;

    public Zone3f(float p1x, float p1y, float p1z, float p2x, float p2y, float p2z) {
        this.p1x = p1x;
        this.p1y = p1y;
        this.p1z = p1z;

        this.p2x = p2x;
        this.p2y = p2y;
        this.p2z = p2z;
        
        minX = Math.min(p1x, p2x);
        maxX = Math.max(p1x, p2x);

        minY = Math.min(p1y, p2y);
        maxY = Math.max(p1y, p2y);

        minZ = Math.min(p1z, p2z);
        maxZ = Math.max(p1z, p2z);
        
        width = maxX - minX;
        height = maxY - minY;
        length = maxZ - minZ;
    }

    public Zone3f(Vector3f p1, Vector3f p2) {
        this(p1.x, p1.y, p1.z,
                p2.x, p2.y, p2.z);
    }

    public Vector3f getFirstPosition() {
        return new Vector3f(p1x, p1y, p1z);
    }

    public Vector3f getSecondPosition() {
        return new Vector3f(p2x, p2y, p2z);
    }

    public Vector3f getMinPosition() {
        return new Vector3f(minX, minY, minZ);
    }

    public Vector3f getMaxPosition() {
        return new Vector3f(maxX, maxY, maxZ);
    }
    
    public Vector3f getSize() {
        return new Vector3f(width, height, length);
    }

    public boolean isEqual(Zone3f zone) {
        return zone.getMinPosition().equals(getMinPosition()) &&
                zone.getMaxPosition().equals(getMaxPosition());
    }

    public boolean isInside(float x, float y, float z) {
        return x >= minX && x <= maxX &&
                y >= minY && y <= maxY &&
                z >= minZ && z <= maxZ;
    }

    public boolean isInside(Vector3f v) {
        return isInside(v.x, v.y, v.z);
    }

    public boolean isInside(Zone3f z) {
        return isInside(z.getMinPosition()) && isInside(z.getMaxPosition());
    }

    public Zone3f copy() {
        return new Zone3f(
                p1x, p1y, p1z,
                p2x, p2y, p2z
        );
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Zone3f && isEqual((Zone3f) object);
    }
}
