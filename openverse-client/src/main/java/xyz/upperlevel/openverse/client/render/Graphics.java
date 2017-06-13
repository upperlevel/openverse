package xyz.upperlevel.openverse.client.render;

public class Graphics {

    private static final RenderUniverse universe = new RenderUniverse(); // system to render universe
    private static final ShapeCompilerManager shapeCompilerManager = new ShapeCompilerManager();
    private static final TextureBakery textureBakery = new TextureBakery();

    public static RenderUniverse getUniverse() {
        return universe;
    }

    public static ShapeCompilerManager getShapeCompilerManager() {
        return shapeCompilerManager;
    }

    public static TextureBakery getTextureBakery() {
        return textureBakery;
    }
}
