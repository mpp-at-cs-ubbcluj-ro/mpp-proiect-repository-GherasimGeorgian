using log4net;
using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using triatlon.domain;

namespace triatlon.repository
{
    class ParticipantRepository : IParticipantRepository
    {
        private static readonly ILog log = LogManager.GetLogger("ParticipantRepository");


        IDictionary<String, string> props;
        public ParticipantRepository(IDictionary<String, string> props)
        {
            log.Info("Creating ParticipantRepository ");
            this.props = props;
        }

        public Participant findOne(long id)
        {
            log.InfoFormat("Entering findOne with value {0}", id);
            IDbConnection con = DBUtils.getConnection(props);

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from participant where idParticipant=@id";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);

                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        long idParticipant = dataR.GetInt64(0);
                        String firstName = dataR.GetString(1);
                        String lastName = dataR.GetString(2);
                        int varsta = dataR.GetInt32(3);
                        

                        Participant participant = new Participant(idParticipant, firstName, lastName, varsta);


                        log.InfoFormat("Exiting findOne with value {0}", participant);
                        return participant;
                    }
                }
            }

            log.InfoFormat("Exiting findOne with value {0}", null);
            return null;

        }
        public IEnumerable<Participant> findAll()
        {
            IDbConnection con = DBUtils.getConnection(props);
            IList<Participant> participantR = new List<Participant>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from participant";

                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        long idParticipant = dataR.GetInt64(0);
                        String firstName = dataR.GetString(1);
                        String lastName = dataR.GetString(2);
                        int varsta = dataR.GetInt32(3);


                        Participant participant = new Participant(idParticipant, firstName, lastName, varsta);


                        participantR.Add(participant);
                    }
                }
            }
            return participantR;
        }
        public void save(Participant entity)
        {

        }

       
        public void delete(long id)
        {

        }

        
        public void update(Participant old, Participant entity)
        {

        }
    }
}
