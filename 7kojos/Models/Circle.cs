using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace _7kojos.Models
{
    public class Circle : Obstacle
    {
        public int X { get; set; }
        public int Y { get; set; }
        public int Radius { get; set; }
        public string Color { get; set; }

        public Circle(int x, int y, int radius, string color)
        {
            X = x;
            Y = y;
            Radius = radius;
            Color = color;
        }

        public Obstacle Clone()
        {
            return new Circle(X, Y, Radius, Color);
        }

        public void setPosition(int x, int y)
        {
            X = x;
            Y = y;
        }
    }
}
