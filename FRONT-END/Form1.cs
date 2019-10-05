using Microsoft.AspNetCore.SignalR.Client;
using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using static System.Net.WebRequestMethods;

namespace FRONT_END
{
    public partial class Form1 : Form
    {
        private static readonly HttpClient client = new HttpClient();
        HubConnection connection;

        delegate void SetTextCallback(Label label, string text);

        private void SetText(Label label, string text)
        {
            // InvokeRequired required compares the thread ID of the
            // calling thread to the thread ID of the creating thread.
            // If these threads are different, it returns true.
            if (label.InvokeRequired)
            {
                SetTextCallback d = new SetTextCallback(SetText);
                this.Invoke(d, new object[] { label, text });
            }
            else
            {
                label.Text = text;
            }
        }

        public Form1()
        {
            InitializeComponent();
        }

        async void ConnectToSignalR(string name)
        {
            connection = new HubConnectionBuilder()
                .WithUrl("http://localhost:52179/api/signalr")
                .Build();

            connection.On<string>("ReceiveGameId", (gameId) =>
            {
                SetText(resultLabel, gameId);
            });

            connection.On<bool>("ReceiveJoinSuccess", (success) =>
            {
                SetText(resultLabel, success.ToString());
            });

            await connection.StartAsync();

            await connection.InvokeAsync("RegisterClient", name);
        }

        private void Form1_Load(object sender, System.EventArgs e)
        {
            newGameButton.Enabled = false;
            joinGameButton.Enabled = false;
            loginButton.Enabled = true;
        }

        /*System.Drawing.SolidBrush myBrush = new System.Drawing.SolidBrush(System.Drawing.Color.Red);
        System.Drawing.Graphics x;
        x = this.CreateGraphics();
        x.FillRectangle(myBrush, new Rectangle(0, 0, 150, 200));
        x.Dispose();
        myBrush.Dispose();*/

        private async void newGameButton_Click(object sender, EventArgs e)
        {
            newGameButton.Enabled = false;
            joinGameButton.Enabled = false;
            loginButton.Enabled = true;

            await connection.InvokeAsync("CreateGame");

            
        }

        private async void joinGameButton_Click(object sender, EventArgs e)
        {
            newGameButton.Enabled = false;
            joinGameButton.Enabled = false;
            loginButton.Enabled = true;

            await connection.InvokeAsync("JoinGame", gameIdTextbox.Text);

            
        }

        private void loginButton_Click(object sender, EventArgs e)
        {
            ConnectToSignalR(nameTextBox.Text);

            newGameButton.Enabled = true;
            joinGameButton.Enabled = true;
            loginButton.Enabled = false;
        }
    }
}
