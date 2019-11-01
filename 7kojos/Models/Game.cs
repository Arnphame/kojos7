using _7kojos.Hubs;
using _7kojos.Models;
using Microsoft.AspNetCore.SignalR;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Timers;

namespace _7kojos.Models
{
    public class Game
    {
        public int GameId;
        public List<Player> Players;
        public List<Obstacle> Obstacles;

        public Game(int id)
        {
            GameId = id;
            Players = new List<Player>();
            Obstacles = new List<Obstacle>();
        }
    }
}
