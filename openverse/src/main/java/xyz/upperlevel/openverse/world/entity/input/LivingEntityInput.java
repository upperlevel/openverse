package xyz.upperlevel.openverse.world.entity.input;

public interface LivingEntityInput {
    LivingEntityInput FREEZE = new LivingEntityInput() {
        @Override
        public void onTick() {

        }

        @Override
        public float getStrafe() {
            return 0f;
        }

        @Override
        public float getUp() {
            return 0f;
        }

        @Override
        public float getForward() {
            return 0f;
        }

        @Override
        public float getYaw() {
            return 0f;
        }

        @Override
        public float getPitch() {
            return 0f;
        }
    };

    void onTick();

    float getStrafe();

    float getUp();

    float getForward();

    float getYaw();

    float getPitch();
}
