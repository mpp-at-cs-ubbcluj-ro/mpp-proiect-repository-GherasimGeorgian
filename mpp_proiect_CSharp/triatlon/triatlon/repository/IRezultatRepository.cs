using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using triatlon.domain;

namespace triatlon.repository
{
    interface IRezultatRepository : IRepository<long, Rezultat>
    {
        List<Rezultat> filterByProba(Proba proba);
    }
}
