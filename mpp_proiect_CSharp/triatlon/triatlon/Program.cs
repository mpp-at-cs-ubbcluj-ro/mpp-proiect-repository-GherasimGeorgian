using log4net.Config;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;
using triatlon.domain;
using triatlon.repository;
using triatlon.repository.database;
using triatlon.service;

[assembly: log4net.Config.XmlConfigurator(Watch =true)]
namespace triatlon
{
    static class Program
    {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]


        static string GetConnectionStringByName(string name)
        {
            // Assume failure.
            string returnValue = null;

            // Look for the name in the connectionStrings section.
            ConnectionStringSettings settings = ConfigurationManager.ConnectionStrings[name];

            // If found, return the connection string.
            if (settings != null)
                returnValue = settings.ConnectionString;

            return returnValue;
        }


        static void Main(string[] args)
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);

            //configurare jurnalizare folosind log4net
            XmlConfigurator.Configure(new System.IO.FileInfo(args[0]));
            //conectionStringul pentru baza de date
            DataBase dataBaseObject = new DataBase();
            
         

            Console.WriteLine("Configuration Settings for  {0}", GetConnectionStringByName("triatlonDB"));
            IDictionary<String, string> properties = new SortedList<String, String>();
            properties.Add("ConnectionString", GetConnectionStringByName("triatlonDB"));


            IArbitruRepository arbitruRepository = new ArbitruRepository(properties);
            IProbaRepository probaRepository = new ProbaRepository(properties);
            IParticipantRepository participantRepository = new ParticipantRepository(properties);
            IRezultatRepository rezultatRepository = new RezultatRepository(properties, probaRepository, participantRepository);
            

            arbitruRepository.findAll().ToList().ForEach(Console.WriteLine);
            probaRepository.findAll().ToList().ForEach(Console.WriteLine);
            participantRepository.findAll().ToList().ForEach(Console.WriteLine);
            rezultatRepository.findAll().ToList().ForEach(Console.WriteLine);

            Service service = new Service(arbitruRepository, probaRepository, participantRepository,rezultatRepository);
            service.GetParticipantDTOs();
            LoginForm loginForm = new LoginForm();
            MainWindowForm mainWindowForm = new MainWindowForm();

            loginForm.Set(service, mainWindowForm);
            mainWindowForm.Set(service, loginForm);

            Application.Run(loginForm);
        }
    }
}
