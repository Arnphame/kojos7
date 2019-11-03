package main;

public class Gun {
    public int ammoCount;
    public String gunType;

    public Gun(int ammoCount, String gunType) {
        this.ammoCount = ammoCount;
        this.gunType = gunType;
    }
    public int increaseAmmo(int count)
    {
        ammoCount+=count;
        return ammoCount;
    }
    public int decreaseAmmo(int count)
    {
        ammoCount-=count;
        return ammoCount;
    }
    public void switchType()
    {
        if(gunType.equals("arrow")) {
            gunType = "bullet";
            ammoCount = 3;
        }
        else if(gunType.equals("bullet")){
            gunType = "grenade";
            ammoCount = 1;
        }
        else{
            gunType = "arrow";
            ammoCount = 5;
        }
    }
}
