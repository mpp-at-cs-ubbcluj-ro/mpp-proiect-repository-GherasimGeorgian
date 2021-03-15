using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace triatlon.domain
{
    class Rezultat : Entity<long>
    {
        public long Id { get; set; }

        public Proba proba { get; set; }
        public Participant participant { get; set; }

        public int numarPuncte { get; set; }

        public Rezultat(long idrezultat1,Proba proba1, Participant participant1, int numarpuncte1)
        {
            this.Id = idrezultat1;
            this.proba = proba1;
            this.participant = participant1;
            this.numarPuncte = numarpuncte1;
            
        }

        public override string ToString()
        {
            return "Rezultat " + proba.tipProba + " " + participant.firstName + " " + numarPuncte;
        }
    }
}
