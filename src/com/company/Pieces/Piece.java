package com.company.Pieces;

public abstract class Piece {
   public enum colors{WHITE, RED, BLUE, GREEN}
   public enum types{HORSE, ARTILLERY, INFANTRY}

   private colors pColor;
   private types pType;
   private int pPower;
   public boolean isDeployed;

   public Piece(colors color, types type, int power, boolean isDeployed){
       this.pColor = color;
       this.pType = type;
       this.pPower = power;
       this.isDeployed = isDeployed;
   }


}
