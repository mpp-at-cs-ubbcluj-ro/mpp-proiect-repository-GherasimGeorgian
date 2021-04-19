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

     
        private MainWindowForm mainWindowForm;
        private TriatlonClientCtrl ctrl;
        public LoginForm(TriatlonClientCtrl ctrl)
        {
            InitializeComponent();
            this.ctrl = ctrl;
        }

        internal void Set(MainWindowForm mainWindowForm)
        {
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
            string user = textBoxUsername.Text;
            string pass = textBoxPassword.Text;
            try
            {
                
               
                ctrl.login(user, pass);
               
                ctrl.username = user;
                ctrl.arbitruLogat();
                mainWindowForm.Show();
                this.Hide();
            }
            catch (Exception ex)
            {
                MessageBox.Show(this, "Login Error " + ex.Message/*+ex.StackTrace*/, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }
        }

        private void LoginForm_Paint(object sender, PaintEventArgs e)
        {
            this.Paint += new PaintEventHandler(set_background);
        }
    }
}
