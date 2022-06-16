package farm.makery.address.view;

import java.awt.Point;

public class AdapterVisitItem implements SimDroneVisItem {

	  public TelloDrone telloDrone;
	  private int convertPxToCm;

	  public AdapterVisitItem(TelloDrone telloDrone) {
	      this.telloDrone = telloDrone;
	      this.convertPxToCm = (int) ((double) Constants.CENTIMETERS_PER_MODEL_FOOT/ (double) Constants.PIXELS_TO_ONE_MODEL_FOOT);

	  }

	@Override
	public void handleVisitItem(Point itemPoint, Point dronePoint) {

		try{
			TelloDrone telloDrone = new TelloDrone();

			telloDrone.activateSDK();

            telloDrone.takeoff();

            telloDrone.gotoXY(itemPoint.y * convertPxToCm,itemPoint.x * convertPxToCm, 100);

            telloDrone.hoverInPlace(5);
            telloDrone.gotoXY((-1 * (itemPoint.y * convertPxToCm)),(-1 * (itemPoint.x * convertPxToCm)), 100);

            telloDrone.land();
            telloDrone.end();
        } catch(Exception ex){

            System.out.println(ex.toString());
        }


	}

}
