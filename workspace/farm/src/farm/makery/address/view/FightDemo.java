package farm.makery.address.view;

import java.io.IOException;

public class FightDemo {

	

		
		public void flight() throws InterruptedException, IOException {
			TelloDrone tello = new TelloDrone();
			tello.activateSDK();
			tello.streamOn();
			tello.streamViewOn();
			tello.hoverInPlace(10);
			tello.takeoff();
			tello.flyForward(100);
			tello.turnCCW(180);
			tello.flip("b");
			tello.flyForward(100);
			tello.flip("f");
			tello.turnCW(180);
			tello.land();
			tello.streamOff();
			tello.streamViewOff();
			tello.end();
		}

		public void main(String[] args) throws InterruptedException, IOException {
			flight();
			System.exit(0);
		}
	
}
