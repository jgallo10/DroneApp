package farm.makery.address.view;


public class ItemContainer implements VIsitable, Item {
	
	
	String name = "name";
	double price =0.0;
	int locX = 0;
	int locY = 0;
	int length = 0;
	int width = 0;
	int height = 0;
	
	ItemContainer(){
		
	}
	
	public ItemContainer(String name, double price, int x, int y, int l, int w, int h /*ArrayList<Item> child*/) {
		this.name = name;
		this.price = price;
		locX = x;
		locY = y;
		length = l;
		width = w;
		height = h;
		/*this.child = child;*/
	}

	//setter methods 
		public void setName(String name) {
			// TODO Auto-generated method stub
			this.name = name;
		}
		public void setPrice(double price) {
			// TODO Auto-generated method stub
			this.price = price;
		}
		public void setX(int locX) {
			// TODO Auto-generated method stub
			this.locX = locX;
		}
		public void setY(int locY) {
			// TODO Auto-generated method stub
			this.locY = locY;
		}
		public void setLength(int length) {
			// TODO Auto-generated method stub
			this.length = length;
		}
		public void setWidth(int width) {
			// TODO Auto-generated method stub
			this.width = width;
		}
		public void setHeight(int height) {
			// TODO Auto-generated method stub
			this.height = height;
		}

		
		
		//getter methods
		
		public String getName() {
			// TODO Auto-generated method stub
			return name;
		}
		
		public double getPrice() {
			// TODO Auto-generated method stub
			return price;
		}
		
		public int getLocX() {
			// TODO Auto-generated method stub
			return locX;
		}

		public int getLocY() {
			// TODO Auto-generated method stub
			return locY;
		}
		
		public int getLength() {
			// TODO Auto-generated method stub
			return length;
		}
		
		public int getWidth() {
			// TODO Auto-generated method stub
			return width;
		}
		
		public int getHeight() {
			// TODO Auto-generated method stub
			return height;
		}
		
		public double accept(Visitor vis) {
			return vis.visit(this);
		}

}
