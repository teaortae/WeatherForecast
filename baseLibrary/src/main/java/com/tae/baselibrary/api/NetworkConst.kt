package com.tae.baselibrary.api

class NetworkConst {
    companion object {
        const val CONTENT_TYPE = "Content-Type"
        const val AUTH = "Authorization"
        const val ACCEPT = "Accept"

        var applicationJson = "application/json"
        var applicationGJson = "application/vnd.github+json"
        var apiKey = "20a71221da69ad86f16e9dbc3df9227c" // git temp access token

        var SERVER_IP = ""
        var PORT = ""

        var HTTP_URL = "http://$SERVER_IP:$PORT/"
    }
}