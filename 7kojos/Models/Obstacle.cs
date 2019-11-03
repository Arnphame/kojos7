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
        ColorInterface GetColor();
        int GetX();
        int GetY();
        int GetId();
        void GetSize(out int width, out int height);
        void SetId(int id);
        void Move();
        void SetMovement(Movement movement);
    }
}
