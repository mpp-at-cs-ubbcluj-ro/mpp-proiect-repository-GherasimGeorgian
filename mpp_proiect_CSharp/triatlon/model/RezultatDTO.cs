using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace model
{
    [Serializable]
    public class RezultatDTO
    {
        public string firstName { get; set; }
        public string lastName { get; set; }

        public string tipproba { get; set; }

        public int numarpuncte { get; set; }
        public RezultatDTO()
        {

        }
        public RezultatDTO(string firstName1, string lastName1, string tipproba1,int numarpuncte1)
        {
            this.firstName = firstName1;
            this.lastName = lastName1;
            this.tipproba = tipproba1;
            this.numarpuncte = numarpuncte1;
        }
       
    }
}
