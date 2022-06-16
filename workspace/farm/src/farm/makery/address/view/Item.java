package farm.makery.address.view;

public interface Item {
	
    // Setters
    public void setName(String name);
    public void setPrice(double price);
    public void setX(int locX);
    public void setY(int locY);
    public void setLength(int length);
    public void setWidth(int width);
    public void setHeight(int height);
    

    // Getters
    public String getName();
    public double getPrice();
    public int getLocX();
    public int getLocY();
    public int getLength();
    public int getWidth();
    public int getHeight();
    
    //visitor
    public double accept(Visitor vis);

}






