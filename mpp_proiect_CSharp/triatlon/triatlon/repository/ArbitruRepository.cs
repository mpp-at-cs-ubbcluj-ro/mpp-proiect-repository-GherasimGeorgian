using log4net;
using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using triatlon.domain;
[assembly: System.Runtime.CompilerServices.InternalsVisibleToAttribute("Tests")]
namespace triatlon.repository
{
     class ArbitruRepository : IRepository<long, Arbitru>
    {
        private static readonly ILog log = LogManager.GetLogger("ArbitruRepository");
        IDictionary<String, string> props;      
      


        public ArbitruRepository(IDictionary<String, string> props)
        {
            log.Info("Creating ArbitruRepository ");
            this.props = props;
        }
        public Arbitru findOne(long id)
        {
            log.InfoFormat("Entering findOne with value {0}", id);
            IDbConnection con = DBUtils.getConnection(props);

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from arbitru where Id=@id";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);

                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        long idArbitru = dataR.GetInt64(0);
                        String firstName = dataR.GetString(1);
                        String lastName = dataR.GetString(2);
                        String email = dataR.GetString(3);
                        String username = dataR.GetString(4);
                        String parola = dataR.GetString(5);

                        Arbitru arbitru = new Arbitru(idArbitru, firstName, lastName, email, username, parola);
                        

                        log.InfoFormat("Exiting findOne with value {0}", arbitru);
                        return arbitru;
                    }
                }
            }
           
            log.InfoFormat("Exiting findOne with value {0}", null);
            return null;
           
        }
        public IEnumerable<Arbitru> findAll()
        {
            IDbConnection con = DBUtils.getConnection(props);
            IList<Arbitru> listaArbitrii = new List<Arbitru>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from arbitru";

                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        long idArbitru = dataR.GetInt64(0);
                        String firstName = dataR.GetString(1);
                        String lastName = dataR.GetString(2);
                        String email = dataR.GetString(3);
                        String username = dataR.GetString(4);
                        String parola = dataR.GetString(5);
                        
                        Arbitru arbitru = new Arbitru(idArbitru,firstName, lastName, email, username, parola);

                        listaArbitrii.Add(arbitru);
                    }
                }
            }
            return listaArbitrii;
        }

       public void save(Arbitru entity)
        {
            IDbConnection con = DBUtils.getConnection(props);
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "insert into arbitru values (@id, @firstname,@lastname,@email,@username,@password)";
                var paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = entity.Id;
                comm.Parameters.Add(paramId);

                var paramfirstName = comm.CreateParameter();
                paramfirstName.ParameterName = "@firstname";
                paramfirstName.Value = entity.firstName;
                comm.Parameters.Add(paramfirstName);

                var paramlastName = comm.CreateParameter();
                paramlastName.ParameterName = "@lastname";
                paramlastName.Value = entity.lastName;
                comm.Parameters.Add(paramlastName);

                var paramemail = comm.CreateParameter();
                paramemail.ParameterName = "@email";
                paramemail.Value = entity.email;
                comm.Parameters.Add(paramemail);

                var paramusername = comm.CreateParameter();
                paramusername.ParameterName = "@username";
                paramusername.Value = entity.username;
                comm.Parameters.Add(paramusername);

                var parampassword = comm.CreateParameter();
                parampassword.ParameterName = "@password";
                parampassword.Value = entity.password;
                comm.Parameters.Add(parampassword);

                var result = comm.ExecuteNonQuery();
                if (result == 0)
                    throw new RepositoryException("Arbitrul nu a putut fi adaugat !");
            }
            
        }

        public void delete(long id)
        {
            IDbConnection con = DBUtils.getConnection(props);
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "delete from arbitru where Id=@id";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);
                var dataR = comm.ExecuteNonQuery();
                if (dataR == 0)
                    throw new RepositoryException("Niciun arbitru nu a fost sters!");
            }
            con.Close();
        }
        public void update(Arbitru old, Arbitru entity)
        {
            ////TODO
        }
    }
}
