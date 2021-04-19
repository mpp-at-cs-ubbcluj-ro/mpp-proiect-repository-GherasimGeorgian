using log4net;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using triatlon.domain;

namespace triatlon.repository.database
{
    public class ProbaRepository : IProbaRepository
    {
        private static readonly ILog log = LogManager.GetLogger("ProbaRepository");
        IDictionary<String, string> props;

        public ProbaRepository(IDictionary<String, string> props)
        {
            log.Info("Creating ProbaRepository ");
            this.props = props;
        }

        public Proba findOne(long id)
        {
            log.InfoFormat("Entering findOne with value {0}", id);
            IDbConnection con = DBUtils.getConnection(props);

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from proba where idproba=@id";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);

                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        long idProba = dataR.GetInt64(0);
                        String tipproba = dataR.GetString(1);
                        int distanta = dataR.GetInt32(2);

                        Proba proba = new Proba(idProba, tipproba, distanta);


                        log.InfoFormat("Exiting findOne with value {0}", proba);
                        return proba;
                    }
                }
            }

            log.InfoFormat("Exiting findOne with value {0}", null);
            return null;

        }

        public IEnumerable<Proba> findAll()
        {
            IDbConnection con = DBUtils.getConnection(props);
            IList<Proba> listaProbe = new List<Proba>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from proba";

                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        long idProba = dataR.GetInt64(0);
                        String tipproba = dataR.GetString(1);
                        int distanta = dataR.GetInt32(2);

                        Proba proba = new Proba(idProba, tipproba, distanta);

                        listaProbe.Add(proba);
                    }
                }
            }
            return listaProbe;
        }

        public void save(Proba entity)
        {
            IDbConnection con = DBUtils.getConnection(props);
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "insert into proba values (@idproba, @tipproba,@distanta)";
                var paramId = comm.CreateParameter();
                paramId.ParameterName = "@idproba";
                paramId.Value = entity.Id;
                comm.Parameters.Add(paramId);

                var paramfirstName = comm.CreateParameter();
                paramfirstName.ParameterName = "@tipproba";
                paramfirstName.Value = entity.tipProba;
                comm.Parameters.Add(paramfirstName);

                var paramlastName = comm.CreateParameter();
                paramlastName.ParameterName = "@distanta";
                paramlastName.Value = entity.distanta;
                comm.Parameters.Add(paramlastName);

                

                var result = comm.ExecuteNonQuery();
                if (result == 0)
                    throw new RepositoryException("Proba nu a putut fi adaugata !");
            }

        }
        public void delete(long id)
        {
            ////TODO
        }
        public void update(Proba old, Proba entity)
        {
            ////TODO
        }


    }
}
