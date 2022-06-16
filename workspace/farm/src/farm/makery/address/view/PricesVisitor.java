package farm.makery.address.view;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class PricesVisitor implements Visitor{
	private double totalPrices;
	private double totalMarket;
	
	public PricesVisitor() {
		totalPrices = 0.0;
		totalMarket = 0.0;
	}
	
	public void gettingPrice(TreeView<String> theTree, ArrayList<ItemBranch> theItems, ArrayList<ItemContainer> theContainers) {
		
		totalPrices = 0;
		
		TreeItem<String> selector = theTree.getRoot();
		ArrayList<TreeItem> perallelLevels = new ArrayList<TreeItem>();
		
		if (theTree.getSelectionModel().getSelectedItem() != null){
			selector = theTree.getSelectionModel().getSelectedItem();
		}
		
		
		int itemCount = theTree.getExpandedItemCount();
		
		int selectLevel = theTree.getTreeItemLevel(selector);
		
		for (int l = 0; l < theItems.size(); l++) {
			
			if (selector.getValue() == theItems.get(l).getName()){
				
				totalPrices = theItems.get(l).getPrice();
				return;
			}
		}
		
		List<Item> fullList = new ArrayList<Item>();
		fullList.addAll(theItems);
		fullList.addAll(theContainers);
		
		for (int k = 0; k < fullList.size(); k++) {
			if (selector.getValue() == fullList.get(k).getName()){
				totalPrices = totalPrices + fullList.get(k).getPrice();
				break;
			}
		}
		
		
		//loops through whole tree
		outerLoop:
		for (int i = 0; i < itemCount; i++) {
			
			TreeItem<String> itemLooped = theTree.getTreeItem(i);
			int loopLevel = theTree.getTreeItemLevel(itemLooped);
			
			if (loopLevel == selectLevel && selector.getValue() != itemLooped.getValue()) {
				perallelLevels.add(itemLooped);
				continue;
			}
			
			for (int m = 0; m < perallelLevels.size(); m++) {
				if (perallelLevels.get(m) == itemLooped.getParent()) {
					continue outerLoop;
				}
			}
			
			
			//If the level of the selected item level is < the level of looped item 
			if (loopLevel > selectLevel) {
				
				//loops through item ArrayList to find price of item and adds it to the total 
				for (int j =0; j < theItems.size(); j++) {
					
					Item item = theItems.get(j);
					
					if (itemLooped.getValue() == item.getName()) {
						
						totalPrices = totalPrices + item.getPrice();
						break;
					}
				}
				
				
				for (int j =0; j < theContainers.size(); j++) {
					
					Item conatiner = theContainers.get(j);
					
					if (itemLooped.getValue() == conatiner.getName()) {
						
						totalPrices = totalPrices + conatiner.getPrice();
						break;
					}
				}
			}
		}
	}
	
	
	public void gettingMarket(TreeView<String> theTree, ArrayList<ItemBranch> theItems) {
		
		ArrayList<TreeItem> otherChildren = new ArrayList<TreeItem>();
		
		TreeItem<String> selector = theTree.getRoot();
		
		if (theTree.getSelectionModel().getSelectedItem() != null){
			selector = theTree.getSelectionModel().getSelectedItem();
		}
		
		int itemCount = theTree.getExpandedItemCount();
		
		int selectLevel = theTree.getTreeItemLevel(selector);
		
		for (int k = 0; k < theItems.size(); k++) {
			if (selector.getValue() == theItems.get(k).getName()) {
				totalMarket = totalMarket + theItems.get(k).getPrice();
			}
		}
		
		//loops through whole tree
		outerLoop:
		for (int i = 0; i < itemCount; i++) {
			
			
			TreeItem<String> itemLooped = theTree.getTreeItem(i);
			int loopLevel = theTree.getTreeItemLevel(itemLooped);
			
			if (loopLevel == selectLevel && selector.getValue() != itemLooped.getValue()) {
				otherChildren.add(itemLooped);
				continue;
			}
			
			
			for (int m = 0; m < otherChildren.size(); m++) {
				if (otherChildren.get(m) == itemLooped.getParent()) {
					continue outerLoop;
				}
			}
			
			//If the level of the selected item is < the level of looped item
			if (loopLevel > selectLevel) {
			
				//loops through item ArrayList and adds price of item to totalMarket
				for (int j =0; j < theItems.size(); j++) {
				
					Item item = theItems.get(j);
				
					if (itemLooped.getValue() == item.getName()) {
					
						totalMarket = totalMarket + item.getPrice();
					}
				}
			}	

		}
	}
	

	@Override
	public double visit(ItemBranch totalPrice) {
		// TODO Auto-generated method stub
		return totalPrices;
	}
	
	

	@Override
	public double visit(ItemContainer itemMarketValue) {
		// TODO Auto-generated method stub
		return totalMarket;
	}


}
