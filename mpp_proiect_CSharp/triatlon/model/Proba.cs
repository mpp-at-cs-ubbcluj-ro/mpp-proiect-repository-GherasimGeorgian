using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace triatlon.domain
{
    [Serializable]
    public class Proba : Entity<long>
    {
        public long Id { get; set; }
        public string tipProba { get; set; }

        public int distanta { get; set; }
        public Proba()
        {

        }

        public Proba(long idProba,string tipProba1,int distanta1)
        {
            this.Id = idProba;
            this.tipProba = tipProba1;
            this.distanta = distanta1;
        }

        public override string ToString()
        {
            return "Proba - tip proba: " + tipProba + '|' +
                "distanta=" + distanta;
        }
    }
}
