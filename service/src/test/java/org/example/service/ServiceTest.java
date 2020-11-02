package org.example.service;

import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import io.grpc.inprocess.InProcessChannelBuilder;
import org.example.common.proto.v1.ByeReply;
import org.example.common.proto.v1.ByeRequest;
import org.example.common.proto.v1.HelloReply;
import org.example.common.proto.v1.HelloRequest;
import org.example.common.proto.v1.GreetServiceGrpc;
import org.example.service.config.TestGrpcSecurityConfig;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.lognet.springboot.grpc.security.AuthClientInterceptor;
import org.lognet.springboot.grpc.security.AuthHeader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(properties = {"grpc.inProcessServerName=ServiceTest"})
@SpringJUnitConfig(classes = TestGrpcSecurityConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ServiceTest {

  private ManagedChannel channel;

  private GreetServiceGrpc.GreetServiceImplBase stub;

  @Test
  public void secureServiceWithAuthenticatedandAuthorizedUser(){
    AuthClientInterceptor clientInterceptor = new AuthClientInterceptor(AuthHeader.builder().basic("abhishek","".getBytes()));
    GreetServiceGrpc.GreetServiceBlockingStub stub=GreetServiceGrpc.newBlockingStub(InProcessChannelBuilder.forName("ServiceTest").usePlaintext().intercept(clientInterceptor).build());
    HelloReply reply=stub.sayHello(HelloRequest.newBuilder().setName("abhishek").build());
    assertEquals(reply.getMessage(),"Hello ==> abhishek");
  }

  @Test
  public void secureServiceWithAuthenticatedandNonAuthorizedUser(){
    AuthClientInterceptor clientInterceptor = new AuthClientInterceptor(AuthHeader.builder().basic("anuj","".getBytes()));
    GreetServiceGrpc.GreetServiceBlockingStub stub=GreetServiceGrpc.newBlockingStub(InProcessChannelBuilder.forName("ServiceTest").usePlaintext().intercept(clientInterceptor).build());
    assertThrows(StatusRuntimeException.class,()->stub.sayHello(HelloRequest.newBuilder().setName("anuj").build()));
  }

  @Test
  public void nonSecureServiceWithAuthenticatedButNonAuthorizedUser(){
    AuthClientInterceptor clientInterceptor = new AuthClientInterceptor(AuthHeader.builder().basic("anuj","".getBytes()));
    GreetServiceGrpc.GreetServiceBlockingStub stub=GreetServiceGrpc.newBlockingStub(InProcessChannelBuilder.forName("ServiceTest").usePlaintext().intercept(clientInterceptor).build());
    ByeReply reply=stub.sayBye(ByeRequest.newBuilder().setName("anuj").build());
    assertEquals(reply.getMessage(),"Bye ==> anuj");
  }

  @Test
  public void secureServiceAccessByNonAuthenticatedNonAuthorizedUser(){
    AuthClientInterceptor clientInterceptor = new AuthClientInterceptor(AuthHeader.builder().basic("aditya","".getBytes()));
    GreetServiceGrpc.GreetServiceBlockingStub stub=GreetServiceGrpc.newBlockingStub(InProcessChannelBuilder.forName("ServiceTest").usePlaintext().intercept(clientInterceptor).build());
    assertThrows(StatusRuntimeException.class,()->stub.sayHello(HelloRequest.newBuilder().setName("aditya").build()));
  }

}
