package com.dimirim.minorhobby.repository.remote

import com.dimirim.minorhobby.models.Post
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

object PostRepository {
    private val posts = Firebase.firestore.collection("posts")

    suspend fun addPost(post: Post): DocumentReference {
        return posts.add(post).await()
    }

    suspend fun getPostById(id: String): Post? {
        return posts.document(id).get().await().toObject(Post::class.java)
    }

    suspend fun getPostsByHobby(hobbyId: String): List<Post> {
        return posts.whereEqualTo("hobby", hobbyId)
            .get().await().toObjects(Post::class.java)
    }

    suspend fun getPostsByCategory(category: String): List<Post> {
        return posts.whereEqualTo("category", category)
            .get().await().toObjects(Post::class.java)
    }

    suspend fun getPostsByAuthor(userId: String): List<Post> {
        return posts.whereEqualTo("author", userId)
            .get().await().toObjects(Post::class.java)
    }

    suspend fun getAllPosts(): List<Post> {
        return posts.get().await().toObjects(Post::class.java)
    }
}
