package org.example;

public class Jump extends Thread {

    /**
     * Height of the jump
     */
    private static final int JUMP_HEIGHT = 85;

    /**
     * Origin Y-Position of the character
     */
    private static final int originYPosition = 420;

    /**
     * Position of the character while jumping
     */
    private int jumpPosition = originYPosition;
    private boolean jumpIsDone = true;
    private boolean highpointReached = false;



    /**
     * Checks if the character is still jumping
     */
    public void run() {
        jumpIsDone = false;
        int jumpLag = 3; // time to wait after every jump, to see the actual jump

        while (!jumpIsDone) {
            jump();

            try {
                //noinspection BusyWait
                Thread.sleep(jumpLag);
            } catch (InterruptedException ignored) {

            }
        }
        highpointReached = false;
    }

    /**
     * Provides the jump of the character
     */
    public void jump() {
        if (!highpointReached) {
            jumpPosition--;
        }
        if (jumpPosition == (originYPosition - JUMP_HEIGHT)) {
            highpointReached = true;
        }
        if (highpointReached && jumpPosition <= originYPosition) {
            jumpPosition++;
            if (jumpPosition == originYPosition) {
                jumpIsDone = true;
            }
        }
    }
}
