using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using _7kojos.Context;
using _7kojos.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace _7kojos.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class GameController : ControllerBase
    {
        public DatabaseContext dbContext;
        public int playersJoined = 0;
        public int mapId;

        public GameController(DatabaseContext context)
        {
            dbContext = context;
        }

        // GET: api/Game/5
        [HttpGet("{id}", Name = "Get")]
        public string Get(int id)
        {
            return "value";
        }

        // POST: api/Game/joinGame
        [HttpPost("joinGame")]
        public ActionResult<Player> AddPlayer([FromBody] Player p1)
        {
            Player player = new Player();
            player.name = p1.name;
            player.x = 10;
            player.y = 0;
            player.hp = 100;
            player.shotsCounter = 0;
            dbContext.Players.Add(player);
            dbContext.SaveChanges();
            return CreatedAtAction("addPlayer", player);
        }
        [HttpGet("players")]
        public List<Player> GetPlayers()
        {
            return dbContext.Players.ToList();
        }

        // PUT: api/Game/5
        [HttpPut("{id}")]
        public void Put(int id, [FromBody] string value)
        {
        }

        // DELETE: api/ApiWithActions/5
        [HttpDelete("{id}")]
        public void Delete(int id)
        {
        }
    }
}
