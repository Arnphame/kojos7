using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Threading.Tasks;

namespace _7kojos.Models
{
    public class Rectangle : Shape
    {
        public int Id { get; set; }
        public Point Position { get; set; }
        public int Width { get; set; }
        public int Height { get; set; }
        public Movement MovementStrategy;

        public Rectangle(Point position, int width, int height, ColorInterface color, Movement movement) : base(color)
        {
            Position = position;
            Width = width;
            Height = height;
            Color = color;
            MovementStrategy = movement;
        }

        public Rectangle Clone()
        {
            //return this;
            return new Rectangle(Position, Width, Height, Color, MovementStrategy.Clone());
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

        public ColorInterface GetColor()
        {
            return Color;
        }

        public override void applyColor()
        {
            Color.applyColor();
        }
    }
}
