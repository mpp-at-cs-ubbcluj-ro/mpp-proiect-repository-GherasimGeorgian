using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Triatlon.Protocol;
using proto = Triatlon.Protocol;
namespace protobuf
{
    static class ProtoUtils
    {
        public static TriatlonRequest createLoginRequest(triatlon.domain.Arbitru user)
        {
            //new proto.User {Id = user.Id, Passwd = user.Password};
            proto.Arbitru userDTO = new proto.Arbitru { UserName = user.username, Password = user.password };
            TriatlonRequest request = new TriatlonRequest { Type = TriatlonRequest.Types.Type.Login, Arbitru = userDTO };

            return request;
        }

        public static TriatlonRequest createLogoutRequest(triatlon.domain.Arbitru user)
        {
            proto.Arbitru userDTO = new proto.Arbitru { UserName = user.username, Password = user.password };
            TriatlonRequest request = new TriatlonRequest { Type = TriatlonRequest.Types.Type.Logout, Arbitru = userDTO };

            return request;
        }

        public static TriatlonRequest createGetParticipantiiDTORequest(triatlon.domain.Arbitru user)
        {
            proto.Arbitru userDTO = new proto.Arbitru { UserName = user.username,Email=user.email,Firstname = user.firstName,Lastname = user.lastName, Password = user.password };
            TriatlonRequest request = new TriatlonRequest { Type = TriatlonRequest.Types.Type.GetParticipantiDto, Arbitru = userDTO };

            return request;
        }



        //public static ChatRequest createSendMesssageRequest(chat.model.Message message)
        //{
        //    proto.Message messageDTO = new proto.Message
        //    {
        //        SenderId = message.Sender.Id,
        //        ReceiverId = message.Receiver.Id,
        //        Text = message.Text
        //    };

        //    ChatRequest request = new ChatRequest { Type = ChatRequest.Types.Type.SendMessage, Message = messageDTO };
        //    return request;
        //}




        public static TriatlonResponse createOkResponse()
        {
            TriatlonResponse response = new TriatlonResponse { Type = TriatlonResponse.Types.Type.Ok };
            return response;
        }


        public static TriatlonResponse createErrorResponse(String text)
        {
            TriatlonResponse response = new TriatlonResponse
            {
                Type = TriatlonResponse.Types.Type.Error,
                Error = text
            };
            return response;
        }

        //public static ChatResponse createFriendLoggedInResponse(chat.model.User user)
        //{
        //    proto.User userDTO = new proto.User { Id = user.Id };
        //    ChatResponse response = new ChatResponse { Type = ChatResponse.Types.Type.FriendLoggedIn, User = userDTO };
        //    return response;
        //}

        //public static ChatResponse createFriendLoggedOutResponse(chat.model.User user)
        //{
        //    proto.User userDTO = new proto.User { Id = user.Id };
        //    ChatResponse response = new ChatResponse { Type = ChatResponse.Types.Type.FriendLoggedOut, User = userDTO };
        //    return response;
        //}

        //public static ChatResponse createNewMessageResponse(chat.model.Message message)
        //{
        //    proto.Message messageDTO = new proto.Message
        //    {
        //        SenderId = message.Sender.Id,
        //        ReceiverId = message.Receiver.Id,
        //        Text = message.Text
        //    };

        //    ChatResponse response = new ChatResponse { Type = ChatResponse.Types.Type.NewMessage, Message = messageDTO };
        //    return response;
        //}


        public static TriatlonResponse  createGetArbitruByNameResponse(triatlon.domain.Arbitru arbitru)
        {
           
            proto.Arbitru arbitruResponse = new proto.Arbitru
            {
                Id = Convert.ToUInt64(arbitru.Id),
                Firstname = arbitru.firstName,
                Lastname = arbitru.lastName,
                Email = arbitru.email,
                Password = arbitru.password,
                ResponsabilProba = Convert.ToUInt64(arbitru.id_proba_responsabil),
                UserName = arbitru.username
            };

            TriatlonResponse response = new TriatlonResponse { Type = TriatlonResponse.Types.Type.GetArbitrubyName, Arbitru = arbitruResponse };
            return response;
        }
        public static TriatlonResponse createFindParticipantByNumePrenumeResponse(triatlon.domain.Participant part)
        {
            proto.Participant participant = new proto.Participant
            {
                Id =part.Id.ToString(),
                Firstname = part.firstName,
                Lastname = part.lastName,
                Varsta = part.varsta.ToString()
               
            };

            TriatlonResponse response = new TriatlonResponse { Type = TriatlonResponse.Types.Type.FindParticipantByNumePrenume, Participant = participant };
            return response;
        }
        public static TriatlonResponse createProbaArbitrubyIdResponse(triatlon.domain.Proba proba)
        {
            proto.Proba probaResponse = new proto.Proba
            {
                Id = Convert.ToUInt64(proba.Id),
                Distanta = Convert.ToUInt32(proba.distanta),
                Tipproba = proba.tipProba
               
            };
            TriatlonResponse response = new TriatlonResponse { Type = TriatlonResponse.Types.Type.GetProbaArbitrubyId, Proba = probaResponse };
            return response;
        }

        public static TriatlonResponse createGetParticipantiiGtoResponse(triatlon.domain.ParticipantDTO[] participantii)
        {
            TriatlonResponse response = new TriatlonResponse
            {
                Type = TriatlonResponse.Types.Type.GetParticipantiDto
            };
            foreach (triatlon.domain.ParticipantDTO user in participantii)
            {
                proto.ParticipantDTO userDTO = new proto.ParticipantDTO { Firstname = user.firstName,Lastname = user.lastName,Punctaj = Convert.ToUInt32(user.punctaj) };

                response.ParticipantiiDTO.Add(userDTO);
            }

            return response;
        }
        public static TriatlonResponse createFilterProbaResponse(triatlon.domain.Rezultat[] rezultate)
        {
            TriatlonResponse response = new TriatlonResponse
            {
                Type = TriatlonResponse.Types.Type.FilterbyProba
            };
            foreach (triatlon.domain.Rezultat rez in rezultate)
            {
                proto.Participant participantX = new proto.Participant {Id = rez.participant.Id.ToString(),Firstname = rez.participant.firstName,Lastname = rez.participant.lastName,Varsta = rez.participant.varsta.ToString() };
                proto.Proba probaX = new proto.Proba { Id = Convert.ToUInt64(rez.proba.Id),Distanta = Convert.ToUInt32(rez.proba.distanta),Tipproba = rez.proba.tipProba};
                proto.Rezultat rezultat = new proto.Rezultat { Id = Convert.ToUInt64(rez.Id), Numarpuncte = Convert.ToUInt32(rez.numarPuncte), Participant =participantX,Proba = probaX};

                response.Rezultate.Add(rezultat);
            }

            return response;
        }

            public static String getError(TriatlonResponse response)
        {
            String errorMessage = response.Error;
            return errorMessage;
        }

        public static triatlon.domain.Arbitru getArbitru(TriatlonRequest response)
        {
            triatlon.domain.Arbitru user = new triatlon.domain.Arbitru(response.Arbitru.UserName,response.Arbitru.Password);
            return user;
        }
        public static triatlon.domain.Proba getProba(TriatlonRequest response)
        {
            triatlon.domain.Proba proba = new triatlon.domain.Proba(Convert.ToInt64(response.Proba.Id), response.Proba.Tipproba, Convert.ToInt32(response.Proba.Distanta));
            return proba;
        }
        public static triatlon.domain.Rezultat getRezultat(TriatlonRequest response)
        {
            triatlon.domain.Proba proba = new triatlon.domain.Proba(Convert.ToInt64(response.Rezultat.Proba.Id), response.Rezultat.Proba.Tipproba, Convert.ToInt32(response.Rezultat.Proba.Distanta));
            triatlon.domain.Participant participant = new triatlon.domain.Participant(Convert.ToInt64(response.Rezultat.Participant.Id),response.Rezultat.Participant.Firstname,response.Rezultat.Participant.Lastname,Convert.ToInt32(response.Rezultat.Participant.Varsta));
            triatlon.domain.Rezultat rez = new triatlon.domain.Rezultat(Convert.ToInt64(response.Rezultat.Id),proba,participant,Convert.ToInt32(response.Rezultat.Numarpuncte));
            return rez;
        }
        public static TriatlonResponse createNewRezultatResponse(triatlon.domain.Rezultat rez)
        {

            proto.Participant participantX = new proto.Participant { Id = rez.participant.Id.ToString(), Firstname = rez.participant.firstName, Lastname = rez.participant.lastName, Varsta = rez.participant.varsta.ToString() };
            proto.Proba probaX = new proto.Proba { Id = Convert.ToUInt64(rez.proba.Id), Distanta = Convert.ToUInt32(rez.proba.distanta), Tipproba = rez.proba.tipProba };
            proto.Rezultat rezultat = new proto.Rezultat { Id = Convert.ToUInt64(rez.Id), Numarpuncte = Convert.ToUInt32(rez.numarPuncte), Participant = participantX, Proba = probaX };


            TriatlonResponse response = new TriatlonResponse { Type = TriatlonResponse.Types.Type.NewRezultat, Rezultat = rezultat };
            return response;
        }

        public static string getNumeArbitru(TriatlonRequest response)
        {
            string nume_arbitru = response.NameArbitru.NameArbitru;
            return nume_arbitru;
        }
        public static long getIdArbitru(TriatlonRequest response)
        {
            long id_arb = Convert.ToInt64(response.IdEntity.IdEntity);
            return id_arb;
        }
        


        //public static chat.model.Message getMessage(ChatResponse response)
        //{
        //    chat.model.User sender = new chat.model.User(response.Message.SenderId);
        //    chat.model.User receiver = new chat.model.User(response.Message.ReceiverId);
        //    chat.model.Message message = new chat.model.Message(sender, receiver, response.Message.Text);
        //    return message;
        //}

        //public static chat.model.User[] getFriends(ChatResponse response)
        //{
        //    chat.model.User[] friends = new chat.model.User[response.Friends.Count];
        //    for (int i = 0; i < response.Friends.Count; i++)
        //    {
        //        chat.model.User user = new chat.model.User(response.Friends[i].Id);
        //        friends[i] = user;
        //    }
        //    return friends;
        //}

        //public static chat.model.User getUser(ChatRequest request)
        //{
        //    chat.model.User user = new chat.model.User(request.User.Id);
        //    user.Password = request.User.Passwd;
        //    return user;
        //}
        //public static chat.model.Message getMessage(ChatRequest request)
        //{
        //    chat.model.User sender = new chat.model.User(request.Message.SenderId);
        //    chat.model.User receiver = new chat.model.User(request.Message.ReceiverId);
        //    chat.model.Message message = new chat.model.Message(sender, receiver, request.Message.Text);
        //    return message;
        //}

    }
}