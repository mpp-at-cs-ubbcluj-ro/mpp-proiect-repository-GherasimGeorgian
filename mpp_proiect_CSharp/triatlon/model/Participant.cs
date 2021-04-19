using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace triatlon.domain
{
    [Serializable]
    public class Participant : Entity<long>
    {
        public long Id { get; set; }
        public string firstName { get; set; }
        public string lastName { get; set; }
        public int varsta { get; set;}

        public Participant(long id, string firstname, string lastname,int varstaP)
        {
            this.Id = id;
            this.firstName = firstname;
            this.lastName = lastname;
            this.varsta = varstaP;
        }
        public Participant(string firstname, string lastname)
        {
         
            this.firstName = firstname;
            this.lastName = lastname;
          
        }
        public override string ToString()
        {
            return "Participant - nume: " + firstName + '|' +
                "prenume=" + lastName;
        }
    }
}
