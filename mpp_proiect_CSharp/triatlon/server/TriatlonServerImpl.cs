using services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using triatlon.domain;
using triatlon.service;

namespace server
{
    class TriatlonServerImpl : ITriatlonServices
    {
        
        private readonly IDictionary<long, ITriatlonObserver> loggedClients;
        private Service service;


        public TriatlonServerImpl(Service service)
        {
            this.service = service;
            loggedClients = new Dictionary<long, ITriatlonObserver>();
        }

        public void login(Arbitru user, ITriatlonObserver client)
        {


            Arbitru userOk = service.getArbitrubyUsername(user.username);
            Console.WriteLine("ArbitruLogat:!!!!!:" + userOk.ToString());
            if (userOk != null)
            {
                if (loggedClients.ContainsKey(userOk.Id))
                    throw new TriatlonException("User already logged in.");
                loggedClients[userOk.Id] = client;
            }
            else
                throw new TriatlonException("Authentication failed.");
            Console.WriteLine("Useri conectati:" + loggedClients.Count);
        }

        public  Arbitru getArbitruLogat(string username)
        {
            Console.WriteLine("ServerImp");
            
            return service.getArbitrubyUsername(username);
        }

        public Proba getProbaByArbitru(long id_proba)
        {
            return service.getProbaByID(id_proba);
        }

        public Participant findParticipantbyfirstlastName(ParticipantDTO part)
        {
            return service.findParticipantbyfirstlastName(part.firstName,part.lastName);
        }
        public void sendRezult(Rezultat rezultat)
        {
            service.adaugaRezultat(rezultat.proba, rezultat.participant, rezultat.numarPuncte);
            
            foreach (KeyValuePair<long, ITriatlonObserver> entry in loggedClients)
            {
                // do something with entry.Value or entry.Key
                Task.Run(() => entry.Value.rezultReceived(rezultat));
            }
            
        }

        public void logout(Arbitru user, ITriatlonObserver client)
        {
            ITriatlonObserver localClient = loggedClients[user.Id];
            if (localClient == null)
                throw new TriatlonException("User " + user.Id + " is not logged in.");
            loggedClients.Remove(user.Id);
        }

        public ParticipantDTO[] getParticipanti(Arbitru arbitru)
        {
            return service.GetParticipantDTOs().ToArray();
        }
        public Rezultat[] filterbyProba(Proba proba)
        {
            return service.filterRezultatebyProba(proba).ToArray();
        }



    }
}
