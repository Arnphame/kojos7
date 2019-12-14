using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Threading.Tasks;

namespace _7kojos.Models
{
    public class ObstacleFactory
    {
        private static Dictionary<string, Obstacle> prototypes = new Dictionary<string, Obstacle>
        {
            { "Cube", new RectangleToObstacleAdapter(new Rectangle(new Point(0,0) ,50,50, new RedColor(), new VerticalMovement(0,400))) },
            { "Rectangle", new RectangleToObstacleAdapter(new Rectangle(new Point(0,0),100,50, new RedColor(), new HorizontalMovement(0,700))) },
            { "Circle", new CircleToObstacleAdapter( new Circle(new Point(0,0),10, new RedColor(), new VerticalMovement(150,300))) },

        };


        public static Dictionary<Powerup.PowerUpType, Obstacle> boostPrototypes = new Dictionary<Powerup.PowerUpType, Obstacle>
        {
            { Powerup.PowerUpType.HP, new Powerup(Powerup.PowerUpType.HP, 30, 0,0, 5) },
            { Powerup.PowerUpType.DMG, new Powerup(Powerup.PowerUpType.DMG, 10,0,0, 5) },
            { Powerup.PowerUpType.SPEED, new Powerup(Powerup.PowerUpType.SPEED, 1.3,0,0, 5) },
        };


        public static Obstacle getPrototype(String type)
        {
            return prototypes.FirstOrDefault((pair) => pair.Key.Equals(type)).Value;
        }
    }
}
