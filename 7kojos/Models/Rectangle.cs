using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace _7kojos.Models
{
    public class Rectangle : Obstacle
    {
        public int X { get; set; }
        public int Y { get; set; }
        public int Width { get; set; }
        public int Height { get; set; }
        public string Color { get; set; }

        public Rectangle(int x, int y, int width, int height, string color)
        {
            X = x;
            Y = y;
            Width = width;
            Height = height;
            Color = color;
        }

        public Obstacle Clone()
        {
            return new Rectangle(X,Y,Width,Height,Color);
        }

        public void setPosition(int x, int y)
        {
            X = x;
            Y = y;
        }
    }
}
