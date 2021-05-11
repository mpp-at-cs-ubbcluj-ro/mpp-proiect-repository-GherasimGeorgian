package triatlon.network.protobuffprotocol;

import com.google.protobuf.Descriptors;
import domain.*;

import java.util.ArrayList;
import java.util.List;

public class ProtoUtils {
    public static TriatlonProtobufs.TriatlonRequest createLoginRequest(Arbitru user){
        TriatlonProtobufs.Arbitru userDTO=TriatlonProtobufs.Arbitru.newBuilder().setId(user.getId()).setFirstname(user.getFirstName()).setLastname(user.getLastName()).setEmail(user.getEmail()).setUserName(user.getUserName()).setPassword(user.getPassword()).setResponsabilProba(user.getIdProbaArbitru()).build();
        TriatlonProtobufs.TriatlonRequest request= TriatlonProtobufs.TriatlonRequest.newBuilder().setType(TriatlonProtobufs.TriatlonRequest.Type.Login)
                .setArbitru(userDTO).build();
        return request;
    }
    public static TriatlonProtobufs.TriatlonRequest createLogoutRequest(Arbitru user){
        TriatlonProtobufs.Arbitru userDTO=TriatlonProtobufs.Arbitru.newBuilder().setId(user.getId()).setFirstname(user.getFirstName()).setLastname(user.getLastName()).setEmail(user.getEmail()).setUserName(user.getUserName()).setPassword(user.getPassword()).setResponsabilProba(user.getIdProbaArbitru()).build();
        TriatlonProtobufs.TriatlonRequest request= TriatlonProtobufs.TriatlonRequest.newBuilder().setType(TriatlonProtobufs.TriatlonRequest.Type.Logout)
                .setArbitru(userDTO).build();
        return request;
    }

    public static TriatlonProtobufs.TriatlonRequest creategetArbitrubyNameRequest(String nume){
        TriatlonProtobufs.StringSS stringSS = TriatlonProtobufs.StringSS.newBuilder().setNameArbitru(nume).build();
        TriatlonProtobufs.TriatlonRequest request= TriatlonProtobufs.TriatlonRequest.newBuilder()
                .setType(TriatlonProtobufs.TriatlonRequest.Type.getArbitrubyName).setNameArbitru(stringSS).build();
        return request;
    }
    public static TriatlonProtobufs.TriatlonRequest createfindParticipantByNumePrenumeRequest(String nume){
        TriatlonProtobufs.StringSS stringSS = TriatlonProtobufs.StringSS.newBuilder().setNameArbitru(nume).build();
        TriatlonProtobufs.TriatlonRequest request= TriatlonProtobufs.TriatlonRequest.newBuilder()
                .setType(TriatlonProtobufs.TriatlonRequest.Type.findParticipantByNumePrenume).setNameArbitru(stringSS).build();
        return request;
    }

    public static TriatlonProtobufs.TriatlonRequest creategetProbaArbitrubyIdRequest(long id){
        TriatlonProtobufs.LonggSS longSS = TriatlonProtobufs.LonggSS.newBuilder().setIdEntity(id).build();
        TriatlonProtobufs.TriatlonRequest request= TriatlonProtobufs.TriatlonRequest.newBuilder()
                .setType(TriatlonProtobufs.TriatlonRequest.Type.getProbaArbitrubyId).setIdEntity(longSS).build();
        return request;
    }
    public static TriatlonProtobufs.TriatlonRequest createGetParticipantiDTORequest(Arbitru user){
        TriatlonProtobufs.Arbitru userDTO=TriatlonProtobufs.Arbitru.newBuilder().setId(user.getId()).setFirstname(user.getFirstName()).setLastname(user.getLastName()).setEmail(user.getEmail()).setUserName(user.getUserName()).setPassword(user.getPassword()).setResponsabilProba(user.getIdProbaArbitru()).build();
        TriatlonProtobufs.TriatlonRequest request= TriatlonProtobufs.TriatlonRequest.newBuilder().setType(TriatlonProtobufs.TriatlonRequest.Type.getParticipantiDTO)
                .setArbitru(userDTO).build();
        return request;
    }
    public static TriatlonProtobufs.TriatlonRequest createfilterbyProbaRequest(Proba proba){
        TriatlonProtobufs.Proba probaPr=TriatlonProtobufs.Proba.newBuilder().setId(proba.getId()).setTipproba(proba.getTipProba()).setDistanta(proba.getDistanta()).build();
        TriatlonProtobufs.TriatlonRequest request= TriatlonProtobufs.TriatlonRequest.newBuilder().setType(TriatlonProtobufs.TriatlonRequest.Type.filterbyProba)
                .setProba(probaPr).build();
        return request;
    }

    public static TriatlonProtobufs.TriatlonRequest createSendRezultatRequest(Rezultat rezultat){
        TriatlonProtobufs.Proba probaPr = TriatlonProtobufs.Proba.newBuilder().setId(rezultat.getProba().getId()).setTipproba(rezultat.getProba().getTipProba()).setDistanta(rezultat.getProba().getDistanta()).build();
        TriatlonProtobufs.Participant participantPr = TriatlonProtobufs.Participant.newBuilder().setId(rezultat.getParticipant().getId().toString()).setFirstname(rezultat.getParticipant().getFirstName()).setLastname(rezultat.getParticipant().getLastName()).setVarsta(rezultat.getParticipant().getVarsta().toString()).build();

        TriatlonProtobufs.Rezultat rezultatR=TriatlonProtobufs.Rezultat.newBuilder().setProba(probaPr).setParticipant(participantPr).setNumarpuncte(rezultat.getNumarPuncte()).build();
        TriatlonProtobufs.TriatlonRequest request= TriatlonProtobufs.TriatlonRequest.newBuilder().setType(TriatlonProtobufs.TriatlonRequest.Type.SendRezultat)
                .setRezultat(rezultatR).build();
        return request;
    }
//    public static TriatlonProtobufs.TriatlonRequest createSendRezultatRequest(Rezultat rezultat){
//        TriatlonProtobufs.Rezultat messageDTO=TriatlonProtobufs.Rezultat.newBuilder().
//                setId(rezultat.getId())
//                .setParticipant(rezultat.getParticipant())
//                .setProba(rezultat.getProba())
//                .setNumarpuncte(rezultat.getNumarPuncte())
//                .build();
//
//
//        TriatlonProtobufs.ChatRequest request= ChatProtobufs.ChatRequest.newBuilder()
//                .setType(ChatProtobufs.ChatRequest.Type.SendMessage)
//                .setMessage(messageDTO).build();
//        return request;
//    }
//
//    public static ChatProtobufs.ChatRequest createLoggedFriendsRequest(User user){
//        ChatProtobufs.User userDTO=ChatProtobufs.User.newBuilder().setId(user.getId()).build();
//        ChatProtobufs.ChatRequest request= ChatProtobufs.ChatRequest.newBuilder()
//                .setType(ChatProtobufs.ChatRequest.Type.GetLoggedFriends)
//                .setUser(userDTO).build();
//        return request;
//    }
//
//
//    public static ChatProtobufs.ChatResponse createOkResponse(){
//        ChatProtobufs.ChatResponse response=ChatProtobufs.ChatResponse.newBuilder()
//                .setType(ChatProtobufs.ChatResponse.Type.Ok).build();
//        return response;
//    }
//
//    public static ChatProtobufs.ChatResponse createErrorResponse(String text){
//        ChatProtobufs.ChatResponse response=ChatProtobufs.ChatResponse.newBuilder()
//                .setType(ChatProtobufs.ChatResponse.Type.Error)
//                .setError(text).build();
//        return response;
//    }
//
//    public static ChatProtobufs.ChatResponse createFriendLoggedInResponse(User user){
//        ChatProtobufs.User userDTO=ChatProtobufs.User.newBuilder().setId(user.getId()).build();
//
//        ChatProtobufs.ChatResponse response=ChatProtobufs.ChatResponse.newBuilder()
//                .setType(ChatProtobufs.ChatResponse.Type.FriendLoggedIn)
//                .setUser(userDTO).build();
//        return response;
//    }
//
//    public static ChatProtobufs.ChatResponse createFriendLoggedOutResponse(User user){
//        ChatProtobufs.User userDTO=ChatProtobufs.User.newBuilder().setId(user.getId()).build();
//
//        ChatProtobufs.ChatResponse response=ChatProtobufs.ChatResponse.newBuilder()
//                .setType(ChatProtobufs.ChatResponse.Type.FriendLoggedOut)
//                .setUser(userDTO).build();
//        return response;
//    }
//    public static TriatlonProtobufs.TriatlonResponse createNewMessageResponse(Message message){
//        ChatProtobufs.Message messageDTO=ChatProtobufs.Message.newBuilder()
//                .setSenderId(message.getSender().getId())
//                .setReceiverId(message.getReceiver().getId())
//                .setText(message.getText())
//                .build();
//
//        ChatProtobufs.ChatResponse response=ChatProtobufs.ChatResponse.newBuilder()
//                .setType(ChatProtobufs.ChatResponse.Type.NewMessage)
//                .setMessage(messageDTO).build();
//        return response;
//    }

//    public static ChatProtobufs.ChatResponse createLoggedFriendsResponse(User[] users){
//        ChatProtobufs.ChatResponse.Builder response=ChatProtobufs.ChatResponse.newBuilder()
//                .setType(ChatProtobufs.ChatResponse.Type.GetLoggedFriends);
//        for (User user: users){
//            ChatProtobufs.User userDTO=ChatProtobufs.User.newBuilder().setId(user.getId()).build();
//            response.addFriends(userDTO);
//        }
//
//        return response.build();
//    }
//
    public static String getError(TriatlonProtobufs.TriatlonResponse response){
        String errorMessage=response.getError();
        return errorMessage;
    }


//    public static Message getMessage(ChatProtobufs.ChatResponse response){
//        User sender=new User();
//        sender.setId(response.getMessage().getSenderId());
//        User receiver=new User();
//        receiver.setId(response.getMessage().getReceiverId());
//        Message message=new Message(sender,response.getMessage().getText(), receiver);
//        return message;
//    }
//
//    public static User[] getFriends(ChatProtobufs.ChatResponse response){
//        User[] friends=new User[response.getFriendsCount()];
//        for(int i=0;i<response.getFriendsCount();i++){
//            ChatProtobufs.User userDTO=response.getFriends(i);
//            User user=new User();
//            user.setId(userDTO.getId());
//            friends[i]=user;
//        }
//        return friends;
//    }
    public static Arbitru getArbitru(TriatlonProtobufs.TriatlonResponse response){
        Arbitru user=new Arbitru(response.getArbitru().getId(),response.getArbitru().getFirstname(),response.getArbitru().getLastname(),response.getArbitru().getEmail(), response.getArbitru().getPassword(),response.getArbitru().getUserName(), response.getArbitru().getResponsabilProba());
        return user;
    }
    public static Participant getParticipant(TriatlonProtobufs.TriatlonResponse response){
        Participant part = new Participant(Long.parseLong(response.getParticipant().getId()),response.getParticipant().getFirstname(),response.getParticipant().getLastname(),Integer.parseInt(response.getParticipant().getVarsta()));
        return part;
    }


    public static Proba getProba(TriatlonProtobufs.TriatlonResponse response){
        Proba proba = new Proba(response.getProba().getId(),response.getProba().getTipproba(),response.getProba().getDistanta());
        return proba;
    }
    public static Rezultat getRezultat(TriatlonProtobufs.TriatlonResponse response){
        Proba proba = new Proba(response.getRezultat().getProba().getId(),response.getRezultat().getProba().getTipproba(),response.getRezultat().getProba().getDistanta());
        Participant participant = new Participant(Long.parseLong(response.getRezultat().getParticipant().getId()),response.getRezultat().getParticipant().getFirstname(),response.getRezultat().getParticipant().getLastname(),Integer.parseInt(response.getRezultat().getParticipant().getVarsta()));
        Rezultat rez = new Rezultat(response.getRezultat().getId(),proba,participant,response.getRezultat().getNumarpuncte());
        return rez;
    }
    public static List<ParticipantDTO> getParticipantiDTO(TriatlonProtobufs.TriatlonResponse response){
        List<ParticipantDTO> listaP = new ArrayList<>();

        for(int i=0;i<response.getParticipantiiDTOCount();i++){
            TriatlonProtobufs.ParticipantDTO participantDTO=response.getParticipantiiDTO(i);
            ParticipantDTO participant=new ParticipantDTO(participantDTO.getFirstname(),participantDTO.getLastname(), participantDTO.getPunctaj());
            listaP.add(participant);
        }
        return listaP;
    }
    public static List<Rezultat> getfilterbyProbaRequest(TriatlonProtobufs.TriatlonResponse response){
        List<Rezultat> listaR = new ArrayList<>();
        for(int i=0;i<response.getRezultateCount();i++){
            TriatlonProtobufs.Rezultat rezultat=response.getRezultate(i);
            Proba proba = new Proba(rezultat.getId(),rezultat.getProba().getTipproba(),rezultat.getProba().getDistanta());
            Participant participant = new Participant(Long.parseLong(rezultat.getParticipant().getId()),rezultat.getParticipant().getFirstname(),rezultat.getParticipant().getLastname(), Integer.parseInt(rezultat.getParticipant().getVarsta()));
            Rezultat rez=new Rezultat(rezultat.getId(),proba,participant,rezultat.getNumarpuncte());
            listaR.add(rez);
        }
        return listaR;
    }
//
//    public static Participant getParticipant(TriatlonProtobufs.TriatlonRequest request){
//        Participant user=new Participant(Long. parseLong(request.getParticipant().getId()),request.getParticipant().getFirstname(),request.getParticipant().getLastname(), Integer. valueOf(request.getParticipant().getVarsta()));
//
//        return user;
//    }
////
//    public static Message getMessage(ChatProtobufs.ChatRequest request){
//        User sender=new User();
//        sender.setId(request.getMessage().getSenderId());
//        User receiver=new User();
//        receiver.setId(request.getMessage().getReceiverId());
//        Message message=new Message(sender,request.getMessage().getText(), receiver);
//        return message;
//    }

}
