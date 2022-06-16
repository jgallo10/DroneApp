package farm.makery.address.view;


import java.awt.Component;
import java.awt.Point;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import farm.makery.address.MainFarmApp;
import javafx.animation.PathTransition;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.VLineTo;
import javafx.util.Duration;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
public class FarmOverviewController implements Initializable{

    //Buttons
	@FXML
	private Button visitItem;
	@FXML
	private Button scanFarm;
	@FXML
	private Button addItem;
	@FXML
	private Button deleteItem;
	@FXML
	private Button addParent;
	@FXML
	private Button addContainer;
	@FXML
	private Button addDrone;
	@FXML
	private Button droneItemFly;
	@FXML
	private Button droneFarmFly;

	//text boxes
	@FXML
	private TextField itemName;
	@FXML
	private TextField pricing;
	@FXML
	private TextField xValue;
	@FXML
	private TextField yValue;
	@FXML
	private TextField lValue;
	@FXML
	private TextField wValue;
	@FXML
	private TextField hValue;
	@FXML
	private TextField marketValue;
	@FXML
	private TextField purchasePrice;

	//pane
	@FXML
	private Pane basePane;

	//Drone Image
	@FXML
	private ImageView drone;


	//TreeView
	@FXML
	public TreeView<String> myTreeView;

    //TelloDrone
	private TelloDrone telloDrone;


	//arrayList for item and containers
	private ArrayList<ItemBranch> theItems;
	private ArrayList<ItemContainer> theContainers;

	//declaring item variables
	String name;
	double price;
	int locX, locY, length, width, height;
	int droneVis = 1;


	//int duplic = 0;
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		//initialize array list for Items
		theItems = new ArrayList<ItemBranch>();

		//initialize array list for Items Containers
		theContainers = new ArrayList<ItemContainer>();


		ItemContainer farm = new ItemContainer("Farm",0,0,0,800,600,0);

		TreeItem<String> root = new TreeItem<String>(farm.getName());
		root.setExpanded(true);
		myTreeView.setRoot(root);

		theContainers.add(farm);

		showSelected();
		showPrices();

		myTreeView.getSelectionModel().selectedItemProperty().addListener((ChangeListener)
				(observable, oldValue, newValue) -> {
					showSelected();
					showPrices();
				});
	}


	private void showSelected() {

		Item ItemSelect = getSelected();

		itemName.setText(ItemSelect.getName());
		pricing.setText(String.valueOf(ItemSelect.getPrice()));
		xValue.setText(String.valueOf(ItemSelect.getLocX()));
		yValue.setText(String.valueOf(ItemSelect.getLocY()));
		lValue.setText(String.valueOf(ItemSelect.getLength()));
		wValue.setText(String.valueOf(ItemSelect.getWidth()));
		hValue.setText(String.valueOf(ItemSelect.getHeight()));
	}

	private void showPrices() {

		PricesVisitor visitor = new PricesVisitor();

		Item visCont = new ItemContainer();
		Item visItem = new ItemBranch();

		visitor.gettingPrice(myTreeView, theItems, theContainers);
		visitor.gettingMarket(myTreeView, theItems);

		visCont.accept(visitor);
		visItem.accept(visitor);

		marketValue.setText(String.valueOf(visCont.accept(visitor)));
		purchasePrice.setText(String.valueOf(visItem.accept(visitor)));

	}


	//method to return the item that is selected
	private Item getSelected() {

		String currentSelect = selectItem();

		for (int i = 0; i < theItems.size(); i++) {

			String currentName = theItems.get(i).getName();
			Item currentItem = theItems.get(i);

			if (currentSelect == currentName) {

				return currentItem;
			}
		}


		for (int j = 0; j < theContainers.size(); j++) {

			String currentName = theContainers.get(j).getName();
			Item currentItem = theContainers.get(j);

			if (currentSelect == currentName) {

				return currentItem;
			}
		}

		return theContainers.get(0);
	}


	// method/handler to return the name of selected item
	public String selectItem() {


		TreeItem<String> selection = myTreeView.getSelectionModel().getSelectedItem();

		if(selection != null) {
			return selection.getValue();
		}

		return myTreeView.getRoot().getValue();

	}


	//Method for adding child to the hierarchy
	@FXML
	private void handleAdd(ActionEvent event){


		//if statement to make sure all boxes are filled
				if (itemName.getText().isEmpty() || pricing.getText().isEmpty() || xValue.getText().isEmpty() || yValue.getText().isEmpty()
						|| lValue.getText().isEmpty() || wValue.getText().isEmpty() || hValue.getText().isEmpty()) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText("Please fill all boxes.");
					alert.show();
				}
				else {
					//exception to get the right data type
					try {
						name = itemName.getText();
						price = Double.parseDouble(pricing.getText());
						locX = Integer.parseInt(xValue.getText());
						locY = Integer.parseInt(yValue.getText());
						length = Integer.parseInt(lValue.getText());
						width = Integer.parseInt(wValue.getText());
						height = Integer.parseInt(hValue.getText());


						addChildHelp(name);
						addPanes();

				}
					catch(Exception e) {
						System.out.print(e);
					}
				}
	}



	//Helper method to add child to the hierarchy for
	private void addChildHelp(String value){

		//create ItemBranch object and add attributes
		Item newContainer = new ItemContainer();
		newContainer.setName(name);
		newContainer.setPrice(price);
		newContainer.setX(locX);
		newContainer.setY(locY);
		newContainer.setLength(length);
		newContainer.setWidth(width);
		newContainer.setHeight(height);

		theContainers.add( (ItemContainer) newContainer);


	    TreeItem parent = myTreeView.getSelectionModel().getSelectedItem();

	     TreeItem newItem = new TreeItem(value);
			 parent.getChildren().add(newItem);


			 if (!parent.isExpanded())
			 {
			   parent.setExpanded(true);
			 }






	}


	@FXML
	private void handleAddItem(ActionEvent event){



		//if statement to make sure all boxes are filled
		if (itemName.getText().isEmpty() || pricing.getText().isEmpty() || xValue.getText().isEmpty() || yValue.getText().isEmpty()
				|| lValue.getText().isEmpty() || wValue.getText().isEmpty() || hValue.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Please fill all boxes.");
			alert.show();
		}
		else {
			//exception to get the right data type
			try {
				name = itemName.getText();
				price = Double.parseDouble(pricing.getText());
				locX = Integer.parseInt(xValue.getText());
				locY = Integer.parseInt(yValue.getText());
				length = Integer.parseInt(lValue.getText());
				width = Integer.parseInt(wValue.getText());
				height = Integer.parseInt(hValue.getText());


				addItemHelp(name);
				addPanes();
		}
			catch(Exception e) {
				System.out.print(e);
			}
		}

	}


	private void addItemHelp(String value){

		//create ItemBranch object and add attributes
		Item newItem = new ItemBranch();
		newItem.setName(name);
		newItem.setPrice(price);
		newItem.setX(locX);
		newItem.setY(locY);
		newItem.setLength(length);
		newItem.setWidth(width);
		newItem.setHeight(height);


        theItems.add( (ItemBranch) newItem);

		//get selected item and create treeItem obj add newItem obj into it
		TreeItem parent = myTreeView.getSelectionModel().getSelectedItem();

		TreeItem<String> newItemTree = new TreeItem<String>(value);
		theItems.add((ItemBranch) newItem);
		parent.getChildren().add(newItemTree);

		if(!parent.isExpanded()) {

			parent.setExpanded(true);
		}




	}






	//Method to add Panes
    private void addPanes() throws FileNotFoundException{



		basePane.getChildren().clear();

		basePane.getChildren().add(drone);



		for (int j = 0; j < theContainers.size(); j++) {

			Pane newPanes = new Pane();

			Item drawItem = theContainers.get(j);

			newPanes.relocate(drawItem.getLocX(),drawItem.getLocY());
			newPanes.setBorder(new Border(new BorderStroke(Color.BLACK,  BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
			newPanes.setPrefSize(drawItem.getLength(),drawItem.getWidth());
			basePane.getChildren().add(newPanes);
		}


		for (int i = 0; i < theItems.size(); i++) {

			Pane newPanes = new Pane();

			Item drawItem = theItems.get(i);

			newPanes.relocate(drawItem.getLocX(),drawItem.getLocY());
			newPanes.setBorder(new Border(new BorderStroke(Color.BLACK,  BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
			newPanes.setPrefSize(drawItem.getLength(),drawItem.getWidth());
			basePane.getChildren().add(newPanes);
		}

    }


	//Method for deleting from the hierarchy
	@FXML
	private void handleDelete(){
		deleteItem();

		//test out drone connection and movement
		/*FightDemo fly = new FightDemo();

		try {
			fly.flight();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

	}


	//Helper method to delete items from the hierarchy

	private void deleteItem(){
		  TreeItem item = myTreeView.getSelectionModel().getSelectedItem();


		    TreeItem parent = item.getParent();
		    parent.getChildren().remove(item);

           //Currently always deletes the oldest pane added
		   //Needs to be fixed
		   basePane.getChildren().remove(0);
	}



	 //Button for scanning the farm
	 @FXML
	 private void handleScan() {

		 int i = 50;
		 int y = 50, j;

		 //variable for the panes bounds
		 double paneHeight = basePane.getPrefHeight();
		 double paneWidth = basePane.getPrefWidth();


		 Path path = new Path();
		 PathTransition transition = new PathTransition();

		 path.getElements().add(new MoveTo(i,i));

		 //loop to add coordinates to path
		 for (y = 50, j = 100; i < paneWidth; i = i + 100, j = j + 100 ) {
			 path.getElements().add(new LineTo(i, y));
			 path.getElements().add(new LineTo(i, paneHeight - y));
			 path.getElements().add(new LineTo(j, paneHeight - y));
			 path.getElements().add(new LineTo(j, y));
		 }

		 path.getElements().add(new LineTo(y,y));



		 transition.setNode(drone);
		 transition.setDuration(Duration.seconds(20));
		 transition.setPath(path);
		 transition.setCycleCount(1);
		 transition.play();




	}




	 //Button for visiting a selected item/container
	 @FXML
	 private void handleVisitItem() throws IOException, InterruptedException{


		 int i = 50, j = 50;

		 Path path = new Path();
		 PathTransition transition = new PathTransition();

		 path.getElements().add(new MoveTo(i,i));

		 Item pickItem = getSelected();

		 path.getElements().add(new LineTo(pickItem.getLocX() + 30, pickItem.getLocY() + 30));

		 path.getElements().add(new LineTo(i, j));

		 transition.setNode(drone);
		 transition.setDuration(Duration.seconds(5));
		 transition.setPath(path);
		 transition.setCycleCount(1);
		 transition.play();





	 }



	// For real drone scan farm
	 @FXML
	 private void handleDroneScanFarm() throws InterruptedException{

		 int  X = (int) drone.getX();
	     int  Y = (int) drone.getY();

		SimDroneScanFarm scanAdapted = new AdapterScanFarm(telloDrone);
        scanAdapted.handleScanFarm(X, Y);

	 }

	 //For real drone visit item
	 @FXML
	 private void handleDroneVisitItem(){

		 Item droneItem = getSelected();


		 int X = (int) ((int) drone.getX() + drone.getFitWidth());
		 int Y = (int) ((int) drone.getY() + drone.getFitWidth());
		 int itemX = droneItem.getLocX();
		 int itemY = droneItem.getLocY();
		 Point dronePoint = new Point (X,Y);
		 Point itemPoint = new Point (itemX, itemY);

		SimDroneVisItem visitAdapted = new AdapterVisitItem(telloDrone);
	    visitAdapted.handleVisitItem(itemPoint, dronePoint);

	 }


	private MainFarmApp mainFarmApp;


	public void setMainFarmApp(MainFarmApp mainFarmApp) {
		this.mainFarmApp = mainFarmApp;

	}




}
