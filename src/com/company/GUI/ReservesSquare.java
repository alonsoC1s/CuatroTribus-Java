package com.company.GUI;

import com.company.Pieces.GuiPiece;
import com.company.Tribes.Tribe;

import java.util.ArrayList;
import java.util.List;

public class ReservesSquare {
    public Tribe ownerTribe;
    public List<GuiPiece> pieces = new ArrayList<>();

    private int x = 605;
    private int y = 0;


    public ReservesSquare(Tribe ownerTribe){};

    public void addReserves(List<GuiPiece> newPieces ){
        this.pieces.addAll(newPieces);
    }
}
