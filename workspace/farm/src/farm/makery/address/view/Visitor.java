package farm.makery.address.view;

interface Visitor {
	
	public double visit(ItemBranch totalPrice);
	
	public double visit(ItemContainer itemMarketValue);

}
