using _7kojos.Context;
using _7kojos.Models;
using _7kojos.Services;
using Microsoft.AspNetCore.SignalR;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace _7kojos.Hubs
{
    public class GameHub : Hub
    {
        private readonly static ConnectionMapping<string> _connections = new ConnectionMapping<string>();

        public DatabaseContext dbContext;

        public GameHub(DatabaseContext context)
        {
            dbContext = context;
        }

        public override Task OnConnectedAsync()
        {
            return base.OnConnectedAsync();
        }

        public override Task OnDisconnectedAsync(Exception exception)
        {
            LeaveGame();
            string userId = _connections.GetUserId(Context.ConnectionId);
            _connections.Remove(userId, Context.ConnectionId);
            return base.OnDisconnectedAsync(exception);
        }

        public void RegisterClient(string name)
        {
            Player player = AddPlayer(name);

            _connections.Add(player.id.ToString(), Context.ConnectionId);
        }

        public static List<string> GetConnectionId(string userId)
        {
            List<string> connectionIds = new List<string>();
            connectionIds.AddRange(_connections.GetConnections(userId));

            return connectionIds;
        }

        public string GetUserId()
        {
            return _connections.GetUserId(Context.ConnectionId);
        }

        public Player AddPlayer(string playerName)
        {
            Player player = dbContext.Players.FirstOrDefault(p => p.name.Equals(playerName));

            if (player == null)
            {
                player = new Player();
                dbContext.Players.Add(player);
                player.name = playerName;
            }

            player.x = 10;
            player.y = 0;
            player.hp = 100;
            player.shotsCounter = 0;
            dbContext.SaveChanges();
            return player;
        }

        public int CreateGame()
        {
            Player player = dbContext.Players.FirstOrDefault(p => p.id.ToString() == GetUserId());
            if (player == null) throw new Exception("null player");

            player.x = 100;
            player.y = 350;

            dbContext.SaveChanges();

            Game game = Program.CreateGame();

            Program.JoinGame(game.GameId, player);

            Clients.Client(Context.ConnectionId).SendAsync("ReceiveGameId", game.GameId, player.x, player.y);

            return game.GameId;
        }

        public bool JoinGame(int gameId)
        {
            Player player = dbContext.Players.FirstOrDefault(p => p.id.ToString() == GetUserId());

            player.x = 500;
            player.y = 350;

            dbContext.SaveChanges();

            bool success = Program.JoinGame(gameId, player);

            Game game = new Game(-1);

            if (success)
            {
                game = Program.FindGame(player);

                Player opponent = game.Players.FirstOrDefault(p => p.id != player.id);

                if (opponent != null)
                    Clients.Clients(GetConnectionId(opponent.id.ToString())).SendAsync("PlayerJoined", player.x, player.y);
            }

            Clients.Client(Context.ConnectionId).SendAsync("ReceiveJoinSuccess", success, game.Players[0].x, game.Players[0].y, player.x, player.y);
            
            return success;
        }

        public void LeaveGame()
        {
            Player player = dbContext.Players.FirstOrDefault(p => p.id.ToString() == GetUserId());

            Program.LeaveGame(player);
        }

        public void Shoot(float xPos, float yPos, float xVel, float yVel, string type)
        {
            Player player = dbContext.Players.FirstOrDefault(p => p.id.ToString() == GetUserId());

            Game game = Program.FindGame(player);

            Player opponent = game.Players.FirstOrDefault(p => p.id != player.id);

            if(opponent != null)
                Clients.Clients(GetConnectionId(opponent.id.ToString())).SendAsync("Shoot", xPos, yPos, xVel, yVel, type);
        }
    }
}
