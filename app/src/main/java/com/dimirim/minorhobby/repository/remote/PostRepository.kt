package com.dimirim.minorhobby.repository.remote

import android.util.Log
import com.dimirim.minorhobby.models.PopulatedPost
import com.dimirim.minorhobby.models.Post
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query
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
            .orderBy("created", Query.Direction.DESCENDING)
            .get().await().toObjects(Post::class.java)
    }

    suspend fun getPostsByTags(
        hobbyId: String,
        tagIds: List<String>,
        containsAll: Boolean = false
    ): List<Post> {
        return if (containsAll) {
            getPostsByHobby(hobbyId).filter { it.tags.containsAll(tagIds) }
        } else {
            posts.whereEqualTo("hobby", hobbyId).orderBy("created", Query.Direction.DESCENDING)
                .whereArrayContainsAny("tags", tagIds)
                .get().await().toObjects(Post::class.java)
        }
    }

    suspend fun getPostsByAuthor(userId: String): List<Post> {
        return posts.whereEqualTo("author", userId)
            .orderBy("created", Query.Direction.DESCENDING)
            .get().await().toObjects(Post::class.java)
    }

    suspend fun getAllPosts(): List<Post> {
        return posts.orderBy("created", Query.Direction.DESCENDING)
            .get().await().toObjects(Post::class.java)
    }

    suspend fun getPostBySearchText(hobbyId: String, searchText: String): MutableList<Post> {
        val basicPosts: MutableList<Post> = getPostsByHobby(hobbyId).toMutableList()
        val searchPosts: MutableList<Post> = mutableListOf<Post>()
        for (post in basicPosts) {
            if (post.title.contains(searchText, true)) {
                searchPosts.add(post)
            }
        }
        Log.d("test", searchPosts.toString())
        return searchPosts
    }

    suspend fun Post.addLike() {
        posts.document(this.id).update("likes", this.likes + 1).await()
        this.likes++
    }

    suspend fun Post.addView() {
        posts.document(this.id).update("views", this.likes + 1).await()
        this.views++
    }

    suspend fun Post.update(field: String, value: Any): Post {
        posts.document(this.id).update(field, value)
        return getPostById(this.id)!!
    }

    suspend fun Post.populate(): PopulatedPost {
        val author = UserRepository.getUserById(author)!!
        val hobby = HobbyRepository.getHobbyById(hobby)!!
        return PopulatedPost(
            author,
            title,
            content,
            images,
            tags,
            hobby,
            likes,
            views,
            createdToString(),
            id
        )
    }
}
