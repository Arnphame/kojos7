package main;

public class StopExpression implements Expression {

    public boolean shouldBeStopped;

    public StopExpression() {
        this.shouldBeStopped = false;
    }

    @Override
    public void interpret(String context) {
            this.shouldBeStopped = true;
    }
}
