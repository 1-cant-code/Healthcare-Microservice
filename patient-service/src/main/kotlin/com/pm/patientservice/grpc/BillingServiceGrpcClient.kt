package com.pm.patientservice.grpc

import billing.BillingRequest
import billing.BillingResponse
import billing.BillingServiceGrpc
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import jakarta.annotation.PreDestroy
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class BillingServiceGrpcClient(
    @Value("\${billing.service.address:localhost}") serverAddress: String,
    @Value("\${billing.service.grpc.port:9001}") serverPort: Int
) {

    private val log = LoggerFactory.getLogger(javaClass)

    private val channel: ManagedChannel =
        ManagedChannelBuilder
            .forAddress(serverAddress, serverPort)
            .usePlaintext()
            .build()

    private val blockingStub: BillingServiceGrpc.BillingServiceBlockingStub =
        BillingServiceGrpc.newBlockingStub(channel)

    init {
        log.info("Connecting to Billing Service GRPC service at {}:{}", serverAddress, serverPort)
    }

    fun createBillingAccount(patientId: String, name: String, email: String): BillingResponse {
        val request = BillingRequest.newBuilder().setPatientId(patientId).setName(name).setEmail(email)
            .build()

        val response = blockingStub.createBillingAccount(request)

        log.info("Received response from billing service via GRPC: {}", response)

        return response
    }

    @PreDestroy
    fun shutdown(){
        channel.shutdown()

    }
}
