using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Threading.Tasks;

namespace _7kojos.Models
{
    public interface Obstacle
    {
        Obstacle Clone();
        void SetPosition(Point position);
        string GetColor();
        int GetX();
        int GetY();
        int GetId();
        int GetWidth();
        int GetHeight();
        void SetId(int id);
        void Move();
    }
}
