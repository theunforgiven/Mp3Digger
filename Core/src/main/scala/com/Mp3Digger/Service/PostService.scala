package com.Mp3Digger.Service

import com.Mp3Digger.Repository.{Post, PostRepository, MongoPostRepository}

class PostService(repository: PostRepository) {
	def updatePost(post: Post): Post = {
		repository.findByPosterAndFileName(post.fileName, post.poster) match {
			case Some(existingPost: Post) => {
				repository.appendFilePartsToPost(existingPost.id.get, post.postFileParts)
				existingPost.copy(postFileParts = existingPost.postFileParts ++ post.postFileParts)
			}
			case None                     => {
				repository.save(post)
			}
		}
	}

	def savePost(post: Post): Post = {
		post.id match {
			case Some(x: String) => {
				repository.save(post)
			}
			case None            => {
				updatePost(post)
			}
		}

	}

	def savePosts(posts: List[Post]): List[Post] = {
		posts.map(savePost(_))
	}
}