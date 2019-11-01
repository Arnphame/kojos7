using _7kojos.ServiceInterfaces;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using sfd_ant_ratu_api.Services;
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
        private Timer _timer;
        private IServiceProvider Services;

        public GamesUpdatingService(IServiceProvider Services)
        {
            this.Services = Services;
        }

        protected override async Task ExecuteAsync(CancellationToken stoppingToken)
        {
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

        public override async Task StopAsync(CancellationToken stoppingToken)
        {
            await Task.CompletedTask;
        }
    }
}
