using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using triatlon.domain;

namespace networking
{
	public interface Request
	{
	}
	[Serializable]
	public class LoginRequest : Request
	{
		private Arbitru user;

		public LoginRequest(Arbitru user)
		{
			this.user = user;
		}

		public virtual Arbitru User
		{
			get
			{
				return user;
			}
		}
	}

	[Serializable]
	public class LogoutRequest : Request
	{
		private Arbitru user;

		public LogoutRequest(Arbitru user)
		{
			this.user = user;
		}

		public virtual Arbitru User
		{
			get
			{
				return user;
			}
		}
	}
	[Serializable]
	public class ArbitruRequest : Request
	{
		private Arbitru user;

		public ArbitruRequest(Arbitru user)
		{
			this.user = user;
		}

		public virtual Arbitru User
		{
			get
			{
				return user;
			}
		}
	}


	[Serializable]
	public class ProbaRequest : Request
	{
		private long id_proba;

		public ProbaRequest(long id_proba)
		{
			this.id_proba = id_proba;
		}

		public virtual long ID_Proba
		{
			get
			{
				return id_proba;
			}
		}
	}

	[Serializable]
	public class FLPARTRequest : Request
	{
		private ParticipantDTO participant;

		public FLPARTRequest(ParticipantDTO participant)
		{
			this.participant = participant;
		}

		public virtual ParticipantDTO ParticipantDTO
		{
			get
			{
				return ParticipantDTO;
			}
		}
	}

	[Serializable]
	public class SendRezultRequest : Request
	{
		private Rezultat rezultat;

		public SendRezultRequest(Rezultat rezultat)
		{
			this.rezultat = rezultat;
		}

		public virtual Rezultat Rezultat
		{
			get
			{
				return rezultat;
			}
		}
	}

	[Serializable]
	public class GetParticipantiRequest : Request
	{
		private Arbitru user;

		public GetParticipantiRequest(Arbitru user)
		{
			this.user = user;
		}

		public virtual Arbitru User
		{
			get
			{
				return user;
			}
		}
	}
	[Serializable]
	public class GetRezultateRequest : Request
	{
		private Proba proba;

		public GetRezultateRequest(Proba proba)
		{
			this.proba = proba;
		}

		public virtual Proba Proba
		{
			get
			{
				return proba;
			}
		}
	}
	


}
