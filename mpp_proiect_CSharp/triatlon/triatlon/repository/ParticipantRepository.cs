//using log4net;
//using System;
//using System.Collections.Generic;
//using System.Data;
//using System.Data.SqlClient;
//using System.Linq;
//using System.Text;
//using System.Threading.Tasks;
//using triatlon.domain;

//namespace triatlon.repository
//{
//    class ParticipantRepository : IRepository<long, Participant>
//    {
//        private static readonly ILog log = LogManager.GetLogger("ParticipantRepository");


//        IDictionary<String, string> props;
//        public ParticipantRepository(IDictionary<String, string> props)
//        {
//            log.Info("Creating ParticipantRepository ");
//            this.props = props;
//        }

//        public Participant findOne(long id)
//        {
//            log.InfoFormat("Entering findOne with value {0}", id);
//            IDbConnection con = DBUtils.getConnection(props);

//            using (var comm = con.CreateCommand())
//            {
//                comm.CommandText = "select * from participant where Id=@id";
//                IDbDataParameter paramId = comm.CreateParameter();
//                paramId.ParameterName = "@id";
//                paramId.Value = id;
//                comm.Parameters.Add(paramId);

//                using (var dataR = comm.ExecuteReader())
//                {
//                    if (dataR.Read())
//                    {
//                        long idArbitru = dataR.GetInt64(0);
//                        String firstName = dataR.GetString(1);
//                        String lastName = dataR.GetString(2);
//                        String email = dataR.GetString(3);
//                        String username = dataR.GetString(4);
//                        String parola = dataR.GetString(5);

//                        Arbitru arbitru = new Arbitru(idArbitru, firstName, lastName, email, username, parola);


//                        log.InfoFormat("Exiting findOne with value {0}", arbitru);
//                        return arbitru;
//                    }
//                }
//            }
           
//            log.InfoFormat("Exiting findOne with value {0}", null);
//            return null;

//        }

//    }
//}
