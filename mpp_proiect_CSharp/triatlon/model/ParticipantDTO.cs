using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace triatlon.domain
{
    [Serializable]
    public class ParticipantDTO
    {
        public string firstName { get; set; }
        public string lastName { get; set; }

        public int punctaj { get; set; }
        public ParticipantDTO()
        {
           
        }
        public ParticipantDTO(string firstName1, string lastName1, int punctaj1)
        {
            this.firstName = firstName1;
            this.lastName = lastName1;
            this.punctaj = punctaj1;
        }
        public ParticipantDTO(string firstName1, string lastName1)
        {
            this.firstName = firstName1;
            this.lastName = lastName1;
        }
        
    }
}
