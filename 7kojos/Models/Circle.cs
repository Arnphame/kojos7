using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Threading.Tasks;

namespace _7kojos.Models
{
    public class Circle : Shape
    {
        public int Id { get; set; }
        public Point Position { get; set; }
        public int Radius { get; set; }
        public Movement MovementStrategy;

        public Circle(Point position, int radius, ColorInterface color, Movement movement) : base(color)
        {
            Position = position;
            Radius = radius;
            Color = color;
            MovementStrategy = movement;
        }

        public Circle Clone()
        {
            return new Circle(new Point(Position.X, Position.Y), Radius, Color, MovementStrategy.Clone());
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


        /*public void GetSize(out int width, out int height)
        {
            width = Radius * 2;
            height = Radius * 2;
        }*/

        public int GetRadius()
        {
            return Radius;
        }

        public override void applyColor()
        {
            Color.applyColor();
        }
    }
}
