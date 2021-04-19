using System;
using System.Net.Sockets;
using System.Threading;
using System.Collections.Generic;
using System.Configuration;
using server;
using triatlon.domain;
using triatlon.repository;
using triatlon.repository.database;
using networking;
using triatlon.service;
using services;

namespace chat
{
   
    class StartServer
    {
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

            IDictionary<String, string> properties = new SortedList<String, String>();
            properties.Add("ConnectionString", GetConnectionStringByName("triatlonDB"));

            IArbitruRepository arbitruRepository = new ArbitruRepository(properties);
            IProbaRepository probaRepository = new ProbaRepository(properties);
            IParticipantRepository participantRepository = new ParticipantRepository(properties);
            IRezultatRepository rezultatRepository = new RezultatRepository(properties, probaRepository, participantRepository);


            

            Service service = new Service(arbitruRepository, probaRepository, participantRepository, rezultatRepository);

            ITriatlonServices serviceImpl = new TriatlonServerImpl(service);

            // IChatServer serviceImpl = new ChatServerImpl();
            SerialTriatlonServer server = new SerialTriatlonServer("127.0.0.1", 55555, serviceImpl);
            server.Start();
            Console.WriteLine("Server started ...");
            //Console.WriteLine("Press <enter> to exit...");
            Console.ReadLine();

        }
    }

    public class SerialTriatlonServer : ConcurrentServer
    {
        private ITriatlonServices server;
        private TriatlonClientWorker worker;
        public SerialTriatlonServer(string host, int port, ITriatlonServices server) : base(host, port)
        {
            this.server = server;
            Console.WriteLine("SerialChatServer...");
        }
        protected override Thread createWorker(TcpClient client)
        {
            worker = new TriatlonClientWorker(server, client);
            return new Thread(new ThreadStart(worker.run));
        }
    }

}
