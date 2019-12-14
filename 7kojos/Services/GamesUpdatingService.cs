using _7kojos.ServiceInterfaces;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using _7Kojos.Services;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;

namespace _7kojos.Services
{
    public class GamesUpdatingService : BackgroundService
    {
        private Random rnd;
        private Timer _timer;
        private Timer boostsTimer;
        private IServiceProvider Services;

        public GamesUpdatingService(IServiceProvider Services)
        {
            this.rnd = new Random();
            this.Services = Services;
        }

        protected override async Task ExecuteAsync(CancellationToken stoppingToken)
        {
            boostsTimer = new Timer(SpawnBoost, null, 2000, 5000);
            _timer = new Timer(UpdateGames, null, 0, 15);
            await Task.CompletedTask;
        }

        private void UpdateGames(object state)
        {
            using (var scope = Services.CreateScope())
            {
                var gamesService =
                    scope.ServiceProvider
                        .GetRequiredService<IGamesService>();

                gamesService.UpdateGames();
            }
        }
        private void SpawnBoost(object state)
        {
            using (var scope = Services.CreateScope())
            {
                var gamesService =
                    scope.ServiceProvider
                        .GetRequiredService<IGamesService>();

                gamesService.SpawnBoosts();
            }
        }

        public override async Task StopAsync(CancellationToken stoppingToken)
        {
            await Task.CompletedTask;
        }
    }
}
