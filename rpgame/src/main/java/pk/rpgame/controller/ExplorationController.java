package pk.rpgame.controller;

import pk.rpgame.model.LevelMap;
import pk.rpgame.model.Room;
import pk.rpgame.model.items.ArmorItem;
import pk.rpgame.model.items.Item;
import pk.rpgame.model.living.Hero;
import pk.rpgame.view.*;

import java.util.List;

public class ExplorationController extends Controller implements MenuClickListener{


    private static ExplorationView explorationView;
    private Room room;

    private Room previousRoom;

    private Map map;

    private Hero heroControler;

    private LevelMap activeLevelMapController;
    
    private GeneralMenuController generalMenuHelp;




    public ExplorationController(Hero hero,Map map, Room currentRoom,Room previousRoom,LevelMap activeLevelMap,
                                 GameEngine gameEngine) {
        super(gameEngine);
        this.explorationView = new ExplorationView();
        this.room = currentRoom;
        this.heroControler=hero;
        this.map=map;
        this.activeLevelMapController=activeLevelMap;
        this.previousRoom=previousRoom;

    }

    public ExplorationController(Hero hero,Map map, Room currentRoom,LevelMap activeLevelMap,
                                 GameEngine gameEngine) {
        super(gameEngine);
        this.explorationView = new ExplorationView();
        this.room = currentRoom;
        this.heroControler=hero;
        this.map=map;
        this.activeLevelMapController=activeLevelMap;

    }

    public void setGameEngineController(GameEngine gameEngineController) {
        this.gameEngine = gameEngineController;
    }

    @Override
    public void initView() {
        explorationView.printRoomDescription(room);
        explorationView.setListener(this);
        explorationView.showMenu();
    }

    @Override
    public void onActionClick(int num) {
        switch (num){
            case 1:
                findItem();
                break;
            case 2:
                nextRoom();
                break;
            case 3:
                showMap();
                break;
            case 4:
                showGeneralMenu();
                break;
            default:
                explorationView.wrongChoice();
                explorationView.showMenu();
        }
    }

    public  void findItem(){
        List<Item> itemList=room.getItems();
        if(itemList.isEmpty()){
            explorationView.nothingFound();
            explorationView.showMenu();
        }else{
            PickUpItems isPickedUp=explorationView.pickUpItems(room.getItems());

            if(isPickedUp==PickUpItems.PICK_UP){
                for (Item item:
                     itemList) {
                     if (item.getClass()==ArmorItem.class) {
                        for (Item item2:
                             heroControler.getItems()) {
                            if (item2.getClass() == ArmorItem.class) {
                                heroControler.removeItem(item2);
                            }
                        }
                    }
                     heroControler.addItem(item);
                     room.removeItem(item);
                     explorationView.addToInventory(item);
                     explorationView.showMenu();
                }
            }else {
                    explorationView.showMenu();
            }
        }
    }

    public void nextRoom(){
        //user choose next room
        List<Room> nearestRoom=room.getNearestRooms();
        int nextDestination=explorationView.getRoomChoice(nearestRoom);
        // new room for hero
        Room nextRoom=nearestRoom.get(nextDestination-1);
        activeLevelMapController.changeRooms(room,nextRoom);
        previousRoom=room;
        room=nextRoom;
        if(room.getCreatures().isEmpty()){
            gameEngine.changeStateControler(new ExplorationController(heroControler,map,room,
                    previousRoom,activeLevelMapController,gameEngine));
        }else{
            gameEngine.changeStateControler(new FightController(gameEngine,heroControler,map,room,previousRoom
                    ,activeLevelMapController));
        }
    }


    public void showMap(){
        map.show();
        explorationView.showMenu();
    }

    public void showGeneralMenu(){
        gameEngine.changeStateControler(new GeneralMenuController(heroControler,room,previousRoom,map,activeLevelMapController,
                gameEngine));
    }


}
