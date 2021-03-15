using log4net;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using triatlon.domain;

namespace triatlon.repository
{
    class RezultatRepository : IRezultatRepository
    {
        private static readonly ILog log = LogManager.GetLogger("RezultatRepository");
        private IProbaRepository probaRepository;
        private IParticipantRepository participantRepository;
       
        IDictionary<String, string> props;
        public RezultatRepository(IDictionary<String, string> props, IProbaRepository probaRepository, IParticipantRepository participantRepository)
        {
            log.Info("Creating RezultatRepository ");
            this.props = props;
            this.probaRepository = probaRepository;
            this.participantRepository = participantRepository;
        }

        public List<Rezultat> filterByProba(Proba proba)
        {
            return null;
        }
        public Rezultat findOne(long id)
        {
            log.InfoFormat("Entering findOne with value {0}", id);
            IDbConnection con = DBUtils.getConnection(props);

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from rezultat where idrezultat=@idrezultat";
                IDbDataParameter paramParticipantID = comm.CreateParameter();
                paramParticipantID.ParameterName = "@idrezultat";
                paramParticipantID.Value = id;
                comm.Parameters.Add(paramParticipantID);
                
                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        long idRezultat = dataR.GetInt64(0);
                        long idProba = dataR.GetInt64(1);
                        long idParticipant = dataR.GetInt64(2);
                        int numarpuncte = dataR.GetInt32(3);
                       

                        Rezultat rezultat = new Rezultat(idRezultat, probaRepository.findOne(idProba),participantRepository.findOne(idParticipant),numarpuncte);

                        log.InfoFormat("Exiting findOne with value {0}", rezultat);
                        return rezultat;
                    }
                }
            }
            log.InfoFormat("Exiting findOne with value {0}", null);
            return null;
        }
        public IEnumerable<Rezultat> findAll()
        {
            IDbConnection con = DBUtils.getConnection(props);
            IList<Rezultat> rezultate = new List<Rezultat>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from rezultat";

                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        long idRezultat = dataR.GetInt64(0);
                        long idProba = dataR.GetInt64(1);
                        long idParticipant = dataR.GetInt64(2);
                        int numarpuncte = dataR.GetInt32(3);


                        Rezultat rezultat = new Rezultat(idRezultat, probaRepository.findOne(idProba), participantRepository.findOne(idParticipant), numarpuncte);

                        rezultate.Add(rezultat);
                    }
                }
            }
            return rezultate;
        }

        public void save(Rezultat entity)
        {
            
        }
        public void delete(long id)
        {

        }


        public void update(Rezultat old, Rezultat entity)
        {

        }
        
    }
}
