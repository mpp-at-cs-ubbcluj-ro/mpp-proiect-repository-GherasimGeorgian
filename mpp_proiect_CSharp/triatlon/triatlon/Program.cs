using log4net.Config;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;
using triatlon.domain;
using triatlon.repository;

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


            IRepository<long, Arbitru> arbitruRepository = new ArbitruRepository(properties);

            arbitruRepository.findAll().ToList().ForEach(Console.WriteLine);

            Application.Run(new Form1());
        }
    }
}
