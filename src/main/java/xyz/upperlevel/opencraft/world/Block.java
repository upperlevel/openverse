package xyz.upperlevel.opencraft.world;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Block {

    @Getter @NonNull public final Location location;

    public World getWorld() {
        return location.getWorld();
    }

    public Chunk getChunk() {
        return location.getChunk();
    }

    public ChunkCache getChunkCache() {
        return getChunk().cache;
    }

    public Block getRelative(BlockFace face) {
        return face.offset(location.copy()).getBlock();
    }

    public boolean isVisible() {
        return BlockFace.FACES.stream()
                .anyMatch(face -> getRelative(face).getData().isTransparent());
    }

    public BlockData getData() {
        BlockData data = getChunkCache().blocks[(int) location.asChunkX()][(int) location.asChunkY()][(int) location.asChunkZ()];
        return data == null ? BlockData.EMPTY : data;
    }

    public void setData(BlockData data) {
        setData(data, true);
    }

    public void setData(BlockData data, boolean update) {
        getChunkCache().blocks[(int) location.asChunkX()][(int) location.asChunkY()][(int) location.asChunkZ()] = data;
        if (update)
            update();
    }

    public void update() {
        if (isVisible()) {
            getChunkCache().renderer.add(this);
        } else {
            getChunkCache().renderer.remove(this);
        }
    }

    @Override
    public int hashCode() {
        return location.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Block ? location.equals(((Block) object).getLocation()) : super.equals(object);
    }
}