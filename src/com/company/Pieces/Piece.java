package com.company.Pieces;

import com.company.colors;

public abstract class Piece {
   public enum types{HORSE, ARTILLERY, INFANTRY, CITY}

   protected colors pColor;
   protected types pType;
   protected int pPower;
   public int piecePrice;
   public boolean isDeployed;

   public Piece(colors color, types type, int power, boolean isDeployed){
       this.pColor = color;
       this.pType = type;
       this.pPower = power;
       this.isDeployed = isDeployed;

       determinePrice();
   }

   private void determinePrice(){
       switch (this.pType){
           case INFANTRY:
               this.piecePrice = 1;
               break;
           case ARTILLERY:
               this.piecePrice = 7;
               break;
           case HORSE:
               this.piecePrice = 4;
               break;
       }
   }

   public int getPiecePrice(){ return  this.piecePrice; }


}
