package com.emenike.randompassword.data.repository

import com.emenike.randompassword.data.model.DirectInformation
import com.emenike.randompassword.data.model.DirectParam
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.util.InternalAPI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NetworkRepositoryImpl(
    private val httpClient: HttpClient
) : INetworkRepository {
    override suspend fun getTitle(directParam: DirectParam): Flow<DirectInformation> {
        return flow {
            val secretKey = directParam.secretKey
            val directCheck = directParam.directCheck
            val response = httpClient.post(directCheck) {
                contentType(ContentType.Application.Json)
                setBody(
                    mapOf(
                        "secret_key" to secretKey
                    )
                )
            }.body<DirectInformation>()
            emit(response)
        }
    }
}