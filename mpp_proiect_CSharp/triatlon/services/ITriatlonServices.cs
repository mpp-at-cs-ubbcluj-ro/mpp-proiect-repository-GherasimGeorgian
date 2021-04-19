using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using triatlon.domain;
namespace services
{
   

    public interface ITriatlonServices

    {
        void login(Arbitru user, ITriatlonObserver client);
        void sendRezult(Rezultat rezultat);
        void logout(Arbitru user, ITriatlonObserver client);
        ParticipantDTO[] getParticipanti(Arbitru user);

        Arbitru getArbitruLogat(string username);

        Proba getProbaByArbitru(long id_proba);

        Participant findParticipantbyfirstlastName(ParticipantDTO part);

        Rezultat[] filterbyProba(Proba proba);

    }
}
