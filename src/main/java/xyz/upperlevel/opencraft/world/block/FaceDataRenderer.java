package xyz.upperlevel.opencraft.world.block;

public interface FaceDataRenderer<FD extends FaceData> {

    Class<FD> getFaceDataClass();

    void render(FD faceData);
}
