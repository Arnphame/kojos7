using _7kojos.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace _7kojos.ServiceInterfaces
{
    public interface IGamesService
    {
        void UpdateGames();
        void SpawnBoosts();
    }
}
