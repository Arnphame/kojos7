using _7kojos.Context;
using _7kojos.Models;
using _7kojos.ServiceInterfaces;
using _7kojos.Services;
using Microsoft.AspNetCore.SignalR;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Threading.Tasks;
using System.Timers;

namespace _7kojos.Hubs
{
    public class GameHub : Hub
    {
        private readonly static ConnectionMapping<string> _connections = new ConnectionMapping<string>();

        public DatabaseContext dbContext;
        public IGamesService gamesService;

        public GameHub(DatabaseContext context, IGamesService gamesService)
        {
            this.dbContext = context;
            this.gamesService = gamesService;
        }

        public override Task OnConnectedAsync()
        {
            Debug.WriteLine("--------------------------------------------------------------------------");
            return base.OnConnectedAsync();
        }

        public override Task OnDisconnectedAsync(Exception exception)
        {
            string userId = _connections.GetUserId(Context.ConnectionId);
            Player player = dbContext.Players.FirstOrDefault(p => p.id.ToString() == userId);

            Game game = new Game(-1);
            game = Program.FindGame(player);

            Player opponent = game.Players.FirstOrDefault(p => p.id != player.id);

            if (opponent != null)
                Clients.Clients(GetConnectionId(opponent.id.ToString())).SendAsync("PlayerLeft", player.id);

            LeaveGame();

            _connections.Remove(userId, Context.ConnectionId);
            return base.OnDisconnectedAsync(exception);
        }

        public void RegisterClient(string name)
        {
            Player player = AddPlayer(name);

            _connections.Add(player.id.ToString(), Context.ConnectionId);
            Clients.Client(Context.ConnectionId).SendAsync("registered");

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

        public void ChangeMovement(string movementType, int currentX, int currentY)
        {
            Player player = dbContext.Players.FirstOrDefault(p => p.id.ToString() == GetUserId());
            Player opponent = Program.FindOpponent(player);

            if (opponent == null)
                return;

            Clients.Clients(GetConnectionId(opponent.id.ToString())).SendAsync("OpponentChangedMovement", movementType, currentX, currentY);
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
            Clients.Client(Context.ConnectionId).SendAsync("gamepls");
            Player player = dbContext.Players.FirstOrDefault(p => p.id.ToString() == GetUserId());
            if (player == null) Clients.Client(Context.ConnectionId).SendAsync("nullPlayer");

            player.x = 100;
            player.y = 350;

            dbContext.SaveChanges();

            Game game = Program.CreateGame();
            if(game == null ) Clients.Client(Context.ConnectionId).SendAsync("nullGame");
            bool isJoined;
            isJoined = Program.JoinGame(game.GameId, player);
            Clients.Client(Context.ConnectionId).SendAsync("joinGame", isJoined);
            Clients.Client(Context.ConnectionId).SendAsync("ReceiveGameId", game.GameId, player.id, player.x, player.y);


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
                    Clients.Clients(GetConnectionId(opponent.id.ToString())).SendAsync("PlayerJoined", player.id, player.x, player.y);
            }

            Clients.Client(Context.ConnectionId).SendAsync("ReceiveJoinSuccess", success, game.Players[0].id, game.Players[0].x, game.Players[0].y, player.id, player.x, player.y);
            
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

        public void updateObstacles()
        {
            Player player = dbContext.Players.FirstOrDefault(p => p.id.ToString() == GetUserId());
            Game game = Program.FindGame(player);

            foreach (Obstacle obstacle in game.Obstacles)
            {
                obstacle.Move();

                foreach (Player p in game.Players)
                {
                    Clients.Clients(GetConnectionId(p.id.ToString())).SendAsync("Obstacle", obstacle.GetType().Name.ToString(), obstacle.GetX(), obstacle.GetY());
                }
            }
        }

        public void CollectBoost(int powerUpId)
        {
            Player player = dbContext.Players.FirstOrDefault(p => p.id.ToString() == GetUserId());

            Game game = Program.FindGame(player);

            Player opponent = game.Players.FirstOrDefault(p => p.id != player.id);

            if (opponent != null)
                Clients.Clients(GetConnectionId(opponent.id.ToString())).SendAsync("CollectedBoost", powerUpId, player.id);
        }
    }
}
