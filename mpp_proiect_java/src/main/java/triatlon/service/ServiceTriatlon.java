//package triatlon.service;
//
//import triatlon.domain.Arbitru;
//import triatlon.domain.Participant;
//import triatlon.domain.Rezultat;
//import triatlon.repository.IRepository;
//
//public class ServiceTriatlon {
//    private IRepository<Long, Arbitru> arbitruDataBase;
//    private IRepository<Long,Participant> participantDataBase;
//    private IRepository<Tuple<Long,Integer>, Rezultat> rezultatDataBase;
//    public ServiceTriatlon(IRepository<Long,Arbitru> arbitruDataBase,
//                           IRepository<Long,Participant> participantDataBase,
//                           IRepository<Tuple<Long,Integer>, Rezultat> rezultatDataBase) {
//        this.arbitruDataBase = arbitruDataBase;
//        this.participantDataBase = participantDataBase;
//        this.rezultatDataBase = rezultatDataBase;
//    }
//    public Iterable<Arbitru> getAllArbitrii(){
//        return arbitruDataBase.findAll();
//    }
//
//    public Arbitru findArbitruByEmailandPassword(String email,String password){
//        for(Arbitru ab : this.getAllArbitrii()){
//            if(ab.getEmail().compareTo(email) == 0 && ab.getPassword().compareTo(password) == 0){
//                return ab;
//            }
//        }
//        return null;
//    }
//}
