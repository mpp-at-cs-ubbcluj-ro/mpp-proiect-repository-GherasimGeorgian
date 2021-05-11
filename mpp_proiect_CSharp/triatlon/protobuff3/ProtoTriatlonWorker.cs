using System;
using System.Threading;
using System.Net.Sockets;


using Google.Protobuf;
using services;
using Triatlon.Protocol;

namespace protobuf
{
	public class ProtoV3TriatlonWorker : ITriatlonObserver
	{
		private ITriatlonServices server;
		private TcpClient connection;

		private NetworkStream stream;
		private volatile bool connected;
		public ProtoV3TriatlonWorker(ITriatlonServices server, TcpClient connection)
		{
			Console.WriteLine("ProtoV3TriatlonWorker ");
			this.server = server;
			this.connection = connection;
			try
			{

				stream = connection.GetStream();
				connected = true;
			}
			catch (Exception e)
			{
				Console.WriteLine(e.StackTrace);
			}
		}

		public virtual void run()
		{
			while (connected)
			{
				try
				{

					TriatlonRequest request = TriatlonRequest.Parser.ParseDelimitedFrom(stream);
					TriatlonResponse response = handleRequest(request);
					if (response != null)
					{
						sendResponse(response);
					}
				}
				catch (Exception e)
				{
					Console.WriteLine(e.StackTrace);
				}

				try
				{
					Thread.Sleep(1000);
				}
				catch (Exception e)
				{
					Console.WriteLine(e.StackTrace);
				}
			}
			try
			{
				stream.Close();
				connection.Close();
			}
			catch (Exception e)
			{
				Console.WriteLine("Error " + e);
			}
		}
        public virtual void rezultReceived(triatlon.domain.Rezultat rezultat)
        {
			Console.WriteLine("Rezultat received  " + rezultat);
			try
			{
				sendResponse(ProtoUtils.createNewRezultatResponse(rezultat));
			}
			catch (Exception e)
			{
				throw new TriatlonException("Sending error: " + e);
			}
		}

        //	public virtual void friendLoggedIn(chat.model.User friend)
        //{
        //Console.WriteLine("Friend logged in " + friend);
        //try
        //{
        //	sendResponse(ProtoUtils.createFriendLoggedInResponse(friend));
        //}
        //catch (Exception e)
        //{
        //	Console.WriteLine(e.StackTrace);
        //}
        //}
        //public virtual void friendLoggedOut(chat.model.User friend)
        //{
        //Console.WriteLine("Friend logged out " + friend);
        //try
        //{
        //	sendResponse(ProtoUtils.createFriendLoggedOutResponse(friend));
        //}
        //catch (Exception e)
        //{
        //	Console.WriteLine(e.StackTrace);
        //}
        //}

        private TriatlonResponse handleRequest(TriatlonRequest request)
		{
			TriatlonResponse response = null;
			TriatlonRequest.Types.Type reqType = request.Type;
			switch (reqType)
			{
				case TriatlonRequest.Types.Type.Login:
					{
						Console.WriteLine("Login request ...");
						triatlon.domain.Arbitru user = ProtoUtils.getArbitru(request);
						try
						{
							lock (server)
							{
								server.login(user, this);
							}
							return ProtoUtils.createOkResponse();
						}
						catch (TriatlonException e)
						{
							connected = false;
							return ProtoUtils.createErrorResponse(e.Message);
						}
					}
				case TriatlonRequest.Types.Type.Logout:
					{
						Console.WriteLine("Logout request");
						triatlon.domain.Arbitru user = ProtoUtils.getArbitru(request);
						try
						{
							lock (server)
							{

								server.logout(user, this);
							}
							connected = false;
							return ProtoUtils.createOkResponse();

						}
						catch (TriatlonException e)
						{
							return ProtoUtils.createErrorResponse(e.Message);
						}
					}
				//case ChatRequest.Types.Type.SendMessage:
				//	{
				//		Console.WriteLine("SendMessageRequest ...");
				//		chat.model.Message message = ProtoUtils.getMessage(request);
				//		try
				//		{
				//			lock (server)
				//			{
				//				server.sendMessage(message);
				//			}
				//			return ProtoUtils.createOkResponse();
				//		}
				//		catch (ChatException e)
				//		{
				//			return ProtoUtils.createErrorResponse(e.Message);
				//		}
				//	}

				case TriatlonRequest.Types.Type.GetArbitrubyName:
					{
						Console.WriteLine("GetArbitrubyName Request ...");
						String numeArbitru = ProtoUtils.getNumeArbitru(request);  //DTOUtils.getFromDTO(udto);
						Console.WriteLine("am gasit numele:"+numeArbitru);
						try
						{
							triatlon.domain.Arbitru arbitru = null;
							lock (server)
							{

								arbitru = server.getArbitruLogat(numeArbitru);
								Console.WriteLine("Arbitrul:" + arbitru.ToString());
							}
							return ProtoUtils.createGetArbitruByNameResponse(arbitru);
						}
						catch (TriatlonException e)
						{
							return ProtoUtils.createErrorResponse(e.Message);
						}
					}
				case TriatlonRequest.Types.Type.FindParticipantByNumePrenume:
					{
						Console.WriteLine("FindParticipantByNumePrenume Request ...");
						String numePart = ProtoUtils.getNumeArbitru(request);  //DTOUtils.getFromDTO(udto);
						
						try
						{
							triatlon.domain.Participant participant = null;
							string[] words = numePart.Split('|');
							string fn = words[0].ToString();
							string ln = words[1].ToString();
							lock (server)
							{
								triatlon.domain.ParticipantDTO part = new triatlon.domain.ParticipantDTO(fn, ln);
								participant = server.findParticipantbyfirstlastName(part);
								
							}
							return ProtoUtils.createFindParticipantByNumePrenumeResponse(participant);
						}
						catch (TriatlonException e)
						{
							return ProtoUtils.createErrorResponse(e.Message);
						}
					}

				case TriatlonRequest.Types.Type.GetParticipantiDto:
					{
						Console.WriteLine("GetParticipantiDto Request ...");
						triatlon.domain.Arbitru arbitru = ProtoUtils.getArbitru(request);  //DTOUtils.getFromDTO(udto);
						
						try
						{
							triatlon.domain.ParticipantDTO[] listaDTOP = null;
							lock (server)
							{

								listaDTOP = server.getParticipanti(arbitru);
								
							}
							return ProtoUtils.createGetParticipantiiGtoResponse(listaDTOP);
						}
						catch (TriatlonException e)
						{
							return ProtoUtils.createErrorResponse(e.Message);
						}
					}

				case TriatlonRequest.Types.Type.FilterbyProba:
					{
						Console.WriteLine("FilterbyProba Request ...");
						triatlon.domain.Proba proba = ProtoUtils.getProba(request);  //DTOUtils.getFromDTO(udto);

						try
						{
							triatlon.domain.Rezultat[] listaRez = null;
							lock (server)
							{

								listaRez = server.filterbyProba(proba);

							}
							return ProtoUtils.createFilterProbaResponse(listaRez);
						}
						catch (TriatlonException e)
						{
							return ProtoUtils.createErrorResponse(e.Message);
						}
					}

				case TriatlonRequest.Types.Type.GetProbaArbitrubyId:
					{
						Console.WriteLine("GetProbaArbitrubyId Request ...");
						long id_arb = ProtoUtils.getIdArbitru(request);  //DTOUtils.getFromDTO(udto);

						try
						{
							triatlon.domain.Proba proba = null;
							lock (server)
							{

								proba = server.getProbaByArbitru(id_arb);

							}
							return ProtoUtils.createProbaArbitrubyIdResponse(proba);
						}
						catch (TriatlonException e)
						{
							return ProtoUtils.createErrorResponse(e.Message);
						}
					}
				case TriatlonRequest.Types.Type.SendRezultat:
					{
						Console.WriteLine("SendRezultat Request ...");
						triatlon.domain.Rezultat rezultat = ProtoUtils.getRezultat(request);  //DTOUtils.getFromDTO(udto);

						try
						{
							
							lock (server)
							{

								server.sendRezult(rezultat);

							}
							return ProtoUtils.createOkResponse();
						}
						catch (TriatlonException e)
						{
							return ProtoUtils.createErrorResponse(e.Message);
						}
					}
			}
			return response;
		}

		private void sendResponse(TriatlonResponse response)
		{
			Console.WriteLine("sending response " + response);
			lock (stream)
			{
				response.WriteDelimitedTo(stream);
				stream.Flush();
			}

		}
	}



}
