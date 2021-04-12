using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using triatlon.repository;
using System.Security.Cryptography;
using triatlon.repository;
using triatlon.domain;
namespace triatlon.service
{
    public class Service
    {
        private IArbitruRepository arbitruRepository;
        private IProbaRepository probaRepository;
        private IParticipantRepository participantRepository;
        private IRezultatRepository rezultatRepository;

        public Service(IArbitruRepository arbitruRepository, IProbaRepository probaRepository, IParticipantRepository participantRepository, IRezultatRepository rezultatRepository)
        {
            this.arbitruRepository = arbitruRepository;
            this.probaRepository = probaRepository;
            this.participantRepository = participantRepository;
            this.rezultatRepository = rezultatRepository;
        }

        public bool Login(String username, String password)
        {
            bool rez = arbitruRepository.loginArbitru(username, password);
            return rez;
        }
        public Arbitru getArbitrubyUsername(String username)
        {
            foreach(Arbitru arbitru in arbitruRepository.findAll())
            {
                if(arbitru.username == username)
                {
                    return arbitru;
                }
            }
            return null;
        }
        public IEnumerable<ParticipantDTO> GetParticipantDTOs()
        {
            IEnumerable<ParticipantDTO> list = from a in participantRepository.findAll()
                       join b in rezultatRepository.findAll() on a.Id equals b.participant.Id
                       group b by new {b.participant.firstName,b.participant.lastName} into y
                       orderby y.Key.lastName
                       select new ParticipantDTO
                       {
                           firstName = y.Key.firstName,
                           lastName = y.Key.lastName,
                           punctaj = y.Sum(g => g.numarPuncte)
                       };
            
            return list;
        }
        public Participant findParticipantbyfirstlastName(string firstName,string lastName)
        {
            Participant participant = participantRepository.findAll().Where(a => a.firstName == firstName && a.lastName == lastName).FirstOrDefault();
                             
            return participant;
                              
        }

        public static Int64 NextInt64()
        {
            var buffer = new byte[8];
            new RNGCryptoServiceProvider().GetBytes(buffer);
            return BitConverter.ToInt64(buffer, 0) & 0x7FFFFFFFFFFFFFFF;
        }
        public IEnumerable<Proba> getProbe()
        {
            return probaRepository.findAll();
        } 
        public Proba getProbaByID(long idProba)
        {
            return probaRepository.findOne(idProba);
        }
        public IEnumerable<Rezultat> filterRezultatebyProba(Proba proba)
        {
            return rezultatRepository.filterByProba(proba);
        }
        public void adaugaRezultat(Proba proba,Participant participant,int numarPuncte)
        {
            Participant part=  this.findParticipantbyfirstlastName(participant.firstName, participant.lastName);
            Random r = new Random();
            long idRez = NextInt64();
            
            Rezultat rez = new Rezultat(idRez, proba, part, numarPuncte);
            rezultatRepository.save(rez);
        }
       
        

    }
}
