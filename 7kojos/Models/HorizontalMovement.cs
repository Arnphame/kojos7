using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Threading.Tasks;

namespace _7kojos.Models
{
    public class HorizontalMovement : Movement
    {
        private int minBound;
        private int maxBound;
        private int dir = 1;
        public HorizontalMovement(int minBound, int maxBound)
        {
            this.minBound = minBound;
            this.maxBound = maxBound;
        }

        public Movement Clone()
        {
            return new HorizontalMovement(minBound, maxBound);
        }

        public Point Move(Point currentPos)
        {
            if (currentPos.X == this.minBound || currentPos.X == this.maxBound)
                dir *= -1;
            return new Point(currentPos.X + dir, currentPos.Y);
        }
    }
}
