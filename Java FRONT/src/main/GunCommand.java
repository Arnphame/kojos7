package main;

public class GunCommand implements ICommand {

    private Gun gun;
    private GunAction gunAction;
    private int amount;

    public GunCommand(Gun gun, GunAction gunAction, int amount) {
        this.gun = gun;
        this.gunAction = gunAction;
        this.amount = amount;
    }

    @Override
    public void execute() {
        if(gunAction == GunAction.increase)
        {
            gun.increaseAmmo(amount);
        }
        else
        {
            gun.decreaseAmmo(amount);
        }
    }

    @Override
    public void undo() {
        if(gunAction != GunAction.increase)
        {
            gun.increaseAmmo(amount);
        }
        else
        {
            gun.decreaseAmmo(amount);
        }
    }
}
