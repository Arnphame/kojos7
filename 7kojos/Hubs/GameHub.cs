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
            string userId = _connections.FindKey(Context.ConnectionId);
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

            Game game = Program.CreateGame();

            JoinGame(game.GameId);

            Clients.Client(Context.ConnectionId).SendAsync("ReceiveGameId", game.GameId);

            return game.GameId;
        }

        public bool JoinGame(int gameId)
        {
            Player player = dbContext.Players.FirstOrDefault(p => p.id.ToString() == GetUserId());

            bool success = Program.JoinGame(gameId, player);
            Clients.Client(Context.ConnectionId).SendAsync("ReceiveJoinSuccess", success);

            return success;
        }
    }
}
