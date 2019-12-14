using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Threading.Tasks;

namespace _7kojos.Models
{
    public class Powerup : Obstacle
    {
        static int currentId = 0;
        public int id { get; set; }
        public enum PowerUpType { HP, DMG, SPEED }
        public PowerUpType type { get; set; }
        public double value { get; set; }

        public int x { get; set; }
        public int y { get; set; }

        public int time { get; set; }

        public Powerup(PowerUpType type, double value, int x, int y, int time)
        {
            this.id = currentId++;
            this.type = type;
            this.value = value;
            this.x = x;
            this.y = y;
            this.time = time;
        }

        public void SetTime(int seconds)
        {
            this.time = seconds;
        }

        public Obstacle Clone()
        {
            return new Powerup(type, value, x,y,time);
        }

        public ColorInterface GetColor()
        {
            throw new NotImplementedException();
        }

        public int GetId()
        {
            throw new NotImplementedException();
        }

        public void GetSize(out int width, out int height)
        {
            throw new NotImplementedException();
        }

        public int GetX()
        {
            throw new NotImplementedException();
        }

        public int GetY()
        {
            throw new NotImplementedException();
        }

        public void Move()
        {
            throw new NotImplementedException();
        }

        public void SetId(int id)
        {
            throw new NotImplementedException();
        }

        public void SetMovement(Movement movement)
        {
            throw new NotImplementedException();
        }

        public void SetPosition(Point position)
        {
            this.x = position.X;
            this.y = position.Y;
        }
    }
}
