package farm.makery.address.view;


import java.io.IOException;

public class AdapterScanFarm implements  SimDroneScanFarm{
    public TelloDrone telloDrone;


    public AdapterScanFarm(TelloDrone telloDrone) {
        this.telloDrone = telloDrone;


    }


	@Override
	public void handleScanFarm(int X, int Y) throws InterruptedException {
		 int Heightf = 200;
	     int Widthf =  800;
         int WidthTravelled = 0;



	        try{

	        	TelloDrone telloDrone = new TelloDrone();

	        	telloDrone.activateSDK();
	            telloDrone.takeoff();
	            telloDrone.gotoXY(0, Heightf, 100); //Goes to starting point

	            while(Widthf > WidthTravelled){
	                telloDrone.flyForward(600); //Flys length of the model
	                telloDrone.turnCCW(90);
	                telloDrone.flyForward(100);
	                WidthTravelled = WidthTravelled + 100; // increment the width traveled
	                telloDrone.turnCCW(90);
	                telloDrone.flyForward(600);
	                telloDrone.turnCW(90);
	                telloDrone.flyForward(100);
	                telloDrone.turnCW(90);

	                if(WidthTravelled > Widthf){

	                	telloDrone.gotoXY(0, Heightf, 100);//X * convertPxToCm, Y * convertPxToCm, 100);
	    	            telloDrone.land();
	    	            telloDrone.end();

	                    break;
	                }

	            }



	        } catch(IOException ex){
	            System.out.println (ex.toString());
	        }
	}






}






