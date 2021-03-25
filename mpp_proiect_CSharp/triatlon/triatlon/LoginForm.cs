using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using triatlon.domain;
using triatlon.service;

namespace triatlon
{
    public partial class LoginForm : Form
    {

        private Service service;
        private MainWindowForm mainWindowForm;
        private Arbitru arbitru = null;
        public LoginForm()
        {
            InitializeComponent();
        }

        internal void Set(Service service, MainWindowForm mainWindowForm)
        {
            this.service = service;
            this.mainWindowForm = mainWindowForm;
        }
        private void LoginForm_Load(object sender, EventArgs e)
        {

        }
        private void set_background(Object sender, PaintEventArgs e)
        {
            Graphics graphics = e.Graphics;

            //the rectangle, the same size as our Form
            Rectangle gradient_rectangle = new Rectangle(0, 0, Width, Height);

            //define gradient's properties
            Brush b = new LinearGradientBrush(gradient_rectangle, Color.FromArgb(0, 0, 0), Color.FromArgb(57, 128, 227), 65f);

            //apply gradient         
            graphics.FillRectangle(b, gradient_rectangle);
        }
        
        private void button1_Click(object sender, EventArgs e)
        {
            String username = textBoxUsername.Text;
            String password = textBoxPassword.Text;
            textBoxPassword.Text = "";
            if (service.Login(username, password) == true)
            {
                arbitru = service.getArbitrubyUsername(username);
                mainWindowForm.SetArbitru(arbitru);
                this.Hide();
                mainWindowForm.Show();
                textBoxPassword.Clear();
                textBoxUsername.Clear();
            }
            else
            {
                textBoxPassword.Clear();
                textBoxUsername.Clear();
                MessageBox.Show("Invalid username or password");
                
            }
        }

        private void LoginForm_Paint(object sender, PaintEventArgs e)
        {
            this.Paint += new PaintEventHandler(set_background);
        }
    }
}
