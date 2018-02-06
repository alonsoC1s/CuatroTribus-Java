package com.company.Tribes;

import com.company.Pieces.Piece;

public class GreenTribe extends Tribe{

    /**
     * Constructor. When called, a new tribe of color x is created.
     * The method then creates a full army for each color as specified by the game´s rules
     * The int arrays are the specific powers each piece of each category must have. These are defined by game rules
     *
     * @param color
     */
    public GreenTribe(Piece.colors color) {
        super(color);
    }
}
