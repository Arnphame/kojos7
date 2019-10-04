namespace FRONT_END
{
    partial class Form1
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.nameLabel = new System.Windows.Forms.Label();
            this.nameTextBox = new System.Windows.Forms.TextBox();
            this.newGameButton = new System.Windows.Forms.Button();
            this.joinGameButton = new System.Windows.Forms.Button();
            this.gameIdLabel = new System.Windows.Forms.Label();
            this.gameIdTextbox = new System.Windows.Forms.TextBox();
            this.resultLabel = new System.Windows.Forms.Label();
            this.loginButton = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // nameLabel
            // 
            this.nameLabel.AutoSize = true;
            this.nameLabel.Location = new System.Drawing.Point(118, 63);
            this.nameLabel.Name = "nameLabel";
            this.nameLabel.Size = new System.Drawing.Size(41, 13);
            this.nameLabel.TabIndex = 0;
            this.nameLabel.Text = "Name :";
            // 
            // nameTextBox
            // 
            this.nameTextBox.Location = new System.Drawing.Point(200, 60);
            this.nameTextBox.Name = "nameTextBox";
            this.nameTextBox.Size = new System.Drawing.Size(91, 20);
            this.nameTextBox.TabIndex = 1;
            // 
            // newGameButton
            // 
            this.newGameButton.Location = new System.Drawing.Point(191, 268);
            this.newGameButton.Name = "newGameButton";
            this.newGameButton.Size = new System.Drawing.Size(124, 31);
            this.newGameButton.TabIndex = 2;
            this.newGameButton.Text = "Create new game";
            this.newGameButton.UseVisualStyleBackColor = true;
            this.newGameButton.Click += new System.EventHandler(this.newGameButton_Click);
            // 
            // joinGameButton
            // 
            this.joinGameButton.Location = new System.Drawing.Point(551, 266);
            this.joinGameButton.Name = "joinGameButton";
            this.joinGameButton.Size = new System.Drawing.Size(132, 33);
            this.joinGameButton.TabIndex = 3;
            this.joinGameButton.Text = "Join existing game";
            this.joinGameButton.UseVisualStyleBackColor = true;
            this.joinGameButton.Click += new System.EventHandler(this.joinGameButton_Click);
            // 
            // gameIdLabel
            // 
            this.gameIdLabel.AutoSize = true;
            this.gameIdLabel.Location = new System.Drawing.Point(548, 211);
            this.gameIdLabel.Name = "gameIdLabel";
            this.gameIdLabel.Size = new System.Drawing.Size(55, 13);
            this.gameIdLabel.TabIndex = 4;
            this.gameIdLabel.Text = "Game ID :";
            // 
            // gameIdTextbox
            // 
            this.gameIdTextbox.Location = new System.Drawing.Point(621, 208);
            this.gameIdTextbox.Name = "gameIdTextbox";
            this.gameIdTextbox.Size = new System.Drawing.Size(100, 20);
            this.gameIdTextbox.TabIndex = 5;
            // 
            // resultLabel
            // 
            this.resultLabel.AutoSize = true;
            this.resultLabel.Location = new System.Drawing.Point(381, 367);
            this.resultLabel.Name = "resultLabel";
            this.resultLabel.Size = new System.Drawing.Size(0, 13);
            this.resultLabel.TabIndex = 6;
            // 
            // loginButton
            // 
            this.loginButton.Location = new System.Drawing.Point(396, 60);
            this.loginButton.Name = "loginButton";
            this.loginButton.Size = new System.Drawing.Size(75, 23);
            this.loginButton.TabIndex = 7;
            this.loginButton.Text = "Login";
            this.loginButton.UseVisualStyleBackColor = true;
            this.loginButton.Click += new System.EventHandler(this.loginButton_Click);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.SystemColors.Highlight;
            this.ClientSize = new System.Drawing.Size(800, 450);
            this.Controls.Add(this.loginButton);
            this.Controls.Add(this.resultLabel);
            this.Controls.Add(this.gameIdTextbox);
            this.Controls.Add(this.gameIdLabel);
            this.Controls.Add(this.joinGameButton);
            this.Controls.Add(this.newGameButton);
            this.Controls.Add(this.nameTextBox);
            this.Controls.Add(this.nameLabel);
            this.Name = "Form1";
            this.Text = "Form1";
            this.Load += new System.EventHandler(this.Form1_Load);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label nameLabel;
        private System.Windows.Forms.TextBox nameTextBox;
        private System.Windows.Forms.Button newGameButton;
        private System.Windows.Forms.Button joinGameButton;
        private System.Windows.Forms.Label gameIdLabel;
        private System.Windows.Forms.TextBox gameIdTextbox;
        private System.Windows.Forms.Label resultLabel;
        private System.Windows.Forms.Button loginButton;
    }
}

