using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace _7kojos.Models
{
    public abstract class Shape
    {
        public ColorInterface Color;
        abstract public void applyColor();

        public Shape (ColorInterface c)
        {
            this.Color = c;
        }
    }
}
