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


    public ReservesSquare(Tribe ownerTribe){
        this.ownerTribe = ownerTribe;
    };

    public void addReserves(List<GuiPiece> newPieces ){
        this.pieces.addAll(newPieces);
        sortReservesOnSquare();
    }

    public void recalculateReserves(){
        for(GuiPiece piece : ownerTribe.getTribalArmy()){
            if (!piece.isDeployed){
                this.pieces.add(piece);
            }
        }

        sortReservesOnSquare();
    }

    private void sortReservesOnSquare() {
        int index = 0;
        int index2 = 1;
        for (GuiPiece piece : pieces) {
            piece.setxPos(620 + index2);
            piece.setyPos(index * 80);

            index++;

            if (index % 8 == 0) {
                index2 += 90;
                index = 0;
            }
        }
    }

}
