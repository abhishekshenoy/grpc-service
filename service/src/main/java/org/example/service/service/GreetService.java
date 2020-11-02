package org.example.service.service;

import io.grpc.stub.StreamObserver;
import org.example.common.proto.v1.ByeReply;
import org.example.common.proto.v1.ByeRequest;
import org.example.common.proto.v1.GreetServiceGrpc;
import org.example.common.proto.v1.HelloReply;
import org.example.common.proto.v1.HelloRequest;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.security.access.annotation.Secured;

@GRpcService
public class GreetService extends GreetServiceGrpc.GreetServiceImplBase {

  @Override
  public void sayBye(ByeRequest request, StreamObserver<ByeReply> responseObserver) {
    ByeReply reply = ByeReply.newBuilder().setMessage("Bye ==> " + request.getName()).build();
    responseObserver.onNext(reply);
    responseObserver.onCompleted();
  }

  @Override
  @Secured("ROLE_EDITOR")
  public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
    HelloReply reply = HelloReply.newBuilder().setMessage("Hello ==> " + request.getName()).build();
    responseObserver.onNext(reply);
    responseObserver.onCompleted();
  }
}
