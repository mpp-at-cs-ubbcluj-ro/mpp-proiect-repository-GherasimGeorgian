using log4net.Config;
using networking;
using services;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;


[assembly: log4net.Config.XmlConfigurator(Watch = true)]
namespace triatlon
{
    static class Program
    {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]


       

        static void Main(string[] args)
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);

            //configurare jurnalizare folosind log4net
            XmlConfigurator.Configure(new System.IO.FileInfo(args[0]));
            //conectionStringul pentru baza de date







            ITriatlonServices server = new TriatlonServerProxy("127.0.0.1", 55555);
           
            TriatlonClientCtrl ctrl = new TriatlonClientCtrl(server);
            LoginForm loginForm = new LoginForm(ctrl);
            MainWindowForm mainWindowForm = new MainWindowForm(ctrl);
            loginForm.Set(mainWindowForm);


            //MainWindowForm mainWindowForm = new MainWindowForm();



            //mainWindowForm.Set(service, loginForm);

            Application.Run(loginForm);
        }
    }
}
