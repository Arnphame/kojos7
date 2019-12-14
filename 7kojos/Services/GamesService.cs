using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using _7kojos;
using _7kojos.Context;
using _7kojos.Hubs;
using _7kojos.Models;
using _7kojos.ServiceInterfaces;
using Microsoft.AspNetCore.SignalR;

namespace _7Kojos.Services
{

    public class GamesService : IGamesService
    {
        Random rnd;

        private DatabaseContext context;
        private readonly IHubContext<GameHub> hubContext;

        public GamesService(DatabaseContext context, IHubContext<GameHub> hubContext)
        {
            this.rnd = new Random();
            this.context = context;
            this.hubContext = hubContext;
        }

        public void UpdateGames()
        {
            foreach (Game game in Program.Games)
            {
                foreach (Obstacle obstacle in game.Obstacles)
                {
                    obstacle.Move();
                    int width, height;
                    obstacle.GetSize(out width, out height);
                    foreach (Player player in game.Players)
                    {
                        hubContext.Clients.Clients(GameHub.GetConnectionId(player.id.ToString())).SendAsync("Obstacle",
                            obstacle.GetType().Name.ToString(),
                            obstacle.GetId(),
                            obstacle.GetX(),
                            obstacle.GetY(),
                            width,
                            height,
                            obstacle.GetColor().applyColor());
                    }
                }
            }

        }
        public void SpawnBoosts()
        {
            Obstacle obs = new Powerup(0, 0, 0,0,0);
            if(rnd.Next(2) == 0) { return; }

            int powerUpType = rnd.Next(3);
            ObstacleFactory.boostPrototypes.TryGetValue((Powerup.PowerUpType)powerUpType, out obs);
            Obstacle pwrUp = obs.Clone();
            pwrUp.SetPosition(new System.Drawing.Point(rnd.Next(720), rnd.Next(280)));
            ((Powerup)pwrUp).SetTime(rnd.Next(3, 8));
            foreach (Game game in Program.Games) {
                foreach (Player player in game.Players) {
                    hubContext.Clients.Clients(GameHub.GetConnectionId(player.id.ToString())).SendAsync("Boost",
                          ((Powerup)pwrUp).id,
                          ((Powerup)pwrUp).type,
                          ((Powerup)pwrUp).value,
                          ((Powerup)pwrUp).x,
                          ((Powerup)pwrUp).y,
                          ((Powerup)pwrUp).time 
                    );
                }
            }
        }
    }
}
