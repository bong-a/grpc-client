package com.example.grpc;

import org.springframework.stereotype.Service;

import com.levi.yoon.proto.SampleRequest;
import com.levi.yoon.proto.SampleResponse;
import com.levi.yoon.proto.SampleServiceGrpc;

import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GrpcClient {
    private static final int PORT = 3030;
    public static final String HOST = "localhost";

    // Stub : Remote procedure call을 하기 위한 가짜 객체
    // Channel : 결국 Stub도 원격 서버의 메서드를 호출한다. 그렇기 때문에 grpc-server와 통신할 채널을 정의해준다
    private final SampleServiceGrpc.SampleServiceStub asyncStub = SampleServiceGrpc.newStub(
        ManagedChannelBuilder.forAddress(HOST, PORT)
            .usePlaintext()
            .build()
    );

    public String sampleCall() {
        final SampleRequest sampleRequest = SampleRequest.newBuilder()
            .setUserId("bong_")
            .setMessage("grpc request")
            .build();

        asyncStub.sampleCall(sampleRequest, new StreamObserver<SampleResponse>() {
            @Override
            public void onNext(SampleResponse value) {
                log.info("GrpcClient#sampleCall - {}", value);
            }

            @Override
            public void onError(Throwable t) {
                log.error("GrpcClient#sampleCall - onError");
            }

            @Override
            public void onCompleted() {
                log.info("GrpcClient#sampleCall - onCompleted");
            }
        });
        return "string";
    }
}