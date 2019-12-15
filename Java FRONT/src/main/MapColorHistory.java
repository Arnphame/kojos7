package main;

import java.util.ArrayList;
import java.util.List;

public class MapColorHistory {
    private List<MapColorSnapshot> history;

    public MapColorHistory(){
        history = new ArrayList<>();
    }

    public void addSnapshot(MapColorSnapshot snapshot){
        history.add(snapshot);
    }

    public void undo(){
        if(history.size() < 1)
            return;
        history.get(history.size()-1).restore();
        history.remove(history.size()-1);
    }
}
