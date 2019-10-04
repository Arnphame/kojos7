using _7kojos.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace _7kojos.Services
{
    public class Game
    {
        public int GameId;
        public List<Player> Players;

        public Game(int id)
        {
            GameId = id;
            Players = new List<Player>();
        }
    }
}
