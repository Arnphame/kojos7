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
            { "Cube", new Rectangle(new Point(0,0) ,50,50, new RedColor().applyColor(), new VerticalMovement(0,400)) },
            { "Rectangle", new Rectangle(new Point(0,0),100,50, new RedColor().applyColor(), new HorizontalMovement(0,700)) },
            { "Circle", new Circle(new Point(0,0),10, new RedColor().applyColor(), new VerticalMovement(150,300)) }
        };


        public static Obstacle getPrototype(String type)
        {
            return prototypes.FirstOrDefault((pair) => pair.Key.Equals(type)).Value;
        }
    }
}
