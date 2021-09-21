package com.example.grpc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class SampleController {
    private final GrpcClient grpcClient;

    @GetMapping("/")
    public Mono<String> test() {
        return Mono.just(grpcClient.sampleCall());
    }
}
