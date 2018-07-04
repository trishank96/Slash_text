package com.example.trishmuk.slashed.Controller.Models

class Channels(val name: String, val description: String, val id: String) {
    override fun toString(): String {
        return "#$name"
    }
}