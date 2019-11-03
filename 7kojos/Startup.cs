using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Threading.Tasks;
using _7kojos.Context;
using _7kojos.Hubs;
using _7kojos.ServiceInterfaces;
using _7kojos.Services;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;
using Microsoft.Extensions.Options;
using _7Kojos.Services;

namespace _7kojos
{
    public class Startup
    {
        //public static IConnectionManager ConnectionManager;

        public Startup(IConfiguration configuration)
        {
            Configuration = configuration;
        }

        public IConfiguration Configuration { get; }

        // This method gets called by the runtime. Use this method to add services to the container.
        public void ConfigureServices(IServiceCollection services)
        {
            services.AddMvc().SetCompatibilityVersion(CompatibilityVersion.Version_2_1);
            services.AddDbContext<DatabaseContext>(opts => opts.UseSqlServer(Configuration["ConnectionString:database"]));

            services.AddScoped<IGamesService, GamesService>();

            services.AddHostedService<GamesUpdatingService>();

            services.AddCors(options =>
            {
                options.AddPolicy("AllowOrigins",
                    builder =>
                    {
                        builder.WithOrigins("http://localhost:52179/")
                        .AllowCredentials()
                        .AllowAnyMethod()
                        .AllowAnyHeader();
                    });
            });
            services.AddSignalR();
        }
        
        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IHostingEnvironment env)
        {
            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
            }

            app.UseCors("AllowOrigins");

            app.UseMvc();

            app.UseSignalR(routes =>
            {
                routes.MapHub<GameHub>("/api/signalr");
            });

            /*app.UseAzureSignalR(routes =>
            {
                routes.MapHub<GameHub>("/api/signalr");
                
            });*/
        }
    }
}
