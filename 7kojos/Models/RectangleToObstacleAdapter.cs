using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace _7kojos.Models
{
    public class RectangleToObstacleAdapter : Rectangle, Obstacle
    {
        public RectangleToObstacleAdapter(Rectangle rectangle)
            : base(rectangle.Position, rectangle.Width, rectangle.Height, rectangle.Color, rectangle.MovementStrategy)
        {
        }

        public void GetSize(out int width, out int height)
        {
            width = Width;
            height = Height;
        }

        public new Obstacle Clone()
        {
            Rectangle clone = base.Clone();
            return new RectangleToObstacleAdapter(clone);
        }

        public void SetMovement(Movement movement)
        {
            base.MovementStrategy = movement;
        }
    }
}
