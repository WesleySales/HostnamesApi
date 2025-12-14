package com.wesley.ValidadorHostnames.services.dtos;

import java.util.List;

public record ProbeResult(
        List<String> ips,
        long rtt
) {}