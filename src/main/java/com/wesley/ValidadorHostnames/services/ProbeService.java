package com.wesley.ValidadorHostnames.services;

import com.wesley.ValidadorHostnames.services.dtos.ProbeResult;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.util.Arrays;
import java.util.List;

@Service
public class ProbeService {

    public ProbeResult probe(String hostname) {
        try {
            long start = System.currentTimeMillis();

            InetAddress[] addresses = InetAddress.getAllByName(hostname);

            List<String> ips = Arrays.stream(addresses)
                    .map(InetAddress::getHostAddress)
                    .toList();

            long rtt = System.currentTimeMillis() - start;

            return new ProbeResult(ips, rtt);

        } catch (Exception e) {
            throw new RuntimeException("Probe falhou para o hostname: " + hostname, e);
        }
    }
}
