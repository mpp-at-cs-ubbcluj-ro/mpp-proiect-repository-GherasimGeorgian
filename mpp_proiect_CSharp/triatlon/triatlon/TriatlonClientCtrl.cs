using services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using triatlon.domain;

namespace triatlon
{
    public class TriatlonClientCtrl : ITriatlonObserver
    {
        public event EventHandler<TriatlonUserEventArgs> updateEvent; //ctrl calls it when it has received an update
        private readonly ITriatlonServices server;
        private Arbitru currentUser;
        public string username { get; set; }
        public TriatlonClientCtrl(ITriatlonServices server)
        {
            this.server = server;
            
        }

        public Participant findParticipantbyfirstlastName(string firstName, string lastName)
        {
            ParticipantDTO prat = new ParticipantDTO(firstName, lastName);
            return server.findParticipantbyfirstlastName(prat);
        }
        public Arbitru getArbitruCurentLogat()
        {
            return currentUser;
        }
        public void rezultReceived(Rezultat rez)
        {
            MessageBox.Show("Am primit un rezultat!" + rez.ToString());
            
            String rez_str = "[" + rez.Id + "]: " + rez.participant.firstName;
            TriatlonUserEventArgs userArgs = new TriatlonUserEventArgs(TriatlonUserEvent.NewRezult, rez);
            Console.WriteLine("Rezultat received");
            OnUserEvent(userArgs);

        }
        public void adaugaRezultat(Proba proba,ParticipantDTO participant,int punctaj)
        {
            Participant part = new Participant(participant.firstName,participant.lastName);
            server.sendRezult(new Rezultat((long)3453,proba, part, punctaj));
        }
        public Proba getProbabyID(long id)
        {
            return server.getProbaByArbitru(id);
        }
        public List<Rezultat> filterRezultateByProba(Proba proba)
        {
            return server.filterbyProba(proba).ToList();
        }
        public Proba getProbabyArbitru()
        {
            return server.getProbaByArbitru(currentUser.id_proba_responsabil);
        }
        public Arbitru arbitruLogat(string username)
        {
            return server.getArbitruLogat(username);
        }
        public void arbitruLogat()
        {
            currentUser = server.getArbitruLogat(username);
        }
        public string getFirstNameArbitru()
        {
            return currentUser.firstName;
        }
        public string getLastNameArbitru()
        {
            return currentUser.lastName;
        }
        public void login(String username, String pass)
        {
            
            Arbitru arb = this.arbitruLogat(username);
            server.login(arb, this);
            Console.WriteLine("Login succeeded ....");
            currentUser = arb;
            Console.WriteLine("Current user {0}", arb.firstName);
        }
     
      

      
        public void logout()
        {
            Console.WriteLine("Ctrl logout");
            server.logout(currentUser, this);
            currentUser = null;
        }

        protected virtual void OnUserEvent(TriatlonUserEventArgs e)
        {
            if (updateEvent == null) return;
            updateEvent(this, e);
            Console.WriteLine("Update Event called");
        }
        public List<ParticipantDTO> getParticipanti()
        {
           
            ParticipantDTO[] participants = server.getParticipanti(currentUser);
            
            return participants.ToList();
           
        }

  
       

    }
}
