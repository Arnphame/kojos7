using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Threading.Tasks;

namespace _7kojos.Models
{
    public class VerticalMovement : Movement
    {
        private int minBound;
        private int maxBound;
        private int dir = 1;
        public VerticalMovement(int minBound, int maxBound)
        {
            this.minBound = minBound;
            this.maxBound = maxBound;
        }
        public Point Move(Point currentPos)
        {
            if (currentPos.Y == this.minBound || currentPos.Y == this.maxBound)
                dir *= -1;
            return new Point(currentPos.X, currentPos.Y + dir);
        }
    }
}
