﻿using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using _7kojos;
using _7kojos.Context;
using _7kojos.Hubs;
using _7kojos.Models;
using _7kojos.ServiceInterfaces;
using Microsoft.AspNetCore.SignalR;

namespace sfd_ant_ratu_api.Services
{
    public class GamesService : IGamesService
    {
        private DatabaseContext context;
        private readonly IHubContext<GameHub> hubContext;

        public GamesService(DatabaseContext context, IHubContext<GameHub> hubContext)
        {
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
                    foreach (Player player in game.Players)
                    {
                        hubContext.Clients.Clients(GameHub.GetConnectionId(player.id.ToString())).SendAsync("Obstacle",
                            obstacle.GetType().Name.ToString(),
                            obstacle.GetId(),
                            obstacle.GetX(),
                            obstacle.GetY(),
                            obstacle.GetWidth(),
                            obstacle.GetHeight(),
                            obstacle.GetColor());
                    }
                }
            }
            
        }
    }
}