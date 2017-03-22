package xyz.upperlevel.opencraft.server.math;

public class ReadonlyVector extends Vector {
    public ReadonlyVector(float x, float y, float z) {
        super(x, y, z);
    }

    public void set(float x, float y, float z) {
        throw new UnsupportedOperationException();
    }

    public void set(Vector o) {
        throw new UnsupportedOperationException();
    }


    public void add(Vector loc) {
        throw new UnsupportedOperationException();
    }

    public void add(float x, float y, float z, float yaw, float pitch) {
        throw new UnsupportedOperationException();
    }

    public void add(float x, float y, float z) {
        throw new UnsupportedOperationException();
    }


    public void sub(Vector loc) {
        throw new UnsupportedOperationException();
    }


    public void sub(float x, float y, float z, float yaw, float pitch) {
        throw new UnsupportedOperationException();
    }

    public void sub(float x, float y, float z) {
        throw new UnsupportedOperationException();
    }


    public void mul(Vector loc) {
        throw new UnsupportedOperationException();
    }


    public void mul(float x, float y, float z, float yaw, float pitch) {
        throw new UnsupportedOperationException();
    }

    public void mul(float x, float y, float z) {
        throw new UnsupportedOperationException();
    }

    public void mul(float m) {
        throw new UnsupportedOperationException();
    }



    public void div(Vector loc) {
        throw new UnsupportedOperationException();
    }


    public void div(float x, float y, float z, float yaw, float pitch) {
        throw new UnsupportedOperationException();
    }

    public void div(float x, float y, float z) {
        throw new UnsupportedOperationException();
    }

    public void div(float m) {
        throw new UnsupportedOperationException();
    }



    public void normalize() {
        throw new UnsupportedOperationException();
    }

    public static ReadonlyVector wrap(Vector v) {
        return new ReadonlyVector(0, 0, 0) {
            public float getX() {
                return v.getX();
            }

            public float getY() {
                return v.getY();
            }

            public float getZ() {
                return v.getZ();
            }


            public void add(Vector loc, Vector dest) {
                dest.set(
                        v.getX() + loc.getX(),
                        v.getY() + loc.getY(),
                        v.getZ() + loc.getZ()
                );
            }

            public void sub(Vector loc, Vector dest) {
                dest.set(
                        v.getX() - loc.getX(),
                        v.getY() - loc.getY(),
                        v.getZ() - loc.getZ()
                );
            }

            public void mul(Vector loc, Vector dest) {
                dest.set(
                        v.getX() * loc.getZ(),
                        v.getY() * loc.getY(),
                        v.getZ() * loc.getZ()
                );
            }

            public void mul(float m, Vector dest) {
                dest.set(
                        v.getX() * m,
                        v.getY() * m,
                        v.getZ() * m
                );
            }



            public void div(Vector loc, Vector dest) {
                dest.set(
                        v.getX() / loc.getX(),
                        v.getY() / loc.getY(),
                        v.getZ() / loc.getZ()
                );
            }

            public void div(float m, Vector dest) {
                dest.set(
                        v.getX() / m,
                        v.getY() / m,
                        v.getZ() / m
                );
            }


            public double length() {
                float   x = v.getX(),
                        y = v.getY(),
                        z = v.getZ();
                return Math.sqrt(x*x + y*y + z*z);
            }

            public double lengthSq() {
                float   x = v.getX(),
                        y = v.getY(),
                        z = v.getZ();
                return x*x + y*y + z*z;
            }

            public double distance(Vector loc) {
                float   dx = v.getX() - loc.getX(),
                        dy = v.getY() - loc.getY(),
                        dz = v.getZ() - loc.getZ();
                return Math.sqrt(dx*dx + dy*dy + dz*dz);
            }

            public double distanceSq(Vector loc) {
                float   dx = v.getX() - loc.getX(),
                        dy = v.getY() - loc.getY(),
                        dz = v.getZ() - loc.getZ();
                return dx*dx + dy*dy + dz*dz;
            }

            public void normalize(Vector dest) {
                float m = (float) length();
                dest.set(
                        v.getX() / m,
                        v.getY() / m,
                        v.getZ() / m
                );
            }
        };
    }
}
