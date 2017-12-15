package xyz.upperlevel.openverse.util.math.bfs;

import java.util.ArrayDeque;
import java.util.Queue;

public class FastFloodAlgorithm {
    private final Queue<BfsNode> removalQueue = new ArrayDeque<>();
    private final Queue<BfsNode> propagationQueue = new ArrayDeque<>();

    public FastFloodAlgorithm() {
    }

    /**
     * Adds a new removal node to the algorithm.
     *
     * @param x               the x-axis location
     * @param y               the y-axis location
     * @param z               the z-axis location
     * @param valueBeforeZero the value the node has before being set to zero
     */
    public FastFloodAlgorithm addRemovalNode(int x, int y, int z, int valueBeforeZero) {
        removalQueue.add(new BfsNode(x, y, z, valueBeforeZero));
        return this;
    }

    /**
     * Adds a new propagation node to the algorithm.
     *
     * @param x     the x-axis location
     * @param y     the y-axis location
     * @param z     the z-axis location
     * @param value the value
     */
    public FastFloodAlgorithm addNode(int x, int y, int z, int value) {
        propagationQueue.add(new BfsNode(x, y, z, value));
        return this;
    }

    /**
     * Starts the algorithm.
     * Firstly runs the removal algorithm and secondly the diffusion algorithm.
     *
     * @param context the context on which it acts
     */
    public void start(FastFloodContext context) {
        // Removal algorithm
        for (BfsNode node : removalQueue) {
            context.setValue(node.x, node.y, node.z, 0);
        }
        while (!removalQueue.isEmpty()) {
            BfsNode node = removalQueue.poll();
            int x = node.x;
            int y = node.y;
            int z = node.z;
            int v = node.value;

            for (BfsRelative r : BfsRelative.values()) {
                int rx = x + r.getOffsetX();
                int ry = y + r.getOffsetY();
                int rz = z + r.getOffsetZ();

                if (!context.isOutOfBounds(rx, ry, rz)) {
                    int rv = context.getValue(rx, ry, rz);
                    if (rv != 0 && rv < v) {
                        context.setValue(rx, ry, rz, 0);
                        addRemovalNode(rx, ry, rz, rv);
                    } else if (rv >= v) {
                        addNode(rx, ry, rz, rv);
                    }
                }
            }
        }
        // Propagation algorithm
        for (BfsNode node : propagationQueue) {
            context.setValue(node.x, node.y, node.z, node.value);
        }
        while (!propagationQueue.isEmpty()) {
            BfsNode node = propagationQueue.poll();
            int x = node.x;
            int y = node.y;
            int z = node.z;
            int v = node.value;

            for (BfsRelative r : BfsRelative.values()) {
                int rx = x + r.getOffsetX();
                int ry = y + r.getOffsetY();
                int rz = z + r.getOffsetZ();

                if (!context.isOutOfBounds(rx, ry, rz)) {
                    // If the value of light of the neighbor is
                    // lower or equal than the current value - 1
                    if ((context.getValue(rx, ry, rz) + 2) <= v) {
                        int rl = v - 1;
                        // Sets the relative value
                        context.setValue(rx, ry, rz, rl);
                        propagationQueue.add(new BfsNode(rx, ry, rz, rl));
                    }
                }
            }
        }
    }
}
