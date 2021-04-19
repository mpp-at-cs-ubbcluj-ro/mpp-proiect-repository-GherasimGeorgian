using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using triatlon.domain;

namespace services
{
    

    public interface ITriatlonObserver
    {
        void rezultReceived(Rezultat rezultat);
      
    }
}
