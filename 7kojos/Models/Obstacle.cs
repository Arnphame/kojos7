using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace _7kojos.Models
{
    public interface Obstacle
    {
        Obstacle Clone();
        void setPosition(int x, int y);
    }
}
