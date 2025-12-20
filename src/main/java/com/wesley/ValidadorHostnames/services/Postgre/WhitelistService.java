//package com.wesley.ValidadorHostnames.services.Postgre;
//
//import com.wesley.ValidadorHostnames.entities.Postgre.AllowedHostname;
//import com.wesley.ValidadorHostnames.repositories.Postgre.AllowedHostnameRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service("whitelistPostgreService")
//public class WhitelistService {
//
//    @Autowired
//    private AllowedHostnameRepository repository;
//
//    public boolean isAllowed(String hostname) {
////        System.out.println("Buscando no banco for ("+hostname+")");
//
//        if (repository.findByHostname(hostname).isPresent()) {
//            return true;
//        }
//
//        List<AllowedHostname> wildcards = repository.findByWildcardTrue();
//        System.out.println("tamanho da lista NAO otimizada: "+ wildcards.size());
//
//        for (AllowedHostname allowed : wildcards) {
//            if (hostname.endsWith("." + allowed.getHostname())) {
//                return true;
//            }
//        }
//
//        return false;
//    }
//
//    public boolean isAllowedOtimizada(String hostname) {
////        System.out.println("Buscando no banco for ("+hostname+")");
//
//        List<AllowedHostname> lista = repository.findByHostnameOtimizada(hostname);
//        System.out.println("tamanho da lista otimizada: "+ lista.size());
//
//        // Verifica se existe um host exatamente igual
//        if (lista.isEmpty()) {
//            return false;
//        }
//
//        for (AllowedHostname allowed : lista) {
//            String allowedHost = allowed.getHostname();
//
//            if (hostname.equals(allowedHost)) {
//                return true;
//            }
//
//            String requiredSuffix = "." + allowedHost;
//
//            if (hostname.endsWith(requiredSuffix)) {
//                return true;
//            }
//        }
//
//        return false;
//    }
//
//    public List<AllowedHostname> listaDeHosts(){
//        System.out.println("Acessando o banco de dados");
//        return repository.findAll();
//    }
//
//    public Long countTotalAllowedHosts(){
//        return repository.count();
//    }
//
//}
