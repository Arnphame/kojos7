using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Threading.Tasks;

namespace _7kojos.Models
{
    public class CircleMovement : Movement
    {
        private int xDirection;
        private int yDirection;
        private int radius;
        private int movesCount;
        public CircleMovement(int radius)
        {
            this.xDirection = 1;
            this.yDirection = 1;
            this.radius = radius;
            this.movesCount = 0;
        }

        public Movement Clone()
        {
            return new CircleMovement(radius);
        }

        public Point Move(Point currentPos)
        {
            movesCount++;
            if (movesCount / radius == 1)
                yDirection = -1;
            if (movesCount / radius == 2)
                xDirection = -1;
            if (movesCount / radius == 3)
            {
                xDirection = -1;
                yDirection = 1;
            }
            if (movesCount / radius == 4)
            {
                xDirection = 1;
                yDirection = 1;
                movesCount = 0;
            }

            return new Point(currentPos.X + xDirection, currentPos.Y + yDirection);
        }
    }
}
