using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Threading.Tasks;

namespace _7kojos.Models
{
    public class DiagonalMovement : Movement
    {
        private int minBound;
        private int maxBound;
        private int dir = 1;
        public DiagonalMovement(int minBound, int maxBound)
        {
            this.minBound = minBound;
            this.maxBound = maxBound;
        }

        public Movement Clone()
        {
            return new DiagonalMovement(minBound, maxBound);
        }

        public Point Move(Point currentPos)
        {
            if (currentPos.X == this.minBound || currentPos.X == this.maxBound)
                dir *= -1;
            return new Point(currentPos.X + dir, currentPos.Y + dir);
        }
    }
}
