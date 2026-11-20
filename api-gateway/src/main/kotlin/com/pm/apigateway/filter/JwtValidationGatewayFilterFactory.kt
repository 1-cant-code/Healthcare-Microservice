package com.pm.apigateway.filter

import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.server.ServerWebExchange

class JwtValidationGatewayFilterFactory(
    webClientBuilder: WebClient.Builder,
    @Value("\${auth.service.url}") authServiceUrl: String): AbstractGatewayFilterFactory<Any>() {

    private val webClient: WebClient =
        webClientBuilder.baseUrl(authServiceUrl).build()

    override fun apply(config: Any?): GatewayFilter? {
        return GatewayFilter { exchange: ServerWebExchange?, chain: GatewayFilterChain? ->
            val token =
                exchange!!.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION)
            if (token == null || !token.startsWith("Bearer ")) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED)
                return@GatewayFilter exchange.getResponse().setComplete()
            }
            this.webClient.get()
                .uri("/validate")
                .header(HttpHeaders.AUTHORIZATION, token)
                .retrieve()
                .toBodilessEntity()
                .then(chain!!.filter(exchange))
        }
    }
}
