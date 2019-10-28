using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Threading.Tasks;
using _7kojos.Models;
using _7kojos.Services;
using Microsoft.AspNetCore;
using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.Logging;

namespace _7kojos
{
    public class Program
    {
        public static List<Game> Games;
        static Random Random;

        public static void Main(string[] args)
        {
            Games = new List<Game>();
            Random = new Random();

            CreateWebHostBuilder(args).Build().Run();
        }

        public static IWebHostBuilder CreateWebHostBuilder(string[] args) =>
            WebHost.CreateDefaultBuilder(args)
                .UseStartup<Startup>();

        public static Game CreateGame()
        {
            int id = 0;
            while (Games.Exists(game => game.GameId == id))
            {
                id = Random.Next(0,10);
            }

            Game createdGame = new Game(id);
            Games.Add(createdGame);

            return createdGame;
        }

        public static bool JoinGame(int id, Player player)
        {
            Game found = Games.Find(game => game.GameId == id);

            if (found != null && found.Players.Count < 2)
            {
                found.Players.Add(player);
                return true;
            }

            return false;
        }

        public static Game FindGame(Player player)
        {
            foreach (Game game in Games)
            {
                int index = game.Players.FindIndex(p => p.id == player.id);
                if (index >= 0)
                    return game;
            }
            return null;
        }

        public static void LeaveGame(Player player)
        {
            foreach (Game game in Games)
            {
                int index = game.Players.FindIndex(p => p.id == player.id);
                if (index >= 0)
                    game.Players.RemoveAt(index);
            }
        }
    }
}
