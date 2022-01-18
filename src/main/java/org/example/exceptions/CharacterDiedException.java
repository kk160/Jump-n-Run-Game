package org.example.exceptions;

public class CharacterDiedException extends Throwable {

    /**
     * Position of the character when he dies in the game
     */
    private final int position;

    public CharacterDiedException(int position) {
        this.position = position;
    }

    @Override
    public String getMessage() {
        return "Character died on position " + position;
    }
}
