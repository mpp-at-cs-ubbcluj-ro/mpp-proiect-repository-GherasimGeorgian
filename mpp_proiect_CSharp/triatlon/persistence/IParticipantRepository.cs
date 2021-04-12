using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using triatlon.domain;

namespace triatlon.repository
{
    public interface IParticipantRepository : IRepository<long, Participant>
    {
    }
}
