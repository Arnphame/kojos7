using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using _7kojos.Context;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace _7kojos.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class GamesController : ControllerBase
    {
        DatabaseContext databaseContext;

        public GamesController(DatabaseContext dbContext)
        {
            databaseContext = dbContext;
        }

        [HttpGet]
        public ActionResult GetGames()
        {
            return Ok(Program.Games);
        }
    }
}