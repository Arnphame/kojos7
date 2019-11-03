using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Threading.Tasks;

namespace _7kojos.Models
{
    public class CircleToObstacleAdapter : Circle, Obstacle
    {
        public CircleToObstacleAdapter(Circle circle)
            : base(circle.Position, circle.Radius, circle.Color, circle.MovementStrategy)
        {
        }

        public void GetSize(out int width, out int height)
        {
            width = height = base.GetRadius() * 2;
        }

        public new Obstacle Clone()
        {
            Circle clone = base.Clone();
            return new CircleToObstacleAdapter(clone);
        }

        public void SetMovement(Movement movement)
        {
            base.MovementStrategy = movement;
        }
    }
}
