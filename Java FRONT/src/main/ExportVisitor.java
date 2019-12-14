package main;

public class ExportVisitor implements Visitor {

    public ExportVisitor() {
    }

    @Override
    public String visitHPBoost(HPBoost hpBoost) {
            return "ID = " + hpBoost.getId() + "\n" +
                    "Type = " + hpBoost.getTime() + "\n" +
                    "Value = " + hpBoost.getValue() + "\n" +
                    "X = " + hpBoost.getX() + "\n" +
                    "Y = " + hpBoost.getY() + "\n" +
                    "Time = " + hpBoost.getTime() + "\n";
    }

}
