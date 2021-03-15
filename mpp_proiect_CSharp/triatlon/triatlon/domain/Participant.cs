using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace triatlon.domain
{
    class Participant : Entity<long>
    {
        public long Id { get; set; }
        public string firstName { get; set; }
        public string lastName { get; set; }

        public List<Rezultat> rezultate { get; set; }

        public int varsta { get; set;}

        public Participant(long id, string firstname, string lastname,int varstaP)
        {

        }
    }
}
