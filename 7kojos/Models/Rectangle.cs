using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Threading.Tasks;

namespace _7kojos.Models
{
    public class Rectangle : Obstacle
    {
        public int Id { get; set; }
        public Point Position { get; set; }
        public int Width { get; set; }
        public int Height { get; set; }
        public string Color { get; set; }
        Movement MovementStrategy;

        public Rectangle(Point position, int width, int height, string color, Movement movement)
        {
            Position = position;
            Width = width;
            Height = height;
            Color = color;
            MovementStrategy = movement;
        }

        public Obstacle Clone()
        {
            return new Rectangle(Position, Width, Height, Color, MovementStrategy);
        }

        public void SetPosition(Point position)
        {
            Position = position;
        }

        public int GetX()
        {
            return Position.X;
        }

        public int GetY()
        {
            return Position.Y;
        }

        public void Move()
        {
            SetPosition(MovementStrategy.Move(Position));
        }

        public int GetId()
        {
            return Id;
        }

        public void SetId(int id)
        {
            Id = id;
        }

        public string GetColor()
        {
            return Color;
        }

        public int GetWidth()
        {
            return Width;
        }

        public int GetHeight()
        {
            return Height;
        }
    }
}
