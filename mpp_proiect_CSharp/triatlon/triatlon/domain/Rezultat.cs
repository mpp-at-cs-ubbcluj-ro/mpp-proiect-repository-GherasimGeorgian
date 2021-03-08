using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace triatlon.domain
{
    class Rezultat
    {
        public long idParticipant { get; set; }
        public int tipProba { get; set; }
        public int numarPuncte { get; set; }
        public long idArbitru { get; set; }
        public long idCompetitie { get; set; }
        public DateTime timp_pornire { get; set; }

        public DateTime timp_finalizare { get; set; }
    }
}
