using System;
using System.Collections.Generic;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Threading.Tasks;
using System.Timers;
using _7kojos.Hubs;
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
            Obstacle cube = ObstacleFactory.getPrototype("Cube").Clone();
            cube.SetPosition(new Point(Random.Next(0, 600), Random.Next(200, 300)));
            Obstacle circle = ObstacleFactory.getPrototype("Circle").Clone();
            circle.SetPosition(new Point(Random.Next(0, 600), Random.Next(200, 300)));


            Obstacle rectangle = ObstacleFactory.getPrototype("Rectangle").Clone();
            rectangle.SetMovement(new DiagonalMovement(50, 500));
            rectangle.SetPosition(new Point(Random.Next(99, 100), Random.Next(10, 30)));

            createdGame.Obstacles.Add(cube);
            cube.SetId(createdGame.Obstacles.Count);
            createdGame.Obstacles.Add(rectangle);
            rectangle.SetId(createdGame.Obstacles.Count);
            
            createdGame.Obstacles.Add(circle);
            circle.SetId(createdGame.Obstacles.Count);
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
            for(int i = 0; i < Games.Count(); i++)
            {
                int index = Games[i].Players.FindIndex(p => p.id == player.id);
                if (index >= 0)
                    Games[i].Players.RemoveAt(index);
                if (Games[i].Players.Count() == 0)
                {
                    Games.Remove(Games[i]);
                }
            }
        }

        public static Player FindOpponent(Player player)
        {
            Game game = FindGame(player);
            if (game != null && game.Players.Count == 2)
            {
                return game.Players.FirstOrDefault(p => p.id != player.id);
            }
            return null;
        }
    }
}
