using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Data;
using _7kojos.Models;
using Microsoft.EntityFrameworkCore;

namespace _7kojos.Context
{
    public class DatabaseContext : DbContext
    {

        public DatabaseContext(DbContextOptions options) : base(options)
        {

        }
        public DbSet<Player> Players { get; set; }

    }
}
