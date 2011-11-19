package com.Mp3Digger.Repository

import com.Mp3Digger.Repository.PostImports._

trait PostRepository {
	def save(question: Post): Post

	def findById(id: String): Option[Post]

	def findByPosterAndFileName(fileName: String, poster: String): Option[Post]

	def appendFilePartsToPost(postId: String, fileParts: List[PostFilePart])

  def findSomeShit(): scala.List[Post]
}