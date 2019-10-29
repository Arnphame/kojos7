using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace _7kojos.Models
{
    public class ObstacleFactory
    {
        private static Dictionary<string, Obstacle> prototypes = new Dictionary<string, Obstacle>
        {
            { "Cube", new Rectangle(0,0,50,50, "White") },
            { "Rectangle", new Rectangle(0,0,100,50, "White") },
            { "Circle", new Circle(0,0,10, "White") }
        };


        public static Obstacle getPrototype(String type)
        {
            return prototypes.FirstOrDefault((pair) => pair.Key.Equals(type)).Value;
        }
    }
}
