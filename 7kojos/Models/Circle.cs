using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Threading.Tasks;

namespace _7kojos.Models
{
    public class Circle : Obstacle
    {
        public int Id { get; set; }
        public Point Position { get; set; }
        public int Radius { get; set; }
        public string Color { get; set; }
        Movement MovementStrategy;

        public Circle(Point position, int radius, string color, Movement movement)
        {
            Position = position;
            Radius = radius;
            Color = color;
            MovementStrategy = movement;
        }

        public Obstacle Clone()
        {
            return new Circle(new Point(Position.X, Position.Y), Radius, Color, MovementStrategy);
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
            return Radius;
        }

        public int GetHeight()
        {
            return Radius;
        }
    }
}
